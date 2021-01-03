@file:Suppress("ClassName")

package com.pierluigifimiano.livedataktx

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

private object UNINITIALIZED_VALUE

fun <T> combineLatest(
    vararg sources: LiveData<out Any?>,
    transform: (Array<Any?>) -> T
): LiveData<T> {
    val result = MediatorLiveData<T>()

    val values: Array<Any?> = Array(sources.size) { UNINITIALIZED_VALUE }
    val onChanged: () -> Unit = {
        if (!values.contains(UNINITIALIZED_VALUE)) {
            result.value = transform(values.copyOf())
        }
    }

    sources.forEachIndexed { index, ld ->
        result.addSource(ld) {
            values[index] = it
            onChanged()
        }
    }

    return result
}

inline fun <S1, S2, T> combineLatest(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    crossinline transform: (S1, S2) -> T
): LiveData<T> = combineLatest(source1, source2) { values: Array<Any?> ->
    @Suppress("UNCHECKED_CAST")
    transform(values[0] as S1, values[1] as S2)
}

inline fun <S1, S2, S3, T> combineLatest(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    source3: LiveData<S3>,
    crossinline transform: (S1, S2, S3) -> T
): LiveData<T> = combineLatest(source1, source2, source3) { values: Array<Any?> ->
    @Suppress("UNCHECKED_CAST")
    transform(values[0] as S1, values[1] as S2, values[2] as S3)
}

inline fun <S1, S2, S3, S4, T> combineLatest(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    source3: LiveData<S3>,
    source4: LiveData<S4>,
    crossinline transform: (S1, S2, S3, S4) -> T
): LiveData<T> = combineLatest(source1, source2, source3, source4) { values: Array<Any?> ->
    @Suppress("UNCHECKED_CAST")
    transform(values[0] as S1, values[1] as S2, values[2] as S3, values[3] as S4)
}

fun <T> combineLatestOrNull(
    vararg sources: LiveData<out Any?>,
    transform: (Array<Any?>) -> T
): LiveData<T> {
    val result = MediatorLiveData<T>()

    var called = false
    val onChanged: () -> Unit = {
        val values: Array<Any?> = Array(sources.size) { sources[it].value }
        result.value = transform(values)
        called = true
    }

    sources.forEach { ld ->
        result.addSource(ld) {
            onChanged()
        }
    }

    if (!called) {
        onChanged()
    }

    return result
}


inline fun <S1, S2, T> combineLatestOrNull(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    crossinline transform: (S1?, S2?) -> T
): LiveData<T> = combineLatestOrNull(source1, source2) { values: Array<Any?> ->
    @Suppress("UNCHECKED_CAST")
    transform(values[0] as? S1, values[1] as? S2)
}

inline fun <S1, S2, S3, T> combineLatestOrNull(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    source3: LiveData<S3>,
    crossinline transform: (S1?, S2?, S3?) -> T
): LiveData<T> = combineLatestOrNull(source1, source2, source3) { values: Array<Any?> ->
    @Suppress("UNCHECKED_CAST")
    transform(values[0] as? S1, values[1] as? S2, values[2] as? S3)
}

inline fun <S1, S2, S3, S4, T> combineLatestOrNull(
    source1: LiveData<S1>,
    source2: LiveData<S2>,
    source3: LiveData<S3>,
    source4: LiveData<S4>,
    crossinline transform: (S1?, S2?, S3?, S4?) -> T
): LiveData<T> = combineLatestOrNull(source1, source2, source3, source4) { values: Array<Any?> ->
    @Suppress("UNCHECKED_CAST")
    transform(values[0] as? S1, values[1] as? S2, values[2] as? S3, values[3] as? S4)
}