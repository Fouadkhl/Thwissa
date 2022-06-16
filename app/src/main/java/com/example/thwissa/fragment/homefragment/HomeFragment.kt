package com.example.thwissa.fragment.homefragment

import ImageViewModel
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.mybottomsheet.ModalBottomSheet
import com.example.thwissa.Adapter.PlacesAdapter2
import com.example.thwissa.Adapter.StoriesAdapter
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.FragmentHomeBinding
import com.example.thwissa.dataclasses.Willaya
import com.example.thwissa.dataclasses.WillayaStory
import com.example.thwissa.fragment.homefragment.willaya.Place
import com.example.thwissa.fragment.homefragment.willaya.Place.Tip
import com.example.thwissa.fragment.homefragment.willaya.PlaceAdapter
import com.example.thwissa.fragment.homefragment.willaya.TopRatedAdapter
import com.example.thwissa.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.text.DecimalFormat
import java.text.NumberFormat


@Suppress("DEPRECATION")
private const val TAG = "HomeFragment"

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

        binding.searchBar.btnFilter.setOnClickListener {
            val drawerLayout =
                requireActivity().findViewById<DrawerLayout>(R.id.filter_drawer_layout)
            drawerLayout.openDrawer(GravityCompat.START, true)
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

        initPlacesList("RATING", 15)
        initPlacesList("POPULARITY", 40)

        var id = resources.getIdentifier("saharaa", "drawable", activity?.packageName)
        listofItmes[0] = id
        id = resources.getIdentifier("sahili", "drawable", activity?.packageName)
        listofItmes[1] = id
        id = resources.getIdentifier("tell", "drawable", activity?.packageName)
        listofItmes[2] = id


        var data = ArrayList<WillayaStory>()

        var item = WillayaStory(listofItmes[0], "Desert")
        data.add(item)

        item = WillayaStory(listofItmes[1], "Coastal")
        data.add(item)

        item = WillayaStory(listofItmes[2], "Hills")
        data.add(item)


        var data2 = homeviewmodel.getWillayaStories()

        val storiesAdapter = StoriesAdapter(data, homeviewmodel, StoriesAdapter.OnClickListener {
            val bundle = bundleOf(Constants.WILLAYANAME to it.text)
            findNavController().navigate(R.id.action_homeFragment_to_storyFragment, bundle)
        })

        // TODO: change this
        val placesAdapter = PlacesAdapter2(data)
//        val nearToYouRecyclerViewAdapter = NearToYouRecyclerViewAdapter(data)

        binding.apply {
            rvStories.adapter = storiesAdapter
            rvStories.layoutManager?.smoothScrollToPosition(binding.rvStories, null, 0)
            rvRegions.adapter = placesAdapter
//            rvRecommendedPlaces.adapter = placesAdapter
//            rvTopsitesNeartoyou.adapter = nearToYouRecyclerViewAdapter
        }
    }


    private fun initPlacesList(sort: String, limit: Int) {
        // Fields: name,location,categories,rating,photos
        val fields =
            "fsq_id%2Cname%2Clocation%2Ccategories%2Cdistance%2Cdescription%2Ctel%2Cwebsite%2Crating%2Cstats%2Cpopularity%2Cprice%2Cphotos%2Ctips%2Cgeocodes"
        val url =
            "https://api.foursquare.com/v3/places/search?fields=" + fields + "&near=algeria&sort=" + sort + "&limit=" + limit
        val placesList = ArrayList<Place>()
        val numberFormat: NumberFormat = DecimalFormat("#0.0")
        val requestQueue = Volley.newRequestQueue(context)
        val stringRequest: StringRequest = object : StringRequest(
            Method.GET, url,
            Response.Listener { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val resultArray = jsonResponse.getJSONArray("results")
                    if (resultArray.length() == 0) // case: empty list
                        return@Listener
                    for (i in 0 until resultArray.length()) {
                        val placeJsonObject = resultArray.getJSONObject(i)
                        val currentPlace = Place()
                        // Place Fsq Id
                        // Place Fsq Id
                        currentPlace.fsqId = placeJsonObject.getString("fsq_id")
                        // Place Name
                        // Place Name
                        currentPlace.placeName = placeJsonObject.getString("name")
                        // Place Address (Location)
                        // Place Address (Location)
                        try {
                            currentPlace.placeAddress = placeJsonObject.getJSONObject("location")
                                .getString("formatted_address")
                        } catch (e: JSONException) {
                            currentPlace.placeAddress = "algeria"
                        }
                        // Category
                        // Category
                        currentPlace.placeCategory =
                            placeJsonObject.getJSONArray("categories").getJSONObject(0)
                                .getString("name")
                        // Distance
                        // Distance
                        currentPlace.placeDistance = placeJsonObject.getInt("distance")
                        // Description
                        // Description
                        try {
                            currentPlace.placeDescription = placeJsonObject.getString("description")
                        } catch (e: JSONException) {
                            currentPlace.placeDescription = "No Description"
                        }
                        // Phone Number
                        // Phone Number
                        try {
                            currentPlace.placePhoneNumber = placeJsonObject.getString("tel")
                        } catch (e: JSONException) {
                            currentPlace.placePhoneNumber = "No Phone Number"
                        }
                        // Website
                        // Website
                        try {
                            currentPlace.placeWebsite = placeJsonObject.getString("website")
                        } catch (e: JSONException) {
                            currentPlace.placeWebsite = "No Website"
                        }
                        // Rating
                        // Rating
                        try {
                            currentPlace.placeRate =
                                numberFormat.format(placeJsonObject.getDouble("rating") / 2)
                                    .toDouble()
                        } catch (e: JSONException) {
                            currentPlace.placeRate = 0.0
                        }
                        // Stats
                        // Stats
                        try {
                            val statJsonObject = placeJsonObject.getJSONObject("stats")
                            currentPlace.placeStats = Place.Stats(
                                statJsonObject.getInt("total_photos"),
                                statJsonObject.getInt("total_ratings"),
                                statJsonObject.getInt("total_tips")
                            )
                        } catch (e: JSONException) {
                            currentPlace.placeStats = Place.Stats(0, 0, 0)
                        }
                        // Popularity
                        // Popularity
                        currentPlace.placePopularity =
                            numberFormat.format(10 * placeJsonObject.getDouble("popularity"))
                                .toDouble()
                        // Price
                        // Price
                        try {
                            when (placeJsonObject.getInt("price")) {
                                1 -> currentPlace.placePrice = "Cheap"
                                2 -> currentPlace.placePrice = "Moderate"
                                3 -> currentPlace.placePrice = "Expensive"
                                4 -> currentPlace.placePrice = "Very Expensive"
                            }
                        } catch (e: JSONException) {
                            currentPlace.placePrice = "No Price"
                        }
                        // Pictures
                        // Pictures
                        try {
                            val picturesJsonArray = placeJsonObject.getJSONArray("photos")
                            for (j in 0 until picturesJsonArray.length()) {
                                val pictureJsonObject = picturesJsonArray.getJSONObject(j)
                                val pictureUrl =
                                    pictureJsonObject.getString("prefix") + pictureJsonObject.getString(
                                        "width"
                                    ) + "x" + pictureJsonObject.getString("height") + pictureJsonObject.getString(
                                        "suffix"
                                    )
                                currentPlace.placeImagesUrls.add(pictureUrl)
                            }
                        } catch (ignored: JSONException) {
                        }
                        // Tips
                        // Tips
                        try {
                            val tipsJsonArray = placeJsonObject.getJSONArray("tips")
                            for (j in 0 until tipsJsonArray.length()) {
                                val tipJsonObject = tipsJsonArray.getJSONObject(j)
                                currentPlace.placeTips.add(
                                    Tip(
                                        tipJsonObject.getString("text"),
                                        tipJsonObject.getString("created_at")
                                    )
                                )
                            }
                        } catch (ignored: JSONException) {
                        }
                        // GEOCODES (latitude, longitude)
                        // GEOCODES (latitude, longitude)
                        currentPlace.latitude =
                            placeJsonObject.getJSONObject("geocodes").getJSONObject("main")
                                .getDouble("latitude")
                        currentPlace.longitude =
                            placeJsonObject.getJSONObject("geocodes").getJSONObject("main")
                                .getDouble("longitude")
                        placesList.add(currentPlace)
                    }
                } catch (ignored: JSONException) {
                }

                /** INITIALISE  */
                if (sort.equals("RATING"))
                    setRecommendedPlaces(placesList)
                else
                    setTopSites(placesList)
            },
            Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val appId = "fsq3+HescpVWi499F8Qtda+huTh/ANyfkuVk3imyuhX9lbM="
                val params: MutableMap<String, String> = HashMap()
                params["Accept"] = "application/json"
                params["Authorization"] = appId
                return params
            }
        }
        requestQueue.add(stringRequest)
    }


    private fun setTopSites(placesList: ArrayList<Place>) {
        val mPlacesAdapter = PlaceAdapter(context, placesList)

        mPlacesAdapter.setOnPlaceClickedListener { place ->
            val bundle = Bundle()
            bundle.putSerializable("place_object", place)
            findNavController().navigate(R.id.action_homeFragment_to_overview, bundle)
        }
        binding.rvTopsitesNeartoyou.setAdapter(mPlacesAdapter)
    }

    private fun setRecommendedPlaces(currentTopRatedList: java.util.ArrayList<Place>) {
        val mTopRatedAdapter = TopRatedAdapter(context, currentTopRatedList)
        mTopRatedAdapter.setOnPlaceClickedListener { place ->
            val bundle = Bundle()
            bundle.putSerializable("place_object", place)
            findNavController().navigate(R.id.action_homeFragment_to_overview, bundle)
        }
        binding.rvRecommendedPlaces.setAdapter(mTopRatedAdapter)
    }




}