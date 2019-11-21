package kr.hs.dgsw.smartschool.fepay_android.base

import android.content.Context
import android.widget.Toast

fun String.createToast(context: Context): Toast
        = Toast.makeText(context, this, Toast.LENGTH_SHORT)