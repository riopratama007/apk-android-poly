package com.poly.ui.fragment.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.poly.ui.fragment.business.BusinessFragment
import com.poly.ui.fragment.collection.CollectionFragment
import com.poly.ui.fragment.home.HomeFragment
import com.poly.ui.fragment.job.JobFragment
import com.poly.ui.fragment.profession.ProfessionFragment
import com.poly.ui.fragment.store.StoreFragment

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 6

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = BusinessFragment()
            2 -> fragment = StoreFragment()
            3 -> fragment = CollectionFragment()
            4 -> fragment = ProfessionFragment()
            5 -> fragment = JobFragment()
        }
        return fragment as Fragment
    }

}