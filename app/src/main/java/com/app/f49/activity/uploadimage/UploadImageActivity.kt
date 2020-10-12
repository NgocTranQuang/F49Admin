package com.app.f49.activity.uploadimage

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.app.f49.R
import com.app.f49.activity.base.BaseMvvmActivity
import com.app.f49.adapter.uploadImage.UploadImageAdapter
import com.app.f49.base.BaseNavigator
import com.app.f49.bottomsheet.imageaction.BottomSheetGetImageFragment
import com.app.f49.custom.CustomGridLayoutManager
import com.app.f49.databinding.ActivityUploadImageBinding
import com.app.f49.decoration.CategoryDecoration
import com.app.f49.model.uploadImage.UploadImageDTO
import com.app.f49.extension.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_upload_image.*

class UploadImageActivity : BaseMvvmActivity<ActivityUploadImageBinding, UploadImageViewModel, BaseNavigator>() {
    var adapter = UploadImageAdapter(mutableListOf())
    var soHopDong: String = ""
    var countOfImage: Int = 0

    companion object {
        val KEY_PASS_DATA = "KEY_PASS_DATA"
        val KEY_COUNT_IMAGE = "KEY_COUNT_IMAGE"
        var CODE_UPLOAD_IMAGE = 10231
        fun start(context: Context?, soHopDong: String?, countOfImage: Int?) {
            (context as Activity)?.startActivityForResult(Intent(context, UploadImageActivity::class.java).putExtra(KEY_PASS_DATA, soHopDong).putExtra(KEY_COUNT_IMAGE, countOfImage), CODE_UPLOAD_IMAGE)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_upload_image
    }

    override fun getMyToolbar(): Toolbar? {
        return tb
    }

    override fun getTitleToolbar(): String? {
        return getString(R.string.hinh_anh)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel?.setNavigator(this)
        soHopDong = intent?.getStringExtra(KEY_PASS_DATA) ?: ""
        countOfImage = intent?.getIntExtra(KEY_COUNT_IMAGE, 0) ?: 0
        setonClick()
        initRV()
        observer()
    }

    private fun observer() {
        mViewModel?.currentIndex?.observe(this, Observer {
            it?.let {
                if (it >= 0) {
                    showDialogWithMessage("Đang up ảnh thứ ${it + 1}/${adapter.items.size}")
                }
            }
        })
        mViewModel?.finishedUploading?.observe(this, Observer
        {
            hideDialogWithMesasge()
            if (it == true) {
                showActionDialog(getString(R.string.upload_thanhcong)) {
                    var intent = Intent()
                    intent.putExtra(KEY_PASS_DATA, true)
                    setResult(RESULT_OK, intent);
                    finish()
                }
            } else {
                showToastErrorMsg(getString(R.string.upload_fail))
            }
        })
    }

    private fun initRV() {

        var layoutManager = CustomGridLayoutManager(this, 4)
        layoutManager.setScrollEnabled(false)
        rvImage.layoutManager = layoutManager
        rvImage.adapter = adapter
        rvImage.addItemDecoration(
            CategoryDecoration(
                resources?.getDimensionPixelSize(R.dimen.toolbar_half_padding_horizontal) ?: 8
            )
        )
    }

    private fun setonClick() {
        btnChooseImage.setOnSingleClickListener {
            BottomSheetGetImageFragment.show(supportFragmentManager, BottomSheetGetImageFragment.TypePickImage.MULTI_PICK) { listImage, listBase64 ->
                if ((countOfImage + adapter.items.size + listImage.size) > 10) {
                    showErrorDialog(getString(R.string.max_image))
                    return@show
                }
                var listImageUpload = mutableListOf<UploadImageDTO>()
                listImage.forEachIndexed { index, s ->
                    var uploadImage = UploadImageDTO()
                    uploadImage.uri = s
                    uploadImage.soHopDong = soHopDong
                    uploadImage.imageBase64 = listBase64.get(index)
                    listImageUpload.add(uploadImage)
                }
                adapter.insertData(listImageUpload)
            }
        }
        btnDone.setOnSingleClickListener {
            mViewModel?.listImageUpload = adapter.items
            mViewModel?.uploadImage()
        }
        btnTuChoi.setOnSingleClickListener {
            finish()
        }
    }
}