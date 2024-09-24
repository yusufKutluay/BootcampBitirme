package com.yusufkutluay.bootcampbitirme.uix.views.home

import android.app.Activity
import android.content.Context
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.data.entity.Foods
import com.yusufkutluay.bootcampbitirme.ui.theme.BasketTextField
import com.yusufkutluay.bootcampbitirme.ui.theme.FoodDetailsFoodName
import com.yusufkutluay.bootcampbitirme.ui.theme.FoodDetailsFoodPrice
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeDark
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeLight
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.FoodDetailsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetails(
    modifier: Modifier,
    navController: NavHostController,
    foodDetailsViewModel: FoodDetailsViewModel,
    objectFood: Foods,
    context: Context,
    currentUseId: String?,
) {

    val userName = remember {
        mutableStateOf(currentUseId!!)
    }

    val categoryFood = foodDetailsViewModel.category.observeAsState("")
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    val selectedQuantity = remember { mutableStateOf(1) }
    val isSheetOpen = remember { mutableStateOf(false) }
    val basketList = foodDetailsViewModel.basketData.observeAsState(listOf())
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp

    LaunchedEffect(key1 = true) {
        foodDetailsViewModel.basketUpload(userName.value)
    }

    for (i in basketList.value){
        if (i.yemek_adi == objectFood.yemek_adi){
            selectedQuantity.value = i.yemek_siparis_adet
        }
    }

    val size = screenHeight * screenWidth  // genişlik ve yüksekliği çarparak kaç piksel lduğunu buluyoruz

    Scaffold { paddingValues ->
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ){
            if (userName.value != ""){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(ThemeLight) // Yarıya kadar olan arka plan rengi
                    ) {
                        Row(
                            modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, top = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick = { (context as Activity).finish() }) {
                                Icon(
                                    Icons.Filled.Close,
                                    contentDescription = "",
                                    modifier.size(30.dp, 30.dp)
                                )
                            }
                        }
                    }
                    Column {
                        ElevatedCard(
                            modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp, top = 100.dp),
                            elevation = CardDefaults.elevatedCardElevation(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Column(
                                modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = "    ${categoryFood.value}    ", // yemek ismi ekleme
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clip(
                                                RoundedCornerShape(
                                                    topStart = 0.dp,
                                                    bottomStart = 0.dp,
                                                    topEnd = 8.dp,
                                                    bottomEnd = 8.dp
                                                )
                                            )
                                            .background(ThemeDark)
                                    )
                                }

                                val url =
                                    "http://kasimadalan.pe.hu/yemekler/resimler/${objectFood.yemek_resim_adi}"
                                GlideImage(
                                    imageModel = url,
                                    modifier.size((size / 1500).dp)  // size /1500 yaparak ne kadar büyük olacağını ekrana göre ayarlıyoruz
                                )

                                LaunchedEffect(key1 = objectFood.yemek_adi) {
                                    foodDetailsViewModel.category(objectFood.yemek_adi, context = context)
                                }

                                Text(
                                    text = "₺ ${objectFood.yemek_fiyat}",
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FoodDetailsFoodPrice
                                )
                                Spacer(modifier = modifier.height(7.dp))
                                Text(
                                    text = objectFood.yemek_adi,
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FoodDetailsFoodName
                                )
                                Spacer(modifier = modifier.height(20.dp))
                                Row {
                                    OutlinedCard(
                                        modifier.wrapContentSize(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = BasketTextField
                                        )
                                    ) {
                                        Text(text = stringResource(R.string.fooddetails_discount),modifier.padding(all = 6.dp), fontSize = 13.sp)
                                    }
                                    Spacer(modifier = modifier.width(6.dp))
                                    OutlinedCard(
                                        modifier.wrapContentSize(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = BasketTextField
                                        )
                                    ) {
                                        Text(text = stringResource(R.string.fooddetails_ınstant),modifier.padding(all = 6.dp), fontSize = 13.sp)
                                    }
                                    Spacer(modifier = modifier.width(6.dp))
                                    OutlinedCard(
                                        modifier.wrapContentSize(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = BasketTextField
                                        )
                                    ) {
                                        Text(text = stringResource(R.string.fooddetails_loved),modifier.padding(all = 6.dp), fontSize = 13.sp)
                                    }
                                }
                                Spacer(modifier = modifier.height(25.dp))
                                OutlinedButton(
                                    onClick = {
                                        isSheetOpen.value = true
                                        scope.launch { sheetState.show() }
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(text = stringResource(
                                        R.string.fooddetails_piece,
                                        selectedQuantity.value
                                    ), color = Color.DarkGray, fontSize = 14.sp)
                                    Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "", tint = Color.Black, modifier = modifier.size(30.dp))
                                }
                                Spacer(modifier = modifier.height(30.dp))
                                // ModalBottomSheet kullanarak adet sayısını belirliyoruz
                                if (isSheetOpen.value) {
                                    ModalBottomSheet(
                                        onDismissRequest = {
                                            isSheetOpen.value = false
                                            scope.launch { sheetState.hide() }
                                        },
                                        sheetState = sheetState,
                                        containerColor = Color.White
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp)
                                        ) {
                                            Text(text = stringResource(R.string.fooddetails_select_quantity), style = MaterialTheme.typography.titleMedium)
                                            Spacer(modifier = Modifier.height(16.dp))

                                            LazyColumn {
                                                item{
                                                    // 1'den 50'ye kadar sayıları listeleme
                                                    for (i in 1..50) {
                                                        Text(
                                                            text = i.toString(),
                                                            modifier = Modifier
                                                                .fillMaxWidth()
                                                                .clickable {
                                                                    selectedQuantity.value = i
                                                                    isSheetOpen.value = false
                                                                    scope.launch { sheetState.hide() }
                                                                }
                                                                .padding(8.dp)
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
                    Column(
                        modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Row (
                            modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Text(
                                text = ("  " + (objectFood.yemek_fiyat * selectedQuantity.value).toString() + " ₺"),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = ThemeLight,
                                modifier = modifier.weight(40f)
                            )
                            OutlinedButton(
                                onClick = {
                                    var itemFound = false
                                    for (i in basketList.value) {
                                        if (i.yemek_adi == objectFood.yemek_adi) {
                                            foodDetailsViewModel.basketDelete(objectFood.yemek_id, currentUseId!!)
                                            foodDetailsViewModel.basketAddOrReplace(
                                                i.yemek_adi,
                                                i.yemek_resim_adi,
                                                i.yemek_fiyat,
                                                selectedQuantity.value,
                                                i.kullanici_adi
                                            )
                                            itemFound = true
                                            break // Ürün bulundu, daha fazla döngüye gerek yok
                                        }
                                    }

                                    if (!itemFound) {
                                        foodDetailsViewModel.basketAdd(
                                            objectFood.yemek_adi,
                                            objectFood.yemek_resim_adi,
                                            objectFood.yemek_fiyat,
                                            selectedQuantity.value,
                                            userName.value
                                        )
                                    }
                                    foodDetailsViewModel.basketUpload(userName.value)
                                    (context as Activity).finish()
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    containerColor = ThemeDark
                                ),
                                modifier = modifier.weight(60f)
                            ) {
                                Text(text = stringResource(R.string.basket),color = Color.White, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }else{
                Column(
                    modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier.size(100.dp))
                }
            }
        }
    }
}