package com.example.thwissa.fragment.storyfragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentStoryBinding;
import com.example.thwissa.dataclasses.StoryItem;
import com.example.thwissa.utils.Constants;
import com.example.thwissa.utils.UsefulFct;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@SuppressWarnings("ALL")
public class StoryFragment extends Fragment {
    private FragmentStoryBinding binding;
    private static final String TAG = "StoryFragment";
    private boolean isLoaded = false;
    //    StoryViewModel storyViewModel ;
    BottomNavigationView navBar;
    StoryViewModel storyViewModel;

    public StoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navBar = getActivity().findViewById(R.id.bottom_nav_view);
        navBar.setVisibility(View.GONE);
    }

    // FOR PAUSE & RESUME BUTTON
    private boolean storyPaused = false;

    // STORIES LIST
    private ArrayList<Story> storiesList = new ArrayList<>();  // LIST OF STORY OBJECTS
    private int currentStory = 0;          // INDEX OF CURRENT STORY TO DISPLAY

    // PROGRESS BAR
    private CardView currentProgressItem;    // Current progress item that contains the moving progress
    private final int storyLength = 5000;  // (ms))
    private final CountDownTimer storyTimer = new CountDownTimer(storyLength, 1) {  // STORY TIMER Length = 5s, (tick(update) each 1ms)
        /** MOVES THE PROGRESS BAR (CALLED EVERY TICK (Time unit (ms))
         * @param millisUntilFinished RESTING MILLIS */
        int progressWidth = 0;

        @Override
        public void onTick(long millisUntilFinished) {
            progressWidth = (storyLength - (int) millisUntilFinished) * currentProgressItem.getWidth() / storyLength;
            currentProgressItem.getChildAt(0).setLayoutParams(new FrameLayout.LayoutParams(progressWidth, ViewGroup.LayoutParams.MATCH_PARENT)); // Move the Progress
        }

        /** PASS TO NEXT STORY */
        @Override
        public void onFinish() {
            changeStory(1);
        }
    };


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
////        initStoriesList();
//    }

    private Story mapStoryItemToStory(StoryItem singleStory) {
        User user1 = new User(singleStory.getUsername(), singleStory.getPicture());
        Story story = new Story(
                singleStory.getPicture(),
                user1,
                singleStory.getUserlocation(),
                singleStory.getLike().size(),
                singleStory.getDislike().size(),
                singleStory.getReport(),
//                new Date(new Date().getYear(), 4, 10)
                fromStringtoDate(singleStory.getAddAT())
        );
        return story;
    }

    @SuppressLint("SimpleDateFormat")
    Date fromStringtoDate(String s) {
        String subString = s.substring(0, 19) + "Z";
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(subString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStoryBinding.inflate(inflater, container, false);   // Inflate the layout for this fragment

        String currentWilaya = getArguments().getString(Constants.WILLAYANAME);
        Log.d(TAG, "onCreateView: inside ");

        StoryViewModelFactory viewModelFactory = new StoryViewModelFactory(requireContext());
        storyViewModel = new ViewModelProvider(this, viewModelFactory).get(StoryViewModel.class);
        Log.d(TAG, "onCreateView: after ");

        /** observe status */
//        storyViewModel.updateDataforStory();


        storyViewModel.getStatus().observe(requireActivity(), status -> {
            if (status == StoryViewModel.StoryLoadingStatus.DONE) {
                Log.d(TAG, "onCreateView: inside ");
                binding.profilePic.setVisibility(View.VISIBLE);

                ArrayList<StoryItem> storiesItemList = (ArrayList<StoryItem>) storyViewModel.getProperty().getValue();
                for (int i = 0; i < storiesItemList.size(); i++) {
                    if (i < 10) {
                        Story story = mapStoryItemToStory(storiesItemList.get(i));
                        Log.d(TAG, "onCreateView: " + story.user.getProfilePicResource());
                        if (story != null) {
                            Log.d(TAG, "insdie story: " + story.user.getUserName());
                            storiesList.add(story);
                        } else {
                            Toast.makeText(requireContext(), "toast invoked", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        break;
                    }
                }

                isLoaded = true;

                /** SET BUTTONS FUNCTIONALITIES */

                setButtonsClickListeners();

                /** INITIALISE STORIES LIST (CHANGE IT LATER) */
//                initStoriesList();

                /** CHECK DATE FOR STORIES (DELETE IF STORY PASSED 7 DAYS)*/
//                checkStoriesDate();

                /** INITIALISE PROGRESS BAR ITEMS depending on the number of stories*/
                initProgressBarItems();

                /** INITIALISE FIRST STORY */
                setCurrentStory(storiesList.get(currentStory));

                /** START COUNTING FOR FIRST STORY */
                storyTimer.start();

            } else {
                binding.storyPic.setImageResource(R.drawable.network_error);
                binding.profilePic.setVisibility(View.GONE);
            }

        });

        return binding.getRoot();
    }


    private void initStoriesList() {

    }

    // STORIES METHODS
    private void initProgressBarItems() {
        for (int i = 0; i < storiesList.size(); i++) {
            CardView progressItem = (CardView) LayoutInflater.from(getContext()).inflate(R.layout.item_progress, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
            layoutParams.rightMargin = 10;
            progressItem.setBackgroundResource(R.drawable.my_progress_item_shape);

            binding.progressBar.addView(progressItem, layoutParams);
        }
    }

    /**
     * SET CURRENT STORY INFORMATIONS (from storiesList) WHEN CHANGING THE STORY(click on screen)
     * (SET STORY LAYOUT)
     *
     * @param story STORY OBJECT TO DISPLAY
     */
    public void setCurrentStory(Story story) {

        UsefulFct.INSTANCE.bindImage(binding.storyPic, story.storyPictureResource);
        UsefulFct.INSTANCE.bindImage(binding.profilePic, story.user.getProfilePicResource());
        binding.userName.setText(story.user.getUserName());
        binding.wilaya.setText(story.wilaya);
        binding.date.setText(story.dateText);
        binding.likes.setText(String.valueOf(story.likes));
        binding.dislikes.setText(String.valueOf(story.dislikes));

        // CHANGE THE CURRENT PROGRESS ITEM
        currentProgressItem = (CardView) binding.progressBar.getChildAt(currentStory);
        currentProgressItem.getChildAt(0).setVisibility(View.VISIBLE);      // PROGRESS: Visible
    }

    /**
     * @param changeIndex 1: NEXT STORY ,  -1: PREVIOUS STORY
     */
    public void changeStory(int changeIndex) {
        if (currentStory + changeIndex >= 0 && currentStory + changeIndex <= storiesList.size() - 1) {
            currentStory += changeIndex;
            currentProgressItem.getChildAt(0).setLayoutParams(new FrameLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)); // PROGRESS = 0 before changing the progress item
            currentProgressItem.getChildAt(0).setVisibility(View.INVISIBLE);   // PROGRESS INVISIBLE before changing the progress item
            setCurrentStory(storiesList.get(currentStory));
            continueStory(); //storyTimer.start(); //storyPaused = false;
        }
    }

    public void pauseStory() {
        storyTimer.cancel();
        storyPaused = true;
        binding.pauseButton.setImageResource(R.drawable.ic_baseline_play_arrow_24);
    }

    public void continueStory() {
        storyTimer.start();
        storyPaused = false;
        binding.pauseButton.setImageResource(R.drawable.ic_baseline_pause_24);
    }

    public void like(Story story) {

        if (isLoaded == true) {
            story.likes++;
            story.liked = true;
            if (story.disliked) {
                story.disliked = false;
                story.dislikes--;
                binding.dislikes.setText(String.valueOf(story.dislikes));
            }
//            if (storyViewModel != null) {
            addLike("Adrar", "62a638c378af3fb061bb9969", story.likes);

            binding.likes.setText(String.valueOf(story.likes));
//            }
        } else {
            Toast.makeText(requireContext(), "you can't like image before it is not loaded ", Toast.LENGTH_SHORT).show();
        }
    }

    public void dislike(Story story) {

        if (isLoaded) {
            story.dislikes++;
            story.disliked = true;
            if (story.liked) {
                story.liked = false;
                story.likes--;
                binding.likes.setText(String.valueOf(story.likes));
            }
            addDislike(
                    "Adrar",
                    "62a638c378af3fb061bb9969",
                    story.dislikes
            );

            binding.dislikes.setText(String.valueOf(story.dislikes));
        } else {
            Toast.makeText(requireContext(), "you can't like image before it is not loaded ", Toast.LENGTH_SHORT).show();
        }
    }

    public void shareStory() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_SUBJECT, "TA7WISSA | Share Story: ");

        if (intent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivity(intent);
        startActivity(intent);
    }

    public void reportStory(Story story) {
        if (isLoaded) {
            if (!story.reported) {
                story.numberReports++;
                story.reported = true;

                addReport(
                        "Adrar",
                        "62a6a272b062ab05e9ac50ba"
                );

                Toast.makeText(getContext(), "This story is reported " + story.numberReports + "time", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "you are reported this story", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "you can't like image before it is not loaded ", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkStoriesDate() {
        Date currentDate = new Date();
        final int msInSevenDays = 604800000;  // (1000 * 60 * 60 * 24) * 7
        long diffMS;
        for (int i = storiesList.size() - 1; i >= 0; i--) {
            diffMS = currentDate.getTime() - storiesList.get(i).postingDate.getTime();      // DIFFERENCE IN MS BETWEEN CURRENT TIME & THE TIME WHEN THE STORY WAS CREATED
            if (diffMS > msInSevenDays) {
                storiesList.remove(i);
            }
        }
    }

    private void setButtonsClickListeners() {
        /** SHARE BUTTON */
        binding.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareStory();
            }
        });
        /** REPORT BUTTON */
        binding.reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!storiesList.get(currentStory).reported) {
                    reportStory(storiesList.get(currentStory));
                }
            }
        });
        /** LIKE BUTTON */
        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!storiesList.get(currentStory).liked)
                    like(storiesList.get(currentStory));
            }
        });


        /** DISLIKE BUTTON */
        binding.dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!storiesList.get(currentStory).disliked)
                    dislike(storiesList.get(currentStory));
            }
        });
        /** PAUSE & RESUME BUTTON */
        binding.pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // RESUME
                if (storyPaused)
                    continueStory();
                    // PAUSE
                else
                    pauseStory();
            }
        });
        /** NEXT STORY CLICK */
        binding.nextStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStory < storiesList.size() - 1)
                    changeStory(1);
            }
        });
        /** PREVIOUS STORY CLICK */
        binding.previousStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentStory > 0)
                    changeStory(-1);
            }
        });
    }

    private void addLike(String willaya, String id, int likes) {
        if (isLoaded) {
            storyViewModel.addLike(willaya, id, likes);
        }
    }

    private void addDislike(String willaya, String id, int likes) {
        if (isLoaded) {
            storyViewModel.addDislike(willaya, id, likes);
        }
    }

    private void addReport(String willaya, String id) {
        if (isLoaded) {
            storyViewModel.addReport(willaya, id);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navBar.setVisibility(View.VISIBLE);
                navController.popBackStack();
            }
        });
    }
}