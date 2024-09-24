package com.yusufkutluay.bootcampbitirme.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.yusufkutluay.bootcampbitirme.data.datasource.FoodsDataSource
import com.yusufkutluay.bootcampbitirme.data.repo.FoodsRepository
import com.yusufkutluay.bootcampbitirme.retrofit.ApiUtils
import com.yusufkutluay.bootcampbitirme.retrofit.FoodsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFoodsRepository(fds : FoodsDataSource) : FoodsRepository{
        return FoodsRepository(fds)
    }

    @Provides
    @Singleton
    fun provideFoodsDataSource(
        fdao : FoodsDao,
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ) : FoodsDataSource{
        return FoodsDataSource(fdao,firestore,auth)
    }

    @Provides
    @Singleton
    fun provideFoodsDao() : FoodsDao{
        return ApiUtils.getFoodsDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseUsers() : FirebaseFirestore{
        return Firebase.firestore
    }


    @Provides
    @Singleton
    fun provideAuth() : FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

}