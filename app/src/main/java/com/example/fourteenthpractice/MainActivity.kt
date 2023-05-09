package com.example.fourteenthpractice

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.fourteenthpractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.contactsFragment -> {
                navController.navigate(R.id.contactsFragment)
                true
            }
            R.id.xmlFragment -> {
                navController.navigate(R.id.xmlFragment)
                true
            }
            R.id.jsonFragment -> {
                navController.navigate(R.id.jsonFragment)
                true
            }
            R.id.myProviderFragment -> {
                navController.navigate(R.id.myProviderFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}