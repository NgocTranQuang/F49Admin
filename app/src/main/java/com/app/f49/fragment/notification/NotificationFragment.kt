package com.app.f49.fragment.notification

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.app.f49.R
import com.app.f49.adapter.notification.NotificationAdapter
import com.app.f49.base.BaseNavigator
import com.app.f49.databinding.FragmentNotificationBinding
import com.app.f49.extension.handleScreenId
import com.app.f49.fragment.base.BaseMvvmFragment
import com.app.f49.model.notification.NotificationDTO
import extension.init
import extension.selectedItemListener
import extension.setList
import extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.fragment_notification.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class NotificationFragment : BaseMvvmFragment<FragmentNotificationBinding, NotificationViewModel, BaseNavigator>() {
    var adapter: NotificationAdapter? = null
    var idStoreCurrent: Int? = null
    var pageIndex = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRV()
        initView()
        initViewModel()
        initSpiner()
    }

    override fun getLayoutResource(): Int {
        return R.layout.fragment_notification
    }

    private fun initSpiner() {
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
            getUnreadNotification()
        })

//        Base.listStore.observe(this, Observer {
//            it?.let {
//                spStore.setList(it.map { it.storeName }.toMutableList(), 0)
//            }
//        })
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

    private fun initView() {
        swipeRefreshLayout.setOnRefreshListener {
            pageIndex = 0
            viewModel?.getListNotification(idStoreCurrent.toString(), pageIndex)
        }
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
                viewModel?.getListNotification(idStoreCurrent.toString(), pageIndex)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: NotificationDTO) {
        adapter?.insertOnTop(event)
    };

    private fun insertData(listNotification: MutableList<NotificationDTO>) {
        if (listNotification.size == 0) {
            tvNoData.visibility = View.VISIBLE
        } else {
            tvNoData.visibility = View.GONE
        }
        swipeRefreshLayout.isRefreshing = false
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
        adapter = NotificationAdapter(mutableListOf(), rvNotificaion, { notificationDTO, finished ->
            context?.handleScreenId(notificationDTO.screenId, notificationDTO.itemId?.toString())
            if (notificationDTO.daDoc != true) {
                viewModel?.changeStatusNotificationToRead(notificationDTO.id) {
                    finished.invoke()
                    getUnreadNotification()
                }
            }
        }) { notificationDTO, finished ->
            showAskDialog(getString(R.string.ban_muon_xoa)) {
                viewModel?.deleteNotification(notificationDTO.id) {
                    finished.invoke()
                    getUnreadNotification()
                }
            }
        }
        rvNotificaion.adapter = adapter
        adapter?.setLoadMoreListener {
            pageIndex++
            viewModel?.getListNotification(idStoreCurrent.toString(), pageIndex)
        }
    }

    fun getUnreadNotification() {
        getMainViewModel().currentPositionStore.value = getMainViewModel().listStore.value?.indexOfFirst {
            it.id?.toIntOrNull() == idStoreCurrent
        }
    }

}