package com.test.botalov.dictionarytest.add_word.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.test.botalov.dictionarytest.R
import com.test.botalov.dictionarytest.application.App.Companion.context
import com.test.botalov.dictionarytest.model.network.entities.TranslateData

class VariantsAdapter(var data: TranslateData?, private val clickListener: (String) -> Unit): RecyclerView.Adapter<VariantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): VariantViewHolder {
        return VariantViewHolder(LayoutInflater.from(context).inflate(R.layout.variant_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return try {
            data!!.def!![0].tr!!.size
        } catch (ex: Exception) {
            0
        }
    }

    override fun onBindViewHolder(holder: VariantViewHolder, position: Int) {
        holder.bind(data!!.def!![0].tr!![position].text!!, clickListener)
    }
}