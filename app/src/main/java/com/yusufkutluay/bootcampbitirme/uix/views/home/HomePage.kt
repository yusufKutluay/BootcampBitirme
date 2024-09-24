package com.yusufkutluay.bootcampbitirme.uix.views.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import com.yusufkutluay.bootcampbitirme.ActivityTransitions
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.ui.theme.BasketHomeButton
import com.yusufkutluay.bootcampbitirme.ui.theme.BasketHomeTotal
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeDark
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeLight
import com.yusufkutluay.bootcampbitirme.ui.theme.roboto_slab
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.HomePageViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale


@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("MissingPermission", "PermissionLaunchedDuringComposition")
@Composable
fun HomePage(
    modifier: Modifier,
    navController: NavHostController,
    homePageViewModel: HomePageViewModel,
    context: Context,
    currentUseId: String?,
){
    val userName = remember {
        mutableStateOf(currentUseId!!)
    }

    /**
     * Retrofit ile çektiğimiz verilerin gerekli listelere aktarılması
     * HomePageView Model
     * FoodRepository - FoodDataSource - FoodDao yolunu izleyerek verileri çekeriz
     */
    val listFoods = homePageViewModel.listFoods.observeAsState(listOf())  // livedata ile listeyi alıyoruz
    val basketList = homePageViewModel.basketData.observeAsState(listOf())
    val tf = remember { mutableStateOf("") }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val permissionControl = rememberPermissionState(permission = android.Manifest.permission.ACCESS_FINE_LOCATION)
    val cityName = remember { mutableStateOf(context.getString(R.string.homepage_city_informationget)) }
    val coroutineScope = rememberCoroutineScope()
    val totalPiece = remember { mutableStateOf(0) }

    // focusManager ile etkin odaklanmaları görüyoruz
    val focusManager = LocalFocusManager.current

    /**
     *  Ekran açılışında verilerin yüklenmesi için LaunchedEffect yapısı kullanılır
     */
    LaunchedEffect(Unit) {
        homePageViewModel.foodsUpload()  // verileri ilk açılışta yüklenir
        homePageViewModel.basketUpload(userName.value)
        if (!permissionControl.status.isGranted) {
            permissionControl.launchPermissionRequest()
        } else {
            getLocation(coroutineScope, fusedLocationClient, cityName, context)
        }
    }

    LaunchedEffect (totalPiece.value){
        homePageViewModel.basketUpload(userName.value)
    }


    // Eğer izin verilirse, konumu güncelle
    if (permissionControl.status.isGranted) {
        getLocation(coroutineScope, fusedLocationClient, cityName, context)
    }

    // sayfanın tamamını kaplayacak bir column yapıyoruz
    Column(
        modifier
            .background(Color.White)  // arka plan rengi
            .fillMaxSize(), // full size
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 15.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Icon(Icons.Filled.LocationOn, contentDescription = "", tint = ThemeLight)
            Text(text = cityName.value)
        }
        /**
         * Arama yapmak için OutlinedTextField ekliyoruz
         * tf.value ile canlı olarak eklenen yazıları ekleme yapıyoruz
         * İçinde close iconu ekliyoruz ve icona basılınca odaklanma kapatılır
         * ve metin içeriği temizlenir
         */

        OutlinedCard(
            onClick = { /*TODO*/ },
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 10.dp)
                .border(
                    color = Color.Gray,
                    width = 1.dp,
                    shape = RoundedCornerShape(11.dp)
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Transparent
            )
        ) {
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth(),
                value = tf.value,
                onValueChange = {
                    tf.value = it
                    homePageViewModel.search(tf.value)
                },
                placeholder = {
                    Text(text = stringResource(R.string.homepage_search_comment))
                },
                trailingIcon = {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "",
                        modifier.clickable {
                            tf.value = ""  // TextField içeriğini temizle
                            homePageViewModel.search(tf.value)
                            focusManager.clearFocus()  // TextField'in odağını kaldır
                        }
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
        }
        if (userName.value != ""){
            LazyVerticalGrid(  // dikey olarak yan yana iki tane item
                columns = GridCells.Fixed(2),
                modifier.fillMaxSize()
            ) {

                items(listFoods.value){ listFor -> // listeyi alır ve gezer
                    /**
                     * Card içinde yemek ismi , fiyatı ve gerekli açıklamalar yer alır
                     * Retrofit ile web servisten çektiğimiz verileri kullanarak kullanıcılara
                     * gerekli bilgiler gösterilir.
                     */

                    // Her öğe için sepete eklenme durumu kontrolü
                    val inBasket = basketList.value.find { it.yemek_adi == listFor.yemek_adi }
                    totalPiece.value = inBasket?.yemek_siparis_adet ?: 0
                    var basketName = if (totalPiece.value > 0) context.getString(R.string.basketed) else context.getString(R.string.basket)

                    ElevatedCard(  // resimlerin ve özelliklerin olacağı card
                        elevation = CardDefaults.elevatedCardElevation(6.dp), // gölgelendirme
                        modifier = Modifier
                            .padding(all = 10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White  // card rengi
                        )
                    ) {
                        /**
                         * Column Card içinde tüm alanı kaplar ve işlemler yaparız içinde
                         */
                        Column (
                            modifier.fillMaxSize() // card içinde ımage fiyat ve isimlendirme
                        ){
                            Spacer(modifier.height(5.dp))  // 10.dp yukarıdan boşluk ekledik
                            Text(
                                text = "  ${listFor.yemek_adi}   ", // yemek ismi ekleme
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 0.dp,
                                            bottomStart = 0.dp,
                                            topEnd = 7.dp,
                                            bottomEnd = 7.dp
                                        )
                                    )
                                    .background(ThemeDark)
                            )
                            Spacer(modifier.height(5.dp))
                            // yemek fiyatı listeleme
                            Text(
                                text = "  ${listFor.yemek_fiyat} ₺  ",
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 0.dp,
                                            bottomStart = 0.dp,
                                            topEnd = 7.dp,
                                            bottomEnd = 7.dp
                                        )
                                    )
                                    .background(ThemeDark)
                            )

                            // yemek fotoğraflarını listeleme Glid kütüphanesini kullanarak
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                                val url = "http://kasimadalan.pe.hu/yemekler/resimler/${listFor.yemek_resim_adi}"
                                GlideImage(
                                    imageModel = url,
                                    modifier
                                        .width(100.dp)
                                        .height(100.dp), contentScale = ContentScale.Fit  // tam sığdırıyoruz
                                )
                            }
                            // açıklamalar

                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = modifier.fillMaxWidth()
                            ){
                                Icon(
                                    painter = painterResource(id = R.drawable.bike),
                                    contentDescription = "",
                                    modifier
                                        .size(20.dp, 20.dp)
                                        .padding(end = 5.dp),
                                    tint = Color.Red
                                )
                                Text(
                                    text = stringResource(R.string.homepage_delivery_comment),
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontFamily = FontFamily.Cursive
                                )
                            }

                            Text(
                                text = stringResource(R.string.homepage_basketed_comment),
                                fontSize = 14.sp,
                                modifier = modifier
                                    .padding(bottom = 2.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontFamily = roboto_slab,
                                fontWeight = FontWeight.Bold,
                            )
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                modifier = modifier
                                    .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
                                    .fillMaxWidth()
                            ){
                                // sepete ekleme butonu
                                OutlinedButton(
                                    onClick = {
                                        val jsonFood = Gson().toJson(listFor)
                                        val intent = Intent(context,ActivityTransitions::class.java)
                                        intent.putExtra("start_destination","food_detail")
                                        intent.putExtra("objectFood",jsonFood)
                                        context.startActivity(intent)
                                    },
                                    modifier
                                        .weight(70f),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = BasketHomeButton
                                    )
                                ) {
                                    Text(text = basketName,color = BasketHomeButton)
                                }

                                for (i in basketList.value){
                                    if (listFor.yemek_adi == i.yemek_adi){
                                        totalPiece.value = i.yemek_siparis_adet
                                        basketName = context.getString(R.string.basketed)
                                        break
                                    }else{
                                        totalPiece.value = 0
                                        basketName = ""
                                    }
                                }
                                if(basketList.value.isNotEmpty()){
                                    if (totalPiece.value != 0){
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .weight(30f)
                                                .padding(all = 5.dp)
                                                .clip(RoundedCornerShape(50.dp)) // Köşeleri yuvarla
                                                .background(BasketHomeTotal) // Arka plan rengi
                                        ) {
                                            Text(
                                                text = "${totalPiece.value}",
                                                fontSize = 16.sp,
                                                color = BasketHomeButton,
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(all = 5.dp), // Text için full size
                                                textAlign = TextAlign.Center // Ortalamak için
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

// Konum güncelleme fonksiyonu
private fun getLocation(
    coroutineScope: CoroutineScope,
    fusedLocationClient: FusedLocationProviderClient,
    cityName: MutableState<String>,
    context: Context
) {
    coroutineScope.launch {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                if (addresses!!.isNotEmpty()) {
                    cityName.value = addresses[0]?.locality ?: "Türkiye"
                }
            }
        }
    }
}


