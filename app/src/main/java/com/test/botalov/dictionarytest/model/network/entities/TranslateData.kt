package com.test.botalov.dictionarytest.model.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class TranslateData {
    /*@SerializedName("code")
    @Expose
    var code: Int? = null
    @SerializedName("lang")
    @Expose
    var lang: String? = null
    @SerializedName("text")
    @Expose
    var text: List<String>? = null*/
    @SerializedName("head")
    @Expose
    val head: Head? = null
    @SerializedName("def")
    @Expose
    val def: List<Def>? = null
}