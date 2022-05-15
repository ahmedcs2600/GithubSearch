package com.app.network.core

import android.content.Context
import com.app.network.R
import javax.inject.Inject
import javax.inject.Singleton

class NetworkErrorProvider(val context: Context) {

    fun somethingWentWrong() = context.getString(R.string.something_went_wrong)

    fun unableToGetTheResponse() = context.getString(R.string.unable_to_get_the_response)

    fun internetConnectionMessage() = context.getString(R.string.internet_connection_message)
}