package kr.hs.dgsw.smartschool.fepay_android.network.response

data class GetUserInfoResponse(
    val id: String,
    val name: String,
    val phone: String,
    val balance: String,
    val festivalId: String
)