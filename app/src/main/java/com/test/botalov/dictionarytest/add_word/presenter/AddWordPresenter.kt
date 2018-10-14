package com.test.botalov.dictionarytest.add_word.presenter

import com.test.botalov.dictionarytest.R
import com.test.botalov.dictionarytest.add_word.view.IAddWordView
import com.test.botalov.dictionarytest.application.App
import com.test.botalov.dictionarytest.application.App.Companion.context
import com.test.botalov.dictionarytest.model.database.WordDataBase
import com.test.botalov.dictionarytest.model.database.entities.WordTranslateEntity
import com.test.botalov.dictionarytest.model.network.DataNetworkClient
import com.test.botalov.dictionarytest.model.network.IDataNetwork
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class AddWordPresenter : IAddWordPresenter {
    private var changedWord: WordTranslateEntity? = null

    private val databaseManager = WordDataBase.getInstance(context!!)
    var view: IAddWordView? = null

    private val key = App.context!!.getString(R.string.api_key)
    private val lang = App.context!!.getString(R.string.lang)

    private var disposable: Disposable? = null
    private var disposableTranslate: Disposable? = null
    private val subject = PublishSubject.create<String>()

    override fun attachView(view: IAddWordView) {
        this.view = view
        disposableTranslate = subject.debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .switchMap { text -> getObservable(text) }
                .subscribe({ data -> view.updateVariants(data) },
                        { t: Throwable? -> view.showError(t.toString()) })
    }

    override fun detachView() {
        this.view = null
        disposable?.dispose()
    }


    fun onTextChanged(text: String) {
        if (text.isEmpty()) {
            subject.onNext(".")
        } else {
            subject.onNext(text)
        }
    }

    private fun getObservable(text: String) = DataNetworkClient.instance.retrofit!!.create(IDataNetwork::class.java)
            .getTranslate(key, text, lang)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun addTranslate() {
        val word = view?.getWord()
        val translate = view?.getTranslate()
        if (!word.isNullOrEmpty() && !translate.isNullOrEmpty()) {
            disposable = Observable.fromCallable { databaseManager?.wordDAO()?.insertWord(WordTranslateEntity(word!!, translate!!)) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe {
                        view?.done()
                    }
        } else {
            view?.showError(context?.getString(R.string.error_add_word))
        }
    }

    override fun fillFields(wordId: Long) {
        databaseManager?.wordDAO()?.getWordById(wordId)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.toObservable()
                ?.doOnNext { it ->
                    changedWord = it
                    view?.fillFields(changedWord?.word, changedWord?.translate)
                }
                ?.doOnError {
                    view?.showError(context?.getString(R.string.error_find_word_in_db))
                }
                ?.subscribe()
    }

    override fun onDeleteWord(wordId: Long) {
        databaseManager?.wordDAO()?.getWordById(wordId)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.toObservable()
                ?.doOnNext { it ->
                    Observable.fromCallable { databaseManager.wordDAO().deleteWord(it) }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                view?.showMessage(context?.getString(R.string.word_was_deleted))
                                view?.done()
                            }
                }
                ?.doOnError {
                    view?.showError(context?.getString(R.string.error_find_word_in_db))
                }
                ?.subscribe()
    }

    override fun onChangeWord(wordId: Long) {
        if (changedWord != null && changedWord?.id == wordId) {

        }
    }

    override fun changeTranslate(wordId: Long) {
        val word = view?.getWord()
        val translateWord = view?.getTranslate()
        if (!word.isNullOrEmpty() && !translateWord.isNullOrEmpty()) {
            databaseManager?.wordDAO()?.getWordById(wordId)
                    ?.subscribeOn(Schedulers.io())
                    ?.observeOn(AndroidSchedulers.mainThread())
                    ?.toObservable()
                    ?.doOnNext { it ->
                        it.word = word!!
                        it.translate = translateWord!!
                        disposable = Observable.fromCallable { databaseManager.wordDAO().replaceWord(it) }
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe {
                                    view?.done()
                                }
                    }
                    ?.doOnError {
                        view?.showError(context?.getString(R.string.error_find_word_in_db))
                    }
                    ?.subscribe()

        } else {
            view?.showError(context?.getString(R.string.error_fields_is_empty))
        }
    }
}
