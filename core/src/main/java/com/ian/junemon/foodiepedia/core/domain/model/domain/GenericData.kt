package com.ian.junemon.foodiepedia.core.domain.model.domain

data class GenericPairData<A, B>(val data1: A, val data2: B)

data class GenericTripleData<A, B, C>(
    val data1: A,
    val data2: B,
    val data3: C
)

data class GenericQuadData<A, B, C, D>(
    val data1: A,
    val data2: B,
    val data3: C,
    val data4: D
)

data class GenericQuintData<A, B, C, D, E>(
    val data1: A,
    val data2: B,
    val data3: C,
    val data4: D,
    val data5: E
)

data class GenericSixData<A, B, C, D, E, F>(
    val data1: A,
    val data2: B,
    val data3: C,
    val data4: D,
    val data5: E,
    val data6: F
)
