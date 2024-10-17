package org.zaed.khana.presentation.leavereview

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.zaed.khana.data.model.ProductReview
import org.zaed.khana.data.repository.AuthenticationRepository
import org.zaed.khana.data.repository.CartRepository
import org.zaed.khana.data.repository.ProductRepository

class LeaveReviewViewModel(
    private val authRepo: AuthenticationRepository,
    private val cartRepo: CartRepository,
    private val productRepo: ProductRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LeaveReviewUiState())
    val uiState = _uiState.asStateFlow()
    fun init(orderId: String, itemId: String) {
        fetchCurrentUser()
        fetchCartItem(orderId, itemId)
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            authRepo.getSignedInUser().onSuccessWithData { user ->
                _uiState.update { it.copy(currentUser = user) }
            }.onFailure {
                Log.e("LeaveReviewViewModel:fetchCurrentUser", "Failed to fetch current user$it")
            }
        }
    }

    private fun fetchCartItem(orderId: String, itemId: String) {
        viewModelScope.launch {
            cartRepo.fetchOrderedCartItem(orderId, itemId).onSuccessWithData { cartItem ->
                _uiState.update { it.copy(item = cartItem) }
            }.onFailure {
                Log.e("LeaveReviewViewModel:fetchCartItem", "Failed to fetch cart item $it")
            }
        }
    }

    fun handleUiAction(action: LeaveReviewUiAction) {
        when (action) {
            is LeaveReviewUiAction.OnRatingChanged -> updateRating(action.rating)
            is LeaveReviewUiAction.OnReviewChanged -> updateReview(action.review)
            LeaveReviewUiAction.OnReorderClicked -> reorderItem()
            LeaveReviewUiAction.OnSubmitClicked -> submitReview()
            else -> Unit
        }
    }

    private fun submitReview() {
        viewModelScope.launch {
            with(_uiState.value) {
                val review = ProductReview(
                    userId = currentUser.id,
                    productId = item.productId,
                    rating = rating,
                    review = review
                )
                productRepo.addProductReview(review).onSuccess {
                    _uiState.update { it.copy(isReviewSubmitted = true) }
                }.onFailure {
                    Log.e("LeaveReviewViewModel:submitReview", "Failed to submit review $it")
                }
            }
        }
    }

    private fun reorderItem() {
        viewModelScope.launch {
            with(_uiState.value) {
                cartRepo.addItemToCart(
                    currentUser.id,
                    item.productId,
                    item.productColor,
                    item.productSize
                ).onSuccess {
                    Log.d("LeaveReviewViewModel:reorderItem", "Item added to cart")
                }.onFailure {
                    Log.e("LeaveReviewViewModel:reorderItem", "Failed to add item to cart $it")
                }

            }
        }
    }

    private fun updateReview(review: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(review = review) }
        }
    }

    private fun updateRating(rating: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(rating = rating) }
        }
    }

}