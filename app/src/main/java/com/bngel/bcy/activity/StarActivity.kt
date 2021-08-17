package com.bngel.bcy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bngel.bcy.R
import com.bngel.bcy.service.FavorControllerService
import com.bngel.bcy.service.LikeControllerService
import com.bngel.bcy.utils.ConstantRepository
import com.bngel.bcy.utils.InfoRepository
import com.bngel.bcy.widget.HomeFragment.DiscussCardHomeFragment
import kotlinx.android.synthetic.main.activity_like.*

class StarActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star)
    }
}