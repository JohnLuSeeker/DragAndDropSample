package com.nogotech.draganddropsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nogotech.draganddropsample.databinding.ItemStringBinding

/**
 * A [ListAdapter] for [String]s.
 */
class RecyclerViewAdapter(private val clickListener: Listener) :
    ListAdapter<String, RecyclerViewAdapter.StringViewHolder>(
        object : DiffUtil.ItemCallback<String>()
        {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return  oldItem.contentEquals(newItem)
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return  oldItem.contentEquals(newItem)
            }
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        return StringViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    /**
     * Basic [RecyclerView.ViewHolder] for our gallery.
     */
    class StringViewHolder private constructor(private val binding: ItemStringBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            text: String,
            clickListener: Listener
        ) {
            binding.text = text
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): StringViewHolder {
                val binding = ItemStringBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return StringViewHolder(binding)
            }
        }
    }
}

class Listener(val clickListener: (text: String) -> Unit) {
    fun onClick(text: String) = clickListener(text)
}