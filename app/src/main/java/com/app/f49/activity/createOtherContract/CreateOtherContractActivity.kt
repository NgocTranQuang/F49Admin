package com.app.f49.activity.createOtherContract

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import android.widget.Toast
import com.app.f49.R
import com.app.f49.ScreenIDEnum
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.adapter.contract.CollateralOtherContractAdapter
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityCreateContractBinding
import com.app.f49.extension.*
import com.app.f49.fragment.dialogCustom.KhachHangDialogFragment
import com.app.f49.fragment.dialogCustom.TaiSanKhacDialogFragment
import com.app.f49.fragment.picker.MyDatePickerFragment
import com.app.f49.model.createcontract.IDCuaHangDTO
import com.app.f49.model.createcontract.KhachHangDTO
import com.app.f49.model.createcontractother.*
import kotlinx.android.synthetic.main.activity_create_contract.*
import kotlinx.android.synthetic.main.activity_create_other_contract.*


class CreateOtherContractActivity : BaseMvvmActivity<ActivityCreateContractBinding, CreateContractViewModel, BaseNavigator>() {
    private val BEFORE = "Trước"
    private val AFTER = "Sau"
    private val handler = Handler()
    private var traGop: MutableList<String?>? = null
    private var tinhLai: InputTinhLaiPhi? = null
    private var tinhPhi: InputTinhLaiPhi? = null
    private var inputKhachNhan: InputTinhTienKhachNhanOtherDTO? = null
    private var taiSanKhacDialogFragment: TaiSanKhacDialogFragment = TaiSanKhacDialogFragment()
    private var collateralOtherContractAdapter: CollateralOtherContractAdapter? = null
    private var createContractViewModel: CreateContractViewModel? = null
    private var typeHD = ""
    private var idCustomer = ""
    private var currentIDStore = ""
    private var isCheck = false
    private var isGopTruoc = false
    private var listCollateral: MutableList<PropertiesCollateralDTO>? = null
    private var requestToServer: RequestOtherContractToServer? = null
    var soKi:Double = 0.0

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
        createContractViewModel = ViewModelProviders.of(this).get(CreateContractViewModel::class.java)
        currentIDStore = intent?.getStringExtra(ID_STORE).toString()
        typeHD = intent?.getStringExtra(TYPE_HD).toString()
        currentIDStore = intent?.getStringExtra("ID_STORE").toString()
        val idStore = IDCuaHangDTO()
        idStore.iDCuaHang = currentIDStore.toInt()
        requestToServer = RequestOtherContractToServer()
        traGop = mutableListOf()
        traGop?.add(BEFORE)
        traGop?.add(AFTER)
        setViewFormat()
        when (typeHD) {
            ScreenIDEnum.CAM_DO_GIA_DUNG.value -> {
                tvTitle.text = getString(R.string.hd_giadung)
                createContractViewModel?.loadTaoMoiDNGD(idStore)
            }
            ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                tvTitle.text = getString(R.string.hd_tragop)
                createContractViewModel?.loadTaoMoiTG(idStore)
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
                if (edtRateLai.text.toString().toFloat() > 100f) {
                    showToast(getString(R.string.min_Lai))
                } else {
                    tinhLai()
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
        edtRatePhi.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(p0: Editable?) {
                if (edtRateLai.text.toString().toFloat() > 100f) {
                    showToast(getString(R.string.min_phi))
                } else {
                    tinhPhi()
                }
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

    fun showToast(message: String){
        val toast =  Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
        val view:View = toast.view
        view.setBackgroundColor(resources.getColor(R.color.colorPrimary))
        toast.show()
    }

    private fun observer() {
        createContractViewModel?.item?.observe(this, Observer {
            setView(it)
        })

        createContractViewModel?.outputLai?.observe(this, Observer {
            edtTienLai.setText((it?.soTien ?: 0).toString())
        })
        createContractViewModel?.outputPhi?.observe(this, Observer {
            edtTienPhi.setText((it?.soTien ?: 0).toString())
        })

        createContractViewModel?.tienNhan?.observe(this, Observer {
            edtKhachNhanOther.setText(it?.soTienKhachNhan?.toDouble()?.let { it1 -> formatMoney(it1) })
        })

        createContractViewModel?.soHD?.observe(this, Observer {
            Toast.makeText(applicationContext, getString(R.string.create_success), Toast.LENGTH_SHORT).show()
            finish()
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
                        createContractViewModel?.tinhTienLaiDNGD(tinhLai ?: InputTinhLaiPhi())
                    }
                    ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                        createContractViewModel?.tinhTienLaiTG(tinhLai ?: InputTinhLaiPhi())
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
                        createContractViewModel?.tinhTienPhiDNGD(tinhPhi ?: InputTinhLaiPhi())
                    }
                    ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                        createContractViewModel?.tinhTienPhiTG(tinhPhi ?: InputTinhLaiPhi())
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
                        createContractViewModel?.tinhSoTienKhachNhanDNGD(inputKhachNhan
                            ?: InputTinhTienKhachNhanOtherDTO())
                    }
                    ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                        createContractViewModel?.tinhSoTienKhachNhanTG(inputKhachNhan
                            ?: InputTinhTienKhachNhanOtherDTO())
                    }
                    else -> {
                    }
                }
            }
        }
        handler.removeCallbacks(mRunnable)
        handler.postDelayed(mRunnable, 2500)
    }

    private fun setView(item: LoadTaoMoiOtherDTO?) {
        item?.apply {
            edtNgayKi.setText(soNgayTrongKy.toString())
            edtSoNgayVay.setText(soNgayVay.toString())
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
            KhachHangDialogFragment.newInstance(customer).show(supportFragmentManager, "String")
        }
        tvNgayVayOther.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, createContractViewModel?.item?.value?.ngayVay?.time
                ?: 0L).setResultListener {
                tvNgayVayOther.text = it.toSimpleString()
            }
        }
        tvNgayVaoSoOther.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, createContractViewModel?.item?.value?.ngayVaoSo?.time
                ?: 0L).setResultListener {
                tvNgayVaoSoOther.text = it.toSimpleString()
            }
        }
        tvLapHopDongOther.setOnClickListener {
            requestToServer?.thongTinHopDong = InfoContractOtherDTO().apply {
                iDCuaHang = currentIDStore
                iDKhachHang = idCustomer
                ngayVay = tvNgayVayOther.text.toString().toDate()
                ngayVaoSo = tvNgayVaoSoOther.text.toString().toDateWithTime()
                soTienVay = edtTienVayOther.text.toString().replace(".", "")
                soNgayVay = edtSoNgayVay.text.toString()
                soNgayTrongKy = edtNgayKi.text.toString()
                soKyVay = soKi.toString()
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
                    requestToServer?.let { it1 -> createContractViewModel?.luuHopDongGDTG(it1) }
                }
                ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                    requestToServer?.let { it1 -> createContractViewModel?.luuHopDongTG(it1) }
                }
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