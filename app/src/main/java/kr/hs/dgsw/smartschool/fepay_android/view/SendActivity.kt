package kr.hs.dgsw.smartschool.fepay_android.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_send.*
import kotlinx.android.synthetic.main.app_bar_basic.view.*
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.database.TokenManager
import kr.hs.dgsw.smartschool.fepay_android.network.request.AcceptPayRequest
import kr.hs.dgsw.smartschool.fepay_android.network.service.UserService
import kr.hs.dgsw.smartschool.fepay_android.util.Utils
import kr.hs.dgsw.smartschool.fepay_android.view.activity.MainActivity
import kr.hs.dgsw.smartschool.fepay_android.view.activity.SuccessActivity


class SendActivity: AppCompatActivity() {

    private val service: UserService
            = Utils.RETROFIT.create(UserService::class.java)

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)

        result_tv.text = "처리 중 입니다"

        app_bar.btn_back.setOnClickListener {
            finish()
        }

        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setBeepEnabled(false)
        intentIntegrator.captureActivity = ScanQrCodeActivity::class.java
        intentIntegrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        compositeDisposable.add(service.acceptPay(TokenManager(this).token, result.contents).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
            when(response.code()) {
                200 -> {
                    startActivity(Intent(this, SuccessActivity::class.java))
                    finish()
                }
                403 -> {
                    result_tv.text = "잔고가 부족합니다"
                    check.text = "확인"
                    check.setOnClickListener {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
                500 -> {
                    result_tv.text = "서버 에러가 발생했습니다"
                    check.text = "다시 스캔"
                    check.setOnClickListener {
                        val intentIntegrator = IntentIntegrator(this)
                        intentIntegrator.setBeepEnabled(false)
                        intentIntegrator.captureActivity = ScanQrCodeActivity::class.java
                        intentIntegrator.initiateScan()
                    }
                }
            }
        }, {
            result_tv.text = "오류가 발생했습니다"
            check.text = "다시 스캔"
            check.setOnClickListener {
                val intentIntegrator = IntentIntegrator(this)
                intentIntegrator.setBeepEnabled(false)
                intentIntegrator.captureActivity = ScanQrCodeActivity::class.java
                intentIntegrator.initiateScan()
            }
        }))
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}