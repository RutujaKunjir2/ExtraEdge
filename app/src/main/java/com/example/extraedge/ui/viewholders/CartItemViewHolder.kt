package com.example.extraedge.ui.viewholders

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.extraedge.R
import com.example.extraedge.models.ProductItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.extraedge.ui.activities.RocketDetails

class CartItemViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    val body: CardView
    val iv_image: ImageView
    val tv_name: TextView
    val tv_country: TextView
    val tv_ecount: TextView

    init {
        iv_image = itemView.findViewById<View>(R.id.iv_image) as ImageView
        tv_name = itemView.findViewById<View>(R.id.tv_name) as TextView
        tv_country = itemView.findViewById<View>(R.id.tv_country) as TextView
        tv_ecount =itemView.findViewById<View>(R.id.tv_ecount) as TextView
        body = itemView.findViewById<View>(R.id.cv_body) as CardView
    }

    fun setData(
        data: ArrayList<ProductItem>,
        position: Int,
        context: Context
    ) {

        var productItem = data[position]
        tv_name.text = productItem.name
        tv_country.text = productItem.country
        tv_ecount.text = productItem.engineCount+" engines"

        Glide.with(itemView.context)
                .applyDefaultRequestOptions( RequestOptions().override(0,0).fitCenter().placeholder(R.drawable.edeg).error(R.drawable.edeg))
            .load(productItem.image)
            .placeholder(R.drawable.edeg)
            .into(iv_image)
        body.setOnClickListener({
            var intent = Intent(it.context, RocketDetails::class.java)
            intent.putExtra("proId", productItem.id)
            intent.putExtra("proName", productItem.name)
            it.context.startActivity(intent)
        })
    }

}