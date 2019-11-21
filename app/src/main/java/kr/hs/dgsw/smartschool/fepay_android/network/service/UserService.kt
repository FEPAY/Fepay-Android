package kr.hs.dgsw.smartschool.fepay_android.network.service

import io.reactivex.Single
import kr.hs.dgsw.smartschool.fepay_android.network.request.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {

    @POST("user/signup")
    fun signUp (
        @Body request: SignUpRequest
    ): Single<Response<String>>

}