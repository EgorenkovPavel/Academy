package ru.epipasha.academy

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MoviesListViewModelFactory(
    private val context : Application
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(context)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}