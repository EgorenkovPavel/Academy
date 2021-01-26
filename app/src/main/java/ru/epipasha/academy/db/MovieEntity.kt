package ru.epipasha.academy.db

import android.icu.text.CaseMap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id : Long,

    @ColumnInfo(name = "title")
    val title: String,

    val overview: String,
)