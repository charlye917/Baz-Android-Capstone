package com.carteagal.baz_android.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.carteagal.baz_android.R
import com.carteagal.baz_android.data.remote.network.CheckInternetConnection
import com.carteagal.baz_android.databinding.ActivityMainBinding
import com.carteagal.baz_android.presentation.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var checkNetworkConnection: CheckInternetConnection
    private val cryptoViewModel: CryptoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenSplash = installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController)
        screenSplash.setKeepOnScreenCondition{ false }
    }

    private fun callNetworkConnection(){
        checkNetworkConnection = CheckInternetConnection(application)
        checkNetworkConnection.observe(this) {
            if (it) {
                Log.d("__tag func", "entro true")
            }else{
                Log.d("__tag func", "entro false")
            }
        }
    }
}