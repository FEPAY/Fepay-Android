package kr.hs.dgsw.smartschool.fepay_android.view.activity

import android.os.Bundle
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.base.BaseActivity
import kr.hs.dgsw.smartschool.fepay_android.databinding.ActivitySignupBinding
import kr.hs.dgsw.smartschool.fepay_android.network.request.SignUpRequest
import kr.hs.dgsw.smartschool.fepay_android.network.service.UserService
import kr.hs.dgsw.smartschool.fepay_android.util.Utils
import java.util.*

class SignUpActivity : BaseActivity<ActivitySignupBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_signup

    private val service: UserService
            = Utils.RETROFIT.create(UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSignup.setOnClickListener {
            addDisposable(service.
                signUp(SignUpRequest(binding.inputId.text.toString(), binding.inputPassword.text.toString(),
                    binding.inputName.text.toString(), binding.inputPhone.text.toString(), Date().toString()
                )).map {
                if (it.code() == 200) return@map it.message()
                else throw Exception(it.errorBody().toString()) },
                object : DisposableSingleObserver<String>() {
                    override fun onSuccess(t: String) {
                        startActivitiesWithFinish(LoginActivity::class.java)
                    }

                    override fun onError(e: Throwable) {
                        simpleToast(e.message)
                    }
            })
        }
    }
}