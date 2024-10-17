package org.zaed.khana.presentation.helpcenter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.SupportRepository

class HelpCenterViewModel(
    private val supportRepo: SupportRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(HelpCenterUiState())
    val uiState = _uiState.asStateFlow()
    fun init(){
        fetchFAQs()
        fetchContactInfo()
    }

    private fun fetchFAQs() {
        viewModelScope.launch {
            supportRepo.fetchFAQs().onSuccessWithData { data ->
                val tags = listOf("All") + data.map { it.tag }.distinct()
                _uiState.update { it.copy(faq = data, faqTags = tags) }
            }.onFailure {
                Log.e("HelpCenterViewModel:fetchFAQ", "Failed to fetch FAQs $it")
            }
        }
    }

    private fun fetchContactInfo() {
        viewModelScope.launch {
            supportRepo.fetchContactInfo().onSuccessWithData { data ->
                _uiState.update { it.copy(contactInfo = data) }
            }.onFailure {
                Log.e("HelpCenterViewModel:fetchContactInfo", "Failed to fetch contact info $it")
            }
        }
    }
}