package com.example.nestworth.apartment

enum class Category {
    SOFA,
    TV,
    TABLE,
    PLANT,
    PAINTING,
    WINDOW,
    WALLS,
    FLOOR,
    SHELF
}

data class ItemLayout(
    // 0.0 = left/top, 1.0 = right/bottom
    val x: Float,
    val y: Float,
    val width: Float,
    val height: Float,
    val layer: Int
)

data class ApartmentItem(
    val id: String,
    val category: Category,
    val unlockAt: Long,
    val asset: Int,
    val layout: ItemLayout
)
