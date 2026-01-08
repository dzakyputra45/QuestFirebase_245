@file:OptIn(InternalSerializationApi::class)

package com.example.myfirebase.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.modeldata.Siswa
import com.example.myfirebase.repositori.RepositorySiswa
import com.example.myfirebase.view.route.DestinasiDetail
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi

sealed interface StatusUIDetail {
    data class Success(val satusiswa: Siswa?) : StatusUIDetail
    object Error : StatusUIDetail
    object Loading : StatusUIDetail
}

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    private val idSiswa: String? =
        savedStateHandle[DestinasiDetail.itemIdArg]

    var statusUIDetail by mutableStateOf<StatusUIDetail>(StatusUIDetail.Loading)
        private set

    init {
        println("===== DETAIL VIEWMODEL =====")
        println("DETAIL ID DARI NAV: $idSiswa")
        if (idSiswa != null) {
            getSatuSiswa(idSiswa)
        } else {
            statusUIDetail = StatusUIDetail.Error
        }
    }

    private fun getSatuSiswa(id: String) {
        viewModelScope.launch {
            val siswa = repositorySiswa.getSatuSiswa(id)
            println("DATA DARI FIRESTORE: $siswa")
            statusUIDetail = StatusUIDetail.Success(siswa)
        }
    }

    suspend fun hapusSatuSiswa() {
        idSiswa?.let {
            repositorySiswa.hapusSatuSiswa(it)
        }
    }
}


