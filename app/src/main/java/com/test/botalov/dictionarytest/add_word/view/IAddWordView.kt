package com.test.botalov.dictionarytest.add_word.view

import com.test.botalov.dictionarytest.model.network.entities.TranslateData

interface IAddWordView {
    fun updateVariants(data: TranslateData?)
    fun showError(text: String?)
    fun showMessage(text: String?)
    fun getWord(): String
    fun getTranslate(): String
    fun fillFields(word: String?, translate: String?)
    fun done()
}