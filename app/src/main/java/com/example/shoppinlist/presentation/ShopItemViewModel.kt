package com.example.shoppinlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinlist.data.ShopRepositoryImp
import com.example.shoppinlist.domain.AddShopItemUseCase
import com.example.shoppinlist.domain.EditShopItemUseCase
import com.example.shoppinlist.domain.GetShopItemUseCase
import com.example.shoppinlist.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopRepositoryImp
    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName: MutableLiveData<Boolean> = MutableLiveData()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount: MutableLiveData<Boolean> = MutableLiveData()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem: MutableLiveData<ShopItem> = MutableLiveData()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldClosedScreen = MutableLiveData<Unit>()
    val shouldClosedScreen: LiveData<Unit>
        get() = _shouldClosedScreen


    fun getShopItem(shopItemId: Int) {
        val item = getShopItemUseCase.getShopItem(shopItemId)
        _shopItem.value = item
    }


    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            val shopItem = ShopItem(name, count, true)
            addShopItemUseCase.addShopItem(shopItem)
            finishWork()
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)

        if (validateInput(name, count)) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                editShopItemUseCase.editShopItem(item)
                finishWork()
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true

        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }

        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    private fun finishWork() {
        _shouldClosedScreen.value = Unit
    }
}