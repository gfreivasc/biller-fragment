package com.gabrielfv.biller.addbill.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

sealed class Either<out L, out R> : Parcelable {
    @Parcelize data class Left<L>(
        val data: @RawValue L
    ) : Either<L, Nothing>()

    @Parcelize data class Right<R>(
        val data: @RawValue R
    ) : Either<Nothing, R>()
}

inline fun <reified T> left(data: T) = Either.Left(data)

inline fun <reified T> right(data: T) = Either.Right(data)

inline fun <L, R> Either<L, R>.map(
    mapLeft: (L) -> Unit = { },
    mapRight: (R) -> Unit = { }
) = when (this) {
    is Either.Left -> mapLeft(data)
    is Either.Right -> mapRight(data)
}

fun <L, R> Either<L, R>.toLeft() = try {
    (this as Either.Left<L>).data
} catch (e: ClassCastException) {
    throw IllegalArgumentException("Either ${toString()} is not a Left.")
}

fun <L, R> Either<L, R>.toRight() = try {
    (this as Either.Right<R>).data
} catch (e: ClassCastException) {
    throw IllegalArgumentException("Either ${toString()} is not a Right.")
}
