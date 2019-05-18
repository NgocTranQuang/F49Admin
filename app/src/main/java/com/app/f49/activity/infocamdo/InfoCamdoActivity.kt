package com.app.f49.activity.infocamdo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.databinding.ActivityInfoCamdoBinding
import com.app.f49.model.dinhgia.InfoCamdoDTO
import kotlinx.android.synthetic.main.activity_info_camdo.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator
import java.util.*

class InfoCamdoActivity : BaseMvvmActivity<ActivityInfoCamdoBinding, InfoDoGiaDungViewModel, BaseNavigator>() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, InfoCamdoActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_info_camdo
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var infoCamdo = InfoCamdoDTO().apply {
            title = "Nguye hoang anh"
            money = "30,000,000 vnd"
            phoneNumber = "009099343"
            nhanHieu = "toyota"
            describe = "laoi xe con moi"
            dateRegister = Date()
            isXyLy = true
        }
        mViewBinding?.item = infoCamdo
    }

    override fun getTitleToolbar(): String? {
        return resources.getString(R.string.info_camdo)
    }
}