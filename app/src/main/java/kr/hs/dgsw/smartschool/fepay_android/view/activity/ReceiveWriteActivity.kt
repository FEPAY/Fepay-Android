package kr.hs.dgsw.smartschool.fepay_android.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_receive_write.*
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.database.TokenManager
import kr.hs.dgsw.smartschool.fepay_android.base.BaseActivity
import kr.hs.dgsw.smartschool.fepay_android.databinding.ActivityReceiveWriteBinding
import kr.hs.dgsw.smartschool.fepay_android.network.request.PostPayRequest
import kr.hs.dgsw.smartschool.fepay_android.network.service.UserService
import kr.hs.dgsw.smartschool.fepay_android.util.Utils
import kr.hs.dgsw.smartschool.fepay_android.view.CreateQrCodeActivity

class ReceiveWriteActivity : BaseActivity<ActivityReceiveWriteBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_receive_write

    val num = MutableLiveData<String>("0")

    private val service: UserService
            = Utils.RETROFIT.create(UserService::class.java)

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.handler = this

        pay.setOnClickListener {
            compositeDisposable.add(service.postPay(
                TokenManager(this).token,
                PostPayRequest(Integer.parseInt(num.value!!))
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ response ->
                when (response.code()) {
                    200 -> startActivity(Intent(this, CreateQrCodeActivity::class.java))
                }
            }, {
                Toast.makeText(this, "오류가 발생했습니다", Toast.LENGTH_SHORT).show()
            })
            )

            binding.appBar.btnBack.setOnClickListener {
                finish()
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}