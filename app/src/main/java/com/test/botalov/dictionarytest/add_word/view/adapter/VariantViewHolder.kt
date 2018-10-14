package com.test.botalov.dictionarytest.add_word.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.variant_item_view.view.*

class VariantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bind(variant: String, clickListener: (String) -> Unit){
        itemView.variantTextView.text = variant
        itemView.setOnClickListener { clickListener(variant)}
    }
}