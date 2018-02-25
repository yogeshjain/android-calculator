package com.yogesh.calculator.data

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log

/**
 * Created by yogesh on 24/2/18.
 */
class MainActivityViewModel : ViewModel() {
    var mText: MutableLiveData<String>

    init {
        mText = MutableLiveData()
        mText.value = "0"
        Log.e("LD", "Initialized to 0")
    }
}