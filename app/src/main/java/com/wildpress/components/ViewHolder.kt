package com.wildpress.components

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.model.Cardable

class ViewHolder<T: Cardable>(itemView: View) : IViewHolder<T>(itemView){
    object Constants {
        const val RESOURCE_ID: Int = R.layout.card_view
    }

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