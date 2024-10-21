package org.zaed.khana.presentation.privacy

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        TODO("Not yet implemented")
    }
}