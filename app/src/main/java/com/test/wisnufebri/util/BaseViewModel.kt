package com.test.wisnufebri.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.wisnufebri.data.config.Event

open class BaseViewModel: ViewModel() {
    protected val mSnackBarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = mSnackBarText
}