package com.app.f49.activity.rutlaicuahang

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.Base
import com.app.f49.R
import com.app.f49.ScreenIDEnum
import com.app.f49.adapter.rutlai.RutLaiAdapter
import com.app.f49.databinding.ActivityRutlaiBinding
import com.app.f49.model.rutlai.RutLaiDTO
import extension.init
import extension.selectedItemListener
import extension.setList
import kotlinx.android.synthetic.main.activity_rutlai.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator


class RutLaiCuaHangActivity : BaseMvvmActivity<ActivityRutlaiBinding, RutLaiCuaHangViewModel, BaseNavigator>() {

    var curentIdStore: String? = null
    var currentIdTab: String = ""
    var countOfInit = 0
    var adapter: RutLaiAdapter? = null
    var typeScreen = ""

    companion object {
        val KEY_CHECK_RELOAD = "IS_RELOAD_DATA"
        val KEY_TYPE_SCREEN = "KEY_TYPE_SCREEN"
        val REQUEST_CODE = 1
        fun start(context: Context, typeScreen: String) {
            context.startActivity(Intent(context, RutLaiCuaHangActivity::class.java).putExtra(KEY_TYPE_SCREEN, typeScreen))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_rutlai
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        getDataIntent()
        getData()
        initView()
        obsever()
        eventClickListener()
    }

    private fun getDataIntent() {
        typeScreen = intent?.getStringExtra(KEY_TYPE_SCREEN) ?: ""
        if (typeScreen == ScreenIDEnum.RUT_LAI.value) {
            title = getString(R.string.rut_lai_cua_hang)
        } else {
            title = getString(R.string.rut_von)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === REQUEST_CODE) {
            if (resultCode === Activity.RESULT_OK) {
                val strEditText = data?.getBooleanExtra(KEY_CHECK_RELOAD, false)
                if (strEditText == true) {
                    getHDCM()
                }
            }
        }
    }

    private fun eventClickListener() {
        tbLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                currentIdTab = tab.tag as String
                getHDCM()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
    }


    private fun getHDCM() {
        synchronized(this) {
            countOfInit++
            if (countOfInit >= 2) {
                if (typeScreen == ScreenIDEnum.RUT_LAI.value) {
                    mViewModel?.getListRutLai(curentIdStore?.toInt(), currentIdTab.toInt())
                } else {
                    mViewModel?.getListRutVon(curentIdStore?.toInt(), currentIdTab.toInt())

                }
            }
        }
    }

    private fun obsever() {
        mViewModel?.listTab?.observe(this, Observer {
            it?.let {
                currentIdTab = it?.get(0).id ?: ""
                it.forEach {
                    var tab = tbLayout.newTab()
                    tab.tag = it.id
                    tab.text = it.tenTrangThai
                    tbLayout.addTab(tab)
                }
            }
        })
        Base.listStore.observe(this, Observer {
            it?.let {
                spSelectStore.setList(it.map { it.storeName }.toMutableList(), 0)
            }
        })
        mViewModel?.listDataHopDong?.observe(this, Observer {
            it?.let {
                insertData(it)
            }
        })
    }

    private fun insertData(list: MutableList<RutLaiDTO>) {
        if (list.size == 0) {
            tvNoData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.GONE
        }
        adapter?.insertData(list)

    }


    private fun initView() {
        spSelectStore?.selectedItemListener {
            curentIdStore = Base.listStore.value?.get(it)?.id
            getHDCM()
        }
        rvRutLai.init(space = R.dimen.height_line_size)
        adapter = RutLaiAdapter(mutableListOf(), typeScreen)
        rvRutLai.adapter = adapter

    }

    override fun showLoading(cancelable: Boolean) {
        shimmer.visibility = View.VISIBLE
        tvNoData.visibility = View.GONE
        rvRutLai.visibility = View.GONE
    }

    override fun hideLoading() {
        shimmer.visibility = View.GONE
        rvRutLai.visibility = View.VISIBLE
    }

    private fun getData() {
        mViewModel?.getListTab()
    }

    private fun initViewModel() {
        mViewModel?.setNavigator(this)
    }

}