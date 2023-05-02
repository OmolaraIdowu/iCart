package com.swancodes.icart.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.swancodes.icart.data.product.Product
import com.swancodes.icart.data.ProductDatabase
import com.swancodes.icart.utilities.PRODUCT_DATA_FILENAME
import kotlinx.coroutines.coroutineScope

class ProductDatabaseWorker(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return coroutineScope {
            try {
                applicationContext.assets.open(PRODUCT_DATA_FILENAME).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val productType = object : TypeToken<List<Product>>() {}.type
                        val productList: List<Product> = Gson().fromJson(jsonReader, productType)

                        val database = ProductDatabase.getInstance(applicationContext)
                        database.productDao().insertAllProducts(productList)

                        Result.success()
                    }
                }
            } catch (t: Throwable) {
                Log.e(TAG, "doWork: Error populating database", t)
                Result.failure()
            }
        }
    }

    companion object {
        private const val TAG = "ProductDatabaseWorker"
    }
}
