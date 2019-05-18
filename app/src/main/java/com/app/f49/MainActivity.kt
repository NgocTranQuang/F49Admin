package com.app.f49

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.app.f49.fragment.home.HomeFragment
import extension.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*
import vn.com.ttc.ecommerce.activity.base.BaseActivity
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.widget.FrameLayout
import com.app.f49.fragment.dashboard.DashBoardFragment
import com.app.f49.fragment.profile.ProfileFragment
import com.app.f49.utils.GeneralUtils


class MainActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private lateinit var textMessage: TextView
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
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_message -> {
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_profile -> {
                showFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    fun showFragment(fg: Fragment) {
        lnContent.replaceFragment(fg)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var layoutParams = container.layoutParams as FrameLayout.LayoutParams
        layoutParams.topMargin = -100

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        showFragment(HomeFragment())
    }
}
