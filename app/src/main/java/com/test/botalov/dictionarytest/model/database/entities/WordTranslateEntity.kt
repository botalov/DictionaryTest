package com.test.botalov.dictionarytest.model.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "wordsTable")
data class WordTranslateEntity(@PrimaryKey(autoGenerate = true) var id: Long?,
                          @ColumnInfo(name = "word") var word: String,
                          @ColumnInfo(name = "translate") var translate: String)
{
    constructor():this(null,"", "")
    constructor(word: String, translate: String):this(null ,word, translate)
}