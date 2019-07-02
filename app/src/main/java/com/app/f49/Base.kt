package com.app.f49

import android.arch.lifecycle.MutableLiveData
import com.app.f49.model.store.StoreDTO

object Base {
    val IMAGE_URL = "https://www.seoclerk.com/pics/622146-1VJrSP1533779279.png"
    val LIST_STRING_DEFAULT = mutableListOf<String>("list1", "list2", "list3", "list4", "list5", "list6", "list7", "list8", "list9", "list10", "list11", "list12")
    var listStore: MutableLiveData<MutableList<StoreDTO>> = MutableLiveData()
    var pageSize: Int = 50
}