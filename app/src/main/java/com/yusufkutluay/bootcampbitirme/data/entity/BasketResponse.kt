package com.yusufkutluay.bootcampbitirme.data.entity

data class BasketResponse(
    var sepet_yemekler : List<Basket>,
    var success : Int
) {
}