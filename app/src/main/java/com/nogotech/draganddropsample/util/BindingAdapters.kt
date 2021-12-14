package com.nogotech.draganddropsample.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nogotech.draganddropsample.RecyclerViewAdapter

@BindingAdapter("listData")
fun RecyclerView.bind(list: List<String>?){
    list?.let{
        val adapter = this.adapter as RecyclerViewAdapter
        adapter.submitList(list)
    }
}