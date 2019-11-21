package kr.hs.dgsw.smartschool.fepay_android.database

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences

class TokenManager(context: Context) : ContextWrapper(context) {
    var token: String = ""
        @SuppressLint("CheckResult")
        get() {
            val sharedPreferences: SharedPreferences = getSharedPreferences("weknot", Context.MODE_PRIVATE)
            val rxPreferences = RxSharedPreferences.create(sharedPreferences)
            val tokenObservable: Preference<String> = rxPreferences.getString("token")
            tokenObservable.asObservable().subscribe { token: String -> field = token }
            return field
        }
        @SuppressLint("ApplySharedPref")
        set(token) {
            val sharedPreferences: SharedPreferences = getSharedPreferences("weknot", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("token", token)
            editor.commit()
        }
}