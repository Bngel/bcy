package com.bngel.bcy.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bngel.bcy.R
import com.bngel.bcy.activity.FollowAndFanActivity
import com.bngel.bcy.activity.LikeActivity
import com.bngel.bcy.activity.LoginActivity
import com.bngel.bcy.activity.UserDetailActivity
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import kotlinx.android.synthetic.main.fragment_me.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MeFragment: Fragment() {

    private var parentContext: Context? = null

    private var loginLauncher: ActivityResultLauncher<Intent>? = null
    private var cropPhotoLauncher: ActivityResultLauncher<Intent>? = null
    private var pickLauncher: ActivityResultLauncher<Intent>? = null
    private var detailLauncher: ActivityResultLauncher<Intent>? = null
    private var fansLauncher: ActivityResultLauncher<Intent>? = null
    private var followLauncher: ActivityResultLauncher<Intent>? = null
    private var likeLauncher: ActivityResultLauncher<Intent>? = null

    private val personalService = PersonalControllerService()
    private var imageFile: File? = null // 声明File对象
    private var imageUri: Uri? = null // 裁剪后的图片uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLaunch()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        parentContext = context?.applicationContext
        initWidget()
    }

    private fun registerLaunch() {
        loginLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val status = data?.getBooleanExtra("loginStatus", false)?:false
                if (status) {
                    ConstantRepository.loginStatus = true
                    val phone = data?.getStringExtra("phone")?:""
                    if (phone != "") {
                        val userPersonalInfoByPhone =
                            personalService.getUserPersonalInfoByPhone(phone)
                        if (userPersonalInfoByPhone != null) {
                            if (userPersonalInfoByPhone.msg == "success" && userPersonalInfoByPhone.data != null) {
                                InfoRepository.user = userPersonalInfoByPhone.data?.personalInfo!!
                                InfoRepository.userCounts = personalService.getUserUserCounts(InfoRepository.user.id)?.data!!.userCountsList[0]
                                InfoRepository.initLogin(parentContext!!, InfoRepository.user.id)
                                initUser()
                            }
                            else {
                                Toast.makeText(parentContext!!,
                                    when(userPersonalInfoByPhone.msg){
                                        "existWrong" -> "账号不存在或已被冻结"
                                        else -> "发生未知错误"
                                    },
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        else {
                            Toast.makeText(parentContext!!, "发生未知错误", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        pickLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
        cropPhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                displayImage(imageUri!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        detailLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
        fansLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
        followLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
        likeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
        }
    }

    override fun onResume() {
        super.onResume()
        if (ConstantRepository.loginStatus) {
            if (!ConstantRepository.meFragmentUpdate) {
                InfoRepository.user =
                    personalService.getUserPersonalInfoById(InfoRepository.user.id)?.data!!.personalInfo
                InfoRepository.userCounts =
                    personalService.getUserUserCounts(InfoRepository.user.id)?.data!!.userCountsList[0]
                initUser()
                ConstantRepository.meFragmentUpdate = true
            }
            else
                initUser()
        }
    }

    private fun initWidget() {
        headCardEvent()
        avtEvent()
        fansEvent()
        followEvent()
        likeEvent()
    }

    private fun likeEvent() {
        like_image_MeFragment.setOnClickListener {
            if (ConstantRepository.loginStatus) {
                val intent = Intent(parentContext!!, LikeActivity::class.java)
                likeLauncher?.launch(intent)
            }
        }
    }

    private fun followEvent() {
        follow_count_text_MeFragment.setOnClickListener {
            val intent = Intent(parentContext!!, FollowAndFanActivity::class.java)
            intent.putExtra("type", "follow")
            followLauncher?.launch(intent)
        }
    }

    private fun fansEvent() {
        fan_count_text_MeFragment.setOnClickListener {
            val intent = Intent(parentContext!!, FollowAndFanActivity::class.java)
            intent.putExtra("type", "fans")
            fansLauncher?.launch(intent)
        }
    }

    private fun avtEvent() {
        avt_MeFragment.setOnClickListener {
            if (ConstantRepository.loginStatus) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                pickLauncher?.launch(intent)
            }
        }
    }

    private fun headCardEvent() {
        headCard_MeFragment.setOnClickListener {
            if (!ConstantRepository.loginStatus){
                val intent = Intent(parentContext!!, LoginActivity::class.java)
                loginLauncher?.launch(intent)
            }
            else {
                val intent = Intent(parentContext!!, UserDetailActivity::class.java)
                intent.putExtra("id", InfoRepository.user.id)
                detailLauncher?.launch(intent)
            }
        }
    }

    private fun initUser() {
        val user = InfoRepository.user
        if (user.photo != null)
            avt_MeFragment.setAvt(user.photo)
        username_MeFragment.text = user.username?:"用户名获取失败"
        description_MeFragment.text = user.description?:""
        val userCounts = InfoRepository.userCounts
        moment_count_text_MeFragment.text = userCounts.momentCounts.toString()
        follow_count_text_MeFragment.text = userCounts.followCounts.toString()
        fan_count_text_MeFragment.text = userCounts.fansCounts.toString()
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
            val PORTRAIT = "portrait.png"
            val filepath = "${ConstantRepository.PORTRAIT_PATH}/${PORTRAIT}"
            imageFile = File(
                parentContext!!.getExternalFilesDir(null),
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
        val file = File(imageUri.path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val part = MultipartBody.Part.createFormData("photo", file.name, requestFile)
        val result = personalService.postUserPhotoUpload(InfoRepository.user.id, part)
        if (result?.msg == "success") {
        }
    }
}