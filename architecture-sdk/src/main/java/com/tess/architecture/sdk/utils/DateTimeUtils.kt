package com.tess.architecture.sdk.utils

import java.util.Calendar

val timestamp: Long
    get() = Calendar.getInstance().time.time
