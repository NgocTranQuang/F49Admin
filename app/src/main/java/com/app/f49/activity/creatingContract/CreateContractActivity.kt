package com.app.f49.activity.creatingContract

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityCreateContractBinding
import com.app.f49.fragment.dialogCustom.KhachHangDialogFragment
import com.app.f49.fragment.dialogCustom.TaiSanDialogFragment
import com.app.f49.fragment.picker.MyDatePickerFragment
import com.app.f49.model.createcontract.*
import extension.*
import kotlinx.android.synthetic.main.activity_create_contract.*
import kotlinx.android.synthetic.main.activity_create_contract.rltTaiSan

class CreateContractActivity : BaseMvvmActivity<ActivityCreateContractBinding, CreateContractViewModel, BaseNavigator>() {
    private val handler = Handler()
    private var catLai: MutableList<String?>? = null
    private var currentIDStore = ""
    private var khachHangViewModel: KhachHangViewModel? = null
    private var input: InputTinhTienKhachNhanDTO? = null
    private val BEFORE = "Trước"
    private val AFTER = "Sau"
    override fun getLayoutId(): Int {
        return R.layout.activity_create_contract
    }

    override fun getTitleToolbar(): String? {
        return resources.getString(R.string.tao_hop_dong_cam_do)
    }

    override fun getMyToolbar(): Toolbar? {
        return tbCreateContract
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        catLai = mutableListOf()
        catLai?.add(BEFORE)
        catLai?.add(AFTER)
        currentIDStore = intent?.getStringExtra("ID_STORE").toString()
        val idStore = IDCuaHangDTO()
        idStore.iDCuaHang = currentIDStore.toInt()
        khachHangViewModel = ViewModelProviders.of(this).get(KhachHangViewModel::class.java)
        khachHangViewModel?.loadTaoMoi(idStore)
        input = InputTinhTienKhachNhanDTO()
        observer()
        evenClickListener()
        edtPhi.setText("0")
        edtTienVay.setText("0")
        edtTienVay.addCurrencyFormatter(textChange = {
            requestServer()
        } )
        edtKiDongLai.addCurrencyFormatter(textChange = {
            requestServer()
        } )
        edtLaiSuat.addCurrencyFormatter(textChange = {
            requestServer()
        } )
        edtPhi.addCurrencyFormatter(textChange = {
            requestServer()
        } )
    }
    private fun requestServer(stt:Boolean = true){
        val mRunnable = Runnable {
            run {
                input?.soTienVay = edtTienVay.text.toString().replace(".", "")
                input?.laiXuat = edtLaiSuat.text.toString().replace(".", "")
                input?.soNgayVay = edtKiDongLai.text.toString()
                input?.catLaiTruoc = stt
                input?.tienThuPhi = edtPhi.text.toString().replace(".", "")
                input?.let {
                    khachHangViewModel?.tinhSoTienKhachNhan(it)
                }
            }
        }
        handler.removeCallbacks(mRunnable)
        handler.postDelayed(mRunnable, 3000)
    }
    private fun observer() {
        khachHangViewModel?.item?.observe(this, Observer {
            setView(it)
        })
        khachHangViewModel?.output?.observe(this, Observer {
            edtKhachNhan.setText(it?.soTienKhachNhan?.toDouble()?.let { it1 -> formatMoney(it1) })
            edtLai.setText(it?.soTienCatLaiTruoc?.toDouble()?.let { it1 -> formatMoney(it1) })
            it?.ngayDongLai.toString()
            tvNgayCatLai.text = it?.ngayDongLai?.toSimpleString()
        })
    }


    private fun setView(item: LoadTaoMoiDTO?) {

        item?.apply {
            edtKiDongLai.setText(kyDongLai.toString())
            edtLaiSuat.setText(laiXuat.toString())
            tvNgayVay.isEnabled = !canChangeNgayVay
            if(canChangeNgayVay){
                lnNgayVaoSo.visibility = View.VISIBLE
                lineUnderNgayVaoSo.visibility = View.VISIBLE
            }else{
                lnNgayVaoSo.visibility = View.GONE
                lineUnderNgayVaoSo.visibility = View.GONE
            }
        }

    }

    private fun evenClickListener() {

//        edtLai.addCurrencyFormatter()
//        edtPhi.addCurrencyFormatter()
//        edtKhachNhan.addCurrencyFormatter()
        rltTaiSan.setOnClickListener {
            TaiSanDialogFragment.newInstance().show(supportFragmentManager, "String")
        }
        edtCusomerName.setOnClickListener {
            val customer: ((KhachHangDTO?) -> Unit)? = {
                edtCusomerName.text = it?.hoTen
            }
            KhachHangDialogFragment.newInstance(customer).show(supportFragmentManager, "String")
        }
        tvNgayVay.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, khachHangViewModel?.item?.value?.ngayVay?.time
                ?: 0L).setResultListener {
            }
        }
        tvNgayVaoSo.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, khachHangViewModel?.item?.value?.ngayVaoSo?.time
                ?: 0L)
        }
        tvNgayCatLai.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, khachHangViewModel?.output?.value?.ngayDongLai?.time
                ?: 0L)
        }
        spSelectCatLai.setList(catLai, 0)
        spSelectCatLai.selectedItemListener{
            when(it) {
                0 -> {
                    requestServer()
                }
                1 -> {
                    requestServer(false)
                }
            }

        }
        tvLapHopDong.setOnClickListener {
            var collateralProperties: ((BasePropertiesDTO) -> Unit)? = {

            }
        }
    }
}