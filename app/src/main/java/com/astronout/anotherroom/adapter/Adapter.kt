package com.astronout.anotherroom.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.astronout.anotherroom.AdapterOnClickListener
import com.astronout.anotherroom.R
import com.astronout.anotherroom.databinding.ItemTaskBinding
import com.astronout.anotherroom.models.Task
import com.astronout.anotherroom.viewmodels.ItemTaskViewModel
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter

class Adapter(context: Context, private var listTask: MutableList<Task>?, private val adapterOnClickListener: AdapterOnClickListener) : RecyclerSwipeAdapter<Adapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.swipeLayoutItemTask
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTaskBinding = DataBindingUtil.inflate(inflater, R.layout.item_task, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTask?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setupItem(listTask!![holder.adapterPosition], adapterOnClickListener, holder.adapterPosition)
        mItemManger.bindView(holder.itemView, holder.adapterPosition)
    }

    fun removeItem(recyclerView: RecyclerView, position: Int){
        recyclerView.post {
            this.listTask?.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, this.listTask?.size!!)
            mItemManger.closeAllItems()
        }
    }

    fun updateItem(recyclerView: RecyclerView){
        recyclerView.post {
            notifyDataSetChanged()
            mItemManger.closeAllItems()
        }
    }

    class ViewHolder(private val binding: ItemTaskBinding?) : RecyclerView.ViewHolder(binding?.root!!){
        fun setupItem(task: Task, adapterOnClickListener: AdapterOnClickListener, position: Int){
            binding?.itemTask = ItemTaskViewModel(task, adapterOnClickListener, position)
            binding?.swipeLayoutItemTask?.showMode = SwipeLayout.ShowMode.LayDown
            binding?.swipeLayoutItemTask?.addDrag(SwipeLayout.DragEdge.Right, binding.bottomWrapper)
        }
    }

}