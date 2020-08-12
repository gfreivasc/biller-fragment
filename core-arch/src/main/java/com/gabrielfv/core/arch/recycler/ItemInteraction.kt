package com.gabrielfv.core.arch.recycler

sealed class ItemInteraction<T> {
    class Click<T>(val onClick: (T) -> Unit) : ItemInteraction<T>() {
        operator fun invoke(item: T) {
            onClick(item)
        }
    }
}