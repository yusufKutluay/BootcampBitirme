#Sepetle Uygulaması
Sepetle, Techcareer tarafından verilen eğitim kapsamında Kasım Adalan hocamızın bitirme ödevi olarak geliştirilmiş bir alışveriş uygulamasıdır. Bu uygulama, kullanıcıların ürünleri sepete ekleyerek sipariş verebileceği bir platform sağlar. Uygulama, ürün verilerini çekmek için Retrofit kütüphanesini kullanır.

##Özellikler
Ürünlerin listesini görüntüleyin.
Ürün detaylarını inceleyin.
Ürünleri sepete ekleyin.
Sepetinizdeki ürünlerle sipariş oluşturun.

##Teknolojiler
Kotlin: Android uygulama geliştirmede kullanılan ana dil.
Retrofit: HTTP isteklerini yapmak ve RESTful API'lerden veri çekmek için kullanılır.
MVVM (Model-View-ViewModel): Uygulama mimarisi, veri yönetimi ve UI güncellemelerinin ayrıştırılması için bu yapı kullanılmıştır.
Firebase: Uygulama içi veritabanı yönetimi için kullanılır.
LiveData: UI'nin veri değişikliklerine tepki verebilmesi için kullanılır.
Coroutines: Arka planda ağ istekleri gibi işlemleri yönetmek için kullanılır.

##API Kullanımı
Bu proje, dış bir API'den ürün verilerini çekmek için Retrofit kütüphanesini kullanır. 

##Kullanım
Uygulamayı açtığınızda ürünler otomatik olarak API'den çekilir ve listelenir.
Ürünlerin üzerine tıklayarak detaylarını inceleyebilir, "Sepete Ekle" butonu ile sepetinize ekleyebilirsiniz.
Sepet sayfasında sepetinize eklediğiniz ürünleri görüntüleyebilir ve siparişinizi tamamlayabilirsiniz.

##Bağımlılıklar
Retrofit
Firebase
Coroutines
Glide (Görsellerin yüklenmesi için)
Android Jetpack (LiveData, ViewModel, vb.)

##Geliştirici
Bu proje Kasım Adalan tarafından verilen eğitimde bitirme ödevi olarak geliştirilmiştir.
