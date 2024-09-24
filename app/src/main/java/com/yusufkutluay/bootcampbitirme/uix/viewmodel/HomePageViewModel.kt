package com.yusufkutluay.bootcampbitirme.uix.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yusufkutluay.bootcampbitirme.data.entity.Basket
import com.yusufkutluay.bootcampbitirme.data.entity.Foods
import com.yusufkutluay.bootcampbitirme.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(var frepo : FoodsRepository) : ViewModel(){

    val listFoods = MutableLiveData<List<Foods>>()
    val basketData = MutableLiveData<List<Basket>>()

    init {
        foodsUpload()
    }

    fun foodsUpload(){
        CoroutineScope(Dispatchers.Main).launch {
            listFoods.value = frepo.foodsUpload()
        }
    }

    fun basketUpload(kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            basketData.value = frepo.basketUpload(kullanici_adi)
        }
    }

    fun search(yemek_adi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val list = frepo.foodsUpload()

            /**
             * listeyi filtreliyoruz gelen yemek ismine göre benzeyenleri listeler
             * ignorecase büyük küçük harf duyarlılığını kaldırır
             * contains bir string içinde belirli bir
             * alt stringin (kelime, karakter dizisi) var olup olmadığını kontrol eder.
             */
            val filteredList = list.filter { it.yemek_adi.contains(yemek_adi, ignoreCase = true) }

            listFoods.value = filteredList
        }
    }


}