package com.example.extraedge.interfaces

import android.view.View
import com.example.extraedge.models.ProductItem

interface OnProductClickListener {
    fun onProductClick(view: View, productItem : ProductItem, position : Int)
}