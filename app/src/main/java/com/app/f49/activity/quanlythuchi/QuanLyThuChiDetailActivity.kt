package com.app.f49.activity.quanlythuchi

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.activity.rutlaicuahang.RutLaiCuaHangActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityQuanlythuchiDetailBinding
import com.app.f49.model.evenbus.MessageEvent
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_quanlythuchi_detail.*
import org.greenrobot.eventbus.EventBus

class QuanLyThuChiDetailActivity : BaseMvvmActivity<ActivityQuanlythuchiDetailBinding, QuanLyThuChiDetailViewModel, BaseNavigator>() {
    var idNoti: Int? = null

    companion object {
        val KEY_ID_ITEM = "KEY_ID_ITEM"
        val KEY_NOTIFICATION_ID = "KEY_NOTIFICATION_ID"

        fun start(context: Context, idItem: Int?, idNoti: Int?) {
            context.startActivity(Intent(context, QuanLyThuChiDetailActivity::class.java).putExtra(KEY_ID_ITEM, idItem).putExtra(KEY_NOTIFICATION_ID, idNoti))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_quanlythuchi_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel?.setNavigator(this)
        getExtra()
        obsever()
        onClickListener()
    }

    private fun onClickListener() {
        btnDone.setOnSingleClickListener {
            mViewModel?.duyetChi(edtYKien.text.toString(), true)
        }
        btnTuChoi.setOnSingleClickListener {
            mViewModel?.duyetChi(edtYKien.text.toString(), false)
        }
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.thong_tin_thu_chi)
    }

    private fun obsever() {
        mViewModel?.quanLyThuChiDetailDTO?.observe(this, Observer {
            mViewBinding?.item = it
        })
        mViewModel?.finished?.observe(this, Observer {
            var intent = Intent()
            intent.putExtra(RutLaiCuaHangActivity.KEY_CHECK_RELOAD, true)
            setResult(RESULT_OK, intent);
            finish()
        })
    }

    private fun hidePheDuyet() {

    }

    private fun showPheDuyet() {

    }

    private fun getExtra() {
        var idItem = intent?.getIntExtra(KEY_ID_ITEM, 0)
        idItem?.let {
            getData(it)
        }
        idNoti = intent?.getIntExtra(KEY_NOTIFICATION_ID, 0)
    }

    private fun getData(id: Int?) {
        mViewModel?.getDetailQuanLyThuChi(id)
    }

    override fun onDestroy() {
        if (idNoti != null)
            EventBus.getDefault().post(MessageEvent().apply {
                value = idNoti
            })
        super.onDestroy()
    }
}