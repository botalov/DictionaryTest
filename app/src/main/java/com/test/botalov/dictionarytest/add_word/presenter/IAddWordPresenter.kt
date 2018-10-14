package com.test.botalov.dictionarytest.add_word.presenter

import com.test.botalov.dictionarytest.add_word.view.IAddWordView
import com.test.botalov.dictionarytest.model.database.entities.WordTranslateEntity

interface IAddWordPresenter {
    fun attachView(view: IAddWordView)
    fun detachView()
    fun addTranslate()
    fun changeTranslate(wordId: Long)
    fun onChangeWord(wordId: Long)
    fun onDeleteWord(wordId: Long)
    fun fillFields(wordId: Long)
}