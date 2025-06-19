package com.example.schedulemanagerapp.di

import com.example.schedulemanagerapp.data.repository.FirestoreScheduleRepository
import com.example.schedulemanagerapp.data.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleModule {

    @Provides
    @Singleton
    fun provideScheduleRepository(): ScheduleRepository {
        return FirestoreScheduleRepository()
    }
}

