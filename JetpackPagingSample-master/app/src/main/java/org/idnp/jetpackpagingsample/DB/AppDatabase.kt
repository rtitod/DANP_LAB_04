package org.idnp.jetpackpagingsample.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.idnp.jetpackpagingsample.entities.Country
import org.idnp.jetpackpagingsample.loader.preloaddata
import org.idnp.jetpackpagingsample.model.CountryDao

@Database(entities = [Country::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database_countries"
                    ).fallbackToDestructiveMigration()
                        .addCallback(preloaddata(context))
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}