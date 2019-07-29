package com.app.f49.activity.infoContract

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.app.f49.R
import com.app.f49.ScreenIDEnum
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.activity.uploadimage.UploadImageActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityInfoContractBinding
import com.stfalcon.frescoimageviewer.ImageViewer
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_info_contract.*

class InfoContractActivity : BaseMvvmActivity<ActivityInfoContractBinding, InfoContractViewModel, BaseNavigator>() {
    var typeHD = ""
    var idHopDong: String? = null
    var countOfImage: Int = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataIntent()
        obsever()
        setOnclickListener()
    }

    private fun setOnclickListener() {
        fbUploadImage.setOnSingleClickListener {
            UploadImageActivity.start(this, mViewModel?.infoContract?.value?.numberContract,countOfImage )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == UploadImageActivity.CODE_UPLOAD_IMAGE) {
                mViewModel?.getChiTietHDCD(idHopDong ?: "")
            }
        }
    }

    private fun obsever() {
        mViewModel?.infoContract?.observe(this, Observer {
            it?.let {
                mViewBinding?.item = it
                var listURL = it.hinhAnh?.map { it.url ?: "" }?.toMutableList()
                countOfImage = listURL?.size ?: 0
                rvBanner.setListImage(listURL, 0)
                rvBanner.setEventClickBanner { view, i ->
                    ImageViewer.Builder(this, listURL).setStartPosition(i).show()
                }
            }
        })
    }

    private fun getDataIntent() {
        mViewModel?.setNavigator(this)
        idHopDong = intent?.getStringExtra(KEY_DATA_ID_ITEM)
        typeHD = intent?.getStringExtra(KEY_DATA_TITLE) ?: ScreenIDEnum.HOP_DONG_CAM_DO.value
        if (typeHD.contains(ScreenIDEnum.CAM_DO_GIA_DUNG.value)) {
            title = getString(R.string.thong_tin_do_gia_dung)
        } else if (typeHD.contains(ScreenIDEnum.HOP_DONG_TRA_GOP.value)) {
            title = getString(R.string.thong_tin_hd_tra_gop)
        } else if (typeHD.contains(ScreenIDEnum.HOP_DONG_CAM_DO.value)) {
            title = getString(R.string.info_contract)
        } else {
            title = getString(R.string.tt_hddngd)
        }
        mViewModel?.getChiTietHDCD(idHopDong ?: "")
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.chi_tiet_hop_dong_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        mViewModel?.infoContract?.value?.numberContract?.let {
            BottomSheetInfoContract.show(supportFragmentManager, idHopDong?.toIntOrNull(), it)
        }
        return super.onOptionsItemSelected(item)
    }
}