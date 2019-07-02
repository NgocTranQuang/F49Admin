package com.app.f49.fragment.picker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import java.util.*


class MyDatePickerFragment : DialogFragment() {
    var resutl: ((Date) -> Unit)? = null

    companion object {
        val KEY_TIME_STAMP = "KEY_TIME_STAMP"
        fun showPicker(fm: FragmentManager, timestamp: Long): MyDatePickerFragment {
            val newFragment = MyDatePickerFragment()
            val args = Bundle()
            args.putLong(KEY_TIME_STAMP, timestamp)
            newFragment.setArguments(args)
            newFragment.show(fm, "date picker")
            return newFragment
        }
    }

    fun setResultListener(resutl: ((Date) -> Unit)?) {
        this.resutl = resutl
    }

    private val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, day ->
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        resutl?.invoke(calendar.time)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var timestamp = arguments?.getLong(KEY_TIME_STAMP) ?: 0
        val c = Calendar.getInstance()
        if (timestamp != 0L) {
            c.time = Date(timestamp)
        }
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(getActivity(), dateSetListener, year, month, day)
    }
}
