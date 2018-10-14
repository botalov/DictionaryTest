package com.test.botalov.dictionarytest.dictionary.view

import com.test.botalov.dictionarytest.model.database.entities.WordTranslateEntity

interface IDictionaryView {
    fun showEmptyList()
    fun showList(data: List<WordTranslateEntity>)
    fun showError(message: String?)
}