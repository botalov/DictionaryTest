package com.test.botalov.dictionarytest.dictionary.presenter

import com.test.botalov.dictionarytest.dictionary.view.IDictionaryView

interface IDictionaryPresenter {
    fun attachView(view: IDictionaryView)
    fun detachView()
    fun onLoad()
    fun onChangeWord(wordId: Long)
    fun onDeleteWord(wordId: Long)
}