package com.app.githubsearch.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.app.githubsearch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navHostFragment: NavHostFragment
        get() = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindDestinationChangedListener()
    }

    private fun bindDestinationChangedListener() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            title = destination.label
        }
    }
}