package com.app.f49.activity.camdo

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.app.f49.R
import com.app.f49.TypeHeader
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.adapter.camdo.CamdoAdapter
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.ActivityCamdoBinding
import com.app.f49.model.dinhgia.CamdoDTO
import extension.init
import extension.selectedItemListener
import extension.setList
import kotlinx.android.synthetic.main.activity_camdo.*

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
        getData()
        observer()

    }

    private fun observer() {
        mViewModel?.setNavigator(this)
        mViewModel?.listStatusDTO?.observe(this, Observer {
            it?.let {
                spSelect.setList(it.map { it.value }.toMutableList(), it.indexOfFirst {
                    it.id == "1"
                })
            }
        })
        mViewModel?.listData?.observe(this, Observer {
            it?.let {
                insertData(it)
            }
        })
    }

    private fun insertData(list: MutableList<CamdoDTO>?) {
        list?.let {
            if (list.size == 0) {
                tvNoData.visibility = View.VISIBLE
            } else {
                tvNoData.visibility = View.GONE
            }
            camdoAdapter?.insertData(it)
        }
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
        spSelect?.selectedItemListener {
            if (typeHeader == TypeHeader.DO_GIA_DUNG.value) {
                mViewModel?.getListDoGiaDung(mViewModel?.listStatusDTO?.value?.get(it)?.id ?: "")
            } else if (typeHeader == TypeHeader.DINH_GIA.value) {
                mViewModel?.getListDinhGia(mViewModel?.listStatusDTO?.value?.get(it)?.id ?: "")
            } else if (typeHeader == TypeHeader.CAM_DO.value) {
                mViewModel?.getListCamDo(mViewModel?.listStatusDTO?.value?.get(it)?.id ?: "")
            }
        }
    }

    private fun initRv() {
        rvListCamdo.init(space = R.dimen.line_separator_size)
        camdoAdapter = CamdoAdapter(mutableListOf(), typeHeader)
        rvListCamdo.adapter = camdoAdapter
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return resources.getString(R.string.cam_do)
    }

    private fun getData() {
        mViewModel?.getStatus()
    }

    override fun showLoading(cancelable: Boolean) {
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }

    override fun hideLoading() {
        shimmer.visibility = View.GONE
        shimmer.stopShimmer()
    }
}