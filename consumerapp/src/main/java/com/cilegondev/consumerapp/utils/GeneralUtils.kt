package com.cilegondev.consumerapp.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.pow

class GeneralUtils {
    companion object {
        fun formatValue(mValue: Double): String? {
            var value = mValue
            val power: Int
            val suffix = " kmbt"
            var formattedNumber: String
            val formatter: NumberFormat = DecimalFormat("#,###.#")
            power = StrictMath.log10(value).toInt()
            value /= 10.0.pow(power / 3 * 3.toDouble())
            formattedNumber = formatter.format(value)
            formattedNumber += suffix[power / 3]
            return if (formattedNumber.length > 4) formattedNumber.replace(
                "\\.[0-9]+".toRegex(),
                ""
            ) else formattedNumber
        }
    }
}