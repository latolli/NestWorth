package com.example.nestworth.apartment

import com.example.nestworth.R

object ApartmentCatalog {

    private fun layoutFor(category: Category): ItemLayout = when (category) {
        // Furniture
        Category.SOFA -> ItemLayout(
            0.25f, 0.60f, 0.60f, 0.30f, 10
        )
        Category.TV -> ItemLayout(
            0.38f, 0.43f, 0.30f, 0.18f, 20
        )
        Category.TABLE -> ItemLayout(
            0.38f, 0.68f, 0.30f, 0.18f, 15
        )
        Category.PLANT -> ItemLayout(
            0.78f, 0.55f, 0.15f, 0.30f, 12
        )

        // Wall decorations
        Category.PAINTING -> ItemLayout(
            0.15f, 0.15f, 0.15f, 0.30f, 10
        )
        Category.SHELF -> ItemLayout(
            0.65f, 0.20f, 0.25f, 0.15f, 11
        )

        // Apartment structure
        Category.WINDOW -> ItemLayout(
            0.65f, 0.05f, 0.25f, 0.35f, 2
        )
        Category.WALLS -> ItemLayout(
            0.00f, 0.00f, 1.00f, 0.75f, 0
        )
        Category.FLOOR -> ItemLayout(
            0.00f, 0.65f, 1.00f, 0.35f, 1
        )
    }

    val apartmentItems = listOf(
        ApartmentItem(
            id = "sofa_01",
            category = Category.SOFA,
            unlockAt = 0,
            asset = R.drawable.sofa_01,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_02",
            category = Category.SOFA,
            unlockAt = 3,
            asset = R.drawable.sofa_02,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_03",
            category = Category.SOFA,
            unlockAt = 15,
            asset = R.drawable.sofa_03,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "painting_01",
            category = Category.PAINTING,
            unlockAt = 5,
            asset = R.drawable.painting_01,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_02",
            category = Category.PAINTING,
            unlockAt = 10,
            asset = R.drawable.painting_02,
            layout = layoutFor(Category.PAINTING)
        ),
    )

    val itemsByCategory = apartmentItems.groupBy { it.category }

    fun getCurrentItem(
        category: Category,
        xpLevel: Int
    ): ApartmentItem? {
        return itemsByCategory[category]
            ?.lastOrNull { it.unlockAt <= xpLevel }
    }

    fun getCurrentItems(xpLevel: Int): List<ApartmentItem> {
        return Category.entries
            .mapNotNull { getCurrentItem(it, xpLevel) }
            .sortedBy { it.layout.layer }
    }
}