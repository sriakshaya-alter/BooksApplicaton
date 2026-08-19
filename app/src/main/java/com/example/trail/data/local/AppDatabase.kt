package com.example.trail.data.local

import android.content.Context //gives code access to android
import androidx.room.Database //this class represents my database
import androidx.room.Room //this imports the room class
import androidx.room.RoomDatabase //this is the parent class inherted by AppDatabse

@Database(
    entities = [BookEntity::class, UserBookStateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun userBookStateDao(): UserBookStateDao

    companion object { // shared area inside the class, call a function without creating a object
        @Volatile
        private var INSTANCE: AppDatabase? = null //Intsnce store the databse obj

        fun getInstance(context: Context): AppDatabase { //give me the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "books_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
