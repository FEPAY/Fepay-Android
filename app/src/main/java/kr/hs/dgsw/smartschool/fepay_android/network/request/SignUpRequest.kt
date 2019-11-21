package kr.hs.dgsw.smartschool.fepay_android.network.request

data class SignUpRequest(val email: String,
                         val password: String,
                         val name: String,
                         val phone: String,
                         val closing_date: String)