package com.app.f49.activity.infocamdo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.Base
import com.app.f49.R
import com.app.f49.databinding.ActivityInfoDinhgiaBinding
import com.app.f49.model.dinhgia.InfoDinhGiaDTO
import com.app.f49.model.dinhgia.InfoDoGiaDungDTO
import kotlinx.android.synthetic.main.activity_info_camdo.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator
import java.util.*

class InfoDinhGiaActivity : BaseMvvmActivity<ActivityInfoDinhgiaBinding, InfoDoGiaDungViewModel, BaseNavigator>() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, InfoDinhGiaActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_info_dinhgia
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var infoDinhGia = InfoDinhGiaDTO().apply {
            title = "SamSung galaxt a30"
            fullName = "Nguyen van A"
            phoneNumber = "924372823748"
            imageURL = Base.IMAGE_URL
            dateRegister = Date()
            isXyLy = true
        }
        mViewBinding?.item = infoDinhGia

    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.info_dinhgia)
    }
}