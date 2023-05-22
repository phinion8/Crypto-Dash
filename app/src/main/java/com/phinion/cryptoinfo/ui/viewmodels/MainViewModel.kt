package com.phinion.cryptoinfo.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.repositories.MainRepository
import com.phinion.cryptoinfo.utils.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private var _textSearch = MutableStateFlow("")

    val textSearch: StateFlow<String> = _textSearch.asStateFlow()






    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    val topGainLossResponse: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    init {
        getCryptoInfoList()
        getTopGainCryptoList()

    }


    fun setSearchText(it: String){
        _textSearch.value = it
    }

    private fun getCryptoInfoList() = viewModelScope.launch {
        mainRepository.getCryptoInfoList()
            .onStart {
                response.value = ApiState.Loading
            }
            .catch {
                response.value = ApiState.Failure(it)
            }
            .collect{
                response.value = ApiState.Success(it)
            }
    }

    private fun getTopGainCryptoList() = viewModelScope.launch {

        mainRepository.getTopGainCryptoList()
            .onStart {
                topGainLossResponse.value = ApiState.Loading
            }
            .catch {
                topGainLossResponse.value = ApiState.Failure(it)
            }
            .collect{
                topGainLossResponse.value = ApiState.Success(it)
            }

    }

    suspend fun addCryptoData(cryptoCurrency: CryptoCurrency){
        mainRepository.addCryptoData(cryptoCurrency)
    }


}