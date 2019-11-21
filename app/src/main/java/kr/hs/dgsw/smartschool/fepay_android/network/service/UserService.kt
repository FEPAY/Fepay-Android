package kr.hs.dgsw.smartschool.fepay_android.network.service

import io.reactivex.Single
import kr.hs.dgsw.smartschool.fepay_android.network.request.LoginRequest
import kr.hs.dgsw.smartschool.fepay_android.network.request.SignUpRequest
import kr.hs.dgsw.smartschool.fepay_android.network.response.BalanceResponse
import kr.hs.dgsw.smartschool.fepay_android.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {

    @POST("user/signup")
    fun signUp (
        @Body request: SignUpRequest
    ): Single<Response<String>>

    @POST("user/auth")
    fun login (
        @Body request: LoginRequest
    ): Single<Response<LoginResponse>>

    @GET("user/auth") // todo
    fun getBalance (
        @Header("Authorization ") token: String
    ): Single<Response<BalanceResponse>>
}