package com.app.f49

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.FrameLayout
import com.app.f49.fragment.NotificationFragment
import com.app.f49.fragment.dashboard.DashBoardFragment
import com.app.f49.fragment.home.HomeFragment
import com.app.f49.fragment.profile.ProfileFragment
import com.app.f49.utils.GeneralUtils
import kotlinx.android.synthetic.main.activity_main.*
import vn.com.ttc.ecommerce.activity.base.BaseActivity
import vn.com.ttc.ecommerce.fragment.base.BaseFragment


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
                showFragment(NotificationFragment())
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var layoutParams = container.layoutParams as FrameLayout.LayoutParams
        layoutParams.topMargin = -GeneralUtils.getStatusBarHeight(this)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        var viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getListStore()
        showFragment(HomeFragment())
    }
}
