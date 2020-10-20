package com.app.f49.fragment.dialogCustom

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.app.f49.R
import com.app.f49.ScreenIDEnum
import com.app.f49.activity.createOtherContract.CreateContractViewModel
import com.app.f49.activity.createOtherContract.CreateOtherContractActivity
import com.app.f49.adapter.contract.UploadImageCollateralAdapter
import com.app.f49.bottomsheet.imageaction.BottomSheetGetImageFragment
import com.app.f49.custom.CustomGridLayoutManager
import com.app.f49.decoration.CategoryDecoration
import com.app.f49.extension.addCurrencyFormatter
import com.app.f49.extension.selectedItemListener
import com.app.f49.extension.setList
import com.app.f49.model.createcontract.PropertiesImageDTO
import com.app.f49.model.createcontractother.PropertiesCollateralDTO
import kotlinx.android.synthetic.main.fragment_dialog_other_collateral.*
import kotlinx.android.synthetic.main.fragment_dialog_other_collateral.view.*

class TaiSanKhacDialogFragment : DialogFragment() {
    private var uploadImageCollateralAdapter: UploadImageCollateralAdapter? = null
    var listInfoImage: MutableList<PropertiesImageDTO> = mutableListOf()
    var createContractViewModel: CreateContractViewModel? = null
    var typeHD: String? = null
    var itemName: String? = null
    var itemId: Int? = null
    var taiSan: PropertiesCollateralDTO? = null
    var collateralProperties: ((PropertiesCollateralDTO) -> Unit)? = null
    var collatertalOnClick: PropertiesCollateralDTO = PropertiesCollateralDTO()

    companion object {
        fun newInstance(): TaiSanKhacDialogFragment {
            return TaiSanKhacDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_other_collateral, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createContractViewModel = ViewModelProviders.of(this).get(CreateContractViewModel::class.java)
        listInfoImage.clear()
        listInfoImage.add(PropertiesImageDTO().apply {
            this.name = null
            this.dataAsURL = null
            this.dataAsURLs = null
        })

        setupView(view)
        when (typeHD) {
            ScreenIDEnum.CAM_DO_GIA_DUNG.value -> {
                createContractViewModel?.layDanhSachTaiSanDNGD()
            }
            ScreenIDEnum.HOP_DONG_TRA_GOP.value -> {
                createContractViewModel?.layDanhSachTaiSanTG()
            }
        }
        createContractViewModel?.taiSan?.observe(this, Observer {
            spSelectTaiSanOther.setList(it?.map { it.tenVatCamCo }?.toMutableList(), 0)
        })

        setupClickListeners(view)
        recyclerViewImage(view)
        collatertalOnClick?.apply {

            if(collatertalOnClick.tenVatCamCo != null){
                spSelectTaiSanOther.setList(mutableListOf(collatertalOnClick.tenVatCamCo), 0)
            }

            edtViTri.setText(viTriDeDo)
            edtDinhGia.setText(dinhGia)
            edtMota.setText(moTa)
            collatertalOnClick.hinhAnh?.let { uploadImageCollateralAdapter?.insertData(it) }
        }
    }


    private fun recyclerViewImage(view: View) {
        view.apply {
            uploadImageCollateralAdapter = UploadImageCollateralAdapter(listInfoImage)
            rvImageCollateralOther.layoutManager = activity?.let { CustomGridLayoutManager(it, 4) }
            rvImageCollateralOther.setHasFixedSize(true)
            rvImageCollateralOther.adapter = uploadImageCollateralAdapter
            rvImageCollateralOther.addItemDecoration(
                CategoryDecoration(
                    resources?.getDimensionPixelSize(R.dimen.toolbar_half_padding_horizontal) ?: 8
                )
            )
            uploadImageCollateralAdapter?.onClick = {
                fragmentManager?.let { it1 ->
                    BottomSheetGetImageFragment.show(it1, BottomSheetGetImageFragment.TypePickImage.MULTI_PICK) { listImage, listBase64 ->
                        if ((listImage.size) > 9) {
                            (activity as CreateOtherContractActivity).showErrorDialog(getString(R.string.max_image))
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
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }

    private fun setupView(view: View) {
        edtDinhGia.addCurrencyFormatter()

    }


    private fun setupClickListeners(view: View) {
        spSelectTaiSanOther.selectedItemListener {

            val list = createContractViewModel?.taiSan?.value
            itemName = list?.get(it)?.tenVatCamCo
            itemId = list?.get(it)?.id

        }
        tvCloseOther.setOnClickListener {
            dismiss()
        }
        tvSaveOther.setOnClickListener {
            taiSan = PropertiesCollateralDTO().apply {
                tenVatCamCo = itemName
                iDVatCamCo = itemId.toString()
                moTa = edtMota.text.toString()
                dinhGia = edtDinhGia.text.toString().replace(".", "")
                viTriDeDo = edtViTri.text.toString()
                hinhAnh = uploadImageCollateralAdapter?.listImage
            }
            uploadImageCollateralAdapter?.listImage?.apply {
                val size = uploadImageCollateralAdapter?.listImage?.size
                if (size != null && size > 0) {
                    removeAt(size - 1)
                }
            }
            collateralProperties?.invoke(taiSan ?: PropertiesCollateralDTO())
            dismiss()
        }
    }
}