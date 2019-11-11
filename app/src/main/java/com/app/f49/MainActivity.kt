package com.app.f49

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
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


class MainActivity : BaseActivity() {
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
        if (idItem == null) {
            if (intent?.extras != null) {
                var notification = getDataFromBackgroundNotification(intent.extras)
                idItem = notification.itemId
                idScreen = notification.screenId
            }
        }
        this.handleScreenId(idScreen, idItem)
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
        var viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getListStore()
        viewModel.getTopMenu()
        showFragment(HomeFragment())
        handleIntent(intent)
        viewModel?.currentPositionStore?.observe(this, Observer {
            it?.let {
                if (it > -1) {
                    var id = viewModel?.listStore?.value?.getOrNull(it)?.id
                    viewModel?.getCountNotificationUnread(id?.toIntOrNull())
                }
            }
        })
        viewModel?.notificationCount?.observe(this, Observer {
            showBadger(it ?: 0)
        })
        navView?.showBadgeQRCode(1,1)
    }

    private fun observer() {

    }

    fun showBadger(count: Int) {
        navView?.showBadgeNotification(ORDINAL_NOTIFICATION_ITEM, count)
    }

}
