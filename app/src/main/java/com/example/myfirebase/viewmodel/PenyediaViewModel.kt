package com.example.myfirebase.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myfirebase.repositori.AplikasiDataSiswa

fun CreationExtras.aplikasiDataSiswa(): AplikasiDataSiswa =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
            as AplikasiDataSiswa

object PenyediaViewModel {

    val Factory = viewModelFactory {

        initializer {
            HomeViewModel(
                aplikasiDataSiswa().container.repositorySiswa
            )
        }

        initializer {
            EntryViewModel(
                aplikasiDataSiswa().container.repositorySiswa
            )
        }
    }
}
