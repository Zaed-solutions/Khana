package org.zaed.khana.presentation.trackorder

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.repository.OrderRepository

class TrackOrderViewModel(
    private val orderRepo: OrderRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TrackOrderUiState())
    val uiState = _uiState.asStateFlow()
    fun init(orderId: String, productId: String) {
        fetchOrderDetails(orderId, productId)
    }

    private fun fetchOrderDetails(orderId: String, productId: String) {
        viewModelScope.launch {
            orderRepo.fetchOrderById(orderId).onSuccessWithData { order ->
                val item = order.cartItems.first { it.productId == productId }
                _uiState.update {
                    it.copy(
                        order = order,
                        cartItem = item,
                        isLoading = false
                    )
                }
            }.onFailure {
                Log.e("TrackOrderViewModel:fetchOrderDetails", "fetchOrderDetails: $it")
            }
        }
    }
}