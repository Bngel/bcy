package com.bngel.bcy.utils

object ConstantRepository {

    var loginStatus = false

    const val PAGE_HOME = 0
    const val PAGE_QANDA = 1
    const val PAGE_COMMUNITY = 2
    const val PAGE_ME = 3

    const val PAGE_FOLLOW = 0
    const val PAGE_FANS = 1

    var homeTabStatus = PAGE_HOME

    var PORTRAIT_PATH :String? = null
    var DOWNLOAD_PATH :String? = null

    var followFragmentUpdate = false
    var meFragmentUpdate = false
}