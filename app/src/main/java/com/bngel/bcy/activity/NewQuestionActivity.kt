package com.bngel.bcy.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.CosControllerService
import com.bngel.bcy.service.QAControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_new_question.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class NewQuestionActivity : BaseActivity() {
    private var tagLauncher: ActivityResultLauncher<Intent>? = null
    private var cropPhotoLauncher: ActivityResultLauncher<Intent>? = null
    private var pickLauncher: ActivityResultLauncher<Intent>? = null
    private val imageList = ArrayList<String>()
    private val labelList = ArrayList<String>()
    private var imageFile: File? = null // 声明File对象
    private var imageUri: Uri? = null // 裁剪后的图片uri
    private val postService = QAControllerService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_question)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        tagLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result->
            val data = result.data
            val tags = data?.getStringExtra("tags")
            if (tags != null) {
                labelList.clear()
                labelList.addAll(tags.split(" "))
                tags_NewQuestionActivity.text = tags
            }
        }

        pickLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri = result.data!!.data!!

                val intent_gallery_crop = Intent("com.android.camera.action.CROP")
                intent_gallery_crop.setDataAndType(uri, "image/*")
                intent_gallery_crop.putExtra("crop", "true")
                intent_gallery_crop.putExtra("scale", true)
                //intent_gallery_crop.putExtra("aspectX", 1)
                //intent_gallery_crop.putExtra("aspectY", 1)
                //intent_gallery_crop.putExtra("outputX", 400)
                //intent_gallery_crop.putExtra("outputY", 400)
                intent_gallery_crop.putExtra("return-data", false)
                createImageFile()
                imageUri = Uri.fromFile(imageFile)
                if (imageUri != null) {
                    intent_gallery_crop.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    intent_gallery_crop.putExtra(
                        "outputFormat",
                        Bitmap.CompressFormat.JPEG.toString()
                    )
                }
                cropPhotoLauncher?.launch(intent_gallery_crop)
            }
        }
        cropPhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                displayImage(imageUri!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initWidget() {
        closeEvent()
        tagEvent()
        uploadEvent()
        sendEvent()
    }

    private fun sendEvent() {
        send_btn_NewQuestionActivity.setOnClickListener {
            val description = description_NewQuestionActivity.text.toString()
            val title = title_NewQuestionActivity.text.toString()
            if (description == "" || title == "") {
                Toast.makeText(this, "标题、内容不得为空", Toast.LENGTH_SHORT).show()
            }
            else if (labelList.count() == 0) {
                Toast.makeText(this, "请选择标签", Toast.LENGTH_SHORT).show()
            }
            else if (imageList.count() == 0) {
                Toast.makeText(this, "请至少上传一张图片", Toast.LENGTH_SHORT).show()
            }
            else {
                val postAcgCos = postService.postAcgQa(description, InfoRepository.user.id, labelList, imageList, title)
                if (postAcgCos != null) {
                    when (postAcgCos.msg) {
                        "success" -> {
                            Toast.makeText(this, "发表成功", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        "dirtyWrong" -> {
                            Toast.makeText(this, "内容含有敏感内容", Toast.LENGTH_SHORT).show()
                        }
                        "repeatWrong" -> {
                            Toast.makeText(this, "您已在今日发布15篇文章，请明日再继续发布~", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun uploadEvent() {
        image_btn_NewQuestionActivity.setOnClickListener {
            if (ConstantRepository.loginStatus) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                pickLauncher?.launch(intent)
            }
        }
    }

    private fun tagEvent() {
        tags_NewQuestionActivity.setOnClickListener {
            val intent = Intent(this, EditTagsActivity::class.java)
            tagLauncher?.launch(intent)
        }
    }

    private fun closeEvent() {
        close_btn_NewQuestionActivity.setOnClickListener {
            finish()
        }
    }

    /**
     * 创建File保存图片
     */
    private fun createImageFile() {
        try {
            if (imageFile != null && imageFile!!.exists()) {
                imageFile!!.delete()
            }
            // 新建文件
            val PORTRAIT = "image.png"
            val filepath = "${ConstantRepository.PORTRAIT_PATH}/${PORTRAIT}"
            imageFile = File(
                getExternalFilesDir(null),
                System.currentTimeMillis().toString() + PORTRAIT
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 显示图片
     * @param imageUri 图片的uri
     */
    private fun displayImage(imageUri: Uri) {
        val photoService = CosControllerService()
        val file = File(imageUri.path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val part = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        val result = photoService.postAcgCosPhotoUpload(part)
        when (result?.msg) {
            "success" -> {
                val imageUrl = result.data.url
                imageList.add(imageUrl!!)
                val image = ImageView(this)
                val param =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1100)
                param.topMargin = 5
                image.layoutParams = param
                image.scaleType = ImageView.ScaleType.CENTER_INSIDE
                Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.rem)
                    .error(R.drawable.rem)
                    .into(image)
                photos_NewQuestionActivity.addView(image)
                Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show()
            }
            "fileWrong" -> {
                Toast.makeText(this, "文件为空", Toast.LENGTH_SHORT).show()
            }
            "typeWrong" -> {
                Toast.makeText(this, "上传格式错误", Toast.LENGTH_SHORT).show()
            }
        }
    }
}