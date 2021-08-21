package com.bngel.bcy.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.CircleControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_create_community.*
import kotlinx.android.synthetic.main.view_avator.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CreateCommunityActivity : BaseActivity() {

    private val circleService = CircleControllerService()
    private var imageFile: File? = null // 声明File对象
    private var imageUri: Uri? = null // 裁剪后的图片uri
    private var imageUrl: String? = null
    private var pickLauncher: ActivityResultLauncher<Intent>? = null
    private var cropPhotoLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_community)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
        pickLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri: Uri = result.data!!.data!! // 获取图片的uri
                val intent_gallery_crop = Intent("com.android.camera.action.CROP")
                intent_gallery_crop.setDataAndType(uri, "image/*")
                // 设置裁剪
                intent_gallery_crop.putExtra("crop", "true")
                intent_gallery_crop.putExtra("scale", true)
                // aspectX aspectY 是宽高的比例
                intent_gallery_crop.putExtra("aspectX", 1)
                intent_gallery_crop.putExtra("aspectY", 1)
                // outputX outputY 是裁剪图片宽高
                intent_gallery_crop.putExtra("outputX", 400)
                intent_gallery_crop.putExtra("outputY", 400)
                intent_gallery_crop.putExtra("return-data", false)
                // 创建文件保存裁剪的图片
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
        imageEvent()
        confirmEvent()
    }

    private fun confirmEvent() {
        confirm_btn_CreateCommunityActivity.setOnClickListener {
            val url = imageUrl
            if (url != null) {
                val circleName = title_CreateCommunityActivity.text.toString()
                val description = description_CreateCommunityActivity.text.toString()
                val nickName = name_CreateCommunityActivity.text.toString()
                if (circleName == "" || description == "" || nickName == ""){
                    Toast.makeText(this, "请完整输入信息", Toast.LENGTH_SHORT).show()
                }
                else {
                    val postAcgCircle = circleService.postAcgCircle(circleName, description, InfoRepository.user.id, nickName, url)
                    if (postAcgCircle != null) {
                        when (postAcgCircle.msg) {
                            "success" -> {
                                Toast.makeText(this, "创建圈子[$circleName]成功", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            "repeatWrong" -> {
                                Toast.makeText(this, "圈子已存在", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else {
                        Toast.makeText(this, "创建圈子发生未知错误", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
            else {
                Toast.makeText(this, "请选择头像", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun closeEvent() {
        close_btn_CreateCommunityActivity.setOnClickListener {
            finish()
        }
    }

    private fun imageEvent() {
        image_CreateCommunityActivity.setOnClickListener {
            if (ConstantRepository.loginStatus) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                pickLauncher?.launch(intent)
            }
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
            val PORTRAIT = "circleCreate.png"
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
        val file = File(imageUri.path!!)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val part = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        val result = circleService.postAcgCirclePhoto(InfoRepository.user.id, part)
        if (result != null) {
            when (result.msg) {
                "success" -> {
                    imageUrl = result.data.url
                    image_CreateCommunityActivity.setPadding(0,0,0,0)
                    image_CreateCommunityActivity.background = null
                    Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.rem)
                        .error(R.drawable.rem)
                        .into(image_CreateCommunityActivity)
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
}