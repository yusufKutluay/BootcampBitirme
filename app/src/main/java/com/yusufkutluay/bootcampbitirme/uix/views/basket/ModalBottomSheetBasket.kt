package com.yusufkutluay.bootcampbitirme.uix.views.basket

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.data.entity.Basket
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeLight
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.BasketViewModel
import com.yusufkutluay.bootcampbitirme.worker.MyWorkerBildirim
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetField(
    sheetState: SheetState,
    isSheetOpen : Boolean,
    onDismissRequest: () -> Unit,
    basketViewModel: BasketViewModel,
    basketList : List<Basket>,
    userName : String,
    context: Context,
    totalPrice : String
){

    val tfAdress = remember { mutableStateOf("Güngören / İstanbul") }
    val numberCard = remember { mutableStateOf("1111 1111 3465 2158") }
    val monthCard = remember { mutableStateOf("09") }
    val yearCard = remember { mutableStateOf("24") }
    val cvvCard = remember { mutableStateOf("999") }
    val animState = remember { mutableStateOf(true) }

    if (isSheetOpen){
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            shape = RectangleShape,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                /**
                 * Kullanıcının teslimat adresini gireceği kart yapısı
                 */
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.elevatedCardElevation(6.dp)
                ) {
                    Text(text = stringResource(R.string.basketpage_adress), fontWeight = FontWeight.Bold, modifier = Modifier.padding(all = 8.dp))
                    Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.LightGray)
                    OutlinedTextField(
                        value = tfAdress.value,
                        onValueChange = {tfAdress.value = it},
                        placeholder = { Text(
                            text = stringResource(R.string.basketpage_adress_comment)
                        )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                /**
                 * Kart bilgilerinin yer aldığı kart numarası , ay , yıl ve cvv girişlerinin yapıldığı kart yapısı
                 */
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.elevatedCardElevation(6.dp)
                ) {
                    Text(text = stringResource(R.string.basketpage_card_information), fontWeight = FontWeight.Bold, modifier = Modifier.padding(all = 8.dp))
                    Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = Color.LightGray)
                    Text(
                        text = stringResource(R.string.basketpage_cardNumber),
                        fontSize = 13.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 20.dp, top = 10.dp, bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = numberCard.value, onValueChange = {numberCard.value = it},
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent,
                            focusedContainerColor = Color(0x3CA8A8A8),
                            unfocusedContainerColor = Color(0x3CA8A8A8)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                    )
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 30.dp)
                    ){
                        /**
                         * Kredi kartı Ay ve Yıl giriş bilgileri
                         */
                        Column (
                            modifier = Modifier.weight(65f)
                        ){
                            Text(
                                text = stringResource(R.string.basketpage_expiration_date),
                                fontSize = 13.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Row {
                                // Ay giriş kartı
                                OutlinedTextField(
                                    value = monthCard.value, onValueChange = {monthCard.value = it},
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent,
                                        focusedContainerColor = Color(0x3CA8A8A8),
                                        unfocusedContainerColor = Color(0x3CA8A8A8)
                                    ),
                                    placeholder = { Text(text = stringResource(R.string.basketpage_month), color = Color.Gray, fontSize = 13.sp) },
                                    modifier = Modifier.width(80.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                // Yıl giriş kartı
                                OutlinedTextField(
                                    value = yearCard.value, onValueChange = {yearCard.value = it},
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent,
                                        focusedContainerColor = Color(0x3CA8A8A8),
                                        unfocusedContainerColor = Color(0x3CA8A8A8)
                                    ),
                                    placeholder = { Text(text = stringResource(R.string.basketpage_year), color = Color.Gray, fontSize = 13.sp) },
                                    modifier = Modifier.width(80.dp)
                                )
                            }
                        }
                        /**
                         *  CVV girişi
                         */
                        Column (
                            modifier = Modifier
                                .weight(35f)
                                .padding(start = 10.dp)
                        ){
                            Text(
                                text = stringResource(R.string.basketpage_cvv),
                                fontSize = 13.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = cvvCard.value, onValueChange = {cvvCard.value = it},
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Transparent,
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedContainerColor = Color(0x3CA8A8A8),
                                    unfocusedContainerColor = Color(0x3CA8A8A8)
                                ),
                                modifier = Modifier
                            )
                        }
                    }
                }

                /**
                 * Siparişi tamamla butonu
                 */
                OutlinedButton(
                    onClick = {
                        workerBildirim(context)
                        for (i in basketList){
                            basketViewModel.deleteAllByYemekAdi(i.yemek_adi,userName)
                        }
                        basketViewModel.saveOrderListFirebase(totalPrice,basketList)
                    },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = ThemeLight
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                ) {
                    Text(text = stringResource(R.string.basketpage_finish_order), fontSize = 16.sp, color = Color.White, modifier = Modifier.padding(all = 5.dp))
                }
            }
        }
    }
}

/**
 * Sipariş onaylanınca sipariş hazırlanıyor bildirimi gösterimi...
 */
fun workerBildirim(context: Context){
    val calismaKosulu = Constraints
        .Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val istek = OneTimeWorkRequestBuilder<MyWorkerBildirim>()
        .setInitialDelay(2, TimeUnit.SECONDS) // gecikme süresi hesaplama
        .setConstraints(calismaKosulu) // koşul belirtme
        .build()
    WorkManager.getInstance(context).enqueue(istek)
}