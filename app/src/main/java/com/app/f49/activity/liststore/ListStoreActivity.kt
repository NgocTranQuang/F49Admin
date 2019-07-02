package com.app.f49.activity.liststore

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseActivity
import com.app.f49.adapter.store.ListStoreAdapter
import com.app.f49.model.store.StoreDTO
import extension.init
import kotlinx.android.synthetic.main.activity_list_store.*

class ListStoreActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ListStoreActivity::class.java))
        }
    }

    var adapter: ListStoreAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_store)
        initRV()
        configView()
    }

    private fun initRV() {
        rvListStore.init(space = R.dimen.height_line_size)
        adapter = ListStoreAdapter(getData())
        rvListStore.adapter = adapter
    }

    private fun getData(): MutableList<StoreDTO> {
        var list = mutableListOf(StoreDTO().apply {
            storeName = "Cua hang f49"
            isChoose = true
        }, StoreDTO().apply {
            storeName = "Cua hang f47"
            isChoose = false
        }, StoreDTO().apply {
            storeName = "Cua hang f45"
            isChoose = false
        }, StoreDTO().apply {
            storeName = "Cua hang f40"
            isChoose = false
        }, StoreDTO().apply {
            storeName = "Cua hang f44"
            isChoose = false
        })
        return list
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return resources.getString(R.string.store_default)
    }
}