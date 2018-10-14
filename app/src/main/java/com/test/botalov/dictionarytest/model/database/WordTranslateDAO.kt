package com.test.botalov.dictionarytest.model.database

import android.arch.persistence.room.*
import com.test.botalov.dictionarytest.model.database.entities.WordTranslateEntity
import io.reactivex.Single

@Dao
interface WordTranslateDAO {
    @Insert
    fun insertWord(wordTranslate: WordTranslateEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceWord(wordTranslate: WordTranslateEntity)
    @Query("SELECT * FROM wordsTable ORDER BY word ASC")
    fun getAllWords() : Single<List<WordTranslateEntity>>
    @Query("SELECT * FROM wordsTable WHERE id=:id")
    fun getWordById(id: Long) : Single<WordTranslateEntity>
    @Delete
    fun deleteWord(word: WordTranslateEntity)
}