package kr.hs.dgsw.smartschool.fepay_android.network.response

data class MyInfoResponse (val user_id: String,
                           val name: String,
                           val phone: String,
                           val balance: String,
                           val festivalId: String)