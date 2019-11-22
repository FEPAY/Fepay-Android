package kr.hs.dgsw.smartschool.fepay_android.network.service

import io.reactivex.Flowable
import io.reactivex.Single
import kr.hs.dgsw.smartschool.fepay_android.network.request.AcceptPayRequest
import kr.hs.dgsw.smartschool.fepay_android.network.request.LoginRequest
import kr.hs.dgsw.smartschool.fepay_android.network.request.SignUpRequest
import kr.hs.dgsw.smartschool.fepay_android.network.response.GetUserInfoResponse
import kr.hs.dgsw.smartschool.fepay_android.network.response.MyInfoResponse
import kr.hs.dgsw.smartschool.fepay_android.network.response.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserService {

    @POST("user/signup")
    fun signUp (
        @Body request: SignUpRequest
    ): Single<Response<Any>>

    @POST("user/auth")
    fun login (
        @Body request: LoginRequest
    ): Single<Response<LoginResponse>>

    @GET("user/me")
    fun getUserInfo(@Header("Authorization") token: String): Flowable<Response<GetUserInfoResponse>>

    @POST
    fun postPay(@Header("Authorization") token: String, @Body body: Any?): Flowable<Response<Unit>>

    @PATCH("pay")
    fun acceptPay(@Header("Authorization") token: String, @Body body: Any?): Flowable<Response<GetUserInfoResponse>>

    @PATCH("user/join")
    fun join(
        @Header("Authorization") token: String,
        @Query("festival_id") festival_id: String
    ): Single<Response<Any>>

    @GET("user/me")
    fun getMyInfo (
        @Header("Authorization") token: String
    ): Single<Response<MyInfoResponse>>
}