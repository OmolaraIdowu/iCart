package com.swancodes.icart.data.cart

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*
import kotlin.collections.List

@Dao
abstract class CartDao {

    @Query("SELECT * FROM cartTable")
    abstract fun getAllCartItems(): Flow<List<Cart>>

    @Query("SELECT * FROM cartTable WHERE productId = :productId")
    abstract fun getCartItemsById(productId: String): Flow<Cart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCartItem(cart: Cart)

    @Update
    abstract fun updateCartItem(cart: Cart)

    @Delete
    abstract fun deleteCartItem(cart: Cart)

}
