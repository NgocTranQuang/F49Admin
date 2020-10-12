package com.app.f49.activity.creatingContract

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityCreateContractBinding
import com.app.f49.extension.selectedItemListener
import com.app.f49.extension.setList
import com.app.f49.extension.setOnSingleClickListener
import com.app.f49.fragment.dialogCustom.KhachHangDialogFragment
import com.app.f49.fragment.dialogCustom.TaiSanKhacDialogFragment
import com.app.f49.fragment.picker.MyDatePickerFragment
import com.app.f49.model.createcontract.KhachHangDTO
import kotlinx.android.synthetic.main.activity_create_contract.*
import kotlinx.android.synthetic.main.activity_create_other_contract.*

class CreateOtherContractActivity: BaseMvvmActivity<ActivityCreateContractBinding, CreateContractViewModel, BaseNavigator>() {
    private val BEFORE = "Trước"
    private val AFTER = "Sau"
    private var traGop: MutableList<String?>? = null
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


        traGop = mutableListOf()
        traGop?.add(BEFORE)
        traGop?.add(AFTER)
        evenClickListener()
    }

    private fun evenClickListener() {
        ivAddTaiSan.setOnClickListener {
            TaiSanKhacDialogFragment.newInstance().show(supportFragmentManager, "String")
        }
        edtCustomerNameOther.setOnClickListener {
            val customer: ((KhachHangDTO?) -> Unit)? = {
                edtCustomerNameOther.text = it?.hoTen
            }
            KhachHangDialogFragment.newInstance(customer).show(supportFragmentManager, "String")
        }
        tvNgayVayOther.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, 0
                ?: 0L).setResultListener {
            }
        }
        tvNgayVaoSoOther.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, 0
                ?: 0L)
        }
        spSelectTraGop.setList(traGop, 0)
        spSelectTraGop.selectedItemListener {
            when (it) {
                0 -> {
//                    requestServer()
                }
                1 -> {
//                    requestServer(false)
                }
            }
        }
    }
}