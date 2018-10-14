package com.test.botalov.dictionarytest.model.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Ex {
    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("tr")
    @Expose
    var tr: List<Tr_>? = null
}