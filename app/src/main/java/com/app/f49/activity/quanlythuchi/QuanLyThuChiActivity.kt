package com.app.f49.activity.quanlythuchi

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.Base
import com.app.f49.DateFilterEnum
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.activity.rutlaicuahang.RutLaiCuaHangActivity
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityQuanlythuchiBinding
import com.app.f49.extension.init
import com.app.f49.fragment.picker.MyDatePickerFragment
import com.app.f49.model.quanlythuchi.QuanLyThuChiDTO
import com.app.f49.extension.selectedItemListener
import com.app.f49.extension.setList
import com.app.f49.extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_quanlythuchi.*

class QuanLyThuChiActivity : BaseMvvmActivity<ActivityQuanlythuchiBinding, QuanLyThuChiViewModel, BaseNavigator>() {
    var listFiterByDate = DateFilterEnum.getListDateFilter()
    var adapter: QuanLyThuChiAdapter? = null
    var idStoreCurrent: String? = null
    var type: String? = ""

    companion object {
        val KEY_PASS_TYPE = "KEY_PASS_TYPE"
        fun start(context: Context, type: String? = null) {
            context.startActivity(Intent(context, QuanLyThuChiActivity::class.java).putExtra(KEY_PASS_TYPE, type))
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_quanlythuchi
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.quan_ly_thu_chi)
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
//        type = intent?.getStringExtra(KEY_PASS_TYPE)
//        if(type == ScreenIDEnum.RUT_VON.value){
//            title = "Qu"
//        }else{
//            title = getString(R.string.quan_ly_thu_chi)
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RutLaiCuaHangActivity.REQUEST_CODE) {
            if (resultCode === Activity.RESULT_OK) {
                val strEditText = data?.getBooleanExtra(RutLaiCuaHangActivity.KEY_CHECK_RELOAD, false)
                if (strEditText == true) {
                    getListQuanLyThuChi()
                }
            }
        }
    }

    private fun initRV() {
        rvQuanLyThuChi.init(space = R.dimen.height_line_size)
        adapter = QuanLyThuChiAdapter(mutableListOf())
        rvQuanLyThuChi.adapter = adapter
        mViewModel?.setNavigator(this)
    }

    private fun evenClickListener() {
        tvFromDate.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.fromDate?.value?.time
                ?: 0).setResultListener {
                mViewModel?.fromDate?.value = it
            }
        }
        tvToDate.setOnSingleClickListener {
            MyDatePickerFragment.showPicker(supportFragmentManager, mViewModel?.toDate?.value?.time
                ?: 0).setResultListener {
                mViewModel?.toDate?.value = it
            }
        }
        btnXem.setOnSingleClickListener {
            getListQuanLyThuChi()
        }
    }

    private fun initSpiner() {
        spSelectStore.selectedItemListener {
            idStoreCurrent = Base.listStore.value?.get(it)?.id
            getListQuanLyThuChi()
        }
        spSelectDay.selectedItemListener {
            mViewModel?.fromDate?.value = DateFilterEnum.get(it)?.getStartDate()
            mViewModel?.toDate?.value = DateFilterEnum.get(it)?.getEndDate()
            mViewModel?.clickable?.value = it == DateFilterEnum.FROM_TO.value
            if (it != DateFilterEnum.FROM_TO.value) {
                btnXem.visibility = View.GONE
                getListQuanLyThuChi()
            } else {
                btnXem.visibility = View.VISIBLE
            }
        }
        spSelectDay.setList(listFiterByDate.map { it.getNameDate() }.toMutableList(), 0)
    }

    fun getListQuanLyThuChi() {
        mViewModel?.getListQuanLyThuChi(idStoreCurrent?.toIntOrNull())
    }

    private fun obsever() {
        Base.listStore.observe(this, Observer {
            it?.let {
                spSelectStore.setList(it.map { it.storeName }.toMutableList(), 0)
            }
        })

        mViewModel?.listQuanLyThuChi?.observe(this, Observer {
            it?.let {
                insertData(it)
            }
        })
    }

    private fun insertData(list: MutableList<QuanLyThuChiDTO>) {
        if (list.size == 0) {
            tvNoData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.GONE
        }
        adapter?.insertData(list)

    }

    override fun showLoading(cancelable: Boolean) {
        shimmer.visibility = View.VISIBLE
        tvNoData.visibility = View.GONE
        rvQuanLyThuChi.visibility = View.GONE
        shimmer.startShimmer()
    }

    override fun hideLoading() {
        shimmer.visibility = View.GONE
        rvQuanLyThuChi.visibility = View.VISIBLE
        shimmer.stopShimmer()
    }
}