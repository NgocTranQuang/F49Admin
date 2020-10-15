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
import com.app.f49.ScreenIDEnum
import com.app.f49.TypeHopDongDashBoardEnum
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.activity.creatingContract.CreateContractActivity
import com.app.f49.activity.createOtherContract.CreateOtherContractActivity
import com.app.f49.activity.infoContract.InfoContractActivity
import com.app.f49.adapter.contract.ContractAdapter
import com.app.f49.base.BaseNavigator
import com.app.f49.bottomsheet.ContractBottomSheet
import com.app.f49.databinding.ActivityManageContractBinding
import com.app.f49.extension.init
import com.app.f49.extension.selectedItemListener
import com.app.f49.extension.setList
import com.app.f49.extension.setOnSingleClickListener
import com.app.f49.model.HopDongCamDoDTO
import kotlinx.android.synthetic.main.activity_manage_contract.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ContractActivity : BaseMvvmActivity<ActivityManageContractBinding, ContractViewModel, BaseNavigator>() {
    var adapter: ContractAdapter? = null
    var currentIdStore = ""
    var currentIdTab = ""
    var countOfInit = 0
    var typeHD = ""
    var loaiHD: Int = 0

    companion object {
        const val KEY_PASS_TYPE_HD = "KEY_PASS_TYPE_HD"
        const val ID_STORE = "ID_STORE"
        const val TYPE_HD = "TYPE_HD"
        fun start(context: Context, type: String?) {
            context.startActivity(Intent(context, ContractActivity::class.java).putExtra(KEY_PASS_TYPE_HD, type))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_manage_contract
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataIntent()
        getData()
        initRV()
        initSpiner()
        evenClickListener()
        obsever()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reloadData(rq: String?) {
        val id = rq
        getListHopDong()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private fun getDataIntent() {
        typeHD = intent?.getStringExtra(KEY_PASS_TYPE_HD) ?: ScreenIDEnum.HOP_DONG_CAM_DO.value
        if (typeHD == ScreenIDEnum.CAM_DO_GIA_DUNG.value) {
            title = getString(R.string.quan_ly_cam_do_gia_dung)
            loaiHD = TypeHopDongDashBoardEnum.HOP_DONG_CAM_DO_GIA_DUNG.value
        } else if (typeHD == ScreenIDEnum.HOP_DONG_TRA_GOP.value) {
            title = getString(R.string.quan_ly_hop_dong_tra_gop)
            loaiHD = TypeHopDongDashBoardEnum.HOP_DONG_TRA_GOP.value
        } else if (typeHD == ScreenIDEnum.HOP_DONG_CAM_DO.value) {
            title = getString(R.string.manager_contact)
            loaiHD = TypeHopDongDashBoardEnum.HOP_DONG_CAM_DO.value
        } else {
            loaiHD = TypeHopDongDashBoardEnum.HOP_DONG_DU_NO_GIAM_DAN.value
            title = getString(R.string.hddngd)
        }
    }

    override fun showLoading(cancelable: Boolean) {
        shimmer.visibility = View.VISIBLE
        tvNoData.visibility = View.GONE
        rvContract.visibility = View.GONE
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
                    val tab = tbLayout.newTab()
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
            rvContract.visibility = View.GONE
        } else {
            tvNoData.visibility = View.GONE
            rvContract.visibility = View.VISIBLE
            adapter?.insertData(list.map { it.toContractDTO() }.toMutableList())
        }
    }

    private fun evenClickListener() {
        btnFilter.setOnSingleClickListener {
            ContractBottomSheet.show(supportFragmentManager) { keySearch, idNguoiQLDH, idStatusHD ->
                getListHopDong(keySearch, idNguoiQLHD = idNguoiQLDH?.toIntOrNull(), idTrangThaiHD = idStatusHD)
            }
        }
        tbLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                currentIdTab = tab.tag as String
                getListHopDong()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }

        })
        fbAddContractImage.setOnClickListener {
            when (typeHD) {
                ScreenIDEnum.HOP_DONG_CAM_DO.value -> {
                    startActivity(Intent(this, CreateContractActivity::class.java).putExtra(ID_STORE, currentIdStore))
                }
                ScreenIDEnum.CAM_DO_GIA_DUNG.value, ScreenIDEnum.HOP_DONG_TRA_GOP.value-> {
                    startActivity(Intent(this, CreateOtherContractActivity::class.java).putExtra(ID_STORE, currentIdStore).putExtra(TYPE_HD, typeHD))
                }

            }
        }
    }

    private fun initSpiner() {
        spStore.selectedItemListener {
            currentIdStore = Base.listStore?.value?.get(it)?.id ?: ""
            getListHopDong()
            if (currentIdStore == "0") {
                fbAddContractImage.visibility = View.GONE
            } else {
                fbAddContractImage.visibility = View.VISIBLE
            }
//            mViewModel?.getListNguoiQLHD(currentIdStore)
        }
    }

    private fun getListHopDong(keySearch: String? = "", idNguoiQLHD: Int? = null, idTrangThaiHD: String? = null) {
        synchronized(this) {
            countOfInit++
            if (countOfInit >= 2) {
                mViewModel?.getListHopDong(currentIdStore.toIntOrNull(), loaiHD, currentIdTab, keySearch, false, idNguoiQLHD, idTrangThaiHD)
            }
        }
    }

    private fun initRV() {
        rvContract.init(space = R.dimen.height_line_size)
        adapter = ContractAdapter(mutableListOf()) {

            InfoContractActivity.start(this, mViewModel?.listHDCM?.value?.get(it)?.id
                ?: "", typeHD, null)

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