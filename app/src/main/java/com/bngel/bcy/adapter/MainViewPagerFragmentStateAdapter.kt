package com.bngel.bcy.adapter

import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bngel.bcy.fragment.CommunityFragment
import com.bngel.bcy.fragment.HomeFragment
import com.bngel.bcy.fragment.MeFragment
import com.bngel.bcy.fragment.QAndAFragment
import com.bngel.bcy.utils.ConstantRepository.PAGE_COMMUNITY
import com.bngel.bcy.utils.ConstantRepository.PAGE_HOME
import com.bngel.bcy.utils.ConstantRepository.PAGE_ME
import com.bngel.bcy.utils.ConstantRepository.PAGE_QANDA

class MainViewPagerFragmentStateAdapter(activity: AppCompatActivity, private val itemsCount: Int):
    FragmentStateAdapter(activity) {

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        fragments.put(PAGE_HOME, HomeFragment())
        fragments.put(PAGE_QANDA, QAndAFragment())
        fragments.put(PAGE_COMMUNITY, CommunityFragment())
        fragments.put(PAGE_ME, MeFragment())
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount() = itemsCount
}