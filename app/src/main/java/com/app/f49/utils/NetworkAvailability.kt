package com.app.f49.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build

class NetworkAvailability private constructor() {
    private var connectivityManager: ConnectivityManager? = null
    private var networkCallback: ConnectivityManager.NetworkCallback? = null
    private var isFirstTimeAvailable = true
    private val connectivityChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, true)) {
                context.sendBroadcast(getNetworkAvailabilityIntent(true))
            }
        }
    }

    companion object {
        val NETWORK_AVAILABILITY_ACTION = "vn.com.ttc.eoffice.utils.NETWORK_AVAILABILITY_ACTION"
        private var instance: NetworkAvailability? = null

        fun getInstance(): NetworkAvailability {
            if (instance == null) {
                instance = NetworkAvailability()
            }
            return instance as NetworkAvailability
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            return if (connectivityManager is ConnectivityManager) {
                val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
                networkInfo?.isConnected ?: false
            } else false
        }
    }

    fun registerNetworkAvailability(context: Context, networkAvailabilityReceiver: BroadcastReceiver) {
        isFirstTimeAvailable = true
        if (!isNetworkAvailable(context)) {
            isFirstTimeAvailable = false
        }
        context.registerReceiver(networkAvailabilityReceiver, IntentFilter(NETWORK_AVAILABILITY_ACTION))

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.registerReceiver(connectivityChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        } else {
            connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val builder = NetworkRequest.Builder()
            networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    if (!isFirstTimeAvailable) {
                        context.sendBroadcast(getNetworkAvailabilityIntent(true))
                    }
                    isFirstTimeAvailable = false
                }

                override fun onLost(network: Network) {
                    context.sendBroadcast(getNetworkAvailabilityIntent(false))
                }
            }
            connectivityManager!!.registerNetworkCallback(builder.build(), networkCallback)
        }
    }

    fun unregisterNetworkAvailability(context: Context, networkAvailabilityReceiver: BroadcastReceiver) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            context.unregisterReceiver(connectivityChangeReceiver)
        } else {
            connectivityManager!!.unregisterNetworkCallback(networkCallback)
        }
        context.unregisterReceiver(networkAvailabilityReceiver)
    }

    private fun getNetworkAvailabilityIntent(isNetworkAvailable: Boolean): Intent {
        val intent = Intent(NETWORK_AVAILABILITY_ACTION)
        intent.putExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, !isNetworkAvailable)
        return intent
    }
}