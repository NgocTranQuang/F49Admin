package com.app.f49.fragment.dialogCustom

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.app.f49.R
import com.app.f49.activity.creatingContract.CreateOtherContractActivity
import com.app.f49.adapter.contract.UploadImageCollateralAdapter
import com.app.f49.bottomsheet.imageaction.BottomSheetGetImageFragment
import com.app.f49.custom.CustomGridLayoutManager
import com.app.f49.decoration.CategoryDecoration
import com.app.f49.model.createcontract.PropertiesImageDTO
import kotlinx.android.synthetic.main.fragment_dialog_other_collateral.view.*

class TaiSanKhacDialogFragment: DialogFragment() {
    private var uploadImageCollateralAdapter: UploadImageCollateralAdapter? = null
    var listInfoImage: MutableList<PropertiesImageDTO> = mutableListOf()
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
        listInfoImage.add(PropertiesImageDTO().apply {
            this.name = null
            this.dataAsURL = null
            this.dataAsURLs = null
        })
        setupView(view)
        setupClickListeners(view)
        recyclerViewImage(view)
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
    }



    private fun setupClickListeners(view: View) {

    }
}