package com.example.schedulemanagerapp.di

import android.app.Application
import androidx.room.Room
import com.example.schedulemanagerapp.data.repository.RoomScheduleRepository
import com.example.schedulemanagerapp.data.repository.ScheduleRepository
import com.example.schedulemanagerapp.data.room.AppDatabase
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
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "schedule_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(
        db: AppDatabase
    ): ScheduleRepository {
        return RoomScheduleRepository(db)
    }
}
