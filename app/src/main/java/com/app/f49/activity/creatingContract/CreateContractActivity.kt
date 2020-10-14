package com.app.f49.activity.creatingContract

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.adapter.contract.UploadImageCollateralAdapter
import com.app.f49.base.BaseNavigator
import com.app.f49.bottomsheet.imageaction.BottomSheetGetImageFragment
import com.app.f49.custom.CustomGridLayoutManager
import com.app.f49.databinding.ActivityCreateContractBinding
import com.app.f49.decoration.CategoryDecoration
import com.app.f49.extension.*
import com.app.f49.fragment.dialogCustom.KhachHangDialogFragment
import com.app.f49.fragment.dialogCustom.TaiSanDialogFragment
import com.app.f49.fragment.picker.MyDatePickerFragment
import com.app.f49.model.createcontract.*
import kotlinx.android.synthetic.main.activity_create_contract.*
import org.greenrobot.eventbus.EventBus

class CreateContractActivity : BaseMvvmActivity<ActivityCreateContractBinding, CreateContractViewModel, BaseNavigator>() {
    private var uploadImageCollateralAdapter: UploadImageCollateralAdapter? = null
    private val handler = Handler()
    private var catLai: MutableList<String?>? = null
    private var currentIDStore = ""
    private var idCustomer = ""
    private var khachHangViewModel: KhachHangViewModel? = null
    private var input: InputTinhTienKhachNhanDTO? = null
    private val BEFORE = "Trước"
    private val AFTER = "Sau"
    var requestToServer: RequestContractToServer? = null
    var listInfoImage: MutableList<PropertiesImageDTO> = mutableListOf()

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
        listInfoImage.add(PropertiesImageDTO().apply {
            this.name = null
            this.dataAsURL = null
            this.dataAsURLs = null
        })
        requestToServer = RequestContractToServer()

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
        setViewFormat()
        recyclerViewImage()

    }

    private fun setViewFormat() {
        edtPhi.setText("0")
        edtTienVay.setText("0")
        edtTienVay.addCurrencyFormatter(textChange = {
            requestServer()
        })
        edtKiDongLai.addCurrencyFormatter(textChange = {
            requestServer()
        })
        edtLaiSuat.addCurrencyFormatter(textChange = {
            requestServer()
        })
        edtPhi.addCurrencyFormatter(textChange = {
            requestServer()
        })
    }

    private fun recyclerViewImage() {
        uploadImageCollateralAdapter = UploadImageCollateralAdapter(listInfoImage)
        rvImageCollateral.layoutManager = CustomGridLayoutManager(this, 5)
        rvImageCollateral.setHasFixedSize(true)
        rvImageCollateral.adapter = uploadImageCollateralAdapter
        rvImageCollateral.addItemDecoration(
            CategoryDecoration(
                resources?.getDimensionPixelSize(R.dimen.toolbar_half_padding_horizontal) ?: 8
            )
        )
        uploadImageCollateralAdapter?.onClick = {
            BottomSheetGetImageFragment.show(supportFragmentManager, BottomSheetGetImageFragment.TypePickImage.MULTI_PICK) { listImage, listBase64 ->
                if ((listImage.size) > 9) {
                    showErrorDialog(getString(R.string.max_image))
                    return@show
                }
                val listImageShow = mutableListOf<PropertiesImageDTO>()

                listImage.forEachIndexed { index, s ->
                    listImageShow.add(PropertiesImageDTO().apply {
                        this.name = "Tài sản thế chấp"
                        this.dataAsURL = listBase64.get(index)
                        this.dataAsURLs = s
                    })
                }

                uploadImageCollateralAdapter?.notifyDataSetChanged()
                uploadImageCollateralAdapter?.insertData(listImageShow)

            }
        }

    }

    private fun requestServer(stt: Boolean = true) {
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
        handler.postDelayed(mRunnable, 2500)
    }

    private fun observer() {
        khachHangViewModel?.item?.observe(this, Observer {
            setView(it)
        })
        khachHangViewModel?.output?.observe(this, Observer {
            edtKhachNhan.setText(it?.soTienKhachNhan?.toDouble()?.let { it1 -> formatMoney(it1) })
            edtLai.setText(it?.soTienCatLaiTruoc?.toDouble()?.let { it1 -> formatMoney(it1) })
            tvNgayCatLai.text = it?.ngayDongLai?.toSimpleString()
            tvNgayCatLai.isEnabled = false
        })
    }


    private fun setView(item: LoadTaoMoiDTO?) {

        item?.apply {
            edtKiDongLai.setText(kyDongLai.toString())
            edtLaiSuat.setText(laiXuat.toString())
            if (canChangeNgayVay) {
                tvNgayVay.isEnabled = true
                lnNgayVaoSo.visibility = View.VISIBLE
                lineUnderNgayVaoSo.visibility = View.VISIBLE
            } else {
                tvNgayVay.isEnabled = false
                lnNgayVaoSo.visibility = View.GONE
                lineUnderNgayVaoSo.visibility = View.GONE
            }
        }

    }

    override fun onDestroy() {
        EventBus.getDefault().post("")
        super.onDestroy()
    }
    private fun evenClickListener() {
        rltTaiSan.setOnClickListener {
            val collateralProperties: ((BasePropertiesDTO) -> Unit)? = {
                tvTaiSan.text = it.tenVatCamCo
                it.hinhAnh = uploadImageCollateralAdapter?.listImage
                requestToServer?.dSTaiSanTheChap = mutableListOf(it)
            }
            TaiSanDialogFragment.newInstance(collateralProperties).show(supportFragmentManager, "String")
        }
        edtCusomerName.setOnClickListener {
            val customer: ((KhachHangDTO?) -> Unit)? = {
                edtCusomerName.text = it?.hoTen
                idCustomer = it?.id.toString()
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

        spSelectCatLai.setList(catLai, 0)
        spSelectCatLai.selectedItemListener {
            when (it) {
                0 -> {
                    requestServer()
                }
                1 -> {
                    requestServer(false)
                }
            }
        }
        tvLapHopDong.setOnClickListener {
            requestToServer?.thongTinHopDong = InfoContractCreateDTO().apply {
                iDCuaHang = currentIDStore
                iDKhachHang = idCustomer
                ngayVay = tvNgayVay.text.toString().toDate()
                ngayVaoSo = tvNgayVaoSo.text.toString().toDateWithTime()
                soTienVay = edtTienVay.text.toString().replace(".", "")
                laiXuat = edtLaiSuat.text.toString().replace(".", "")
                soNgayVay = edtKiDongLai.text.toString()
                ngayCatLai = tvNgayCatLai.text.toString().toDate()
                val itemSelected = spSelectCatLai.selectedItem.toString()
                catLaiTruoc = (itemSelected == BEFORE)
                soTienCatLaiTruoc = edtLai.text.toString().replace(".", "")
                soTienThuPhi = edtPhi.text.toString().replace(".", "")
                soTienKhachNhan = edtKhachNhan.text.toString().replace(".", "")
                ghiChu = edtNoiDung.text.toString()
            }

            uploadImageCollateralAdapter?.listImage?.apply {
                val size = uploadImageCollateralAdapter?.listImage?.size
                if (size != null && size > 0) {
                    removeAt(size - 1)
                }
            }
            khachHangViewModel?.luuHopDong(requestToServer ?: RequestContractToServer())
            khachHangViewModel?.result?.observe(this, Observer {
                finish()
            })
        }
    }
}