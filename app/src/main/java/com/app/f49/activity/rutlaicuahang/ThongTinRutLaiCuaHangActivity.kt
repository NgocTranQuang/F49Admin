package com.app.f49.activity.rutlaicuahang

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.ScreenIDEnum
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityThongtinRutlaiBinding
import com.app.f49.model.evenbus.MessageEvent
import com.app.f49.extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_thongtin_rutlai.*
import org.greenrobot.eventbus.EventBus

class ThongTinRutLaiCuaHangActivity : BaseMvvmActivity<ActivityThongtinRutlaiBinding, ThongTinRutLaiViewModel, BaseNavigator>() {
    var idItem: String? = null
    var idNoti: Int? = null
    var typeScreen = ""

    companion object {
        val KEY_PASS_DATA = "KEY_PASS_DATA"
        val KEY_NOTIFICATION_ID = "KEY_NOTIFICATION_ID"
        val KEY_SCREEN_TYPE = "KEY_SCREEN_TYPE"
        fun start(context: Context, id: String?, screenType: String, idNoti: Int?) {
            (context as Activity).startActivityForResult(Intent(context, ThongTinRutLaiCuaHangActivity::class.java)
                .putExtra(KEY_PASS_DATA, id)
                .putExtra(KEY_SCREEN_TYPE, screenType)
                .putExtra(KEY_NOTIFICATION_ID, idNoti), RutLaiCuaHangActivity.REQUEST_CODE)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_thongtin_rutlai
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        getIntentData()
        obsever()
        eventClickListener()
    }

    private fun eventClickListener() {
        btnTuChoi.setOnSingleClickListener {
            handleAction(false)
        }
        btnDone.setOnSingleClickListener {
            handleAction(true)
        }
    }

    fun handleAction(isAccept: Boolean) {
        var yKien = edtYKien.text.toString()
        if (yKien.isNullOrBlank()) {
            showErrorDialog(getString(R.string.provide_ykien))
        } else {
            if (typeScreen.contains(ScreenIDEnum.RUT_LAI.value)) {
                mViewModel?.duyetRutLai(edtYKien.text.toString(), isAccept)
            } else {
                mViewModel?.duyetRutVon(edtYKien.text.toString(), isAccept)
            }
        }
    }

    private fun obsever() {
        mViewModel?.thongTinRutLai?.observe(this, Observer {
            it?.let {
                mViewBinding?.item = it
            }
        })
        mViewModel?.finished?.observe(this, Observer {
            var intent = Intent()
            intent.putExtra(RutLaiCuaHangActivity.KEY_CHECK_RELOAD, true)
            setResult(RESULT_OK, intent);
            finish()
        })
    }

    private fun getIntentData() {
        idItem = intent?.getStringExtra(KEY_PASS_DATA) ?: "1"
        typeScreen = intent?.getStringExtra(KEY_SCREEN_TYPE) ?: ""
        idNoti= intent?.getIntExtra(KEY_NOTIFICATION_ID,0)
        if (typeScreen.contains(ScreenIDEnum.RUT_LAI.value)) {
            title = getString(R.string.thong_tin_rut_lai)
            mViewModel?.getThongTinRutLaiChiTiet(idItem)
        } else {
            title = getString(R.string.thong_tin_rut_von)
            mViewModel?.getThongTinRutVonChiTiet(idItem)

        }
    }

    override fun onDestroy() {
        if (idNoti != null)
        EventBus.getDefault().post(MessageEvent().apply {
            value = idNoti
        })
        super.onDestroy()
    }
    private fun initViewModel() {
        mViewModel?.setNavigator(this)
    }
}