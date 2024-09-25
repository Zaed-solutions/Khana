package org.zaed.khana.presentation.util

import android.icu.text.DecimalFormat

fun Float.toMoney(): String = "\$${DecimalFormat("#.00").format(this)}"