package org.zaed.khana.presentation.privacy

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.SupportRepository

class PrivacyPolicyViewModel(
    private val supportRepo: SupportRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(PrivacyPolicyUiState())
    val uiState = _uiState.asStateFlow()
    init {
        fetchLegalInfo()
    }

    private fun fetchLegalInfo() {
        viewModelScope.launch {
            supportRepo.fetchLegalInfo().onSuccessWithData { legalInfo ->
                _uiState.value = uiState.value.copy(legalInfo = legalInfo)
            }.onFailure {
                Log.e("PrivacyPolicyViewModel:fetchLegalInfo", "Failed to fetch legal info $it")
            }
        }
    }
}