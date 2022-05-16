package com.app.githubsearch.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.intentActionView(uri : Uri) {
    val browserIntent = Intent(Intent.ACTION_VIEW, uri)
    startActivity(browserIntent)
}