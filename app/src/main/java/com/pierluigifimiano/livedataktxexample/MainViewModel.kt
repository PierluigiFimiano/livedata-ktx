package com.pierluigifimiano.livedataktxexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pierluigifimiano.livedataktx.combineLatest

class MainViewModel : ViewModel() {

    private val _a = MutableLiveData<Int>()
    val a: LiveData<Int> = _a

    private val _b = MutableLiveData<Int>()
    val b: LiveData<Int> = _b

    val sum: LiveData<Int> = combineLatest(_a, _b) { a, b -> a + b }

    init {
        _a.value = 0
        _b.value = 0
    }

    fun incOrDecAOrB(isA: Boolean, isInc: Boolean) {
        val ld: MutableLiveData<Int> = if (isA) _a else _b
        ld.value = ld.value!! + if (isInc) 1 else -1
    }
}