package com.example.thwissa.fragment.storyfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.thwissa.R;
import com.example.thwissa.databinding.FragmentStoryBinding;

import java.util.ArrayList;
import java.util.Date;


public class StoryFragment extends Fragment {
    private FragmentStoryBinding binding;
    public StoryFragment() { }

    // FOR PAUSE & RESUME BUTTON
    private boolean storyPaused = false;

    // STORIES LIST
    private ArrayList<Story> storiesList;  // LIST OF STORY OBJECTS
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentStoryBinding.inflate(inflater, container, false);   // Inflate the layout for this fragment

        /** SET BUTTONS FUNCTIONALITIES */
        setButtonsClickListeners();

        /** INITIALISE STORIES LIST (CHANGE IT LATER) */
        initStoriesList();

        /** CHECK DATE FOR STORIES (DELETE IF STORY PASSED 7 DAYS)*/
        checkStoriesDate();

        /** INITIALISE PROGRESS BAR ITEMS depending on the number of stories*/
        initProgressBarItems();

        /** INITIALISE FIRST STORY */
        setCurrentStory(storiesList.get(currentStory));

        /** START COUNTING FOR FIRST STORY */
        storyTimer.start();

        return binding.getRoot();
    }


    private void initStoriesList(){
        // CURRENT WILAYA
        String currentWilaya = "Algiers";
        // USERS
        User user1 = new User("Yusuf Belkhiri", R.drawable._4);
        User user2 = new User("Khelil Fouad", R.drawable._4);
        User user3 = new User("Fellah Abdelnour", R.drawable._4);
        User user4 = new User("Kuider", R.drawable._4);
        // STORIES LIST
        storiesList = new ArrayList<>();
        storiesList.add(new Story(R.drawable.beach, user1, currentWilaya, 35, 3, 0, new Date(new Date().getYear(), 4, 10)));  // new Date() GIVES CURRENT DAY
        storiesList.add(new Story(R.drawable.beach, user1, currentWilaya, 30, 5, 0, new Date()));
        storiesList.add(new Story(R.drawable._3, user2, currentWilaya, 40, 5, 0, new Date()));
        storiesList.add(new Story(R.drawable.beach, user3, currentWilaya, 17, 3, 0, new Date()));
        storiesList.add(new Story(R.drawable.beach, user4, currentWilaya, 20, 4, 0, new Date(new Date().getYear(), 4, 2)));
        storiesList.add(new Story(R.drawable._3, user1, currentWilaya, 10, 0, 0, new Date()));
    }

    // STORIES METHODS
    private void initProgressBarItems(){
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
     * @param story STORY OBJECT TO DISPLAY
     */
    public void setCurrentStory(Story story) {
        binding.storyPic.setImageResource(story.storyPictureResource);
        binding.profilePic.setImageResource(story.user.getProfilePicResource());
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

    public void like(Story story){
        story.likes++;
        binding.likes.setText(String.valueOf(story.likes));

        story.liked = true;
        if(story.disliked){
            story.disliked = false;
            story.dislikes --;
            binding.dislikes.setText(String.valueOf(story.dislikes));
        }
    }

    public void dislike(Story story){
        story.dislikes++;
        binding.dislikes.setText(String.valueOf(story.dislikes));

        story.disliked = true;
        if(story.liked){
            story.liked = false;
            story.likes --;
            binding.likes.setText(String.valueOf(story.likes));
        }
    }

    public void shareStory(){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_SUBJECT, "TA7WISSA | Share Story: ");

        if (intent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivity(intent);
        startActivity(intent);
    }

    public void reportStory(){
        storiesList.get(currentStory).numberReports ++;
        Toast.makeText(getContext(), "Report  " + storiesList.get(currentStory).numberReports, Toast.LENGTH_SHORT).show();
    }


    public void checkStoriesDate(){
        Date currentDate = new Date();
        final int  msInSevenDays = 604800000;  // (1000 * 60 * 60 * 24) * 7
        long diffMS;
        for (int i = storiesList.size() - 1; i >= 0 ; i--) {
            diffMS = currentDate.getTime() - storiesList.get(i).postingDate.getTime();      // DIFFERENCE IN MS BETWEEN CURRENT TIME & THE TIME WHEN THE STORY WAS CREATED
            if(diffMS > msInSevenDays){
                storiesList.remove(i);
            }
        }
    }

    private void setButtonsClickListeners(){
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
                reportStory();
            }
        });
        /** LIKE BUTTON */
        binding.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!storiesList.get(currentStory).liked)
                    like(storiesList.get(currentStory));
            }
        });
        /** DISLIKE BUTTON */
        binding.dislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!storiesList.get(currentStory).disliked)
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
}