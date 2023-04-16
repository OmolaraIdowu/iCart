package com.swancodes.icart.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "productTable")
data class Product(
    @PrimaryKey(autoGenerate = false)
    val productId: String,
    val name: String,
    val description: String,
    val rating: Double,
    val category: String,
    val imageUrl: String,
    val price: Long,
    var quantityRemaining: Int,
): Parcelable
