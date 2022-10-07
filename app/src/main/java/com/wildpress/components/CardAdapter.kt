package com.wildpress.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.model.Exercise
import com.wildpress.model.Resumeable

class CardAdapter<T: Resumeable>(list: ArrayList<T>) : RecyclerView.Adapter<CardAdapter<T>.ViewHolder<T>>() {

    private val list: ArrayList<T> = list

    inner class ViewHolder<T: Resumeable>(itemView: View) : RecyclerView.ViewHolder(itemView){
        var item: T? = null

        var cardImage: ImageView
        var cardTitle: TextView

        init {
            cardImage = itemView.findViewById(R.id.card_image)
            cardTitle = itemView.findViewById(R.id.card_title)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder<T> {
        val inflater = LayoutInflater.from(viewGroup.context)
        val row = inflater.inflate(R.layout.view_holder_exercises, viewGroup, false)
        return ViewHolder(row)
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