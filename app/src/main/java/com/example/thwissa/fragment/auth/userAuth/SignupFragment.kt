package com.example.thwissa.fragment.auth.userAuth

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.thwissa.LogService
import com.example.thwissa.R
import com.example.thwissa.databinding.TouristSignUpBinding
import com.example.thwissa.dataclasses.AgencyRes
import com.example.thwissa.dataclasses.UserRes
import com.example.thwissa.fragment.auth.validation.BaseValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.ConfirmPasswordValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmailValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.EmptyValidator
import com.example.thwissa.fragment.auth.validation.controlValidators.PasswordValidator
import com.example.thwissa.repository.userLocalStore.SPUserData
import com.example.thwissa.utils.Constants
import com.example.thwissa.utils.Constants.USER_ROLE
import com.example.thwissa.utils.MyApp
import com.example.thwissa.utils.createPartFromString
import com.example.thwissa.utils.getFilePart
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


private const val TAG = "SignupFragment"

@Suppress("DEPRECATION")
class SignupFragment : Fragment() {

    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    lateinit var callbackManager: CallbackManager

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var binding: TouristSignUpBinding
    var picturePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TouristSignUpBinding.inflate(inflater, container, false)

        sharedPreferences = MyApp.getContext()
            .getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // getDataAndSignUpUser()

        var resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes

                    val data: Intent? = result.data
                    data?.data?.let { it ->
                        context?.contentResolver?.query(it, null, null, null, null)?.use {
                            if (it.moveToFirst()) {
                                picturePath = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DATA))
                            }
                        }
                        //then we set the image in the image view
                        binding.ivShapeableSignUpUser.setImageURI(it)

                    }
                }
            }

        binding.ivShapeableSignUpUser.setOnClickListener {
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
                resultLauncher.launch(it)
            }
        }

        binding.btnSignUp.setOnClickListener {
            if (dataIsControlled()) {
                signUpUser()
            }
        }


        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(requireActivity(), gso)

        binding.btnSignupWithGoogle.setOnClickListener {
            signUpWithGoogle()
        }

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Toast.makeText(requireContext(), "success reslut", Toast.LENGTH_SHORT).show()
                    navigateToProfileFragment()
                }

                override fun onCancel() {
                    // App code
                }

                override fun onError(error: FacebookException) {
                    Toast.makeText(
                        requireContext(),
                        "an error occurred please check your facebook again",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })

        binding.signupFacebook.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(requireActivity(), listOf("public_profile"))
        }

        // back button
        val navController = Navigation.findNavController(view)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.popBackStack()
            }
        })

    }


    /**
     * get the data from xml control it and pass it to register
     */
    private fun dataIsControlled(): Boolean {
        // get the data from xml
        var name = binding.etName.text.toString()
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()
        var confirmPassword = binding.etPassword.text.toString()
        var location = binding.etLocation.text.toString()
        var gender = if (binding.rbtnMale.isChecked) "male" else "female"

        val usernameEmptyValidation = EmptyValidator(name).validate()
        binding.etName.error =
            if (!usernameEmptyValidation.isSuccess) getString(usernameEmptyValidation.message) else null
        val locationEmptyValidation = EmptyValidator(location).validate()
        binding.etLocation.error =
            if (!usernameEmptyValidation.isSuccess) getString(locationEmptyValidation.message) else null


        val emailValidations = BaseValidator.validate(
            EmptyValidator(email), EmailValidator(email)
        )
        binding.etEmail.error =
            if (!emailValidations.isSuccess) getString(emailValidations.message) else null


        val passwordValidations = BaseValidator.validate(
            EmptyValidator(password), PasswordValidator(password)
        )
        binding.etPassword.error =
            if (!passwordValidations.isSuccess) getString(passwordValidations.message) else null

        val confirmPasswordValidations = BaseValidator.validate(
            EmptyValidator(confirmPassword), ConfirmPasswordValidator(confirmPassword, password)
        )
        binding.etConfirmPassword.error =
            if (!confirmPasswordValidations.isSuccess) getString(passwordValidations.message) else null

        return (usernameEmptyValidation.isSuccess &&
                emailValidations.isSuccess &&
                confirmPasswordValidations.isSuccess)

        // TODO: get the image from the internal storage and pass it and return request object from this fun
        // TODO: set setUserloggedin to true

    }

     private  fun signUpUser() {
        val userinfo = HashMap<String, RequestBody>()
        userinfo.put("name", createPartFromString(binding.etName.text.toString()))
        userinfo.put("email", createPartFromString(binding.etEmail.text.toString()))
        userinfo.put("password", createPartFromString(binding.etPassword.text.toString()))
        userinfo.put("location", createPartFromString(binding.etLocation.text.toString()))
        userinfo.put("confirmepassword", createPartFromString(binding.etConfirmPassword.text.toString())        )
//            var gender = binding.radioGroup.checkedRadioButtonId // 0 for male and 1 for female
//            userinfo.put("gender", gender.toString())


        val filepart = if (picturePath != null) getFilePart((File(picturePath)) , "photo") else null
        val spUserData = SPUserData(MyApp.getContext())
        LogService.retrofitService.executeSignUp(userinfo, filepart)
            .enqueue(object : retrofit2.Callback<UserRes> {
                override fun onResponse(call: Call<UserRes>, response: Response<UserRes>) {
                    /**handle the sign up  */
//                        boolearn / id

                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@SignupFragment.context,
                            "sign up successefuly",
                            Toast.LENGTH_SHORT
                        ).show()

                        val res = response.body()
                        storeUsertoSharedPreferences(res!!)
                        setUserLoggedIn(true)
                        // TODO: save data in external storage and set the user loggedin


                        Log.d(TAG, "sign up success :" + response.body()?.name +
                                "is saved "+ spUserData.getUserLoggedIn() +
                                "data preferences" +spUserData.getLoggedInUser().name)
                        //redirect the to his profile
                        val bundle = bundleOf(
                            USER_ROLE to 1,
                            Constants.USER_ID to res.id
                        )
                        findNavController().navigate(
                            R.id.action_signupFragment_to_codeValidationFragment,
                            bundle
                        )
//                        findNavController().popBackStack()


                    } else {
                        Toast.makeText(
                            this@SignupFragment.context,
                            "error in response ${response.message()}, ${response.code()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UserRes>, t: Throwable) {
//                    Toast.makeText(
//                        this@SignupFragment.context,
//                        "sign up fail ${t.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
                    Log.i("signupuser", "onFailure:${t.message} ")
                }
            }
            )

    }

    fun signUpWithGoogle() {
        val signinintent = gsc.signInIntent
        startActivityForResult(signinintent, 1000)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000 && resultCode != Activity.RESULT_CANCELED) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                navigateToProfileFragment()
            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "something want wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun navigateToProfileFragment() {
        findNavController().navigate(R.id.action_signupFragment_to_profileFragment)
    }

    fun storeUsertoSharedPreferences(user: UserRes) {
        val spEditor = sharedPreferences.edit()
        spEditor.apply {
            putString("name", user.name)
            putString("email", user.email)
            putString("userid", user.id)
            putString("userLocation", user.location)
            putString("userPicture", user.picture)
            putString("userRole", "User")
//            putBoolean("isvalidate" , user.isvalidate)
        }
        spEditor.apply()
    }

    fun setUserLoggedIn(loggedin: Boolean) {
        val spEditor = sharedPreferences.edit()
        spEditor.putBoolean("loggedIn", loggedin)
        spEditor.apply()
    }





}