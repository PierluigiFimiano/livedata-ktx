package com.pierluigifimiano.livedataktxexample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pierluigifimiano.livedataktx.combineLatest
import com.pierluigifimiano.livedataktxexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val ld1 = MutableLiveData<Int>()
    val ld2 = MutableLiveData<Int>()

    val ld: LiveData<Int> = combineLatest(ld1, ld2) { a: Int, b: Int -> a + b }

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.activity = this

        ld1.value = 0
        ld2.value = 0
    }

    fun incOrDecAOrB(isA: Boolean, isInc: Boolean) {
        val ld: MutableLiveData<Int> = if (isA) ld1 else ld2
        ld.value = ld.value!! + if (isInc) 1 else -1
    }
}