package com.example.shoppinlist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinlist.R

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.shopList.observe(this) {
            Log.d("TTT",it.toString())
            if (count == 0) {
                count++
                val item = it[0]
                mainViewModel.deleteShopItem(item)
            }
        }
    }
}