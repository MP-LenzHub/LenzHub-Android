package com.plzgpt.lenzhub.ui.data

import com.plzgpt.lenzhub.R

enum class Category(
    val category: String,
    val image: Int
) {
    ANIMAL("동물", R.drawable.ic_animal),
    PERSON("인물", R.drawable.ic_person),
    SIGHT("풍경", R.drawable.ic_sight),
    FOOD("음식", R.drawable.ic_food)
}