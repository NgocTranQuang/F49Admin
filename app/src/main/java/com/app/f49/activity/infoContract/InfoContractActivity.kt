package com.app.f49.activity.infoContract

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.ScreenIDEnum
import com.app.f49.activity.managerContract.ContractActivity
import com.app.f49.databinding.ActivityInfoContractBinding
import kotlinx.android.synthetic.main.activity_info_contract.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator

class InfoContractActivity : BaseMvvmActivity<ActivityInfoContractBinding, InfoContractViewModel, BaseNavigator>() {
    var typeHD = ""

    companion object {
        val KEY_DATA_TITLE = "KEY_DATA_TITLE"
        val KEY_DATA_ID_ITEM = "KEY_DATA_ID_ITEM"

        fun start(context: Context, idItem: String, title: String?) {
            context.startActivity(Intent(context, InfoContractActivity::class.java).putExtra(KEY_DATA_ID_ITEM, idItem).putExtra(KEY_DATA_TITLE, title))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_info_contract
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.info_contract)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataIntent()
        setData()
        obsever()
    }

    private fun obsever() {
        mViewModel?.infoContract?.observe(this, Observer {
            it?.let {
                mViewBinding?.item = it
            }
        })
    }

    private fun getDataIntent() {
        typeHD = intent?.getStringExtra(ContractActivity.KEY_PASS_TYPE_HD) ?: ScreenIDEnum.HOP_DONG_CAM_DO.value
        if (typeHD.contains(ScreenIDEnum.CAM_DO_GIA_DUNG.value)) {
            title = getString(R.string.thong_tin_do_gia_dung)
        } else if (typeHD.contains(ScreenIDEnum.HOP_DONG_TRA_GOP.value)) {
            title = getString(R.string.thong_tin_hd_tra_gop)
        } else {
            title = getString(R.string.info_contract)
        }
    }

    private fun setData() {
        mViewModel?.setNavigator(this)

        var idItem = intent?.getStringExtra(KEY_DATA_ID_ITEM)
        mViewModel?.getChiTietHDCD(idItem ?: "")

    }
}