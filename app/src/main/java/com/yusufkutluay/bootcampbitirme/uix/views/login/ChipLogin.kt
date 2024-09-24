package com.yusufkutluay.bootcampbitirme.uix.views.login

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeDark
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeLight

@Composable
fun TextandCardLogin(
    modifier: Modifier,
    title:String,
    tf: MutableState<String>,
    keyboardType: KeyboardType,
    leadingIcon: ImageVector?,
    trailingState : Boolean
){

    val isPasswordVisible = remember { mutableStateOf(false) }

    Text(text = title, color = ThemeLight, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = modifier.padding(start = 35.dp, end = 35.dp, bottom = 10.dp))

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 35.dp, end = 35.dp),
        value = tf.value,
        onValueChange = { if (it.length <= 27) tf.value = it},
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = ThemeDark,
            unfocusedIndicatorColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),

        leadingIcon = {
            if (leadingIcon != null) Icon(leadingIcon, contentDescription = "", tint = ThemeDark)
        },
        trailingIcon = {
            if (trailingState){
                if (isPasswordVisible.value){
                    IconButton(onClick = { isPasswordVisible.value = false }) {
                        Icon(painter = painterResource(id = R.drawable.visibility), contentDescription = "", tint = ThemeDark)
                    }

                }else{
                    IconButton(onClick = { isPasswordVisible.value = true }) {
                        Icon(painter = painterResource(id = R.drawable.visibility_off), contentDescription = "", tint = ThemeDark)
                    }
                }
            }
        },
        visualTransformation = if(trailingState && !isPasswordVisible.value) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Composable
fun TextandCardSign(
    modifier: Modifier,
    title:String,
    tf: MutableState<String>,
    keyboardType: KeyboardType
){
    Text(text = title, color = ThemeLight, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = modifier.padding(bottom = 10.dp))

    OutlinedTextField(
        value = tf.value,
        onValueChange = { if (it.length <= 28) tf.value = it},
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = ThemeDark,
            unfocusedIndicatorColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        )
    )
}