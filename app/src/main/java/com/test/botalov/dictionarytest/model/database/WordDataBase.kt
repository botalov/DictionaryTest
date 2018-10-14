package com.test.botalov.dictionarytest.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.test.botalov.dictionarytest.model.database.entities.WordTranslateEntity

@Database(entities = arrayOf(WordTranslateEntity::class), version = 1)
abstract class WordDataBase : RoomDatabase() {
    abstract fun wordDAO(): WordTranslateDAO

    companion object {
        private var INSTANCE: WordDataBase? = null

        fun getInstance(context: Context): WordDataBase? {
            if (INSTANCE == null) {
                synchronized(WordDataBase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            WordDataBase::class.java, "words.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}