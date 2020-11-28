/*
 * Copyright 2020 Gabriel Freitas Vasconcelos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gabrielfv.biller.addbill.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
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

fun <L, R> Either<L, R>.isLeft() = this is Either.Left<L>

fun <L, R> Either<L, R>.isRight() = this is Either.Right<R>
