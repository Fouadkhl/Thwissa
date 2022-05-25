package com.example.thwissa.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.thwissa.R
import com.example.thwissa.databinding.ProfileScreenBinding
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {

    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient

    lateinit var binding: ProfileScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_screen, container, false)
        setUpUiForProfile()

        binding.apply {
            settings.ivNextMyTrip.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_profileFragment_to_settingsFragment)
            }

            myTrip.ivNextMyTrip.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_profileFragment_to_editProfile)
            }
            /** FAVORITE NAVIGATION */
            favorite.ivNextMyTrip.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_profileFragment_to_favoritesFragment)
            }

        }

        if (isLoggedInWithGoogle()){
            loadLogInWithGoogle()
        }

        loadLogInWithFacebook()
        if (isLoggedInWithFacebook()){
        }

        binding.logOut.ivNextMyTrip.setOnClickListener {
            signOut()
        }

        return binding.root
    }

    private fun loadLogInWithFacebook() {
        val accessToken = AccessToken.getCurrentAccessToken()
        val request = GraphRequest.newMeRequest(accessToken) { `object`, response ->
            val name = `object`?.getString("name")
            // TODO: location photo url and picture does not work
            val facebookLink = `object`?.getString("link")
//            val location = `object`?.getJSONArray("location")?.get(1)
//            val photoUrl = `object`?.getJSONObject("picture")?.getJSONObject("data")?.getString("url")
            binding.tvEmail.text = facebookLink
            binding.tvName.text = name
//            binding.tvPlacee.text = location.toString()
//            Glide
//                .with(requireContext())
//                .asBitmap()
//                .override(1600, 1600)
//                .load(photoUrl)
//                .into(binding.ivProfile)

            // Application code
        }
        val parameters = Bundle()
        parameters.putString("fields", "id,name,link")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun loadLogInWithGoogle() {
        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(requireActivity(), gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())

        if (account != null) {
            binding.tvEmail.text = account.email
            binding.tvName.text = account.displayName
            Glide
                .with(requireContext())
                .asBitmap()
                .override(1600, 1600)
                .load(account.photoUrl)
                .into(binding.ivProfile)
        }
    }

    private fun signOut() {
        if (isLoggedInWithGoogle()){
            gsc.signOut().addOnCompleteListener {
                nagivateToHomeScreen()
            }
        }

        if (isLoggedInWithFacebook()){
            LoginManager.getInstance().logOut()
        }
        //if is loggedin in our server
    }

    private fun nagivateToHomeScreen() {
        findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
    }

    private fun isLoggedInWithGoogle() = GoogleSignIn.getLastSignedInAccount(requireActivity()) != null

    private fun isLoggedInWithFacebook()= AccessToken.getCurrentAccessToken() != null


    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setUpUiForProfile() {
        binding.apply {
            myTrip.apply {
                colorBack = resources.getColor(R.color.colorback1)
                photo = resources.getDrawable(R.drawable.ic_person_icon)
            }
            settings.apply {
                colorBack = resources.getColor(R.color.colorback2)
                photo = resources.getDrawable(R.drawable.ic_baseline_settings_24)

            }
            contactUs.apply {
                colorBack = resources.getColor(R.color.colorback4)
                photo = resources.getDrawable(R.drawable.ic_baseline_phone_forwarded_24)
            }
            feedBack.apply {
                colorBack = resources.getColor(R.color.colorback5)
                photo = resources.getDrawable(R.drawable.ic_baseline_feedback_24)
            }
            favorite.apply {
                colorBack = resources.getColor(R.color.colorback3)
                photo = resources.getDrawable(R.drawable.ic_baseline_bookmark_24)
            }
            logOut.apply {
                colorBack = resources.getColor(R.color.colorback6)
                photo = resources.getDrawable(R.drawable.ic_baseline_logout_24)
            }
        }
    }

}
