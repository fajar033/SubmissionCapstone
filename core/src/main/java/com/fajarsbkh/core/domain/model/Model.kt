package com.fajarsbkh.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Movie(
    val id: Int?,
    val image: String?,
    val title: String?,
    val overview: String?,
    val movieImage: String?,
    val releaseDate: String?,
    val voteCount: Int?,
    val isFavorite: Boolean?,
): Parcelable