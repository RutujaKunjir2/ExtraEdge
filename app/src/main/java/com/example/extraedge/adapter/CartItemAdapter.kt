package com.example.extraedge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.extraedge.R
import com.example.extraedge.models.ProductItem
import com.example.extraedge.ui.activities.MainActivity
import com.example.extraedge.ui.viewholders.CartItemViewHolder

class CartItemAdapter(private val data: ArrayList<ProductItem>, private val listener: MainActivity,
                      private val textColor: Int, private val textCall: Int) :
    RecyclerView.Adapter<CartItemViewHolder>()
{
    lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_cart, parent, false)
        context = parent.context
        return CartItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        // Initialize data
        holder.setData( data, position, context)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
