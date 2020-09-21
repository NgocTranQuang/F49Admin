package com.app.f49

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.Log
import android.widget.FrameLayout
import com.app.f49.activity.base.BaseActivity
import com.app.f49.custom.BadgedBottomNavigationBar
import com.app.f49.extension.handleScreenId
import com.app.f49.firebase.MyFirebaseMessagingService
import com.app.f49.fragment.base.BaseFragment
import com.app.f49.fragment.dashboard.DashBoardFragment
import com.app.f49.fragment.home.HomeFragment
import com.app.f49.fragment.notification.NotificationFragment
import com.app.f49.fragment.profile.ProfileFragment
import com.app.f49.model.notification.NotificationVO
import com.app.f49.utils.GeneralUtils
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import yergalizhakhan.kz.qrcodescannerkotlin.presentation.activities.main.QRCodeScanActivity


class MainActivity : BaseActivity() {

    private val RECORD_REQUEST_CODE = 101
    var mainViewModel: MainViewModel? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                showFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                showFragment(DashBoardFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                showFragment(NotificationFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_message -> {
                setupPermissions()
//                showFragment(NotificationFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_profile -> {
                showFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED)
            requestPermissions()
        else {
            permissionGranted()
        }
    }

    private fun permissionGranted() {
        startActivity(Intent(this, QRCodeScanActivity::class.java))
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA),
            RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
//                    setupPermissions()
                } else {
                    permissionGranted()
                }
            }
        }
    }

    fun showFragment(fragment: BaseFragment) {
        displayFragment(fragment)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent == null) {
            log("intent null")
        } else {
            log("intent # null")
        }
        handleIntent(intent)
    }

    fun handleIntent(intent: Intent?) {
        var idItem = intent?.getStringExtra(MyFirebaseMessagingService.ITEMID)
        var idScreen = intent?.getStringExtra(MyFirebaseMessagingService.SCREENID)
        var idNotification = intent?.getStringExtra(MyFirebaseMessagingService.NOTIFICATION_ID)
        if (idItem == null) {
            if (intent?.extras != null) {
                var notification = getDataFromBackgroundNotification(intent.extras)
                idItem = notification.itemId
                idScreen = notification.screenId
                idNotification = notification.id
            }
        }
        if (!idNotification.isNullOrEmpty()) {
            mainViewModel?.changeStatusNotificationToRead(idNotification?.toIntOrNull()) {

            }
        }
        this.handleScreenId(idNotification?.toIntOrNull(),idScreen, idItem)
    }

    fun log(msg: String) {
        Log.d("MainActivityNewIntent", msg)
    }

    private fun getDataFromBackgroundNotification(bundle: Bundle): NotificationVO {
        val notitication = NotificationVO()
        for (key in bundle.keySet()) {
            val value = bundle.get(key)
            Timber.i(MyFirebaseMessagingService.TAG + "Key: " + key + " Value: " + value)
            if (TextUtils.equals(key, MyFirebaseMessagingService.ITEMID)) {
                notitication.itemId = bundle.get(key)?.toString()
            }
            if (TextUtils.equals(key, MyFirebaseMessagingService.SCREENID)) {
                notitication.screenId = bundle.get(key)?.toString()
            }

            if (TextUtils.equals(key, MyFirebaseMessagingService.TITLE)) {
                notitication.title = bundle.get(key)?.toString()
            }

            if (TextUtils.equals(key, MyFirebaseMessagingService.NOTIFICATION_ID)) {
                notitication.id = bundle.get(key)?.toString()
            }

//            if (TextUtils.equals(key, EOfficeFirebaseMessagingService.KEY_SITE_URL)) {
//                action.setSiteUrl(bundle.get(key)!!.toString())
//            }
//            if (TextUtils.equals(key, EOfficeFirebaseMessagingService.KEY_ITEM_ID)) {
//                val itemIdStr = bundle.get(key)!!.toString()
//                action.setItemId(if (TextUtils.isEmpty(itemIdStr)) 0 else Integer.parseInt(itemIdStr))
//            }
//            if (TextUtils.equals(key, EOfficeFirebaseMessagingService.KEY_COMPANY_ID)) {
//                action.setCompanyId(bundle.get(key)!!.toString())
//            }
        }
        return notitication
    }

    private fun displayFragment(frag: BaseFragment) {
        try {
            val transaction = supportFragmentManager.beginTransaction()

            //hide other
            supportFragmentManager.fragments.forEach {
                if (it != frag) {
                    transaction.hide(it)
                }
            }

            if (!frag.isAdded) {
                transaction.add(R.id.lnContent, frag)
            } else {
                transaction.show(frag)
            }

            transaction.commit()

        } catch (e: Exception) {

        }
    }

    var navView: BadgedBottomNavigationBar? = null
    val ORDINAL_NOTIFICATION_ITEM = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configView()
        var layoutParams = container.layoutParams as FrameLayout.LayoutParams
        layoutParams.topMargin = -GeneralUtils.getStatusBarHeight(this)
        navView = findViewById(R.id.nav_view)

        navView?.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel?.getListStore()
        mainViewModel?.getTopMenu()
        showFragment(HomeFragment())
        handleIntent(intent)
        mainViewModel?.currentPositionStore?.observe(this, Observer {
            it?.let {
                if (it > -1) {
                    var id = mainViewModel?.listStore?.value?.getOrNull(it)?.id
                    mainViewModel?.getCountNotificationUnread(id?.toIntOrNull())
                }
            }
        })
        mainViewModel?.notificationCount?.observe(this, Observer {
            showBadger(it ?: 0)
        })
        navView?.showBadgeQRCode(1, 1)
    }

    private fun observer() {

    }

    fun showBadger(count: Int) {
        navView?.showBadgeNotification(ORDINAL_NOTIFICATION_ITEM, count)
    }


}
