package com.test.botalov.dictionarytest.model.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Syn {
    @SerializedName("text")
    @Expose
    var text: String? = null
    @SerializedName("pos")
    @Expose
    var pos: String? = null
}