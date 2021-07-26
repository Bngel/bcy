package com.bngel.bcy.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.bngel.bcy.R
import com.bngel.bcy.widget.MainActivity.ItemTabMainActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : BaseActivity() {
    private val curPage = MutableLiveData<View>(null)
    private val totalPages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        totalPages.addAll(listOf("关注","关注","关注","关注","关注","关注","关注","关注","关注"))
        for (page in totalPages) {
            tab_MainActivity.addView(createTabItem(page))
        }
    }

    private fun createTabItem(text: String): View {
        val view = ItemTabMainActivity(this, text)
        view.setOnClickListener {
            val curView = curPage.value
            if (curView == null){
                curPage.value = it
                (it as ItemTabMainActivity).setClicked()
            }
            else if (curView != it) {
                (curView as ItemTabMainActivity).setUnClicked()
                curPage.value = it
                (it as ItemTabMainActivity).setClicked()
            }
        }

        return view
    }
}