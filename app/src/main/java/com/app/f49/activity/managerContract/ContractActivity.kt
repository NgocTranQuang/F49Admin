package com.app.f49.activity.managerContract

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.Base
import com.app.f49.R
import com.app.f49.activity.infoContract.InfoContractActivity
import com.app.f49.adapter.contract.ContractAdapter
import com.app.f49.bottomsheet.ContractBottomSheet
import com.app.f49.databinding.ActivityManageContractBinding
import com.app.f49.model.HopDongCamDoDTO
import extension.init
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_manage_contract.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator


class ContractActivity : BaseMvvmActivity<ActivityManageContractBinding, ContractViewModel, BaseNavigator>() {
    var adapter: ContractAdapter? = null
    var currentIdStore = ""
    var currentIdTab = ""

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ContractActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_manage_contract
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.manager_contact)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
        initRV()
        initSpiner()
        evenClickListener()
        obsever()
    }

    override fun showLoading(cancelable: Boolean) {
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }

    override fun hideLoading() {
        shimmer.visibility = View.GONE
        shimmer.stopShimmer()
    }

    private fun obsever() {
        mViewModel?.setNavigator(this)
        mViewModel?.listTab?.observe(this, Observer {
            it?.let {
                currentIdTab = it?.get(0).id ?: ""
                it.forEach {
                    var tab = tbLayout.newTab()
                    tab.tag = it.id
                    tab.text = it.value
                    tbLayout.addTab(tab)
                }
            }
        })
        Base.listStore.observe(this, Observer {
            it?.let {
                spStore.setList(it.map { it.storeName }.toMutableList(), 0)
            }
        })
        mViewModel?.listHDCM?.observe(this, Observer {
            it?.let {
                insertDataToRV(it)
            }
        })
    }

    private fun insertDataToRV(list: MutableList<HopDongCamDoDTO>) {
        if (list.size == 0) {
            tvNoData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.GONE
            rvContract.visibility = View.VISIBLE
            adapter?.insertData(list.map { it.toContractDTO() }.toMutableList())
        }
    }

    private fun evenClickListener() {
        btnFilter.setOnSingleClickListener {
            ContractBottomSheet.show(supportFragmentManager) { keySearch, idNguoiQLDH, idStatusHD ->
                rvContract.visibility = View.GONE
                mViewModel?.getHDCM(currentIdStore.toIntOrNull(), currentIdTab, keySearch, false, idNguoiQLDH?.toIntOrNull(), idStatusHD)
            }
        }
        tbLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                currentIdTab = tab.tag as String
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })

    }

    private fun initSpiner() {
        spStore.selectedItemListener {
            currentIdStore = Base.listStore?.value?.get(it)?.id ?: ""
            mViewModel?.getHDCM(currentIdStore.toIntOrNull(), currentIdTab, "", false, null, null)
            mViewModel?.getListNguoiQLHD(currentIdStore)
        }
    }

    private fun initRV() {
        rvContract.init(space = R.dimen.height_line_size)
        adapter = ContractAdapter(mutableListOf()) {
            InfoContractActivity.start(this, mViewModel?.listHDCM?.value?.get(it) ?: return@ContractAdapter)

        }
        rvContract.adapter = adapter
    }

    private fun getData() {
        mViewModel?.getListTab()
        mViewModel?.getListStatusContract()
    }


    fun getStringBy(id: Int): String {
        return resources.getString(id)
    }
}