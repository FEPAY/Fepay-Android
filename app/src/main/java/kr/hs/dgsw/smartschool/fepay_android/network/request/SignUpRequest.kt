package kr.hs.dgsw.smartschool.fepay_android.network.request

import com.google.gson.annotations.SerializedName

data class SignUpRequest(@SerializedName("user_id")
                         val id: String,
                         val password: String,
                         val name: String,
                         val phone: String,
                         val closing_date: String)