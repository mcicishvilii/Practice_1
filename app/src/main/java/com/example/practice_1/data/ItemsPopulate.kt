package com.example.practice_1.data

import com.example.practice_1.R

class ItemsPopulate {
    companion object {
        fun getItems(): List<Items> = listOf(
            Items("item number 1","this is item n1 description",null,HasImage.FALSE),
            Items("item number 2","this is item n2 description",null,HasImage.FALSE),
            Items("item number 3","this is item n3 description", R.drawable.ic_launcher_background,HasImage.TRUE),
            Items("item number 4","this is item n4 description",null,HasImage.FALSE),
            Items("item number 5","this is item n5 description",R.drawable.ic_launcher_foreground,HasImage.TRUE),
            Items("item number 6","this is item n6 description",null,HasImage.FALSE),
            Items("item number 7","this is item n7 description",R.drawable.ic_launcher_background,HasImage.TRUE),
            Items("item number 8","this is item n8 description",null,HasImage.FALSE),
            Items("item number 9","this is item n9 description",null,HasImage.FALSE),
            Items("item number 10","this is item n10 description",null,HasImage.FALSE),
        )
    }
}