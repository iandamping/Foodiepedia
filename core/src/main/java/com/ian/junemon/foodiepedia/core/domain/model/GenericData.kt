package com.ian.junemon.foodiepedia.core.domain.model

data class GenericPairData<out A, out B>(val data1: A, val data2: B)

data class GenericTripleData<out A,out B,out C>(
    val data1: A,
    val data2: B,
    val data3: C
)

data class GenericQuadData<out A,out B,out C,out  D>(
    val data1: A,
    val data2: B,
    val data3: C,
    val data4: D
)

data class GenericQuintData<out A,out B,out C,out D,out E>(
    val data1: A,
    val data2: B,
    val data3: C,
    val data4: D,
    val data5: E
)

data class GenericSixData<out A,out B,out C,out D,out E,out F>(
    val data1: A,
    val data2: B,
    val data3: C,
    val data4: D,
    val data5: E,
    val data6: F
)
