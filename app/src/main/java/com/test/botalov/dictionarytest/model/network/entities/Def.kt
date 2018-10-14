package com.test.botalov.dictionarytest.model.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Def {
    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("pos")
    @Expose
    var pos: String? = null
    @SerializedName("gen")
    @Expose
    var gen: String? = null
    @SerializedName("anm")
    @Expose
    var anm: String? = null
    @SerializedName("tr")
    @Expose
    var tr: List<Tr>? = null
}