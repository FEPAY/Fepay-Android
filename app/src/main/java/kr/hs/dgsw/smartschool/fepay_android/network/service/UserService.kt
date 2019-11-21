package kr.hs.dgsw.smartschool.fepay_android.network.service

import io.reactivex.Single
import kr.hs.dgsw.smartschool.fepay_android.network.request.LoginRequest
import kr.hs.dgsw.smartschool.fepay_android.network.request.SignUpRequest
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

    @PATCH("user/join/{festival_id}")
    fun join(
        @Header("Authorization") token: String,
        @Path("festival_id") festival_id: String
    ): Single<Response<Any>>

    @GET("user/me")
    fun getMyInfo (
        @Header("Authorization") token: String
    ): Single<Response<MyInfoResponse>>
}