package com.example.practice_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.practice_1.databinding.ActivityMainBinding
import com.example.practice_1.fragments.LessonOneFragment
import com.example.practice_1.fragments.LessonThreeFragment
import com.example.practice_1.fragments.LessonTwoFragment
import com.example.practice_1.services.MusicService

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var isServiceRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.myDrawer
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_closed)

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.myNavigationView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.lesson_one -> {
                    navigate(LessonOneFragment())
                }
                R.id.lesson_two -> {
                    navigate(LessonTwoFragment())
                }
                R.id.lesson_three -> {
                    navigate(LessonThreeFragment())
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


        binding.btnPlay.setOnClickListener {
            Intent(this,MusicService::class.java).also{
                startService(it)
            }
        }
        binding.btnStop.setOnClickListener {
            Intent(this,MusicService::class.java).also{
                stopService(it)
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }


    private fun navigate(fragment:Fragment):Fragment{
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flContent, fragment)
            addToBackStack(fragment::javaClass.name)
            commit()
        }
        return fragment
    }
}