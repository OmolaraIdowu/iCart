package com.swancodes.icart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
abstract class ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAllProducts(products: List<Product>)

    @Query("SELECT DISTINCT category FROM productTable")
    abstract fun getAllCategories(): Flow<List<String>>

    @Query("SELECT * FROM productTable WHERE productId = :productId")
    abstract fun getProductById(productId: String): Flow<Product>

    /**
     * Searches for products based on category and returns all products when an empty string is passed
     */
    @Query("SELECT * FROM productTable WHERE category LIKE '%' || :category || '%'")
    abstract fun getProductsByCategory(category: String): Flow<List<Product>>

    @Update
    abstract fun updateProduct(product: Product)
}
