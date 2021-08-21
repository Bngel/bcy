package com.bngel.bcy.adapter

import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bngel.bcy.fragment.*
import com.bngel.bcy.utils.ConstantRepository.PAGE_COMMUNITY
import com.bngel.bcy.utils.ConstantRepository.PAGE_HOME
import com.bngel.bcy.utils.ConstantRepository.PAGE_ME
import com.bngel.bcy.utils.ConstantRepository.PAGE_QANDA

class HomeViewPagerFragmentStateAdapter(activity: AppCompatActivity, private val itemsCount: Int):
    FragmentStateAdapter(activity) {

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        fragments.put(0, FollowFragment())
        fragments.put(1, DiscoveryFragment())
        fragments.put(2, TopFragment())
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount() = itemsCount
}