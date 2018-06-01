package com.jeebley.task.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

open class SimpleViewHolder<T>(itemView: View, type: Int) : RecyclerView.ViewHolder(itemView) {
    var view = itemView
    var viewType = type
    var item: T? = null
    var pos = 0
    var adapter: RxAdapter<T, *>? = null
}