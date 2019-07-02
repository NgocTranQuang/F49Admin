package com.app.f49.activity.infocamdo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityInfoDogiadungBinding
import com.app.f49.model.dinhgia.CamdoDTO
import com.app.f49.model.dinhgia.InfoDoGiaDungDTO
import kotlinx.android.synthetic.main.activity_info_camdo.*

class InfoDoGiaDungActivity : BaseMvvmActivity<ActivityInfoDogiadungBinding, InfoDoGiaDungViewModel, BaseNavigator>() {
    companion object {
        fun start(context: Context,camdoDTO:CamdoDTO) {
            var intent = Intent(context, InfoDoGiaDungActivity::class.java)
            intent.putExtra(InfoDinhGiaActivity.KEY_DATA, camdoDTO)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_info_dogiadung
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    private fun getData() {
        var camdoDTO = intent?.getSerializableExtra(InfoDinhGiaActivity.KEY_DATA) as? CamdoDTO
        camdoDTO?.let { camdoDTO ->
            var infoDoGiaDungDTO = InfoDoGiaDungDTO().apply {
                title = camdoDTO.name
                taiSan = camdoDTO.asset
                phoneNumber = camdoDTO.phoneNumber
                dateRegister = camdoDTO.dateRegister
                isXyLy = camdoDTO.active
            }
            mViewBinding?.item = infoDoGiaDungDTO
        }
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.info_do_gia_dung)
    }
}