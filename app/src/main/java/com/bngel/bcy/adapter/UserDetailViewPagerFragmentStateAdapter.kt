package com.bngel.bcy.adapter

import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bngel.bcy.fragment.DetailCreateFragment
import com.bngel.bcy.fragment.DetailLikeFragment
import com.bngel.bcy.fragment.DiscussOfCommunityFragment
import com.bngel.bcy.fragment.QaOfCommunityFragment

class UserDetailViewPagerFragmentStateAdapter (activity: AppCompatActivity, private val itemsCount: Int):
    FragmentStateAdapter(activity) {

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        fragments.put(0, DetailCreateFragment())
        fragments.put(1, DetailLikeFragment())
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount() = itemsCount
}