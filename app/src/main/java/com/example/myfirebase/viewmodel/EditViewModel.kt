package com.example.myfirebase.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirebase.modeldata.DetailSiswa
import com.example.myfirebase.modeldata.UIStateSiswa
import com.example.myfirebase.modeldata.toDataSiswa
import com.example.myfirebase.modeldata.toUiStateSiswa
import com.example.myfirebase.repositori.RepositorySiswa
import com.example.myfirebase.view.route.DestinasiDetail
import kotlinx.coroutines.launch

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    private val idSiswa: String? =
        savedStateHandle[DestinasiDetail.itemIdArg]

    init {
        println("EDIT VM ID: $idSiswa")

        if (idSiswa != null) {
            viewModelScope.launch {
                repositorySiswa.getSatuSiswa(idSiswa)?.let { siswa ->
                    uiStateSiswa = siswa.toUiStateSiswa(isEntryValid = true)
                }
            }
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa = UIStateSiswa(
            detailSiswa = detailSiswa,
            isEntryValid = validasiInput(detailSiswa)
        )
    }

    private fun validasiInput(
        uiState: DetailSiswa = uiStateSiswa.detailSiswa
    ): Boolean = with(uiState) {
        nama.isNotBlank() &&
                alamat.isNotBlank() &&
                telpon.isNotBlank()
    }

    suspend fun editSatuSiswa() {
        val id = idSiswa ?: return

        if (validasiInput(uiStateSiswa.detailSiswa)) {
            repositorySiswa.editSatuSiswa(
                id,
                uiStateSiswa.detailSiswa.toDataSiswa()
            )
        }
    }
}