package kr.hs.dgsw.smartschool.fepay_android.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.database.TokenManager

class SplashActivity : AppCompatActivity() {

    private val handler = Handler()

    private val runnableLogin =
        Runnable { startActivity(Intent(this, LoginActivity::class.java)) }
    private val runnableMain =
        Runnable { startActivity(Intent(this, MainActivity::class.java)) }
    private val runnableFinish =
        Runnable { finish() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val isEmpty = TokenManager(this).token == ""
        if (isEmpty) {
            handler.postDelayed(runnableLogin, 2000)
        } else {
            handler.postDelayed(runnableMain, 2000)
        }
        handler.postDelayed(runnableFinish, 3000)
    }
}