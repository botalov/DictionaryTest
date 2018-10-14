package com.test.botalov.dictionarytest.dictionary.presenter

import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.test.botalov.dictionarytest.R
import com.test.botalov.dictionarytest.add_word.view.AddWordActivity
import com.test.botalov.dictionarytest.application.App
import com.test.botalov.dictionarytest.application.App.Companion.context
import com.test.botalov.dictionarytest.dictionary.view.IDictionaryView
import com.test.botalov.dictionarytest.model.database.WordDataBase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class DictionaryPresenter : IDictionaryPresenter {
    private val databaseManager = WordDataBase.getInstance(App.context!!)
    private var disposableDelete: Disposable? = null
    var view: IDictionaryView? = null

    override fun attachView(view: IDictionaryView) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun onLoad() {
        databaseManager?.wordDAO()?.getAllWords()
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.toObservable()
                ?.doOnNext {
                    if (it == null || it.isEmpty()) {
                        view?.showEmptyList()
                    } else {
                        view?.showList(it)
                    }
                }
                ?.doOnError {
                    view?.showError(context?.getString(R.string.error_load_list_words))
                }
                ?.subscribe()
    }

    override fun onChangeWord(wordId: Long) {
        val intent = Intent(context, AddWordActivity::class.java)
        intent.putExtra(AddWordActivity.IS_CHANGE_ARG, true)
        intent.putExtra(AddWordActivity.CHANGE_WORD_ID_ARG, wordId)
        context?.startActivity(intent)
    }

    override fun onDeleteWord(wordId: Long) {
        val builder = AlertDialog.Builder(view as AppCompatActivity)
        builder.setTitle(context?.getString(R.string.delete_question))

        builder.setPositiveButton(context?.getString(R.string.yes)) { _, _ ->
            databaseManager?.wordDAO()?.getWordById(wordId)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.toObservable()
                    ?.doOnNext { it ->
                        disposableDelete = Observable.fromCallable { databaseManager.wordDAO().deleteWord(it) }
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe {
                                    onLoad()
                                }
                    }
                    ?.doOnError {
                        view?.showError(context?.getString(R.string.error_find_word_in_db))
                    }
                    ?.subscribe()
            onLoad()
        }
        builder.setNegativeButton(context?.getString(R.string.no)) { _, _ ->

        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}