package com.app.f49.activity.camdo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.Base
import com.app.f49.R
import com.app.f49.TypeHeader
import com.app.f49.adapter.camdo.CamdoAdapter
import com.app.f49.databinding.ActivityCamdoBinding
import com.app.f49.model.dinhgia.CamdoDTO
import extension.init
import extension.selectedItemListener
import extension.setList
import kotlinx.android.synthetic.main.activity_camdo.*
import vn.com.ttc.ecommerce.activity.base.BaseMvvmActivity
import vn.com.ttc.ecommerce.base.BaseNavigator
import java.util.*

class CamdoActivity : BaseMvvmActivity<ActivityCamdoBinding, CamdoViewModel, BaseNavigator>() {
    var camdoAdapter: CamdoAdapter? = null
    var typeHeader: Int = TypeHeader.CAM_DO.value

    companion object {
        val KEY_TYPE_HEADER = "KEY_TYPE_HEADER"
        fun start(context: Context, type: Int) {
            context.startActivity(Intent(context, CamdoActivity::class.java).putExtra(KEY_TYPE_HEADER, type))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_camdo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getTypeHeader()
        initRv()
        initSP()

    }

    private fun getTypeHeader() {
        typeHeader = intent?.getIntExtra(KEY_TYPE_HEADER, TypeHeader.CAM_DO.value) ?: typeHeader
        if (typeHeader == TypeHeader.DO_GIA_DUNG.value) {
            supportActionBar?.title = resources.getString(R.string.do_gia_dung)
        } else if (typeHeader == TypeHeader.DINH_GIA.value) {
            supportActionBar?.title = resources.getString(R.string.dinh_gia)
        }
    }

    private fun initSP() {
        spSelect.setList(Base.LIST_STRING_DEFAULT)
        spSelect?.selectedItemListener {

        }

    }

    private fun initRv() {
        rvListCamdo.init(space = R.dimen.line_separator_size)
        camdoAdapter = CamdoAdapter(getData(), typeHeader)
        rvListCamdo.adapter = camdoAdapter
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return resources.getString(R.string.cam_do)
    }

    private fun getData(): MutableList<CamdoDTO> {
        var list = mutableListOf<CamdoDTO>(CamdoDTO().apply {
            name = "Tran Quang Ngoc"
            phoneNumber = "0972847859"
            nhanhieu = "xe wave"
            dateRegister = Date()
        }, CamdoDTO().apply {
            name = "Tran Quang Ngoc"
            phoneNumber = "0972847859"
            nhanhieu = "xe wave"
            dateRegister = Date()
        }, CamdoDTO().apply {
            name = "Tran Quang Ngoc"
            phoneNumber = "0972847859"
            nhanhieu = "xe wave"
            dateRegister = Date()
        }, CamdoDTO().apply {
            name = "Tran Quang Ngoc"
            phoneNumber = "0972847859"
            nhanhieu = "xe wave"
            dateRegister = Date()
        }, CamdoDTO().apply {
            name = "Tran Quang Ngoc"
            phoneNumber = "0972847859"
            nhanhieu = "xe wave"
            dateRegister = Date()
        }, CamdoDTO().apply {
            name = "Tran Quang Ngoc"
            phoneNumber = "0972847859"
            nhanhieu = "xe wave"
            dateRegister = Date()
        }, CamdoDTO().apply {
            name = "Tran Quang Ngoc"
            phoneNumber = "0972847859"
            nhanhieu = "xe wave"
            dateRegister = Date()
        })
        return list
    }


}