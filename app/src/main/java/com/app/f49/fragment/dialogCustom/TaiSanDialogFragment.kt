package com.app.f49.fragment.dialogCustom

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.app.f49.R
import com.app.f49.activity.creatingContract.KhachHangViewModel
import com.app.f49.adapter.contract.PropertiesCollateralAdapter
import com.app.f49.model.createcontract.*
import extension.selectedItemListener
import extension.setList
import kotlinx.android.synthetic.main.fragment_dialog_collateral.*

class TaiSanDialogFragment : DialogFragment() {
    private var propertiesCollateralAdapter: PropertiesCollateralAdapter? = null
    private var khachHangViewModel: KhachHangViewModel? = null
    var propertiesVehicleDTO: PropertiesVehicleDTO? = null
    var propertiesLaptopDTO: PropertiesLaptopDTO? = null
    var propertiesOtherDTO: PropertiesOtherDTO? = null
    var itemName: String? = null
    var itemId: Int? = null
    var collateralProperties: ((BasePropertiesDTO) -> Unit)? = null
    companion object {
        fun newInstance(): TaiSanDialogFragment {
            return TaiSanDialogFragment()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_collateral, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        khachHangViewModel = ViewModelProviders.of(this).get(KhachHangViewModel::class.java)
        setupClickListeners(view)
        khachHangViewModel?.layDanhSachTaiSan()
        createRecyclerView()
        obsever()

    }

    private fun createRecyclerView() {
        propertiesCollateralAdapter = PropertiesCollateralAdapter(mutableListOf())
        rvProperties.layoutManager = GridLayoutManager(activity, 1)
        rvProperties.setHasFixedSize(true)
        rvProperties.adapter = propertiesCollateralAdapter
    }

    private fun obsever() {
        khachHangViewModel?.taiSan?.observe(this, Observer {
            spSelectTaiSan.setList(it?.map { it.tenVatCamCo }?.toMutableList(), 0)
        })
        khachHangViewModel?.thuocTinh?.observe(this, Observer {
            propertiesCollateralAdapter?.insertData(it ?: mutableListOf())
        })
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }


    private fun setupClickListeners(view: View) {

        spSelectTaiSan.selectedItemListener {

            val list = khachHangViewModel?.taiSan?.value
            itemName = list?.get(it)?.tenVatCamCo
            itemId = list?.get(it)?.id
            list?.get(it)?.loai?.let { it1 -> khachHangViewModel?.layThuocTinhTaiSan(it1) }

        }
        tvClose.setOnClickListener { dismiss() }
        tvSave.setOnClickListener {
            when (itemName) {
                "O tô", "Xe máy" -> {
                    val vehicleDTO = getObject(PropertiesVehicleDTO())
                    collateralProperties?.invoke(vehicleDTO)
                }
                "Laptop" -> {
                    val laptopDTO = getObject(PropertiesLaptopDTO())
                    collateralProperties?.invoke(laptopDTO)
                }
                else -> {
                    val otherDTO = getObject(PropertiesOtherDTO())
                    collateralProperties?.invoke(otherDTO)
                }
            }

        }
    }

    private fun getObject(propertiesDTO: BasePropertiesDTO): BasePropertiesDTO {
        val listProperties = propertiesCollateralAdapter?.properties
        listProperties?.forEach {
            val keyPro = it.key?.decapitalize()
            val field = propertiesDTO.javaClass.getDeclaredField(keyPro)
            if (field != null) {
                field.isAccessible = true
                field.set(propertiesDTO, it.value)
            }
        }
        propertiesDTO.iDVatCamCo = itemId.toString()
        propertiesDTO.tenVatCamCo = itemName
        return propertiesDTO
    }
}