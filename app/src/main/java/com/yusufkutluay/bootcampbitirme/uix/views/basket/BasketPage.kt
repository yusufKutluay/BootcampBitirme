package com.yusufkutluay.bootcampbitirme.uix.views.basket

import LottieAnimationExample
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeDark
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeLight
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.BasketViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketPage(
    modifier: Modifier,
    basketViewModel: BasketViewModel,
    context: Context,
    currentUseId: String
) {
    // kullanıcının ismini alma
    val userName = remember { mutableStateOf(currentUseId) }
    /**
     * Retrofit ile sepete eklediğimiz ürünleri çekiyoruz ve basketList'e aktarıyoruz
     * MutableLiveData ile verileri kontrol ediyoruz
     */
    val groupedBasketList = basketViewModel.basketData.observeAsState(listOf())
    val isSheetOpen = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    // total fiyatı siparis adet ve yemek fiyatını çarparak totalPrice aktarıyoruz..
    val totalPrice = groupedBasketList.value.sumOf { it.yemek_siparis_adet * it.yemek_fiyat }

    // ekran açıldığında veriler yüklenir
    LaunchedEffect(key1 = true) {
        basketViewModel.basketUpload(userName.value)
    }
    /**
     * listenin boş olup olmadığına göre işlemler yapıyoruz
     */
    if (userName.value != ""){
        Column(
            modifier
                .fillMaxSize()
                .background(Color.White),
        ) {
            if (groupedBasketList.value.isNotEmpty()){
                /**
                 *  BasketPage başlığı
                 */
                Text(
                    text = stringResource(R.string.basketpage_title),
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp),
                    textAlign = TextAlign.Center, // metin ortalanır
                    fontSize = 40.sp,
                    color = ThemeDark,
                    fontFamily = FontFamily.Cursive  // el yazısı
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {

                    ModalBottomSheetField(
                        sheetState = sheetState,
                        isSheetOpen = isSheetOpen.value,
                        onDismissRequest = {
                            isSheetOpen.value = false
                        },
                        basketList = groupedBasketList.value,
                        basketViewModel = basketViewModel,
                        userName = userName.value,
                        context = context,
                        totalPrice = totalPrice.toString()
                    )

                    /**
                     *  Sepetteki ürünleri listelemek için LazyColumn kullanıyoruz
                     */
                    LazyColumn (
                        modifier
                            .fillMaxSize()
                            .padding()
                    ){
                        items(
                            count = groupedBasketList.value.reversed().count(), // grupladığımız listeyi ekliyoruz
                            itemContent = { index ->
                                val listBasket = groupedBasketList.value.reversed()[index] // listenin itemi listBaskete aktarılır
                                /**
                                 *  Sepete eklenen ürünlerin özelliklerinin yer aldığı card yapısı
                                 */
                                ElevatedCard(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    elevation = CardDefaults.elevatedCardElevation(6.dp), // gölgelendirme
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = Color(0xFFFFF3E0)
                                    )
                                ) {
                                    /**
                                     * Card içinde yan yana yapılar
                                     * Image - (Yemek ismi,Adet,toplam fiyat) - DeleteIcon
                                     */
                                    Row (
                                        modifier
                                            .fillMaxWidth()
                                            .padding(all = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ){
                                        /**
                                         * url yi alarak Glide kütüphanesine ulaşıyoruz ve resimleri alıyoruz
                                         */
                                        val url = "http://kasimadalan.pe.hu/yemekler/resimler/${listBasket.yemek_resim_adi}"
                                        /**
                                         * IMAGE
                                         */
                                        GlideImage(
                                            imageModel = url,
                                            modifier
                                                .padding(start = 20.dp)
                                                .width(100.dp)
                                                .height(100.dp), contentScale = ContentScale.Fit  // tam sığdırıyoruz
                                        )
                                        Row (
                                            verticalAlignment = Alignment.CenterVertically
                                        ){
                                            // total fiyatı belirleme
                                            val totalPrices = (listBasket.yemek_siparis_adet * listBasket.yemek_fiyat)
                                            /**
                                             * Yemek ismi,Adet,toplam fiyat Column içinde ifadesi
                                             */
                                            Column(modifier = Modifier
                                                .padding(start = 20.dp)
                                                .weight(1f)) {
                                                Text(text = listBasket.yemek_adi, fontWeight = FontWeight.Bold)
                                                Text(text = stringResource(id = R.string.piece) + " : ${listBasket.yemek_siparis_adet}")
                                                Text(text = stringResource(R.string.total, totalPrices))
                                            }
                                            // Delete Iconu
                                            IconButton(
                                                onClick = {
                                                    basketViewModel.basketDelete(listBasket.sepet_yemek_id, userName.value)
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Filled.Delete,
                                                    contentDescription = "",
                                                    tint = ThemeDark,
                                                    modifier = modifier.size(30.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
                /**
                 * Sayfanın en altında yer alan toplam sepet tutarı ve sepeti onayla
                 * butonu yapısı
                 */
                Row (
                    modifier
                        .fillMaxWidth()
                        .padding(all = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    // Toplam Sepet Tutarı
                    Text(
                        text = "$totalPrice ₺",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = ThemeDark,
                        modifier = modifier.padding(start = 10.dp)
                    )
                    // Sepeti Onayla Butonu
                    OutlinedButton(
                        onClick = {
                            isSheetOpen.value = true
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = modifier
                            .weight(1f)
                            .padding(start = 30.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.basketpage_button_confirm),
                            fontSize = 20.sp,
                            modifier = modifier.padding(start = 5.dp),
                            color = ThemeLight
                        )
                    }
                }
            }else{
                // animasyon uygulaması Lottie
                Column (
                    modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Box(modifier = modifier.size(500.dp)){
                        LottieAnimationExample(R.raw.anim_bicycle)
                    }
                }
            }
        }
    }
}
