package kr.hs.dgsw.smartschool.fepay_android.view.activity

import android.os.Bundle
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.base.BaseActivity
import kr.hs.dgsw.smartschool.fepay_android.database.TokenManager
import kr.hs.dgsw.smartschool.fepay_android.databinding.ActivityLoginBinding
import kr.hs.dgsw.smartschool.fepay_android.network.request.LoginRequest
import kr.hs.dgsw.smartschool.fepay_android.network.response.LoginResponse
import kr.hs.dgsw.smartschool.fepay_android.network.service.UserService
import kr.hs.dgsw.smartschool.fepay_android.util.Utils
import kr.hs.dgsw.smartschool.fepay_android.view.dialog.RoomCodeDialog

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_login

    private val service: UserService
            = Utils.RETROFIT.create(UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnLogin.setOnClickListener {
            addDisposable(service.
                login(
                    LoginRequest(binding.inputId.text.toString(), binding.inputPassword.text.toString())
                ).map { if (it.isSuccessful) it.body() else throw Exception("로그인 실패") },
                object : DisposableSingleObserver<LoginResponse>() {
                    override fun onSuccess(t: LoginResponse) {
                        RoomCodeDialog(t.access).show(supportFragmentManager)
                    }

                    override fun onError(e: Throwable) {
                        simpleToast(e.message)
                    }
                })
        }

        binding.btnRegister.setOnClickListener {
            startActivity(SignUpActivity::class.java)
        }
    }
}