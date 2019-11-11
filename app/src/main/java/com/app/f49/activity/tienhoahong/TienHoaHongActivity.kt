package com.app.f49.activity.tienhoahong

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.DateFilterEnum
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityTienhoahongBinding
import com.app.f49.extension.toPrice
import com.app.f49.fragment.picker.MyDatePickerFragment
import com.app.f49.model.nhanvien.HoaHongDTO
import extension.init
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_tienhoahong.*

class TienHoaHongActivity : BaseMvvmActivity<ActivityTienhoahongBinding, TienHoaHongViewModel, BaseNavigator>() {

    var listFiterByDate = DateFilterEnum.getListDateFilter()
    var adapter: TienHoaHongAdapter? = null
    var idNhanVienCurrent: Int? = null
    var type: String? = ""

    companion object {
        val KEY_PASS_TYPE = "KEY_PASS_TYPE"
        fun start(context: Context) {
            context.startActivity(Intent(context, TienHoaHongActivity::class.java))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_tienhoahong
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.tien_hoa_hong)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getDataIntent()
        initRV()
        obsever()
        initSpiner()
        evenClickListener()

    }

    private fun getDataIntent() {
        mViewModel?.getListNhanVien()
    }


    private fun initRV() {
        rvTienHoaHong.init(space = R.dimen.height_line_size)
        adapter = TienHoaHongAdapter(mutableListOf())
        rvTienHoaHong.adapter = adapter
        mViewModel?.setNavigator(this)
    }

    private fun evenClickListener() {
        tvFromDate.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.fromDate?.value?.time
                ?: 0).setResultListener {
                mViewModel?.fromDate?.value = it
                getListHoaHong()
            }
        }
        tvToDate.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.toDate?.value?.time
                ?: 0).setResultListener {
                mViewModel?.toDate?.value = it
                getListHoaHong()
            }
        }
        btnXem.setOnSingleClickListener {
            getListHoaHong()
        }
    }

    private fun initSpiner() {
        spNhanVien.selectedItemListener {
            idNhanVienCurrent = mViewModel?.listNhanVien?.value?.get(it)?.id
            getListHoaHong()
        }
//        spSelectDay.selectedItemListener {
//            mViewModel?.fromDate?.value = DateFilterEnum.get(it)?.getStartDate()
//            mViewModel?.toDate?.value = DateFilterEnum.get(it)?.getEndDate()
//            mViewModel?.clickable?.value = it == DateFilterEnum.FROM_TO.value
//            if (it != DateFilterEnum.FROM_TO.value) {
//                btnXem.visibility = View.GONE
//                getListHoaHong()
//            } else {
//                btnXem.visibility = View.VISIBLE
//            }
//        }
//        spSelectDay.setList(listFiterByDate.map { it.getNameDate() }.toMutableList(), 0)
    }

    fun getListHoaHong() {
        mViewModel?.getListHoaHong(idNhanVienCurrent)
    }

    private fun obsever() {

        mViewModel?.listHoaHong?.observe(this, Observer {
            it?.let {
                insertData(it)
            }
        })

        mViewModel?.listNhanVien?.observe(this, Observer {
            it?.let {
                spNhanVien.setList(it.map { it.hoTen }.toMutableList(), 0)
            }
        })
    }

    private fun insertData(list: MutableList<HoaHongDTO.HoaHongItem>) {
        if (list.size == 0) {
            tvNoData.visibility = View.VISIBLE
            rlTongTien.visibility = View.GONE
        } else {
            tvNoData.visibility = View.GONE
            rlTongTien.visibility = View.VISIBLE
        }
        var sotien = 0.0;
        for (item in list) {
            sotien += item.soTienTinhTong
        }
        tvTongTien.text = "${sotien.toPrice()}"
        adapter?.insertData(list)

    }

    override fun showLoading(cancelable: Boolean) {
        shimmer.visibility = View.VISIBLE
        tvNoData.visibility = View.GONE
        rvTienHoaHong.visibility = View.GONE
        shimmer.startShimmer()
    }

    override fun hideLoading() {
        shimmer.visibility = View.GONE
        rvTienHoaHong.visibility = View.VISIBLE
        shimmer.stopShimmer()
    }
}
