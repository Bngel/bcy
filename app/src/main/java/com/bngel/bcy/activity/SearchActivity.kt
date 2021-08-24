package com.bngel.bcy.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.bngel.bcy.R
import com.bngel.bcy.adapter.SearchViewPagerFragmentStateAdapter
import com.bngel.bcy.utils.ConstantRepository
import kotlinx.android.synthetic.main.activity_community.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity() {

    private var curPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initWidget()
    }

    private fun initWidget() {
        vpEvent()
        tabEvent()
        inputEvent()
    }

    private fun inputEvent() {
        search_SearchActivity.setOnClickListener {
            ConstantRepository.searchConfirm.value = false
        }
        search_SearchActivity.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || (event!=null && event.keyCode == KeyEvent.KEYCODE_ENTER) ) {
                intent.putExtra("keyword", v.text.toString())
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
                ConstantRepository.searchConfirm.value = true
            }
            true
        }
    }

    private fun vpEvent() {
        val adapter = SearchViewPagerFragmentStateAdapter(this, 4)
        viewpager_SearchActivity.adapter = adapter
        viewpager_SearchActivity.isUserInputEnabled = false
    }

    private fun tabEvent() {
        cos_SearchActivity.setOnClickListener {
            curPage = 0
            cos_SearchActivity.setTextColor(Color.parseColor("#DFC048"))
            qa_SearchActivity.setTextColor(Color.parseColor("#101010"))
            community_SearchActivity.setTextColor(Color.parseColor("#101010"))
            user_SearchActivity.setTextColor(Color.parseColor("#101010"))
            viewpager_SearchActivity.currentItem = curPage
        }
        qa_SearchActivity.setOnClickListener {
            curPage = 1
            cos_SearchActivity.setTextColor(Color.parseColor("#101010"))
            qa_SearchActivity.setTextColor(Color.parseColor("#DFC048"))
            community_SearchActivity.setTextColor(Color.parseColor("#101010"))
            user_SearchActivity.setTextColor(Color.parseColor("#101010"))
            viewpager_SearchActivity.currentItem = curPage
        }
        community_SearchActivity.setOnClickListener {
            curPage = 2
            community_SearchActivity.setTextColor(Color.parseColor("#DFC048"))
            qa_SearchActivity.setTextColor(Color.parseColor("#101010"))
            cos_SearchActivity.setTextColor(Color.parseColor("#101010"))
            user_SearchActivity.setTextColor(Color.parseColor("#101010"))
            viewpager_SearchActivity.currentItem = curPage
        }
        user_SearchActivity.setOnClickListener {
            curPage = 3
            user_SearchActivity.setTextColor(Color.parseColor("#DFC048"))
            qa_SearchActivity.setTextColor(Color.parseColor("#101010"))
            community_SearchActivity.setTextColor(Color.parseColor("#101010"))
            cos_SearchActivity.setTextColor(Color.parseColor("#101010"))
            viewpager_SearchActivity.currentItem = curPage
        }
    }


}