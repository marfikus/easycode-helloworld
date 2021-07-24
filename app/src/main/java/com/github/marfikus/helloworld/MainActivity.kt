package com.github.marfikus.helloworld

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private companion object {
//        const val URL = "https://zavistnik.com/wp-content/uploads/2020/03/Android-kursy-zastavka.jpg"
        const val URL = "https://www.pexels.com/photo/50594/download/?search_query=8k%20wallpaper&tracking_id=bcohr3tutr5"
//        const val URL = "https://images4.alphacoders.com/978/978193.jpg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val textInputEditText = textInputLayout.editText as TextInputEditText

        textInputEditText.addTextChangedListener(object : SimpleTextWatcher() {

            override fun afterTextChanged(s: Editable?) {
                val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
                textInputLayout.isErrorEnabled = !valid
                val error = if (valid) "" else getString(R.string.invalid_email_message)
                textInputLayout.error = error

/*                if (valid)
                    Toast.makeText(
                    this@MainActivity,
                    R.string.valid_email_message,
                    Toast.LENGTH_SHORT
                ).show()*/

                val input = s.toString()
                if (input.endsWith("@g")) {
                    val fullMail = "${input}mail.com"
                    textInputEditText.setTextCorrectly(fullMail)
                }

            }
        })

    }

    private fun TextInputEditText.setTextCorrectly(text: CharSequence) {
        setText(text)
        setSelection(text.length)
    }

    private fun ImageView.load(url: String) {
/*        Picasso.get()
            .load(URL)
//            .centerCrop()
//            .resize(720, 1280)
            .placeholder(android.R.drawable.ic_media_pause)
            .error(android.R.drawable.ic_dialog_alert)
            .transform(PicassoCircleTransformation())
            .into(this)*/

        Glide.with(this)
            .load(url)
            .thumbnail(0.1f)
//            .override(720, 1280)
            .circleCrop()
            .placeholder(android.R.drawable.ic_media_pause)
            .error(android.R.drawable.ic_dialog_alert)
            .into(this)

    }

    private fun TextView.setColor(@ColorRes colorResId: Int, theme: Resources.Theme? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextColor(resources.getColor(colorResId, theme))
        } else {
            setTextColor(resources.getColor(colorResId))
        }
    }
}