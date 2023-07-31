package com.example.contact.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.dao.ContactDao
import com.example.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalCaseModule {

    @[Provides Singleton]
    fun getDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "contact_db")
            .fallbackToDestructiveMigration()
            .build()

    @[Provides Singleton]
    fun getContactDao(database: AppDatabase): ContactDao = database.getContactDao()
}