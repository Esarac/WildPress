package com.wildpress.components

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView){
    private var item: T? = null

    open fun setItem(item: T) {
        this.item = item
    }
}