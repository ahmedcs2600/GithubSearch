package com.app.githubsearch.utils

import android.net.Uri
import android.widget.TextView
import androidx.annotation.StringRes

val String.toUri : Uri
get() = Uri.parse(this)