package com.test.botalov.dictionarytest.dictionary.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.test.botalov.dictionarytest.R
import com.test.botalov.dictionarytest.application.App
import com.test.botalov.dictionarytest.model.database.entities.WordTranslateEntity

class WordsAdapter(var data: List<WordTranslateEntity>,
                   private val clickListener: (Long) -> Unit,
                   private val longClickListener: (Long) -> Boolean) : RecyclerView.Adapter<WordViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): WordViewHolder {
        return WordViewHolder(LayoutInflater.from(App.context).inflate(R.layout.word_item_view, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(data[position], clickListener, longClickListener)
    }
}