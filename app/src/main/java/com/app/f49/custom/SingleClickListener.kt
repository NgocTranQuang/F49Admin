package com.app.f49.custom

import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


abstract class SingleClickListener : View.OnClickListener {
    private val viewPublishSubject = PublishSubject.create<View>()

    init {
        viewPublishSubject.throttleFirst(THRESHOLD_MILLIS, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                onClicked(it)
            }
    }

    override fun onClick(v: View) {
        viewPublishSubject.onNext(v)
    }

    abstract fun onClicked(v: View)

    companion object {
        //Todo : change THRESHOLD_MILLIS
        private val THRESHOLD_MILLIS = 600L
    }
}
