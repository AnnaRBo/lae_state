package com.example.cryptohack.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptohack.network.Asset
import com.example.cryptohack.network.CryptoCurrency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {


    fun loadData() : List<CryptoCurrency> {
        var list = emptyList<CryptoCurrency>()
        val assetService = Asset
        val scope = CoroutineScope(Job() + Dispatchers.IO)
        try {
            scope.launch {
                val res = assetService.assetService.getAllAssets().data
                list = res
                println(res)
            }
        } catch (err: Error) {
            println(err)
        }
        return list
    }
}