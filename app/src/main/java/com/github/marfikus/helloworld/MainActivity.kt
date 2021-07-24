package com.github.marfikus.helloworld

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
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

private const val TAG = "TextWatcherTag"

class MainActivity : AppCompatActivity() {

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText

    private val textWatcher: TextWatcher = object : SimpleTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            Log.d(TAG, "afterTextChanged $s")

/*            val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
            textInputLayout.isErrorEnabled = !valid
            val error = if (valid) "" else getString(R.string.invalid_email_message)
            textInputLayout.error = error*/

/*                if (valid)
                    Toast.makeText(
                    this@MainActivity,
                    R.string.valid_email_message,
                    Toast.LENGTH_SHORT
                ).show()*/

            val input = s.toString()
            if (input.endsWith("@g")) {
                Log.d(TAG, "programmatically set text")
//                val fullMail = "${input}mail.com"
//                textInputEditText.setTextCorrectly(fullMail)
                setText("${input}mail.com")
            }

        }
    }

    private fun setText(text: String) {
        textInputEditText.removeTextChangedListener(textWatcher)
        textInputEditText.setTextCorrectly(text)
        textInputEditText.addTextChangedListener(textWatcher)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        textInputEditText = textInputLayout.editText as TextInputEditText

//        textInputEditText.addTextChangedListener(textWatcher)
        textInputEditText.listenChanges { textInputLayout.isErrorEnabled = false }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            if (EMAIL_ADDRESS.matcher(textInputEditText.text.toString()).matches()) {
                hideKeyboard(textInputEditText)
                loginButton.isEnabled = false
                Snackbar.make(loginButton, "Go to postLogin", Snackbar.LENGTH_SHORT).show()
            } else {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = getString(R.string.invalid_email_message)
            }
        }

    }

    private fun AppCompatActivity.hideKeyboard(view: View) {
        val imm = this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun TextInputEditText.listenChanges(block: (text: String) -> Unit) {
        addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                block.invoke(s.toString())
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