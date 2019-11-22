package kr.hs.dgsw.smartschool.fepay_android.network.request

import com.google.gson.annotations.SerializedName

data class AcceptPayRequest(
    @SerializedName(value = "recipient_id")
    val id: String
)