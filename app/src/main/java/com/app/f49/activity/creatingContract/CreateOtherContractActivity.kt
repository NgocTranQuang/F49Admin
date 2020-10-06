package com.app.f49.activity.creatingContract

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityCreateContractBinding
import com.app.f49.fragment.dialogCustom.TaiSanDialogFragment
import com.app.f49.fragment.dialogCustom.TaiSanKhacDialogFragment
import kotlinx.android.synthetic.main.activity_create_contract.*
import kotlinx.android.synthetic.main.activity_create_other_contract.*

class CreateOtherContractActivity: BaseMvvmActivity<ActivityCreateContractBinding, CreateContractViewModel, BaseNavigator>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_create_other_contract
    }
    override fun getTitleToolbar(): String? {
        return resources.getString(R.string.tao_hop_dong_gia_dung)
    }

    override fun getMyToolbar(): Toolbar? {
        return tbCreateOtherContract
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        evenClickListener()

    }

    private fun evenClickListener() {
        ivAddTaiSan.setOnClickListener {
            TaiSanKhacDialogFragment.newInstance(getString(R.string.accept), getString(R.string.accept)).show(supportFragmentManager, "String")
        }
    }
}