package com.fatec.merge_skills_kmp.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Product(
    val id: String,
    val name: String,
    val description: String? = null,
    val sku: Int,
    val category: String,
    @SerialName("created_at")
    val createdAt: String? = null
)

data class Stock(
    val id: String,
    @SerialName("product_id")
    val productId: Int,
    val quantity: Int,
    val unitPrice: Double,
    val location: String,
    @SerialName("update_at")
    val updateAt: Date
)

data class Summary(
    @SerialName("product_id")
    val productId: String,
    @SerialName("product_name")
    val productName: String,
    @SerialName("total_quantity")
    val totalQuantity: Int
)

@Serializable
data class ProductInsert(
    val name: String,
    val description: String? = null,
    val sku: Int,
    val category: String
)

@Serializable
data class StockInsert(
    val productId: Int,
    val quantity: Int,
    val unitPrice: Double,
    val location: String
)