package com.app.f49.custom

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.app.f49.R

class CustomProgressDialog : AlertDialog {
    constructor(context: Context, theme: Int) : super(context, theme)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.progress_dialog_layout)
    }
}