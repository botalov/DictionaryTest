package com.test.botalov.dictionarytest.dictionary.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.test.botalov.dictionarytest.model.database.entities.WordTranslateEntity
import kotlinx.android.synthetic.main.word_item_view.view.*

class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(word: WordTranslateEntity, clickListener: (Long) -> Unit, longClickListener: (Long) -> Boolean){
        itemView.wordTextView.text = word.word
        itemView.translateTextView.text = word.translate
        itemView.setOnClickListener {clickListener(word.id!!)}
        itemView.setOnLongClickListener {longClickListener(word.id!!)}
    }
}