package com.ampersand.core

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class UiText : Parcelable {
    class DynamicString(val value: String) : UiText()
    data class StringResource(
        @StringRes val id: Int,
        val args: Array<Int> = arrayOf()
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}
