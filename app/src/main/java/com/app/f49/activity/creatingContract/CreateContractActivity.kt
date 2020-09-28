package com.app.f49.activity.creatingContract

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityCreateContractBinding
import com.app.f49.fragment.dialogCustom.TaiSanDialogFragment
import kotlinx.android.synthetic.main.activity_create_contract.*
import kotlinx.android.synthetic.main.activity_create_contract.rltTaiSan
import kotlinx.android.synthetic.main.activity_create_other_contract.*

class CreateContractActivity : BaseMvvmActivity<ActivityCreateContractBinding, CreateContractViewModel, BaseNavigator>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_create_contract
    }

    override fun getTitleToolbar(): String? {
        return "Tạo hợp đồng cầm đồ"
    }

    override fun getMyToolbar(): Toolbar? {
        return tbCreateContract
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        evenClickListener()

    }

    private fun evenClickListener() {
        rltTaiSan.setOnClickListener {
            TaiSanDialogFragment.newInstance(getString(R.string.accept), getString(R.string.accept)).show(supportFragmentManager, "String")
        }
    }
}