package com.github.marfikus.helloworld

import android.graphics.Bitmap

interface ImageCallback {

    fun success(bitmap: Bitmap)

    fun failed()
}