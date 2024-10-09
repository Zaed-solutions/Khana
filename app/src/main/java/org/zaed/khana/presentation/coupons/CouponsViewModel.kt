package org.zaed.khana.presentation.coupons

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.CouponRepository

class CouponsViewModel(
    private val couponRepo: CouponRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(CouponsUiState())
    val uiState = _uiState.asStateFlow()
    init {
        fetchCoupons()
    }
    private fun fetchCoupons() {
        viewModelScope.launch {
            couponRepo.fetchCoupons().collect { result ->
                result.onSuccessWithData { coupons ->
                    _uiState.update { it.copy(coupons = coupons) }
                }.onFailure { error ->
                    Log.e("CouponsViewModel:fetchCoupons", error.userMessage)
                }
            }
        }
    }
}