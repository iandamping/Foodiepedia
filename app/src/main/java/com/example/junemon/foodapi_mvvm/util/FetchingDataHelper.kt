package com.example.junemon.foodapi_mvvm.util

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

inline fun <reified T1, reified T2, reified T3> obsWithTripleZip(source1: ObservableSource<T1>, source2: ObservableSource<T2>, source3: ObservableSource<T3>): Observable<Triple<T1, T2, T3>> =
        Observable.zip(source1, source2, source3, Function3<T1, T2, T3, Triple<T1, T2, T3>> { t1, t2, t3 ->
            Triple(t1, t2, t3)
        })

inline fun <reified T, reified U> obsWithSecondZip(sources1: ObservableSource<T>, sources2: ObservableSource<U>): Observable<Pair<T, U>> =
        Observable.zip(sources1, sources2, BiFunction { t1, t2 -> Pair(t1, t2) })


inline fun <reified T, reified U> Single<T>.singleWithDoubleZIP(sources: Single<U>): Single<Pair<T, U>> = zipWith(sources,
        BiFunction { firstData, secondData -> Pair(firstData, secondData) })

inline fun <reified T> CompositeDisposable.executes(obs: Observable<T>, crossinline onFailed: (Throwable) -> Unit, crossinline onSuccess: (T?) -> Unit) {
    this.add(obs.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
        onSuccess(it)
    }, {
        onFailed(it)
    }))
}