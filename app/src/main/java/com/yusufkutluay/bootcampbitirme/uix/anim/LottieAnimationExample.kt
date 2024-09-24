import androidx.compose.runtime.*
import com.airbnb.lottie.compose.*

@Composable
fun LottieAnimationExample(animationResId: Int) {
    // Animasyon kontrolü için bir state
    val isPlaying by remember { mutableStateOf(true) }

    // Lottie animasyon dosyasını yükle
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying
    )

    // Animasyonu göster
    LottieAnimation(
        composition,
        progress,// İstediğin boyutu ayarla
    )
}
