package com.disney.data.model


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class GuestItem(
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "id")
    val id: String? = null
) : Parcelable