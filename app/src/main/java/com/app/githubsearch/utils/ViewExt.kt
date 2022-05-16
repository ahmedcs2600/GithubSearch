package com.app.githubsearch.utils

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

fun SearchView.bindOnQueryTextListener() = callbackFlow {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(p0: String?): Boolean {
            p0?.let { trySend(it) }
            return true
        }
        override fun onQueryTextChange(p0: String?): Boolean {
            return true
        }
    })

    awaitClose {
        setOnQueryTextListener(null)
    }
}

fun View.clicks() = callbackFlow {
    setOnClickListener {
        trySend(Unit)
    }

    awaitClose {
        setOnClickListener(null)
    }
}


fun PagingDataAdapter<*, *>.bindLoadStateListener() = callbackFlow {
    val listener : (CombinedLoadStates) -> Unit = {
        trySend(it)
    }
    addLoadStateListener(listener)

    awaitClose {
        removeLoadStateListener(listener)
    }
}

fun Context.toast(@StringRes message : Int) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun Context.toast(message : String) {
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}

fun TextView.setTextVal(@StringRes resId : Int, value : String?) {
    text = context.getString(resId, if(value.isNullOrEmpty()) "N/A" else value)
}