package kr.hs.dgsw.smartschool.fepay_android.base

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.Response
import java.util.*

abstract class BaseActivity<VB : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: VB

    private val disposable: CompositeDisposable = CompositeDisposable()

    protected abstract val layoutId: Int

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    protected fun addDisposable(single: Single<*>, observer: DisposableSingleObserver<*>) {
        disposable.add(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer as SingleObserver<Any>) as Disposable
        )
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    protected fun checkError(response: Response<*>) {
        if (!response.isSuccessful) {
            val errorBody = JSONObject(
                Objects
                    .requireNonNull(
                        response.errorBody())?.string()
            )
            throw Exception(errorBody.getString("message"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(::binding.isInitialized) binding.unbind()
        disposable.dispose()
    }

    protected fun startActivity(activity: Class<*>) {
        startActivity(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
    }

    protected fun startActivityWithFinish(activity: Class<*>) {
        startActivityWithFinish(Intent(this, activity).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
    }

    protected fun startActivityWithFinish(intent: Intent) {
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
        finish()
    }

    protected fun startActivitiesWithFinish(vararg activityClass: Class<*>) {
        val intents: ArrayList<Intent> = ArrayList()
        for (clazz in activityClass) {
            val intent = Intent(this, clazz)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intents.add(intent)
        }
        startActivities(intents.toArray(arrayOf<Intent?>()))
        finish()
    }

    protected fun startActivityWithFinishAll(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finishAffinity()
    }

    protected fun simpleToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun simpleToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}