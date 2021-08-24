package com.bngel.bcy.adapter

import android.util.SparseArray
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bngel.bcy.fragment.*
import com.bngel.bcy.utils.ConstantRepository

class SearchViewPagerFragmentStateAdapter(activity: AppCompatActivity, private val itemsCount: Int):
    FragmentStateAdapter(activity) {

    private val fragments: SparseArray<Fragment> = SparseArray()

    init {
        fragments.put(0, SearchCosFragment())
        fragments.put(1, SearchQaFragment())
        fragments.put(2, SearchCommunityFragment())
        fragments.put(3, SearchUserFragment())
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount() = itemsCount
}