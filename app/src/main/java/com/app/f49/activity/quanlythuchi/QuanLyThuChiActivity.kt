package com.app.f49.activity.quanlythuchi

import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.databinding.ActivityQuanlythuchiBinding
import kotlinx.android.synthetic.main.activity_quanlythuchi.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator

class QuanLyThuChiActivity : BaseMvvmActivity<ActivityQuanlythuchiBinding, QuanLyThuChiViewModel, BaseNavigator>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_quanlythuchi
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.quan_ly_thu_chi)
    }
}