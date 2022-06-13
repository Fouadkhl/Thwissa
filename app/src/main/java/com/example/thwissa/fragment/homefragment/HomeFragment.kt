package com.example.thwissa.fragment.homefragment

import ImageViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mybottomsheet.ModalBottomSheet
import com.example.thwissa.Adapter.NearToYouRecyclerViewAdapter
import com.example.thwissa.Adapter.PlacesAdapter
import com.example.thwissa.Adapter.StoriesAdapter
import com.example.thwissa.R
import com.example.thwissa.databinding.FragmentHomeBinding
import com.example.thwissa.dataclasses.WillayaStory
import com.example.thwissa.utils.Constants
import kotlinx.coroutines.flow.collectLatest


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: ImageViewModel by viewModels()
    val homeviewmodel: ModelHomeFragment by viewModels()
    lateinit var gallery: ArrayList<Uri>
//    lateinit var storiesAdapter : StoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // list that hold the gallery

        gallery = ArrayList<Uri>()
        setupui()

        // TODO: remove this
        binding.btn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_agencyProfileFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //request the read and show the gallery
        requestRead()
        homeviewmodel.canLoad.observe(requireActivity(), Observer {
            if (it == true) {
                val modalBottomSheet = ModalBottomSheet(gallery)
                modalBottomSheet.show(
                    requireActivity().supportFragmentManager,
                    ModalBottomSheet.TAG
                )
                homeviewmodel.setCanNotLoad()
            }
        })

        binding.searchBar.btnFilter.setOnClickListener {

        }

        // TODO: change the button to story click
//        binding.rlToBtnAddStory.setOnClickListener{
//            val modalBottomSheet = ModalBottomSheet(listofItmes)
//            modalBottomSheet.show(requireActivity().supportFragmentManager, ModalBottomSheet.TAG)
//        }

    }


    private fun setupCollecting() {
        lifecycleScope.launchWhenStarted {
            viewModel.allImagesFromGallery.collectLatest {
                // simple test, take first image and load it to ImageView using Glide
                if (it.isNotEmpty()) {
                    gallery = it as ArrayList<Uri>
                }
            }
        }
    }

    /**
     * permission code
     */
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1

    /**
     * requestPermissions and do something
     *
     */
    fun requestRead() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
        } else {
            readFile()
            Toast.makeText(
                requireContext(),
                "the external storage is not granted",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    fun readFile() {
        // do something
        viewModel.loadAllImages()
        setupCollecting()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readFile()
            } else {
                // Permission Denied
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun setupui() {
        val listofItmes = IntArray(3)

        var id = resources.getIdentifier("saharaa", "drawable", activity?.packageName)
        listofItmes[0] = id
        id = resources.getIdentifier("sahili", "drawable", activity?.packageName)
        listofItmes[1] = id
        id = resources.getIdentifier("tell", "drawable", activity?.packageName)
        listofItmes[2] = id


        var data = ArrayList<WillayaStory>()

        var item = WillayaStory(listofItmes[0], "medea")
        data.add(item)

        item = WillayaStory(listofItmes[1], "medea")
        data.add(item)

        item = WillayaStory(listofItmes[2], "medea")
        data.add(item)


        var data2 = homeviewmodel.getWillayaStories()

        val storiesAdapter = StoriesAdapter(data, homeviewmodel, StoriesAdapter.OnClickListener {
            val bundle = bundleOf(Constants.WILLAYANAME to it.text)
            findNavController().navigate(R.id.action_homeFragment_to_storyFragment, bundle)
        })

        // TODO: change this
        val placesAdapter = PlacesAdapter(data)
        val nearToYouRecyclerViewAdapter = NearToYouRecyclerViewAdapter(data)

        binding.apply {
            rvStories.adapter = storiesAdapter
            rvStories.layoutManager?.smoothScrollToPosition(binding.rvStories, null, 0)
            rvRegions.adapter = placesAdapter
            rvRecommendedPlaces.adapter = placesAdapter
            rvTopsitesNeartoyou.adapter = nearToYouRecyclerViewAdapter
        }
    }

}