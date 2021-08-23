package com.bngel.bcy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bngel.bcy.R
import com.bngel.bcy.service.CosControllerService
import com.bngel.bcy.widget.others.TagTextView
import kotlinx.android.synthetic.main.activity_edit_tags.*

class EditTagsActivity : BaseActivity() {

    private val tagService = CosControllerService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tags)
        initWidget()
    }

    private fun initWidget() {
        closeEvent()
        tagsEvent()
        confirmEvent()
    }

    private fun confirmEvent() {
        confirm_btn_EditTagsActivity.setOnClickListener {
            val intent = Intent(this, PostDiscussActivity::class.java)
            intent.putExtra("tags", tags_edit_EditTagsActivity.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun tagsEvent() {
        val acgRecommendList = tagService.getAcgRecommendList()
        recommend_tags_EditTagsActivity.removeAllViews()
        if (acgRecommendList != null && acgRecommendList.msg == "success") {
            for (tag in acgRecommendList.data.cosRecommendLabelList.subList(0,4)) {
                val view = TagTextView(this, tag)
                view.setOnClickListener {
                    tags_edit_EditTagsActivity.setText("${tags_edit_EditTagsActivity.text} $tag")
                }
                recommend_tags_EditTagsActivity.addView(view)
            }
        }
    }

    private fun closeEvent() {
        close_btn_EditTagsActivity.setOnClickListener {
            finish()
        }
    }
}