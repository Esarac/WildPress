package com.wildpress.components

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wildpress.model.Cardable

abstract class ViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView){
    private var item: T? = null

    open fun setItem(item: T) {
        this.item = item;
    }

    object Factory{
        fun <T: Cardable>FactoryViewHolder(itemView: View, resource: Int): ViewHolder<T>{
            return when(resource){
                CardViewHolder.Constants.RESOURCE_ID -> CardViewHolder(itemView)
                else -> CardViewHolder(itemView)
            }
        }
    }
}