package kr.hs.dgsw.smartschool.fepay_android.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlinx.android.synthetic.main.activity_reader_qr_code.*
import kr.hs.dgsw.smartschool.fepay_android.R


class ScanQrCodeActivity: AppCompatActivity(), DecoratedBarcodeView.TorchListener {

    private lateinit var manager: CaptureManager
    private var isFlashOn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reader_qr_code)

        manager = CaptureManager(this, scan_qr)
        manager.initializeFromIntent(intent, savedInstanceState)
        manager.decode()
    }

    override fun onTorchOn() {
        isFlashOn = true
    }

    override fun onTorchOff() {
        isFlashOn = false
    }

    override fun onResume() {
        super.onResume()
        manager.onResume()
    }

    override fun onPause() {
        super.onPause()
        manager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        manager.onSaveInstanceState(outState)
    }
}