package com.yusufkutluay.bootcampbitirme.retrofit

import com.yusufkutluay.bootcampbitirme.data.entity.BasketResponse
import com.yusufkutluay.bootcampbitirme.data.entity.CrudResponse
import com.yusufkutluay.bootcampbitirme.data.entity.FoodsResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodsDao {

    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun foodsUpload(): FoodsResponse

    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    suspend fun basketAdd(
        @Field("yemek_adi") yemek_adi: String,
        @Field("yemek_resim_adi") yemek_resim_adi: String,
        @Field("yemek_fiyat") yemek_fiyat: Int,
        @Field("yemek_siparis_adet") yemek_siparis_adet: Int,
        @Field("kullanici_adi") kullanici_adi: String
    ): CrudResponse

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun basketUpload(
        @Field("kullanici_adi") kullanici_adi: String
    ): BasketResponse

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun basketDelete(@Field("sepet_yemek_id") sepet_yemek_id:Int,@Field("kullanici_adi")kullanici_adi:String):CrudResponse

}