package com.app.f49.activity.infocamdo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.databinding.ActivityInfoCamdoBinding
import com.app.f49.model.dinhgia.CamdoDTO
import com.app.f49.model.dinhgia.InfoCamdoDTO
import kotlinx.android.synthetic.main.activity_info_camdo.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator

class InfoCamdoActivity : BaseMvvmActivity<ActivityInfoCamdoBinding, InfoDoGiaDungViewModel, BaseNavigator>() {
    companion object {
        fun start(context: Context, camdoDTO: CamdoDTO) {
            var intent = Intent(context, InfoCamdoActivity::class.java)
            intent.putExtra(InfoDinhGiaActivity.KEY_DATA, camdoDTO)
            context.startActivity(intent)
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
        getData()
    }

    private fun getData() {
        var camdoDTO = intent?.getSerializableExtra(InfoDinhGiaActivity.KEY_DATA) as? CamdoDTO
        camdoDTO?.let { camdoDTO ->
            var infoDinhGia = InfoCamdoDTO().apply {
                title = camdoDTO.name
                money = camdoDTO.balance
                phoneNumber = camdoDTO.phoneNumber
                nhanHieu = camdoDTO.brand
                describe = camdoDTO.description
                dateRegister = camdoDTO.date
                isXyLy = camdoDTO.active
            }
            mViewBinding?.item = infoDinhGia
        }
    }

    override fun getTitleToolbar(): String? {
        return resources.getString(R.string.info_camdo)
    }
}