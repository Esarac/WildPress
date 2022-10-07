package com.wildpress.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.model.Cardable

class CardAdapter<T: Cardable>(list: ArrayList<T>) : RecyclerView.Adapter<ViewHolder<T>>() {

    private val list: ArrayList<T> = list

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder<T> {
        val inflater = LayoutInflater.from(viewGroup.context)
        val item = inflater.inflate(R.layout.card_view, viewGroup, false)
        return ViewHolder<T>(item)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder<T>, i: Int) {
        val item = list[i]
        viewHolder.item = item
        viewHolder.cardImage.setImageResource(item.getImage())
        viewHolder.cardTitle.text = item.getTitle()
    }

    override fun getItemCount(): Int = list.size

    fun addItem(item: T){
        this.list.add(item)
        notifyItemInserted(list.size - 1)
    }
}