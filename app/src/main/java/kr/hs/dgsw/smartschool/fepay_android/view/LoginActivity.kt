package kr.hs.dgsw.smartschool.fepay_android.view

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
                ).map { it.body() }, object : DisposableSingleObserver<LoginResponse>() {
                override fun onSuccess(t: LoginResponse) {
                    TokenManager(this@LoginActivity).token = t.access
                    startActivitiesWithFinish(MainActivity::class.java)
                }

                override fun onError(e: Throwable) { }
            })
        }

        binding.btnRegister.setOnClickListener {
            startActivity(SignUpActivity::class.java)
        }
    }
}