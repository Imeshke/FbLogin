package com.imeshke.fblogin.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.imeshke.fblogin.R
import com.imeshke.fblogin.api.model.Hotel
import com.imeshke.fblogin.databinding.ActivityMainBinding
import com.imeshke.fblogin.ui.main.HotelsViewModel
import com.imeshke.fblogin.ui.main.structure.HotelListAdapter
import com.imeshke.fblogin.utils.startDetailActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONException


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), HotelListAdapter.OnClickEvent {

    private lateinit var callbackManager: CallbackManager
    private lateinit var loginButton: LoginButton
    private lateinit var viewModel: HotelsViewModel
    private var hotelListAdapter: HotelListAdapter? = null
    private var notifyUIJob: Job? = null
    private lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //init
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(HotelsViewModel::class.java)
        binding.viewmodel = viewModel

        val recyclerView: RecyclerView = binding.hotelsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        hotelListAdapter = HotelListAdapter(this)
        progressbar = findViewById<ProgressBar>(R.id.progressbar)
        binding.hotelsRecyclerView.adapter = hotelListAdapter

        loginButton = findViewById(R.id.login_button)
        callbackManager = CallbackManager.Factory.create()
        loginButton.setPermissions(listOf("email", "user_birthday"))
        //login callback
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                val bundle = Bundle()
                bundle.putString("fields", "id, email, first_name, last_name, gender,age_range")

                //Graph API to access the data of user's facebook account
                val request = GraphRequest.newMeRequest(
                    result?.accessToken
                ) { fbObject, response ->
                    Log.v("Login Success", response.toString())

                    //For safety measure enclose the request with try and catch
                    try {
                        val firstName = fbObject?.getString("first_name")
                        val lastName = fbObject?.getString("last_name")
                        binding.textViewEmail.text = fbObject?.getString("email")
                        binding.textViewName.text = "$firstName $lastName"

                    } //If no data has been retrieve throw some error
                    catch (e: JSONException) {
                        Log.d(TAG, e.localizedMessage)
                    }
                }
                request.setParameters(bundle)
                request.executeAsync()

                updateHotels()
            }

            override fun onCancel() {
                Log.d(TAG, "onCancel: called")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "onError: called")
            }
        })
    }

    fun updateHotels() {
        progressbar!!.visibility = View.VISIBLE
        notifyUIJob = lifecycleScope.launch {
            viewModel?.getHotels()?.asFlow()?.collectLatest {
                hotelListAdapter?.setHotelList(it)
                progressbar!!.visibility = View.GONE
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

    }

    override fun onClickListner(hotel: Hotel) {
        startDetailActivity(hotel)
    }


    override fun onStop() {
        notifyUIJob?.cancel()
        super.onStop()
    }
}


