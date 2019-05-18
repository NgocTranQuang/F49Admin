package com.app.f49.activity.infocamdo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.databinding.ActivityInfoDinhgiaBinding
import com.app.f49.databinding.ActivityInfoDogiadungBinding
import com.app.f49.model.dinhgia.InfoDoGiaDungDTO
import kotlinx.android.synthetic.main.activity_info_camdo.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator
import java.util.*

class InfoDoGiaDungActivity : BaseMvvmActivity<ActivityInfoDogiadungBinding, InfoDoGiaDungViewModel, BaseNavigator>() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, InfoDoGiaDungActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_info_dogiadung
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var infoDoGiaDungDTO = InfoDoGiaDungDTO().apply {
            title = "SamSung galaxy s7"
            phoneNumber = "0988373623"
            taiSan = "Noi com dien"
            dateRegister = Date()
            isXyLy = false
        }
        mViewBinding?.item = infoDoGiaDungDTO
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.info_do_gia_dung)
    }
}