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
            0.29f, 0.72f, 0.30f, 0.20f, 15
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
            0.00f, 0.00f, 1.00f, 1.00f, 0
        )

        Category.FLOOR -> ItemLayout(
            0.00f, 0.65f, 1.00f, 1.00f, 1
        )
    }

    // TODO: Think about optimal level unlocks
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
            id = "sofa_04",
            category = Category.SOFA,
            unlockAt = 30,
            asset = R.drawable.sofa_04,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_05",
            category = Category.SOFA,
            unlockAt = 60,
            asset = R.drawable.sofa_05,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_06",
            category = Category.SOFA,
            unlockAt = 100,
            asset = R.drawable.sofa_06,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_07",
            category = Category.SOFA,
            unlockAt = 160,
            asset = R.drawable.sofa_07,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_08",
            category = Category.SOFA,
            unlockAt = 250,
            asset = R.drawable.sofa_08,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_09",
            category = Category.SOFA,
            unlockAt = 400,
            asset = R.drawable.sofa_09,
            layout = layoutFor(Category.SOFA)
        ),
        ApartmentItem(
            id = "sofa_10",
            category = Category.SOFA,
            unlockAt = 600,
            asset = R.drawable.sofa_10,
            layout = layoutFor(Category.SOFA)
        ),


        // =========================================================
        // TV
        // =========================================================

        ApartmentItem(
            id = "tv_01",
            category = Category.TV,
            unlockAt = 1,
            asset = R.drawable.tv_01,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_02",
            category = Category.TV,
            unlockAt = 7,
            asset = R.drawable.tv_02,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_03",
            category = Category.TV,
            unlockAt = 20,
            asset = R.drawable.tv_03,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_04",
            category = Category.TV,
            unlockAt = 40,
            asset = R.drawable.tv_04,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_05",
            category = Category.TV,
            unlockAt = 75,
            asset = R.drawable.tv_05,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_06",
            category = Category.TV,
            unlockAt = 130,
            asset = R.drawable.tv_06,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_07",
            category = Category.TV,
            unlockAt = 200,
            asset = R.drawable.tv_07,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_08",
            category = Category.TV,
            unlockAt = 325,
            asset = R.drawable.tv_08,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_09",
            category = Category.TV,
            unlockAt = 500,
            asset = R.drawable.tv_09,
            layout = layoutFor(Category.TV)
        ),
        ApartmentItem(
            id = "tv_10",
            category = Category.TV,
            unlockAt = 750,
            asset = R.drawable.tv_10,
            layout = layoutFor(Category.TV)
        ),


        // =========================================================
        // TABLE
        // =========================================================

        ApartmentItem(
            id = "table_01",
            category = Category.TABLE,
            unlockAt = 4,
            asset = R.drawable.table_01,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_02",
            category = Category.TABLE,
            unlockAt = 12,
            asset = R.drawable.table_02,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_03",
            category = Category.TABLE,
            unlockAt = 25,
            asset = R.drawable.table_03,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_04",
            category = Category.TABLE,
            unlockAt = 50,
            asset = R.drawable.table_04,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_05",
            category = Category.TABLE,
            unlockAt = 90,
            asset = R.drawable.table_05,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_06",
            category = Category.TABLE,
            unlockAt = 150,
            asset = R.drawable.table_06,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_07",
            category = Category.TABLE,
            unlockAt = 240,
            asset = R.drawable.table_07,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_08",
            category = Category.TABLE,
            unlockAt = 375,
            asset = R.drawable.table_08,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_09",
            category = Category.TABLE,
            unlockAt = 550,
            asset = R.drawable.table_09,
            layout = layoutFor(Category.TABLE)
        ),
        ApartmentItem(
            id = "table_10",
            category = Category.TABLE,
            unlockAt = 800,
            asset = R.drawable.table_10,
            layout = layoutFor(Category.TABLE)
        ),


        // =========================================================
        // PLANT
        // =========================================================

        ApartmentItem(
            id = "plant_01",
            category = Category.PLANT,
            unlockAt = 6,
            asset = R.drawable.plant_01,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_02",
            category = Category.PLANT,
            unlockAt = 18,
            asset = R.drawable.plant_02,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_03",
            category = Category.PLANT,
            unlockAt = 35,
            asset = R.drawable.plant_03,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_04",
            category = Category.PLANT,
            unlockAt = 65,
            asset = R.drawable.plant_04,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_05",
            category = Category.PLANT,
            unlockAt = 110,
            asset = R.drawable.plant_05,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_06",
            category = Category.PLANT,
            unlockAt = 180,
            asset = R.drawable.plant_06,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_07",
            category = Category.PLANT,
            unlockAt = 280,
            asset = R.drawable.plant_07,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_08",
            category = Category.PLANT,
            unlockAt = 425,
            asset = R.drawable.plant_08,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_09",
            category = Category.PLANT,
            unlockAt = 625,
            asset = R.drawable.plant_09,
            layout = layoutFor(Category.PLANT)
        ),
        ApartmentItem(
            id = "plant_10",
            category = Category.PLANT,
            unlockAt = 900,
            asset = R.drawable.plant_10,
            layout = layoutFor(Category.PLANT)
        ),


        // =========================================================
        // PAINTING
        // =========================================================

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
            unlockAt = 16,
            asset = R.drawable.painting_02,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_03",
            category = Category.PAINTING,
            unlockAt = 32,
            asset = R.drawable.painting_03,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_04",
            category = Category.PAINTING,
            unlockAt = 60,
            asset = R.drawable.painting_04,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_05",
            category = Category.PAINTING,
            unlockAt = 105,
            asset = R.drawable.painting_05,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_06",
            category = Category.PAINTING,
            unlockAt = 170,
            asset = R.drawable.painting_06,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_07",
            category = Category.PAINTING,
            unlockAt = 260,
            asset = R.drawable.painting_07,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_08",
            category = Category.PAINTING,
            unlockAt = 400,
            asset = R.drawable.painting_08,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_09",
            category = Category.PAINTING,
            unlockAt = 600,
            asset = R.drawable.painting_09,
            layout = layoutFor(Category.PAINTING)
        ),
        ApartmentItem(
            id = "painting_10",
            category = Category.PAINTING,
            unlockAt = 850,
            asset = R.drawable.painting_10,
            layout = layoutFor(Category.PAINTING)
        ),


        // =========================================================
        // SHELF
        // =========================================================

        ApartmentItem(
            id = "shelf_01",
            category = Category.SHELF,
            unlockAt = 8,
            asset = R.drawable.shelf_01,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_02",
            category = Category.SHELF,
            unlockAt = 22,
            asset = R.drawable.shelf_02,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_03",
            category = Category.SHELF,
            unlockAt = 45,
            asset = R.drawable.shelf_03,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_04",
            category = Category.SHELF,
            unlockAt = 80,
            asset = R.drawable.shelf_04,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_05",
            category = Category.SHELF,
            unlockAt = 130,
            asset = R.drawable.shelf_05,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_06",
            category = Category.SHELF,
            unlockAt = 210,
            asset = R.drawable.shelf_06,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_07",
            category = Category.SHELF,
            unlockAt = 320,
            asset = R.drawable.shelf_07,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_08",
            category = Category.SHELF,
            unlockAt = 475,
            asset = R.drawable.shelf_08,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_09",
            category = Category.SHELF,
            unlockAt = 700,
            asset = R.drawable.shelf_09,
            layout = layoutFor(Category.SHELF)
        ),
        ApartmentItem(
            id = "shelf_10",
            category = Category.SHELF,
            unlockAt = 950,
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
            unlockAt = 50,
            asset = R.drawable.window_03,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_04",
            category = Category.WINDOW,
            unlockAt = 100,
            asset = R.drawable.window_04,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_05",
            category = Category.WINDOW,
            unlockAt = 180,
            asset = R.drawable.window_05,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_06",
            category = Category.WINDOW,
            unlockAt = 300,
            asset = R.drawable.window_06,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_07",
            category = Category.WINDOW,
            unlockAt = 450,
            asset = R.drawable.window_07,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_08",
            category = Category.WINDOW,
            unlockAt = 650,
            asset = R.drawable.window_08,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_09",
            category = Category.WINDOW,
            unlockAt = 850,
            asset = R.drawable.window_09,
            layout = layoutFor(Category.WINDOW)
        ),
        ApartmentItem(
            id = "window_10",
            category = Category.WINDOW,
            unlockAt = 1100,
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
            unlockAt = 35,
            asset = R.drawable.walls_02,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_03",
            category = Category.WALLS,
            unlockAt = 90,
            asset = R.drawable.walls_03,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_04",
            category = Category.WALLS,
            unlockAt = 180,
            asset = R.drawable.walls_04,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_05",
            category = Category.WALLS,
            unlockAt = 300,
            asset = R.drawable.walls_05,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_06",
            category = Category.WALLS,
            unlockAt = 450,
            asset = R.drawable.walls_06,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_07",
            category = Category.WALLS,
            unlockAt = 650,
            asset = R.drawable.walls_07,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_08",
            category = Category.WALLS,
            unlockAt = 900,
            asset = R.drawable.walls_08,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_09",
            category = Category.WALLS,
            unlockAt = 1200,
            asset = R.drawable.walls_09,
            layout = layoutFor(Category.WALLS)
        ),
        ApartmentItem(
            id = "walls_10",
            category = Category.WALLS,
            unlockAt = 1600,
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
            unlockAt = 25,
            asset = R.drawable.floor_02,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_03",
            category = Category.FLOOR,
            unlockAt = 70,
            asset = R.drawable.floor_03,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_04",
            category = Category.FLOOR,
            unlockAt = 150,
            asset = R.drawable.floor_04,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_05",
            category = Category.FLOOR,
            unlockAt = 260,
            asset = R.drawable.floor_05,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_06",
            category = Category.FLOOR,
            unlockAt = 400,
            asset = R.drawable.floor_06,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_07",
            category = Category.FLOOR,
            unlockAt = 600,
            asset = R.drawable.floor_07,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_08",
            category = Category.FLOOR,
            unlockAt = 850,
            asset = R.drawable.floor_08,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_09",
            category = Category.FLOOR,
            unlockAt = 1150,
            asset = R.drawable.floor_09,
            layout = layoutFor(Category.FLOOR)
        ),
        ApartmentItem(
            id = "floor_10",
            category = Category.FLOOR,
            unlockAt = 1500,
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