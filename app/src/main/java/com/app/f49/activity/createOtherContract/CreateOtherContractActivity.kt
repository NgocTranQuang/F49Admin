package com.app.f49.activity.createOtherContract

import android.app.Activity
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.app.f49.R
import com.app.f49.ScreenIDEnum
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.activity.creatingContract.KhachHangViewModel
import com.app.f49.adapter.contract.CollateralOtherContractAdapter
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityCreateContractBinding
import com.app.f49.extension.*
import com.app.f49.fragment.dialogCustom.KhachHangDialogFragment
import com.app.f49.fragment.dialogCustom.NewKhachHangDialogFragment
import com.app.f49.fragment.dialogCustom.TaiSanKhacDialogFragment
import com.app.f49.fragment.picker.MyDatePickerFragment
import com.app.f49.model.createcontract.IDCuaHangDTO
import com.app.f49.model.createcontract.KhachHangDTO
import com.app.f49.model.createcontractother.*
import kotlinx.android.synthetic.main.activity_create_other_contract.*


class CreateOtherContractActivity : BaseMvvmActivity<ActivityCreateContractBinding, KhachHangViewModel, BaseNavigator>() {
    private val BEFORE = "Trước"
    private val AFTER = "Sau"
    private val handler = Handler()
    private var traGop: MutableList<String?>? = null
    private var tinhLai: InputTinhLaiPhi? = null
    private var tinhPhi: InputTinhLaiPhi? = null
    private var inputKhachNhan: InputTinhTienKhachNhanOtherDTO? = null
    private var khachHangDialogFragment: KhachHangDialogFragment? = null
    private var newKhachHangDialogFragment: NewKhachHangDialogFragment? = null
    private var taiSanKhacDialogFragment: TaiSanKhacDialogFragment = TaiSanKhacDialogFragment()
    private var collateralOtherContractAdapter: CollateralOtherContractAdapter? = null
    private var typeHD = ""
    private var idCustomer = ""
    private var currentIDStore = ""
    private var isCheck = false
    private var isGopTruoc = false
    private var listCollateral: MutableList<PropertiesCollateralDTO>? = null
    private var requestToServer: RequestOtherContractToServer? = null
    var soKi: Double = 0.0

    companion object {
        const val ID_STORE = "ID_STORE"
        const val TYPE_HD = "TYPE_HD"
    }

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
        mViewModel?.setNavigator(this)
        currentIDStore = intent?.getStringExtra(ID_STORE).toString()
        typeHD = intent?.getStringExtra(TYPE_HD).toString()
        currentIDStore = intent?.getStringExtra("ID_STORE").toString()
        val idStore = IDCuaHangDTO()
        idStore.iDCuaHang = currentIDStore.toInt()
        khachHangDialogFragment = KhachHangDialogFragment()
        newKhachHangDialogFragment = NewKhachHangDialogFragment()
        requestToServer = RequestOtherContractToServer()
        traGop = mutableListOf()
        traGop?.add(BEFORE)
        traGop?.add(AFTER)
        setViewFormat()
        when (typeHD) {
            ScreenIDEnum.CAM_DO_GIA_DUNG.value -> {
                tvTitle.text = getString(R.string.hd_giadung)
                mViewModel?.loadTaoMoiDNGD(idStore)
            }
            ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                tvTitle.text = getString(R.string.hd_tragop)
                mViewModel?.loadTaoMoiTG(idStore)
            }
        }

        observer()
        evenClickListener()
        createRecyclerView()
        setupUI(llRootItem)
    }

    private fun setViewFormat() {
        edtTienVayOther.setText("0")
        edtTienPhi.setText("0")
        edtTienVayOther.addCurrencyFormatter(textChange = {
            tinhLai()
            requestKhachNhan()
        })
        edtTienLai.addCurrencyFormatter()
        edtTienPhi.addCurrencyFormatter()
        edtRateLai.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length != 0) {

                    if (edtRateLai.text.toString().toFloat() > 100f) {
                        showToast(getString(R.string.min_Lai))
                    } else {
                        tinhLai()

                    }
                }

            }
        })
        edtSoNgayVay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                getKy()

            }
        })
        edtNgayKi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                getKy()

            }
        })
        edtRatePhi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0?.length != 0) {
                    if (edtRatePhi.text.toString().toFloat() > 100f) {
                        showToast(getString(R.string.min_phi))
                    } else {
                        tinhPhi()


                    }
                }

            }
        })
        edtSoNgayVay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                tinhLai()
                requestKhachNhan()
            }
        })
        edtNgayKi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                requestKhachNhan()

            }
        })

        cbThuPhi.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                isCheck = !isCheck
                requestKhachNhan(isGopTruoc, isCheck)
            } else if (!isChecked) {
                isCheck = !isCheck
                requestKhachNhan(isGopTruoc, isCheck)
            }
        }
        spSelectTraGop.setList(traGop, 1)
        spSelectTraGop.selectedItemListener {
            when (it) {
                0 -> {
                    isGopTruoc = true
                    requestKhachNhan(isGopTruoc, isCheck)
                }
                1 -> {
                    isGopTruoc = false
                    requestKhachNhan(isGopTruoc, isCheck)
                }
            }
        }
    }


    private fun observer() {
        mViewModel?.item2?.observe(this, Observer {
            setView(it)
        })

        mViewModel?.outputLai?.observe(this, Observer {
            edtTienLai.setText((it?.soTien ?: 0).toString())
        })
        mViewModel?.outputPhi?.observe(this, Observer {
            edtTienPhi.setText((it?.soTien ?: 0).toString())
        })

        mViewModel?.tienNhan?.observe(this, Observer {
            edtKhachNhanOther.setText(it?.soTienKhachNhan?.toDouble()?.let { it1 -> formatMoney(it1) })
        })

        mViewModel?.soHD?.observe(this, Observer {
            showToast(getString(R.string.create_success))
            finish()
        })

        mViewModel?.newKhachHang?.observe(this, Observer {
            newKhachHangDialogFragment?.dismiss()
            edtCustomerNameOther.text = it?.hoTen
            idCustomer = it?.id.toString()
        })
    }


    private fun tinhLai() {
        val mRunnable = Runnable {
            run {
                tinhLai = InputTinhLaiPhi().apply {
                    soTienVay = edtTienVayOther.text.toString().replace(".", "")
                    phanTramLai = edtRateLai.text.toString()
                    soNgayVay = edtSoNgayVay.text.toString()
                }
                when (typeHD) {
                    ScreenIDEnum.CAM_DO_GIA_DUNG.value -> {
                        mViewModel?.tinhTienLaiDNGD(tinhLai ?: InputTinhLaiPhi())
                    }
                    ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                        mViewModel?.tinhTienLaiTG(tinhLai ?: InputTinhLaiPhi())
                    }
                    else -> {
                    }
                }
            }
        }
        handler.removeCallbacks(mRunnable)
        handler.postDelayed(mRunnable, 2000)
    }

    private fun tinhPhi() {
        val mRunnable = Runnable {
            run {
                tinhPhi = InputTinhLaiPhi().apply {
                    soTienVay = edtTienVayOther.text.toString().replace(".", "")
                    phanTramPhi = edtRatePhi.text.toString()
                    soNgayVay = edtSoNgayVay.text.toString()
                }
                when (typeHD) {
                    ScreenIDEnum.CAM_DO_GIA_DUNG.value -> {
                        mViewModel?.tinhTienPhiDNGD(tinhPhi ?: InputTinhLaiPhi())
                    }
                    ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                        mViewModel?.tinhTienPhiTG(tinhPhi ?: InputTinhLaiPhi())
                    }
                    else -> {
                    }
                }
            }
        }
        handler.removeCallbacks(mRunnable)
        handler.postDelayed(mRunnable, 2000)
    }

    private fun requestKhachNhan(catLai: Boolean = false, thuPhi: Boolean = false) {
        val mRunnable = Runnable {
            run {
                inputKhachNhan = InputTinhTienKhachNhanOtherDTO().apply {
                    soTienVay = edtTienVayOther.text.toString().replace(".", "")
                    soNgayVay = edtSoNgayVay.text.toString()
                    soTienLai = edtTienLai.text.toString().replace(".", "")
                    soNgayTrongKy = edtNgayKi.text.toString()
                    tienThuPhi = edtTienPhi.text.toString().replace(".", "")
                    catLaiTruoc = catLai
                    thuPhiTruoc = thuPhi
                }
                when (typeHD) {
                    ScreenIDEnum.CAM_DO_GIA_DUNG.value -> {
                        mViewModel?.tinhSoTienKhachNhanDNGD(inputKhachNhan
                            ?: InputTinhTienKhachNhanOtherDTO())
                    }
                    ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                        mViewModel?.tinhSoTienKhachNhanTG(inputKhachNhan
                            ?: InputTinhTienKhachNhanOtherDTO())
                    }
                    else -> {
                    }
                }
            }
        }
        handler.removeCallbacks(mRunnable)
        handler.postDelayed(mRunnable, 2000)
    }

    fun getKy() {
        if(edtNgayKi.text.isNotEmpty() && edtSoNgayVay.text.isNotEmpty()){
            soKi = Math.ceil((edtSoNgayVay.text.toString().toInt() / edtNgayKi.text.toString().toInt()).toDouble())
        }

        edtSoNgayKi.setText("${soKi.toInt()} Kỳ")
    }

    private fun setView(item: LoadTaoMoiOtherDTO?) {
        item?.apply {
            edtNgayKi.setText(soNgayTrongKy.toString())
            edtSoNgayVay.setText(soNgayVay.toString())
            tvNgayVayOther.text = ngayVay?.toSimpleString()
           soKi = Math.ceil((soNgayVay!! / soNgayTrongKy!!).toDouble())
            edtSoNgayKi.setText("${soKi.toInt()} Kỳ")
            if (canChangeNgayVay) {
                tvNgayVayOther.isEnabled = true
                lnNgayVaoSoOther.visibility = View.VISIBLE
                lineUnderNgayVaoSoOther.visibility = View.VISIBLE
            } else {
                tvNgayVayOther.isEnabled = false
                lnNgayVaoSoOther.visibility = View.GONE
                lineUnderNgayVaoSoOther.visibility = View.GONE
            }
        }
    }

    private fun evenClickListener() {
        khachHangDialogFragment?.signOpenFragment = {

            val customer: ((KhachHangDTO?) -> Unit)? = {
                edtCustomerNameOther.text = it?.hoTen
                idCustomer = it?.id.toString()
            }


            newKhachHangDialogFragment?.show(supportFragmentManager, "String")

        }
        newKhachHangDialogFragment?.customer = {
            mViewModel?.luuKhachHang(it ?: KhachHangDTO())
        }
        ivAddTaiSan.setOnClickListener {
            taiSanKhacDialogFragment = TaiSanKhacDialogFragment()
            taiSanKhacDialogFragment.typeHD = typeHD
            taiSanKhacDialogFragment.collateralProperties = {
                collateralOtherContractAdapter?.insertData(it)
                requestToServer?.dSTaiSanTheChap = mutableListOf()
                requestToServer?.dSTaiSanTheChap = collateralOtherContractAdapter?.listCollateral
            }
            taiSanKhacDialogFragment.show(supportFragmentManager, "String")
        }
        edtCustomerNameOther.setOnClickListener {
            val customer: ((KhachHangDTO?) -> Unit)? = {
                edtCustomerNameOther.text = it?.hoTen
                idCustomer = it?.id.toString()
            }
            khachHangDialogFragment?.customer = customer
            khachHangDialogFragment?.show(supportFragmentManager, "String")

        }
        tvNgayVayOther.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.item2?.value?.ngayVay?.time
                ?: 0L).setResultListener {
                tvNgayVayOther.text = it.toSimpleString()
            }
        }
        tvNgayVaoSoOther.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.item2?.value?.ngayVaoSo?.time
                ?: 0L).setResultListener {
                tvNgayVaoSoOther.text = it.toSimpleString()
            }
        }
        tvLapHopDongOther.setOnClickListener {
            if (requestToServer?.dSTaiSanTheChap == null) {
                showErrorDialog(getString(R.string.not_empty_assets))
            } else {
                saveContract()
            }

        }
    }

    private fun saveContract() {
        requestToServer?.thongTinHopDong = InfoContractOtherDTO().apply {
            iDCuaHang = currentIDStore
            iDKhachHang = idCustomer
            ngayVay = tvNgayVayOther.text.toString().toDate()
            ngayVaoSo = tvNgayVaoSoOther.text.toString().toDateWithTime()
            soTienVay = edtTienVayOther.text.toString().replace(".", "")
            soNgayVay = edtSoNgayVay.text.toString()
            soNgayTrongKy = edtNgayKi.text.toString()
            soKyVay = soKi.toInt().toString()
//                ngayCatLai = tvNgayCatLai.text.toString().toDate()
            val itemSelected = spSelectTraGop.selectedItem.toString()
            catLaiTruoc = (itemSelected == BEFORE)
            soTienCatLaiTruoc = edtTienLai.text.toString().replace(".", "")
            thuPhiTruoc = isCheck
            soTienThuPhi = edtTienPhi.text.toString().replace(".", "")
            soTienKhachNhan = edtKhachNhanOther.text.toString().replace(".", "")
        }

        when (typeHD) {
            ScreenIDEnum.CAM_DO_GIA_DUNG.value -> {
                requestToServer?.let { it1 -> mViewModel?.luuHopDongGDTG(it1) }
            }
            ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                requestToServer?.let { it1 -> mViewModel?.luuHopDongTG(it1) }
            }
        }
    }

    //hide keyboard when click other
    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager: InputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus.windowToken, 0)
    }

    private fun setupUI(view: View) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (view !is EditText) {
            view.setOnTouchListener { v, event ->
                hideSoftKeyboard(this@CreateOtherContractActivity)
                false
            }
        }
        //If a layout container, iterate over children and seed recursion.
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val innerView = view.getChildAt(i)
                setupUI(innerView)
            }
        }
    }

    private fun createRecyclerView() {
        collateralOtherContractAdapter = CollateralOtherContractAdapter(mutableListOf())
        rvCollateral.layoutManager = LinearLayoutManager(this)
        rvCollateral.setHasFixedSize(true)
        rvCollateral.adapter = collateralOtherContractAdapter

        collateralOtherContractAdapter?.onClick = {
            taiSanKhacDialogFragment = TaiSanKhacDialogFragment()
            if (it != null) {
                taiSanKhacDialogFragment.collatertalOnClick = it
            }
            taiSanKhacDialogFragment.show(supportFragmentManager, "String")
        }
    }
}