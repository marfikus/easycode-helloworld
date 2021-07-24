package com.github.marfikus.helloworld

import android.graphics.*
import com.squareup.picasso.Transformation
import java.lang.RuntimeException
import kotlin.math.min

class PicassoCircleTransformation : Transformation {

    override fun transform(source: Bitmap?): Bitmap {
        if (source == null)
            throw RuntimeException("Source bitmap is null!")

        val size = min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source)
            source.recycle()

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true

        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)

        squaredBitmap.recycle()
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}