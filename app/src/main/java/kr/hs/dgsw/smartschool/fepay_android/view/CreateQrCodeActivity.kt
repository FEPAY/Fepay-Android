package kr.hs.dgsw.smartschool.fepay_android.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_create_qr_code.*
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.database.TokenManager
import kr.hs.dgsw.smartschool.fepay_android.network.service.UserService
import kr.hs.dgsw.smartschool.fepay_android.util.Utils

class CreateQrCodeActivity: AppCompatActivity() {

    private val service: UserService
            = Utils.RETROFIT.create(UserService::class.java)

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_qr_code)

        compositeDisposable.add(service.getUserInfo(TokenManager(this).token).subscribe ({ response ->
            when (response.code()) {
                200 -> {
                    Toast.makeText(applicationContext, "성공적으로 취소되었습니다.", Toast.LENGTH_SHORT).show()
                    createQrCode(response.body()!!.id)
                }
            }
        }, {
            Toast.makeText(applicationContext, "네트워크 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
        }))

        check.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
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