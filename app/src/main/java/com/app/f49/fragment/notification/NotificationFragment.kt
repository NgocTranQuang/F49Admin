package com.app.f49.fragment.notification

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.app.f49.R
import com.app.f49.adapter.notification.NotificationAdapter
import com.app.f49.databinding.FragmentNotificationBinding
import com.app.f49.extension.handleScreenId
import com.app.f49.model.notification.NotificationDTO
import extension.init
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_notification.*
import vn.com.ttc.ecommerce.base.BaseNavigator
import vn.com.ttc.ecommerce.fragment.base.BaseMvvmFragment

class NotificationFragment : BaseMvvmFragment<FragmentNotificationBinding, NotificationViewModel, BaseNavigator>() {
    var adapter: NotificationAdapter? = null
    var idStoreCurrent: Int? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        initViewModel()
        initSpiner()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_notification
    }

    private fun initSpiner() {
//        spStore.selectedItemListener(Color.WHITE) {
//            viewModel?.getListNotification(Base.listStore.value?.get(it)?.id)
//        }
        setSpiner()
    }

    private fun initViewModel() {
        viewModel?.setNavigator(this)
        viewModel?.listNotification?.observe(this, Observer {
            it?.let {
                insertData(it)
            }
        })
        viewModel?.putReadAll?.observe(this, Observer {
            adapter?.changeRealAll()
        })

//        Base.listStore.observe(this, Observer {
//            it?.let {
//                spStore.setList(it.map { it.storeName }.toMutableList(), 0)
//            }
//        })
    }

    private fun setSpiner() {
        getMainViewModel()?.listStore?.observe(this, Observer {
            spStore.setList((it?.map { it.storeName })?.toMutableList(), getMainViewModel()?.currentPositionStore?.value)
        })
        spStore.selectedItemListener(Color.WHITE) { position ->
            var idStoreChoose = getMainViewModel()?.listStore?.value?.get(position)
            idStoreChoose?.let {
                getMainViewModel()?.currentPositionStore?.value = position
                idStoreCurrent = it.id?.toIntOrNull()
                viewModel?.getListNotification(it?.id)
            }
        }
        tvReadAll.setOnSingleClickListener {
            if ((adapter?.items?.size ?: 0) > 0)
                viewModel?.putReadAll(idStoreCurrent)
        }
    }

    override fun getMyToolbar(): View {
        return tb
    }

    private fun insertData(listNotification: MutableList<NotificationDTO>) {
        if (listNotification.size == 0) {
            tvNoData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.GONE
        }
        adapter?.insertData(listNotification)

    }

    override fun showLoading(cancelable: Boolean) {
        tvNoData.visibility = View.GONE
        rvNotificaion.visibility = View.GONE
        shimmer.visibility = View.VISIBLE
        shimmer.startShimmer()
    }

    override fun hideLoading() {
        shimmer.visibility = View.GONE
        rvNotificaion.visibility = View.VISIBLE
        shimmer.stopShimmer()
    }

    private fun initRV() {
        rvNotificaion.init(space = R.dimen.height_line_size)
        adapter = NotificationAdapter(mutableListOf(), { notificationDTO, finished ->
            context?.handleScreenId(notificationDTO.screenId, notificationDTO.itemId?.toString())
            if (notificationDTO.daDoc != true) {
                viewModel?.changeStatusNotificationToRead(notificationDTO.id) {
                    finished.invoke()
                }
            }
        }) { notificationDTO, finished ->
            showAskDialog("Bạn chắc chắn muốn xoá?") {
                viewModel?.deleteNotification(notificationDTO.id) {
                    finished.invoke()
                }
            }
        }
        rvNotificaion.adapter = adapter
    }

}