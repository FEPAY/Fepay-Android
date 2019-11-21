package kr.hs.dgsw.smartschool.fepay_android.view.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.base.BaseActivity
import kr.hs.dgsw.smartschool.fepay_android.databinding.ActivityReceiveWriteBinding

class ReceiveWriteActivity : BaseActivity<ActivityReceiveWriteBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_receive_write

    val num = MutableLiveData<String>("0")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.handler = this
    }

    fun onClickButton(view: View) {
        if (num.value == "0") {
            num.value = (view as TextView).text.toString()
            return
        }
        num.value += (view as TextView).text.toString()
    }

    fun onClickBackButton(view: View) {
        if (num.value!!.length < 2) {
            num.value = "0"
            return
        }
        num.value = num.value!!.substring(0, num.value!!.length - 1)
    }

    fun onClickClearButton(view: View) {
        num.value = "0"
    }
}