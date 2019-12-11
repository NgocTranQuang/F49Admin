package com.app.f49.activity.thulai

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ThulaiActivityBinding
import com.app.f49.fragment.picker.MyDatePickerFragment
import extension.addCurrencyFormatter
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.thulai_activity.*

class ThuLaiActivity : BaseMvvmActivity<ThulaiActivityBinding, ThuLaiViewModel, BaseNavigator>() {
    var idHopDong: Int? = null
    var idLoaiGiaoDich: Int? = null
    var nameAction: String? = null

    companion object {
        val KEY_DATA = "ID"
        fun start(context: Context?, id: Int?) {
            context?.startActivity(Intent(context, ThuLaiActivity::class.java).putExtra(KEY_DATA, id))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.thulai_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setonClick()
        observer()
        getData()
    }

    private fun getData() {
        mViewModel?.setNavigator(this)
        idHopDong = intent?.getIntExtra(KEY_DATA, 0)
        mViewModel?.getThuLaiChiTiet(idHopDong)
    }

    private fun observer() {
        mViewModel?.listLoaiGiaoDich?.observe(this, Observer {
            spSelect.setList(it?.map { it.value }?.toMutableList(), 0)
        })
        mViewModel?.thulaiDTO?.observe(this, Observer {
            mViewModel?.getLoaiGiaoDich()
            mViewBinding?.item = it
            edtPrice.setText(it?.phaiThu?.toInt()?.toString())
        })
    }

    private fun setonClick() {
        edtPrice.addCurrencyFormatter()
        spSelect.selectedItemListener {
            idLoaiGiaoDich = mViewModel?.listLoaiGiaoDich?.value?.get(it)?.id?.toIntOrNull()
            nameAction = mViewModel?.listLoaiGiaoDich?.value?.get(it)?.value

            when (mViewModel?.listLoaiGiaoDich?.value?.get(it)?.id) {
                "5" -> {
                    // thu lai
                    mViewModel?.phaiThu?.value = mViewModel?.thulaiDTO?.value?.noLai
                    edtPrice.setText(mViewModel?.phaiThu?.value?.toInt()?.toString())
                }
                "3" -> {
                    // tat toan
                    var noLai = mViewModel?.thulaiDTO?.value?.noLai ?: 0.0
                    var noGoc = mViewModel?.thulaiDTO?.value?.noGoc ?: 0.0
                    mViewModel?.phaiThu?.value = noLai + noGoc
                    edtPrice.setText(mViewModel?.phaiThu?.value?.toInt()?.toString())
                }
                else -> {
                    mViewModel?.phaiThu?.value = mViewModel?.thulaiDTO?.value?.phaiThu
                    edtPrice.setText(mViewModel?.phaiThu?.value?.toInt()?.toString())
                }
            }
        }
        cvThucHien.setOnSingleClickListener {
            if (edtPrice.text.isNullOrEmpty()) {
                showErrorDialog(getString(R.string.nhap_thu_thuc_te))
                return@setOnSingleClickListener
            }
            showAskDialog("Thực hiện thu lãi cho cửa hàng " + mViewModel?.thulaiDTO?.value?.tenCuaHang + "?") {
                mViewModel?.putThucHienThuLai(idHopDong, idLoaiGiaoDich, mViewModel?.thulaiDTO?.value?.idCuaHang, edtPrice.text.toString().replace(".", "").toDoubleOrNull(), nameAction
                    ?: "") {
                    finish()
                }
            }
        }
        tvNgayHieuLuc.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.thulaiDTO?.value?.ngayHieuLuc?.time
                ?: 0).setResultListener {
                mViewModel?.dateHieuLuc?.value = it
            }
        }
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.dong_lai)
    }
}