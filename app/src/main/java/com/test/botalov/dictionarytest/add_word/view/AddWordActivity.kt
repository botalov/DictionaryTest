package com.test.botalov.dictionarytest.add_word.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.test.botalov.dictionarytest.R
import com.test.botalov.dictionarytest.add_word.presenter.AddWordPresenter
import com.test.botalov.dictionarytest.add_word.view.adapter.VariantsAdapter
import com.test.botalov.dictionarytest.model.network.entities.TranslateData
import kotlinx.android.synthetic.main.activity_add_word.*
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem


class AddWordActivity : AppCompatActivity(), IAddWordView {
    companion object {
        const val IS_CHANGE_ARG = "IS_CHANGE_ARG"
        const val CHANGE_WORD_ID_ARG = "CHANGE_WORD_ID_ARG"
    }

    var presenter: AddWordPresenter = AddWordPresenter()
    private val listener : (String) -> Unit = {translateEditText.text = Editable.Factory.getInstance().newEditable(it)}
    private val adapter = VariantsAdapter(null, listener)

    private var isChange = false
    private var wordId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        setSupportActionBar(addWordToolbar)

        isChange = when (intent) {
            null -> false
            else -> intent.getBooleanExtra(IS_CHANGE_ARG, false)
        }

        presenter.attachView(this)

        variantsRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        variantsRecyclerView.adapter = adapter

        wordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                presenter.onTextChanged(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        if(isChange) {
            button.text = getString(R.string.save)
            wordId = intent.getLongExtra(CHANGE_WORD_ID_ARG, -1)
            addWordToolbar.title = getString(R.string.change_word)
            presenter.fillFields(wordId)
            button.setOnClickListener { presenter.changeTranslate(wordId) }
        }
        else{
            button.text = getString(R.string.add)
            addWordToolbar.title = getString(R.string.add_word)
            button.setOnClickListener { presenter.addTranslate() }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(isChange) {
            menuInflater.inflate(R.menu.add_word_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete_word -> {
            if(wordId >= 0) {
                presenter.onDeleteWord(wordId)
            }
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun updateVariants(data: TranslateData?) {
        adapter.data = data
        adapter.notifyDataSetChanged()
    }

    override fun fillFields(word: String?, translate: String?) {
        wordEditText.text = Editable.Factory.getInstance().newEditable(word)
        translateEditText.text = Editable.Factory.getInstance().newEditable(translate)
    }

    override fun getWord(): String = wordEditText.text.toString()

    override fun getTranslate(): String = translateEditText.text.toString()

    override fun showError(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
    override fun showMessage(text: String?) {
        showError(text)
    }

    override fun done() {
        finish()
    }
}
