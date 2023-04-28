package com.swancodes.icart.data.cart

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.swancodes.icart.data.product.Product

@Entity(tableName = "cartTable")
data class Cart(
    @PrimaryKey(autoGenerate = false)
    @Embedded val product: Product,
    val quantity: Int
)

