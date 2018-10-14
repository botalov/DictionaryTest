package com.test.botalov.dictionarytest.model.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Tr {
    @SerializedName("text")
    @Expose
    val text: String? = null
    @SerializedName("pos")
    @Expose
    val pos: String? = null
    @SerializedName("syn")
    @Expose
    val syn: List<Syn>? = null
    @SerializedName("mean")
    @Expose
    val mean: List<Mean>? = null
    @SerializedName("ex")
    @Expose
    val ex: List<Ex>? = null
}