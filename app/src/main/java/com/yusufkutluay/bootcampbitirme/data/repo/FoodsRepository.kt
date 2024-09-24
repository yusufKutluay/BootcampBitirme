package com.yusufkutluay.bootcampbitirme.data.repo

import android.content.Context
import com.yusufkutluay.bootcampbitirme.data.datasource.FoodsDataSource
import com.yusufkutluay.bootcampbitirme.data.entity.Basket
import com.yusufkutluay.bootcampbitirme.data.entity.Foods
import com.yusufkutluay.bootcampbitirme.data.entity.OrderPrevious
import com.yusufkutluay.bootcampbitirme.data.entity.Users

class FoodsRepository (var fds : FoodsDataSource){

    fun createUserWithEmail(email: String, password: String, fullName: String,userName:String, callback: (Boolean) -> Unit)
    = fds.createUserWithEmail(email,password,fullName,userName,callback)

    fun signInUser(email: String, password: String, callback: (Boolean) -> Unit) = fds.signInUser(email,password,callback)

    fun getUserNameById(id:String,callback: (String) -> Unit) = fds.getUserNameById(id,callback)

    fun getAllInformationById(id:String,callback: (List<Users>) -> Unit) = fds.getAllInformationById(id,callback)

    fun saveOrderListFirebase(total : String,listOrder : List<Basket>) = fds.saveOrderListFirebase(total,listOrder)

    fun getPreviousOrder(userId : String,callback: (List<OrderPrevious>?) -> Unit) = fds.getPreviousOrder(userId,callback)

    suspend fun foodsUpload() : List<Foods> = fds.foodsUpload()

    suspend fun basketUpload(kullanici_adi: String) : List<Basket> = fds.basketUpload(kullanici_adi)

    suspend fun category(foodName : String,context: Context) : String = fds.category(foodName,context)

    suspend fun basketDelete(sepet_yemek_id:Int,kullanici_adi: String) = fds.basketDelete(sepet_yemek_id,kullanici_adi)

    suspend fun basketAdd(
        yemek_adi:String,
        yemek_resim_adi:String,
        yemek_fiyat:Int,
        yemek_siparis_adet:Int,
        kullanici_adi:String
    ) = fds.basketAdd(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)

}