package com.swancodes.icart.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.swancodes.icart.data.cart.Cart
import com.swancodes.icart.data.cart.CartDao
import com.swancodes.icart.data.product.Product
import com.swancodes.icart.data.product.ProductDao
import com.swancodes.icart.utilities.DATABASE_NAME
import com.swancodes.icart.worker.ProductDatabaseWorker

@Database(entities = [Product::class, Cart::class], version = 1, exportSchema = true)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao

    companion object {

        @Volatile
        private var instance: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ProductDatabase {
            return Room.databaseBuilder(context, ProductDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<ProductDatabaseWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)

                        Log.d("TAG_database", "onCreate: Database created")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)

                        Log.d("TAG_database", "onOpen: Database opened")
                    }
                })
                .build()
        }
    }
}
