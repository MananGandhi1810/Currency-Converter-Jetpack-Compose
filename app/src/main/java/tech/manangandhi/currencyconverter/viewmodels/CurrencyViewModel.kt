package tech.manangandhi.currencyconverter.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tech.manangandhi.currencyconverter.repositories.CurrencyRepository
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {
    var from: String = "USD"
    var to: String = "INR"
    var amount: Double = 1.0
    var isLoading by mutableStateOf(false)
    var result by mutableStateOf(0.0)

    fun exchange() {
        viewModelScope.launch {
            isLoading = true
            result = repository.exchange(from, to) * amount
            isLoading = false
        }
    }
}