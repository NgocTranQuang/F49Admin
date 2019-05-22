package com.app.f49.activity.infocamdo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.databinding.ActivityInfoDinhgiaBinding
import com.app.f49.model.dinhgia.CamdoDTO
import com.app.f49.model.dinhgia.InfoDinhGiaDTO
import kotlinx.android.synthetic.main.activity_info_camdo.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator

class InfoDinhGiaActivity : BaseMvvmActivity<ActivityInfoDinhgiaBinding, InfoDoGiaDungViewModel, BaseNavigator>() {
    companion object {
        val KEY_DATA = "KEY_DATA"
        fun start(context: Context, camdoDTO: CamdoDTO) {
            var intent = Intent(context, InfoDinhGiaActivity::class.java)
            intent.putExtra(KEY_DATA, camdoDTO)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_info_dinhgia
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    private fun getData() {
        var camdoDTO = intent?.getSerializableExtra(KEY_DATA) as? CamdoDTO
        camdoDTO?.let { camdoDTO ->
            var infoDinhGia = InfoDinhGiaDTO().apply {
                title = camdoDTO.name
                fullName = camdoDTO.email
                phoneNumber = camdoDTO.phoneNumber
                imageURL = camdoDTO.image
                dateRegister = camdoDTO.dateRegister
                isXyLy = camdoDTO.active
            }
            mViewBinding?.item = infoDinhGia
        }
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.info_dinhgia)
    }
}