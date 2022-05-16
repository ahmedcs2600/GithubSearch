package com.app.githubsearch.utils

import android.net.Uri
import android.widget.TextView
import androidx.annotation.StringRes

val String.toUri : Uri
get() = Uri.parse(this)

fun TextView.setText(@StringRes resId : Int, value : String?) {
    text = context.getString(resId, if(value.isNullOrEmpty()) "N/A" else value)
}