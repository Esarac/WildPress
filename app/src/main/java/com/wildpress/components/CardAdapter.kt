package com.wildpress.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.model.Cardable

class CardAdapter<T: Cardable>(list: ArrayList<T>, @LayoutRes resource: Int) : RecyclerView.Adapter<IViewHolder<T>>() {

    private val list: ArrayList<T> = list
    private val resource: Int = resource

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): IViewHolder<T> {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(resource, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: IViewHolder<T>, i: Int) {
        viewHolder.setItem(list[i])
    }

    override fun getItemCount(): Int = list.size

    fun addItem(item: T){
        this.list.add(item)
        notifyItemInserted(list.size - 1)
    }
}