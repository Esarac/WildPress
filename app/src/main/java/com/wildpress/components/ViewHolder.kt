package com.wildpress.components

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.R
import com.wildpress.model.Cardable

class ViewHolder<T: Cardable>(itemView: View) : RecyclerView.ViewHolder(itemView){
    var item: T? = null

    var cardImage: ImageView
    var cardTitle: TextView

    init {
        cardImage = itemView.findViewById(R.id.card_image)
        cardTitle = itemView.findViewById(R.id.card_title)
    }
}