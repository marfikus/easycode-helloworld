package com.github.marfikus.helloworld

import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import com.bumptech.glide.Glide
import com.github.marfikus.helloworld.password_checker.PasswordCheckerChain
import com.github.marfikus.helloworld.password_checker.PasswordCheckerContainsOneDigit
import com.github.marfikus.helloworld.password_checker.PasswordCheckerEmpty
import com.github.marfikus.helloworld.password_checker.PasswordCheckerMinLength
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException

private const val TEXT_WATCHER_TAG = "TextWatcherTag"
private const val ACTIVITY_TAG = "ActivityTag"

class MainActivity : AppCompatActivity() {

    private lateinit var loginInputLayout: TextInputLayout
    private lateinit var loginInputEditText: TextInputEditText

    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var passwordInputEditText: TextInputEditText

    private val textWatcher: TextWatcher = object : SimpleTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            Log.d(TEXT_WATCHER_TAG, "afterTextChanged $s")

/*            val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()
            loginInputLayout.isErrorEnabled = !valid
            val error = if (valid) "" else getString(R.string.invalid_email_message)
            loginInputLayout.error = error*/

/*                if (valid)
                    Toast.makeText(
                    this@MainActivity,
                    R.string.valid_email_message,
                    Toast.LENGTH_SHORT
                ).show()*/

            val input = s.toString()
            if (input.endsWith("@g")) {
                Log.d(TEXT_WATCHER_TAG, "programmatically set text")
//                val fullMail = "${input}mail.com"
//                loginInputEditText.setTextCorrectly(fullMail)
                setText("${input}mail.com")
            }

        }
    }

    private fun setText(text: String) {
        loginInputEditText.removeTextChangedListener(textWatcher)
        loginInputEditText.setTextCorrectly(text)
        loginInputEditText.addTextChangedListener(textWatcher)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginInputLayout = findViewById(R.id.loginInputLayout)
        loginInputEditText = loginInputLayout.editText as TextInputEditText

        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        passwordInputEditText = passwordInputLayout.editText as TextInputEditText

//        loginInputEditText.addTextChangedListener(textWatcher)
        loginInputEditText.listenChanges { loginInputLayout.isErrorEnabled = false }
        passwordInputEditText.listenChanges { passwordInputLayout.isErrorEnabled = false }

        val contentLayout = findViewById<View>(R.id.contentLayout)
        val progressBar = findViewById<View>(R.id.progressBar)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {

            if (!loginIsValid(loginInputEditText.text.toString())) {
                loginInputLayout.isErrorEnabled = true
                loginInputLayout.error = getString(R.string.invalid_email_message)
                return@setOnClickListener
            }

            val passwordCheckResult = passwordIsValid(passwordInputEditText.text.toString())
            val isValid = passwordCheckResult["isValid"].toBoolean()
            if (!isValid) {
                passwordInputLayout.isErrorEnabled = true
//                passwordInputLayout.error = getString(R.string.invalid_password_message)
                // TODO: 25.07.21 правильнее бы брать строку из ресурсов на основании кода ошибки
                passwordInputLayout.error = passwordCheckResult["error"]
                return@setOnClickListener
            }

            hideKeyboard(loginInputEditText)
//            loginButton.isEnabled = false
            contentLayout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            Snackbar.make(loginButton, "Go to postLogin", Snackbar.LENGTH_SHORT).show()
        }

        loginButton.isEnabled = false

        val termsCheckbox = findViewById<CheckBox>(R.id.termsCheckBox)
        termsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            loginButton.isEnabled = isChecked
        }


    }

    private fun loginIsValid(login: String): Boolean = EMAIL_ADDRESS.matcher(login).matches()

    private fun passwordIsValid(password: String): Map<String, String> {
        var isValid = false
        var error = ""

        val chain = PasswordCheckerChain(
            PasswordCheckerEmpty(),
            PasswordCheckerChain(
                PasswordCheckerMinLength(),
                PasswordCheckerContainsOneDigit()
            )
        )

        try {
            isValid = chain.isValid(password)
        } catch (e: RuntimeException) {
            error = e.message.toString()
//            Log.d(ACTIVITY_TAG, error)
        }

        return mapOf<String, String>(
            "isValid" to isValid.toString(),
            "error" to error
        )
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