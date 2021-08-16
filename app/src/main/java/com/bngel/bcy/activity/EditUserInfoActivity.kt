package com.bngel.bcy.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bngel.bcy.R
import com.bngel.bcy.service.PersonalControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.github.gzuliyujiang.wheelpicker.AddressPicker
import com.github.gzuliyujiang.wheelpicker.BirthdayPicker
import com.github.gzuliyujiang.wheelpicker.annotation.AddressMode
import kotlinx.android.synthetic.main.activity_edit_user_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EditUserInfoActivity : BaseActivity() {

    private var pickLauncher: ActivityResultLauncher<Intent>? = null
    private var cropPhotoLauncher: ActivityResultLauncher<Intent>? = null
    private val personalService = PersonalControllerService()
    private var imageFile: File? = null // 声明File对象
    private var imageUri: Uri? = null // 裁剪后的图片uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_info)
        registerLaunch()
        initWidget()
    }

    private fun registerLaunch() {
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
    }

    private fun initWidget() {
        avtEvent()
        addressEvent()
        birthdayEvent()
        usernameEvent()
        sexEvent()
        descriptionEvent()
        saveBtnEvent()
        closeEvent()
    }

    private fun closeEvent() {
        close_btn_EditUserInfoActivity.setOnClickListener {
            finish()
        }
    }

    private fun avtEvent() {
        if (InfoRepository.user.photo != null) {
            avt_EditUserInfoActivity.setAvt(InfoRepository.user.photo!!)
        }
        avt_EditUserInfoActivity.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickLauncher?.launch(intent)
        }
    }

    private fun descriptionEvent() {
        description_EditUserInfoActivity.setText(InfoRepository.user.description)
    }

    private fun sexEvent() {
        if (InfoRepository.user.sex == "男") {
            sex_nan_EditUserInfoActivity.isChecked = true
        }
        else {
            sex_nv_EditUserInfoActivity.isChecked = true
        }
    }

    private fun usernameEvent() {
        username_EditUserInfoActivity.setText(InfoRepository.user.username)
    }

    @SuppressLint("SetTextI18n")
    private fun addressEvent() {
        if (InfoRepository.user.province != null && InfoRepository.user.city != null) {
            address_EditUserInfoActivity.text =
                "${InfoRepository.user.province} ${InfoRepository.user.city}"
            address_EditUserInfoActivity.setTextColor(Color.parseColor("#101010"))
        }
        address_EditUserInfoActivity.setOnClickListener {
            val addressPicker = AddressPicker(this)
            addressPicker.setAddressMode(AddressMode.PROVINCE_CITY_COUNTY)
            addressPicker.setOnAddressPickedListener { province, city, county ->
                if (province.name == "北京市" || province.name == "上海市" || province.name == "天津市" || province.name == "重庆市")
                    address_EditUserInfoActivity.text = "${province.name} ${county.name}"
                else
                    address_EditUserInfoActivity.text = "${province.name} ${city.name}"
                address_EditUserInfoActivity.setTextColor(Color.parseColor("#101010"))
            }
            addressPicker.show()
        }
    }

    private fun birthdayEvent() {
        if (InfoRepository.user.birthday != null) {
            val pattern = "yyyy-MM-dd'T'HH:mm:ss"
            val dfParse = SimpleDateFormat(pattern, Locale.ENGLISH)
            dfParse.timeZone = TimeZone.getTimeZone("UTC")
            val df = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            df.timeZone = TimeZone.getTimeZone("Asia/Shanghai")
            val dateTime = dfParse.parse(InfoRepository.user.birthday)
            val birthday = df.format(dateTime)
            birthday_EditUserInfoActivity.text = birthday
            birthday_EditUserInfoActivity.setTextColor(Color.parseColor("#101010"))
        }
        birthday_EditUserInfoActivity.setOnClickListener {
            val birthdayPicker = BirthdayPicker(this)
            birthdayPicker.setDefaultValue(2001,1,1)
            birthdayPicker.setOnDatePickedListener { year, month, day ->
                birthday_EditUserInfoActivity.text = "$year-$month-$day"
                birthday_EditUserInfoActivity.setTextColor(Color.parseColor("#101010"))
            }
            birthdayPicker.show()
        }
    }

    private fun saveBtnEvent() {
        save_btn_EditUserInfoActivity.setOnClickListener {
            val username = username_EditUserInfoActivity.text.toString()
            if (username == "") {
                Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val sex = findViewById<RadioButton>(sex_group_EditUserInfoActivity.checkedRadioButtonId).text.toString()
            val address = address_EditUserInfoActivity.text.toString().split(" ")
            val province = address[0]
            val city = address[1]
            val birthday = birthday_EditUserInfoActivity.text.toString()
            val description = description_EditUserInfoActivity.text.toString()
            val putUserPersonalInfo = personalService.putUserPersonalInfo(
                InfoRepository.user.id,
                if(username == InfoRepository.user.username) null else username,
                if(sex == InfoRepository.user.sex) null else sex,
                if(province == InfoRepository.user.province) null else province,
                if(city == InfoRepository.user.city) null else city,
                if(birthday == InfoRepository.user.birthday) null else birthday,
                if(description == InfoRepository.user.description) null else description
            )
            if (putUserPersonalInfo != null) {
                Toast.makeText(this,
                when (putUserPersonalInfo.msg) {
                    "existWrong" -> "账号不存在或已被冻结"
                    "success" -> "修改成功"
                    else -> "未知错误"
                },Toast.LENGTH_SHORT).show()
                if (putUserPersonalInfo.msg == "success") {
                    InfoRepository.user = personalService.getUserPersonalInfoById(InfoRepository.user.id)?.data!!.personalInfo
                    val intent = Intent(this, UserDetailActivity::class.java)
                    intent.putExtra("id", InfoRepository.user.id)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
            //Log.d("TestLog", "${InfoRepository.user.id}, $username, $sex, $province, $city, $birthday, $description")
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
            val PORTRAIT = "portrait.png"
            val filepath = "${ConstantRepository.PORTRAIT_PATH}/${PORTRAIT}"
            imageFile = File(
                this.getExternalFilesDir(null),
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
            InfoRepository.user = personalService.getUserPersonalInfoById(InfoRepository.user.id)?.data!!.personalInfo
            InfoRepository.userCounts = personalService.getUserUserCounts(InfoRepository.user.id)?.data!!.userCountsList[0]
            if (InfoRepository.user.photo != null) {
                avt_EditUserInfoActivity.setAvt(InfoRepository.user.photo!!)
            }
        }
    }
}