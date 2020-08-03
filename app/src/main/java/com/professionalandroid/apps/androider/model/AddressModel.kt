package com.professionalandroid.apps.androider.model

data class AddressModel(
    val results: AddressResponse
)

data class AddressResponse(
    val common: Common,
    val juso: List<Juso>
)

data class Common (
    val totalCount: String,
    val currentPage: Int,
    val countPerPage: Int,
    val errorCode: String,
    val errorMessage: String
)

data class Juso (
    val detBdNmList: String,
    val engAddr: String,
    val rn: String,
    val emdNm: String,
    val zipNo: String,
    val roadAddrPart2: String,
    val emdNo: String,
    val sggNm: String,
    val jibunAddr: String,
    val siNm: String,
    val roadAddrPart1: String,
    val bdNm: String,
    val admCd: String,
    val udrtYn: String,
    val lnbrMnnm: Int,
    val roadAddr: String,
    val lnbrSlno: Int,
    val buldMnnm: Int,
    val bdKdcd: String,
    val liNm: String,
    val rnMgtSn: String,
    val mtYn: String,
    val bdMgtSn: String,
    val buldSlno: Int
)

