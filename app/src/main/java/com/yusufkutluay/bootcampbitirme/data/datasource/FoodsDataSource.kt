package com.yusufkutluay.bootcampbitirme.data.datasource


import android.content.Context
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.data.entity.Basket
import com.yusufkutluay.bootcampbitirme.data.entity.Foods
import com.yusufkutluay.bootcampbitirme.data.entity.Order
import com.yusufkutluay.bootcampbitirme.data.entity.OrderPrevious
import com.yusufkutluay.bootcampbitirme.data.entity.Users
import com.yusufkutluay.bootcampbitirme.retrofit.FoodsDao
import com.yusufkutluay.bootcampbitirme.uix.views.profil.PreviousOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FoodsDataSource(
    var fdao: FoodsDao,
    var db: FirebaseFirestore,
    var auth: FirebaseAuth
) {

    // kullanıcı bilgilerini firestore a kaydetme
    fun firebaseUserAdd(
        id: String,
        fullName: String,
        userName: String,
        email: String,
        password: String
    ) {
        val user = Users(id, fullName, userName, email, password)
        db.collection("users").document().set(user)
    }

    /**
     * Kullanıcıyı Auth kullanarak firebasee kaydediyoruz.
     * Kaydın başarılı olup olmamasına görede callBack döndürüyoruz
     * ve ona göre işlem yapıyoruz
     */
    fun createUserWithEmail(
        email: String,
        password: String,
        fullName: String,
        userName: String,
        callback: (Boolean) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        firebaseUserAdd(userId, fullName, userName, email, password)
                    } else {
                        callback(false)
                    }
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

    //Id ye göre kullanıcı adı döndürme
    fun getUserNameById(id: String, callback: (String) -> Unit) {
        db.collection("users").whereEqualTo("id", id).get()
            .addOnSuccessListener { querySnapshot ->

                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]
                    val user = document.toObject(Users::class.java)
                    callback(user?.fullName!!)
                } else {
                    callback("")
                }
            }
            .addOnFailureListener {
                callback("")
            }
    }

    //Id ye göre kullanıcı bilgileri döndürme
    fun getAllInformationById(id: String, callback: (List<Users>) -> Unit) {
        db.collection("users").whereEqualTo("id", id).get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val usersList = querySnapshot.documents.mapNotNull { document ->
                        document.toObject(Users::class.java)
                    }
                    callback(usersList)
                } else {
                    callback(listOf())
                }
            }
            .addOnFailureListener {
                callback(listOf())
            }
    }

    // Kullanıcı girişi
    fun signInUser(email: String, password: String, callback: (Boolean) -> Unit) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Kullanıcı başarıyla giriş yaptı
                    val user = auth.currentUser
                    callback(true) // Giriş başarılı, kullanıcı ID'sini geri döndür
                } else {
                    // Giriş başarısız oldu
                    callback(false) // Giriş başarısız
                }
            }
    }

    // sepetteki öğeleri siparişlerim sayfasına kaydetme
    fun saveOrderListFirebase(total: String, listOrder: List<Basket>) {
        val id = auth.currentUser?.uid
        val currentTime = System.currentTimeMillis() // Şu anki zamanın millisaniye cinsinden değeri

        // Gün ve saat formatları
        val dayFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())  // Gün formatı
        val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())   // Saat formatı

        val formattedDay = dayFormat.format(Date(currentTime)) // Formatlanmış gün
        val formattedTime = timeFormat.format(Date(currentTime)) // Formatlanmış saat
        val orderRef =
            db.collection("order").document("id").collection(id!!).document("$formattedDay - $formattedTime")

        // Sipariş verilerini hazırlama (listeyi bir map'e çevirme)
        val orderData = hashMapOf<String, Any>(
            "userId" to id,
            "orderTotal" to total,
            "orderDay" to formattedDay,
            "orderTime" to formattedTime,
            "orders" to listOrder.map { food ->
                hashMapOf(
                    "ad" to food.yemek_adi,
                    "adet" to food.yemek_siparis_adet,
                    "fiyat" to food.yemek_fiyat,
                    "resim" to food.yemek_resim_adi
                )
            }
        )
        // verileri yükleme
        orderRef.set(orderData)
    }

    fun getPreviousOrder(userId : String,callback: (List<OrderPrevious>?) -> Unit) {

        db.collection("order").document("id").collection(userId)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val orders = documents.map { document ->
                        document.toObject(OrderPrevious::class.java)
                    }
                    callback(orders)
                } else {
                    callback(emptyList()) // Eğer boşsa boş liste döner
                }
            }
            .addOnFailureListener {
                callback(null) // Hata durumunda null döner
            }
    }

    suspend fun foodsUpload(): List<Foods> = withContext(Dispatchers.IO) {
        try {
            return@withContext fdao.foodsUpload().yemekler
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun basketUpload(kullanici_adi: String): List<Basket> =
        withContext(Dispatchers.IO) {
            try {
                return@withContext fdao.basketUpload(kullanici_adi).sepet_yemekler
            } catch (e: Exception) {
                emptyList()
            }
        }

    suspend fun basketAdd(
        yemek_adi: String,
        yemek_resim_adi: String,
        yemek_fiyat: Int,
        yemek_siparis_adet: Int,
        kullanici_adi: String
    ) {
        fdao.basketAdd(
            yemek_adi,
            yemek_resim_adi,
            yemek_fiyat,
            yemek_siparis_adet,
            kullanici_adi
        )
    }


    suspend fun basketDelete(sepet_yemek_id: Int, kullanici_adi: String) {
        fdao.basketDelete(sepet_yemek_id, kullanici_adi)
    }

    suspend fun category(foodName: String, context: Context): String =
        withContext(Dispatchers.IO) {


            val drinkList = arrayListOf("Ayran", "Fanta", "Kahve", "Su")
            val tatliList = arrayListOf("Baklava", "Kadayıf", "Sütlaç", "Tiramisu")
            val izgaraList = arrayListOf("Izgara Somon", "Izgara Tavuk", "Köfte")
            val makarnaList = arrayListOf("Makarna")
            val firinList = arrayListOf("Lazanya", "Pizza")

            return@withContext when {
                foodName in drinkList -> context.getString(R.string.drink)
                foodName in tatliList -> context.getString(R.string.sweet)
                foodName in izgaraList -> context.getString(R.string.grill)
                foodName in makarnaList -> context.getString(R.string.pasta)
                foodName in firinList -> context.getString(R.string.oven)
                else -> context.getString(R.string.category_not_found)
            }
        }
}


