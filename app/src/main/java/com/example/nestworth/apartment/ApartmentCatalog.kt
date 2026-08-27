package com.example.nestworth.apartment

import com.example.nestworth.R

object ApartmentCatalog {

    private fun layoutFor(category: Category): ItemLayout = when (category) {
        // Furniture
        Category.SOFA -> ItemLayout(
            0.19f, 0.40f, 0.50f, 0.45f, 10
        )

        Category.TV -> ItemLayout(
            0.38f, 0.15f, 0.20f, 0.20f, 20
        )

        Category.TABLE -> ItemLayout(
            0.30f, 0.76f, 0.30f, 0.30f, 15
        )

        Category.PLANT -> ItemLayout(
            0.02f, 0.47f, 0.18f, 0.38f, 12
        )

        // Wall decorations
        Category.PAINTING -> ItemLayout(
            0.05f, 0.10f, 0.25f, 0.25f, 10
        )

        Category.SHELF -> ItemLayout(
            0.05f, 0.30f, 0.25f, 0.15f, 11
        )

        // Apartment structure
        Category.WINDOW -> ItemLayout(
            0.64f, 0.08f, 0.32f, 0.58f, 2
        )

        Category.WALLS -> ItemLayout(
            0.00f, 0.00f, 1.00f, 0.90f, 0
        )

        Category.FLOOR -> ItemLayout(
            0.00f, 0.65f, 1.00f, 0.35f, 1
        )
    }

    val apartmentItems = listOf(

        // =========================================================
        // SOFA
        // =========================================================

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
            unlockAt = 10,
            asset = R.drawable.sofa_02,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_03",
            category = Category.SOFA,
            unlockAt = 26,
            asset = R.drawable.sofa_03,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_04",
            category = Category.SOFA,
            unlockAt = 42,
            asset = R.drawable.sofa_04,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_05",
            category = Category.SOFA,
            unlockAt = 57,
            asset = R.drawable.sofa_05,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_06",
            category = Category.SOFA,
            unlockAt = 73,
            asset = R.drawable.sofa_06,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_07",
            category = Category.SOFA,
            unlockAt = 89,
            asset = R.drawable.sofa_07,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_08",
            category = Category.SOFA,
            unlockAt = 104,
            asset = R.drawable.sofa_08,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_09",
            category = Category.SOFA,
            unlockAt = 120,
            asset = R.drawable.sofa_09,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_10",
            category = Category.SOFA,
            unlockAt = 136,
            asset = R.drawable.sofa_10,
            layout = layoutFor(Category.SOFA)
        ),


        // =========================================================
        // TV
        // =========================================================

        ApartmentItem(
            id = "tv_01",
            category = Category.TV,
            unlockAt = 0,
            asset = R.drawable.tv_01,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_02",
            category = Category.TV,
            unlockAt = 12,
            asset = R.drawable.tv_02,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_03",
            category = Category.TV,
            unlockAt = 28,
            asset = R.drawable.tv_03,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_04",
            category = Category.TV,
            unlockAt = 43,
            asset = R.drawable.tv_04,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_05",
            category = Category.TV,
            unlockAt = 59,
            asset = R.drawable.tv_05,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_06",
            category = Category.TV,
            unlockAt = 75,
            asset = R.drawable.tv_06,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_07",
            category = Category.TV,
            unlockAt = 90,
            asset = R.drawable.tv_07,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_08",
            category = Category.TV,
            unlockAt = 106,
            asset = R.drawable.tv_08,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_09",
            category = Category.TV,
            unlockAt = 122,
            asset = R.drawable.tv_09,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_10",
            category = Category.TV,
            unlockAt = 138,
            asset = R.drawable.tv_10,
            layout = layoutFor(Category.TV)
        ),


        // =========================================================
        // TABLE
        // =========================================================

        ApartmentItem(
            id = "table_01",
            category = Category.TABLE,
            unlockAt = 2,
            asset = R.drawable.table_01,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_02",
            category = Category.TABLE,
            unlockAt = 14,
            asset = R.drawable.table_02,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_03",
            category = Category.TABLE,
            unlockAt = 29,
            asset = R.drawable.table_03,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_04",
            category = Category.TABLE,
            unlockAt = 45,
            asset = R.drawable.table_04,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_05",
            category = Category.TABLE,
            unlockAt = 61,
            asset = R.drawable.table_05,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_06",
            category = Category.TABLE,
            unlockAt = 76,
            asset = R.drawable.table_06,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_07",
            category = Category.TABLE,
            unlockAt = 92,
            asset = R.drawable.table_07,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_08",
            category = Category.TABLE,
            unlockAt = 108,
            asset = R.drawable.table_08,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_09",
            category = Category.TABLE,
            unlockAt = 124,
            asset = R.drawable.table_09,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_10",
            category = Category.TABLE,
            unlockAt = 140,
            asset = R.drawable.table_10,
            layout = layoutFor(Category.TABLE)
        ),


        // =========================================================
        // PLANT
        // =========================================================

        ApartmentItem(
            id = "plant_01",
            category = Category.PLANT,
            unlockAt = 4,
            asset = R.drawable.plant_01,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_02",
            category = Category.PLANT,
            unlockAt = 15,
            asset = R.drawable.plant_02,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_03",
            category = Category.PLANT,
            unlockAt = 31,
            asset = R.drawable.plant_03,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_04",
            category = Category.PLANT,
            unlockAt = 47,
            asset = R.drawable.plant_04,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_05",
            category = Category.PLANT,
            unlockAt = 62,
            asset = R.drawable.plant_05,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_06",
            category = Category.PLANT,
            unlockAt = 78,
            asset = R.drawable.plant_06,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_07",
            category = Category.PLANT,
            unlockAt = 94,
            asset = R.drawable.plant_07,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_08",
            category = Category.PLANT,
            unlockAt = 110,
            asset = R.drawable.plant_08,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_09",
            category = Category.PLANT,
            unlockAt = 126,
            asset = R.drawable.plant_09,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_10",
            category = Category.PLANT,
            unlockAt = 141,
            asset = R.drawable.plant_10,
            layout = layoutFor(Category.PLANT)
        ),


        // =========================================================
        // PAINTING
        // =========================================================

        ApartmentItem(
            id = "painting_01",
            category = Category.PAINTING,
            unlockAt = 7,
            asset = R.drawable.painting_01,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_02",
            category = Category.PAINTING,
            unlockAt = 17,
            asset = R.drawable.painting_02,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_03",
            category = Category.PAINTING,
            unlockAt = 33,
            asset = R.drawable.painting_03,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_04",
            category = Category.PAINTING,
            unlockAt = 48,
            asset = R.drawable.painting_04,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_05",
            category = Category.PAINTING,
            unlockAt = 64,
            asset = R.drawable.painting_05,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_06",
            category = Category.PAINTING,
            unlockAt = 80,
            asset = R.drawable.painting_06,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_07",
            category = Category.PAINTING,
            unlockAt = 96,
            asset = R.drawable.painting_07,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_08",
            category = Category.PAINTING,
            unlockAt = 112,
            asset = R.drawable.painting_08,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_09",
            category = Category.PAINTING,
            unlockAt = 127,
            asset = R.drawable.painting_09,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_10",
            category = Category.PAINTING,
            unlockAt = 143,
            asset = R.drawable.painting_10,
            layout = layoutFor(Category.PAINTING)
        ),


        // =========================================================
        // SHELF
        // =========================================================

        ApartmentItem(
            id = "shelf_01",
            category = Category.SHELF,
            unlockAt = 9,
            asset = R.drawable.shelf_01,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_02",
            category = Category.SHELF,
            unlockAt = 19,
            asset = R.drawable.shelf_02,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_03",
            category = Category.SHELF,
            unlockAt = 34,
            asset = R.drawable.shelf_03,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_04",
            category = Category.SHELF,
            unlockAt = 50,
            asset = R.drawable.shelf_04,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_05",
            category = Category.SHELF,
            unlockAt = 66,
            asset = R.drawable.shelf_05,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_06",
            category = Category.SHELF,
            unlockAt = 82,
            asset = R.drawable.shelf_06,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_07",
            category = Category.SHELF,
            unlockAt = 98,
            asset = R.drawable.shelf_07,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_08",
            category = Category.SHELF,
            unlockAt = 113,
            asset = R.drawable.shelf_08,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_09",
            category = Category.SHELF,
            unlockAt = 129,
            asset = R.drawable.shelf_09,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_10",
            category = Category.SHELF,
            unlockAt = 145,
            asset = R.drawable.shelf_10,
            layout = layoutFor(Category.SHELF)
        ),


        // =========================================================
        // WINDOW
        // =========================================================

        ApartmentItem(
            id = "window_01",
            category = Category.WINDOW,
            unlockAt = 0,
            asset = R.drawable.window_01,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_02",
            category = Category.WINDOW,
            unlockAt = 20,
            asset = R.drawable.window_02,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_03",
            category = Category.WINDOW,
            unlockAt = 36,
            asset = R.drawable.window_03,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_04",
            category = Category.WINDOW,
            unlockAt = 52,
            asset = R.drawable.window_04,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_05",
            category = Category.WINDOW,
            unlockAt = 68,
            asset = R.drawable.window_05,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_06",
            category = Category.WINDOW,
            unlockAt = 84,
            asset = R.drawable.window_06,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_07",
            category = Category.WINDOW,
            unlockAt = 99,
            asset = R.drawable.window_07,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_08",
            category = Category.WINDOW,
            unlockAt = 115,
            asset = R.drawable.window_08,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_09",
            category = Category.WINDOW,
            unlockAt = 131,
            asset = R.drawable.window_09,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_10",
            category = Category.WINDOW,
            unlockAt = 146,
            asset = R.drawable.window_10,
            layout = layoutFor(Category.WINDOW)
        ),


        // =========================================================
        // WALLS
        // =========================================================

        ApartmentItem(
            id = "walls_01",
            category = Category.WALLS,
            unlockAt = 0,
            asset = R.drawable.walls_01,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_02",
            category = Category.WALLS,
            unlockAt = 22,
            asset = R.drawable.walls_02,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_03",
            category = Category.WALLS,
            unlockAt = 38,
            asset = R.drawable.walls_03,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_04",
            category = Category.WALLS,
            unlockAt = 54,
            asset = R.drawable.walls_04,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_05",
            category = Category.WALLS,
            unlockAt = 70,
            asset = R.drawable.walls_05,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_06",
            category = Category.WALLS,
            unlockAt = 85,
            asset = R.drawable.walls_06,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_07",
            category = Category.WALLS,
            unlockAt = 101,
            asset = R.drawable.walls_07,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_08",
            category = Category.WALLS,
            unlockAt = 117,
            asset = R.drawable.walls_08,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_09",
            category = Category.WALLS,
            unlockAt = 132,
            asset = R.drawable.walls_09,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_10",
            category = Category.WALLS,
            unlockAt = 148,
            asset = R.drawable.walls_10,
            layout = layoutFor(Category.WALLS)
        ),


        // =========================================================
        // FLOOR
        // =========================================================

        ApartmentItem(
            id = "floor_01",
            category = Category.FLOOR,
            unlockAt = 0,
            asset = R.drawable.floor_01,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_02",
            category = Category.FLOOR,
            unlockAt = 24,
            asset = R.drawable.floor_02,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_03",
            category = Category.FLOOR,
            unlockAt = 40,
            asset = R.drawable.floor_03,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_04",
            category = Category.FLOOR,
            unlockAt = 56,
            asset = R.drawable.floor_04,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_05",
            category = Category.FLOOR,
            unlockAt = 71,
            asset = R.drawable.floor_05,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_06",
            category = Category.FLOOR,
            unlockAt = 87,
            asset = R.drawable.floor_06,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_07",
            category = Category.FLOOR,
            unlockAt = 103,
            asset = R.drawable.floor_07,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_08",
            category = Category.FLOOR,
            unlockAt = 118,
            asset = R.drawable.floor_08,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_09",
            category = Category.FLOOR,
            unlockAt = 134,
            asset = R.drawable.floor_09,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_10",
            category = Category.FLOOR,
            unlockAt = 150,
            asset = R.drawable.floor_10,
            layout = layoutFor(Category.FLOOR)
        ),
    )

    val itemsByCategory = apartmentItems.groupBy { it.category }

    fun getCurrentItem(
        category: Category,
        xpLevel: Int
    ): ApartmentItem? {
        return itemsByCategory[category]
            ?.filter { it.unlockAt <= xpLevel }
            ?.maxByOrNull { it.unlockAt }
    }

    fun getCurrentItems(xpLevel: Int): List<ApartmentItem> {
        return Category.entries
            .mapNotNull { getCurrentItem(it, xpLevel) }
            .sortedBy { it.layout.layer }
    }
}
