package com.app.githubsearch.utils

import android.net.Uri
import android.widget.TextView
import androidx.annotation.StringRes
import java.net.URL

val String.toUri : Uri
get() = Uri.parse(this)

fun String.toURL() = URL(this)