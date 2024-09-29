package org.zaed.khana.presentation.search

sealed interface SearchUiAction{
    data object OnBackPressed: SearchUiAction

}