package com.example.practice_1.fragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.practice_1.adapters.ViewPagerAdapter
import com.example.practice_1.common.BaseFragment
import com.example.practice_1.databinding.FragmentLessonOneBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class
LessonOneFragment:BaseFragment<FragmentLessonOneBinding>(FragmentLessonOneBinding::inflate)
{
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    override fun viewCreated() {
        setupTabLayout()
    }

    override fun listeners() {

    }

    private fun setupTabLayout() {
        viewPager = binding.viewPager2
        tabLayout = binding.tabLayout
        viewPager.adapter = ViewPagerAdapter(requireActivity())
        TabLayoutMediator(tabLayout,viewPager){tab,index ->
            tab.text = when(index){
                0 -> {"Hello"}
                1 -> {"recycler"}
                2 -> {"editText"}
                else -> {"Tab Not Found"}
            }
        }.attach()
    }
}