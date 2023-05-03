package com.example.practice_1.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.practice_1.fragments.lesson_1_fragments.FragmentHelloAndersen
import com.example.practice_1.fragments.lesson_1_fragments.ListFragment
import com.example.practice_1.fragments.lesson_1_fragments.TextWatcherFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                FragmentHelloAndersen()
            }
            1 -> {
                ListFragment()
            }
            2 -> {
                TextWatcherFragment()
            }

            else ->{throw Resources.NotFoundException("not found")}
        }
    }
}
