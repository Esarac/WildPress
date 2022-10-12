package com.wildpress.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.model.Cardable

class CardRecyclerView<T: Cardable>(items: ArrayList<T>) : RecyclerView.Adapter<CardRecyclerView<T>.CardViewHolder<T>>() {

    private val items: ArrayList<T> = items

    open inner class CardViewHolder<T: Cardable>(itemView: View) : ViewHolder<T>(itemView){
        private var cardImage: ImageView
        private var cardTitle: TextView

        init {
            cardImage = itemView.findViewById(R.id.card_image)
            cardTitle = itemView.findViewById(R.id.card_title)
        }

        override fun setItem(item: T) {
            super.setItem(item)
            this.cardImage.setImageResource(item.getImage())
            this.cardTitle.text = item.getTitle()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): CardViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.card_view, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: CardViewHolder<T>, i: Int) {
        viewHolder.setItem(items[i])
    }

    override fun getItemCount(): Int = items.size

    fun addItem(item: T){
        this.items.add(item)
        notifyItemInserted(items.size - 1)
    }
}