package com.test.botalov.dictionarytest.dictionary.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.test.botalov.dictionarytest.R
import com.test.botalov.dictionarytest.add_word.view.AddWordActivity
import com.test.botalov.dictionarytest.dictionary.presenter.DictionaryPresenter
import com.test.botalov.dictionarytest.dictionary.view.adapter.WordsAdapter
import com.test.botalov.dictionarytest.model.database.entities.WordTranslateEntity
import kotlinx.android.synthetic.main.activity_dictionary.*

class DictionaryActivity : AppCompatActivity(), IDictionaryView {
    private val presenter = DictionaryPresenter()

    private val clickListener : (Long) -> Unit = {
        presenter.onChangeWord(it)
    }
    private val longClickListener : (Long) -> Boolean = {
        presenter.onDeleteWord(it)
        true
    }
    private val adapter = WordsAdapter(ArrayList(), clickListener, longClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        setSupportActionBar(dictionaryToolbar)

        dictionaryRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dictionaryRecyclerView.adapter = adapter

        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.onLoad()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dictionary_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_word -> {
            val intent = Intent(this, AddWordActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun showEmptyList() {
        emptyMessageTextView.visibility = View.VISIBLE
        adapter.data = ArrayList()
        adapter.notifyDataSetChanged()
    }

    override fun showList(data: List<WordTranslateEntity>) {
        emptyMessageTextView.visibility = View.GONE
        adapter.data = data
        adapter.notifyDataSetChanged()
    }

    override fun showError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
