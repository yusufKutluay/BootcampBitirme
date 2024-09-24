package com.yusufkutluay.bootcampbitirme.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.yusufkutluay.bootcampbitirme.ActivityLogin
import com.yusufkutluay.bootcampbitirme.R


class MyWorkerBildirim (appContext: Context, workerParametres: WorkerParameters) :
    Worker(appContext,workerParametres){
    override fun doWork(): Result {
        bildirimOlustur()
        return Result.success()
    }
    fun bildirimOlustur(){
        val builder: NotificationCompat.Builder
        val bildirimYoneticisi = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(applicationContext, ActivityLogin::class.java)
        val gidilecekIntent = PendingIntent.getActivity(applicationContext,1,intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val kanalId = "kanalId"
            val kanalAd = "kanalAd"
            val kanalAciklama = "kanalAciklama"
            val kanalOnceligi = NotificationManager.IMPORTANCE_HIGH

            var kanal: NotificationChannel? = bildirimYoneticisi.getNotificationChannel(kanalId)

            if(kanal == null){
                kanal = NotificationChannel(kanalId,kanalAd,kanalOnceligi)
                kanal.description = kanalAciklama
                bildirimYoneticisi.createNotificationChannel(kanal)
            }

            builder = NotificationCompat.Builder(applicationContext,kanalId)

            builder.setContentTitle("Sepetle")
                .setContentText("Siparişiniz hazırlanıyor...")
                .setSmallIcon(R.drawable.sepetle_transparent)
                .setContentIntent(gidilecekIntent)
                .setAutoCancel(true)
        }else{
            builder = NotificationCompat.Builder(applicationContext)

            builder.setContentTitle("Sepetle")
                .setContentText("Siparişiniz hazırlanıyor...")
                .setSmallIcon(R.drawable.sepetle_transparent)
                .setContentIntent(gidilecekIntent)
                .setAutoCancel(true)
                .priority = Notification.PRIORITY_HIGH
        }

        bildirimYoneticisi.notify(1,builder.build())
    }

}