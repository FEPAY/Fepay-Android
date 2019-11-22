package kr.hs.dgsw.smartschool.fepay_android.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_qr_code.*
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.database.TokenManager
import kr.hs.dgsw.smartschool.fepay_android.network.service.UserService
import kr.hs.dgsw.smartschool.fepay_android.util.Utils
import kr.hs.dgsw.smartschool.fepay_android.view.activity.MainActivity
import kr.hs.dgsw.smartschool.fepay_android.view.activity.SuccessActivity

class CreateQrCodeActivity: AppCompatActivity() {

    private val service: UserService
            = Utils.RETROFIT.create(UserService::class.java)

    private val compositeDisposable = CompositeDisposable()

    private val beforeData = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_qr_code)

        compositeDisposable.add(service.getUserInfo(TokenManager(this).token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
            when(response.code()) {
                200 -> beforeData.value = response.body()!!.balance
            }
        }, {
            Toast.makeText(applicationContext, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
        }))

        compositeDisposable.add(service.getUserInfo(TokenManager(this).token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe ({ response ->
            when (response.code()) {
                200 -> {
                    createQrCode(response.body()!!.id)
                }
            }
        }, {
            Toast.makeText(applicationContext, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
        }))

        check.setOnClickListener {
            compositeDisposable.add(service.getUserInfo(TokenManager(this).token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                when(response.code()) {
                    200 -> {
                        if (beforeData.value!! != response.body()!!.balance){
                            startActivity(Intent(this, SuccessActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "아직 결제가 완료되지 않았습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }, {
                Toast.makeText(applicationContext, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
            }))
        }
    }

    private fun createQrCode(text: String) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,300,300)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            qr_code.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}