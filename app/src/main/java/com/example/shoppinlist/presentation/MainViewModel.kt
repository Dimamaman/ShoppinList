package com.example.shoppinlist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinlist.data.ShopRepositoryImp
import com.example.shoppinlist.domain.*

class MainViewModel: ViewModel() {
    private val repository = ShopRepositoryImp

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList: LiveData<List<ShopItem>> = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun editShopItem(shopItem: ShopItem) {
        val newShopItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newShopItem)
    }
}