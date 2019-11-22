package kr.hs.dgsw.smartschool.fepay_android.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.base.BaseActivity
import kr.hs.dgsw.smartschool.fepay_android.database.TokenManager
import kr.hs.dgsw.smartschool.fepay_android.databinding.ActivityMainBinding
import kr.hs.dgsw.smartschool.fepay_android.network.response.MyInfoResponse
import kr.hs.dgsw.smartschool.fepay_android.network.service.UserService
import kr.hs.dgsw.smartschool.fepay_android.util.Utils

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_main

    private val service: UserService
            = Utils.RETROFIT.create(UserService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addDisposable(service.
            getMyInfo(TokenManager(this).token).map {  if (it.isSuccessful) it.body() else throw Exception(it.errorBody().toString()) },
                object : DisposableSingleObserver<MyInfoResponse>() {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(t: MyInfoResponse) {
                        binding.balanceText.text = t.balance + "원"
                    }

                    override fun onError(e: Throwable) { }
                })

        binding.btnSell.setOnClickListener {
            startActivity(ReceiveWriteActivity::class.java)
        }

        binding.appBar.btnLogout.setOnClickListener {
            TokenManager(this).token = ""
            startActivitiesWithFinish(LoginActivity::class.java)
        }
    }
}
