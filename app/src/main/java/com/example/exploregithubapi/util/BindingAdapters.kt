package com.example.exploregithubapi.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.exploregithubapi.R
import de.hdodenhof.circleimageview.CircleImageView

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("app:setImage")
    fun setImage(iv: CircleImageView, url: String?){
        url?.let {
            Glide.with(iv.context)
                .load(it)
                .placeholder(R.drawable.ic_person)
                .into(iv)
        }
    }

//    @BindingAdapter("app:setName")
//    fun setName(tv: TextView, name: String?){
//        name?.let {
//
//        }
//    }

}