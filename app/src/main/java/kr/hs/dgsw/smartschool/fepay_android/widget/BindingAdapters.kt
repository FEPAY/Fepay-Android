package kr.hs.dgsw.smartschool.fepay_android.widget

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import kr.hs.dgsw.smartschool.dodamdodam_teacher.widget.extension.getParentActivity
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView,  text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity = view.getParentActivity() ?: return

    text?.observe(parentActivity, Observer { value -> view.text = value?:""})
}