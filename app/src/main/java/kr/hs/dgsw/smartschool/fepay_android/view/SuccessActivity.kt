package kr.hs.dgsw.smartschool.fepay_android.view

import android.os.Bundle
import com.airbnb.lottie.LottieDrawable
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.base.BaseActivity
import kr.hs.dgsw.smartschool.fepay_android.databinding.ActivitySuccessBinding

class SuccessActivity : BaseActivity<ActivitySuccessBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_success

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lottie.playAnimation()
        binding.lottie.repeatCount = 0

        binding.finishBtn.setOnClickListener {
            startActivitiesWithFinish(MainActivity::class.java)
        }
    }
}