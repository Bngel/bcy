package com.bngel.bcy.utils

import com.bngel.bcy.bean.PersonalController.getUserPersonalInfo.PersonalInfo
import com.bngel.bcy.bean.PersonalController.getUserUserCounts.UserCounts

object InfoRepository {
    var user = PersonalInfo(null,null,null,"",null,null,null,null)
    var userCounts = UserCounts(0,0,"",0)
    var token = ""
}