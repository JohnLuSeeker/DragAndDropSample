package com.nogotech.draganddropsample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.DOWN
import androidx.recyclerview.widget.ItemTouchHelper.UP
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nogotech.draganddropsample.databinding.ItemStringBinding

/**
 * A [ListAdapter] for [String]s.
 */
class RecyclerViewAdapter(private val listener: Listener<String>) :
    ListAdapter<String, RecyclerViewAdapter.StringViewHolder>(
        object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.contentEquals(newItem)
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.contentEquals(newItem)
            }
        }
    ) {

    private val itemTouchHelper by lazy {
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                UP or DOWN, 0
            ) {
                /**
                 * onMove lets you check if an item has been moved
                 * from its position either up or down
                 */
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    //the position from where item has been moved
                    val from = viewHolder.adapterPosition

                    //the position where the item is moved
                    val to = target.adapterPosition

                    //telling the adapter to move the item
                    notifyItemMoved(from, to)

                    return true
                }

                /**
                 * onSwiped tells you when an item is swiped
                 * left or right from its position ( swipe to delete)
                 */
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    TODO("Not yet implemented")
                }

                var from = 0

                /**
                 * When an item changes its location that is currently selected
                 * this function is called
                 */
                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int
                ) {
                    super.onSelectedChanged(viewHolder, actionState)
                    viewHolder?.let {
                        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                            it.itemView.alpha = 0.3f
                            from = viewHolder.adapterPosition
                            listener.onSelectedChanged(viewHolder.adapterPosition, true)
                        } else if(actionState == ItemTouchHelper.ACTION_STATE_IDLE){
                            listener.onSelectedChanged(viewHolder.adapterPosition, false)
                        }
                    }
                }

                /**
                 * When we stop dragging , swiping or moving an item this function is called
                 */
                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)
                    viewHolder.itemView.alpha = 1.0f
                }
            })
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        return StringViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(getItem(position), itemTouchHelper, listener)
    }

    /**
     * Basic [RecyclerView.ViewHolder] for our gallery.
     */
    class StringViewHolder private constructor(private val binding: ItemStringBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            text: String,
            itemTouchHelper: ItemTouchHelper,
            listener: Listener<String>
        ) {
            binding.root.setOnDragListener { _, _ ->
                itemTouchHelper.startDrag(this)
                true
            }
            binding.text = text
            binding.listener = listener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): StringViewHolder {
                val binding =
                    ItemStringBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                return StringViewHolder(binding)
            }
        }
    }
}

class Listener<T>(
    val clickListener: (obj: T) -> Unit,
    val selectedChangedListener: (Int, Boolean) -> Unit
) {
    fun onClicked(obj: T) = clickListener(obj)
    fun onSelectedChanged(position: Int, isSelected: Boolean) =
        selectedChangedListener(position, isSelected)
}