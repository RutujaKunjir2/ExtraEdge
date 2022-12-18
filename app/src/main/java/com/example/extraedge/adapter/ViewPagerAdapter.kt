package com.example.extraedge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.extraedge.R
import java.util.*

class ViewPagerAdapter(val context: Context, val imageList: List<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return imageList.size
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


       val itemView: View = mLayoutInflater.inflate(R.layout.item_image, container, false)


        val imageView: ImageView = itemView.findViewById<View>(R.id.idIVImage) as ImageView


        Glide.with(itemView.context)
            .applyDefaultRequestOptions( RequestOptions().override(0,0).fitCenter().placeholder(R.drawable.edeg).error(R.drawable.edeg))
            .load(imageList.get(position))
            .placeholder(R.drawable.edeg)
            .into(imageView)

        Objects.requireNonNull(container).addView(itemView)


        return itemView
    }

    // on below line we are creating a destroy item method.
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // on below line we are removing view
        container.removeView(`object` as RelativeLayout)
    }
}