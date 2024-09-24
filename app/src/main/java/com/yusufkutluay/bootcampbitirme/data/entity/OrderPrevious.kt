package com.yusufkutluay.bootcampbitirme.data.entity

data class OrderPrevious(
    var userId : String = "",
    var orderTotal : String = "",
    var orderDay : String = "",
    var orderTime : String = "",
    var orders : List<Order> = emptyList(),
) {
}