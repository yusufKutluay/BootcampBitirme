package com.yusufkutluay.bootcampbitirme.uix.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yusufkutluay.bootcampbitirme.data.entity.Basket
import com.yusufkutluay.bootcampbitirme.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(var frepo : FoodsRepository) : ViewModel() {

    var category = MutableLiveData<String>()
    val basketData = MutableLiveData<List<Basket>>()

    fun category(foodName : String,context: Context){
        CoroutineScope(Dispatchers.Main).launch {
            category.value = frepo.category(foodName,context)
        }
    }

    fun basketDelete(sepet_yemek_id:Int,kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            frepo.basketDelete(sepet_yemek_id,kullanici_adi)
        }
    }

    fun basketAdd(
        yemek_adi:String,
        yemek_resim_adi:String,
        yemek_fiyat:Int,
        yemek_siparis_adet:Int,
        kullanici_adi:String
    ){
        CoroutineScope(Dispatchers.Main).launch {
            frepo.basketAdd(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,kullanici_adi)
            basketData.value = frepo.basketUpload(kullanici_adi)
        }
    }

    fun basketUpload(kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            basketData.value = frepo.basketUpload(kullanici_adi)
        }
    }

    fun basketAddOrReplace(
        yemek_adi:String,
        yemek_resim_adi:String,
        yemek_fiyat:Int,
        yemek_siparis_adet:Int,
        kullanici_adi:String
    ) {
        CoroutineScope(Dispatchers.Main).launch {

            val currentBasket = frepo.basketUpload(kullanici_adi)
            val existingItem = currentBasket.find { it.yemek_adi == yemek_adi }
            if (existingItem != null) {
                frepo.basketDelete(existingItem.sepet_yemek_id, kullanici_adi)
            }
            frepo.basketAdd(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
            basketData.value = frepo.basketUpload(kullanici_adi)
        }
    }

}