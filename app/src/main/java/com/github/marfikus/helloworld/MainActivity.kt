package com.github.marfikus.helloworld

import android.app.Dialog
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.*
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.github.marfikus.helloworld.password_checker.PasswordCheckerChain
import com.github.marfikus.helloworld.password_checker.PasswordCheckerContainsOneDigit
import com.github.marfikus.helloworld.password_checker.PasswordCheckerEmpty
import com.github.marfikus.helloworld.password_checker.PasswordCheckerMinLength
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException

private const val TEXT_WATCHER_TAG = "TextWatcherTag"
private const val ACTIVITY_TAG = "ActivityTag"
private const val KEY_SCREEN_STATE = "screenState"

class MainActivity : AppCompatActivity() {

    private companion object {
        const val INITIAL = 0
        const val PROGRESS = 1
        const val SUCCESS = 2
        const val FAILED = 3
    }

    private var state = INITIAL

    private lateinit var loginInputLayout: TextInputLayout
    private lateinit var loginInputEditText: TextInputEditText

    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var passwordInputEditText: TextInputEditText

    private val textWatcher: TextWatcher = object : SimpleTextWatcher() {

        override fun afterTextChanged(s: Editable?) {
            Log.d(ACTIVITY_TAG, "changed ${s.toString()}")
            loginInputLayout.isErrorEnabled = false
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

        Log.d(ACTIVITY_TAG, "onCreate ${savedInstanceState == null}")
        savedInstanceState?.let {
            state = it.getInt(KEY_SCREEN_STATE)
        }
        Log.d(ACTIVITY_TAG, "state is $state")

        loginInputLayout = findViewById(R.id.loginInputLayout)
        loginInputEditText = loginInputLayout.editText as TextInputEditText

//        loginInputEditText.addTextChangedListener(textWatcher)

        val contentLayout = findViewById<LinearLayout>(R.id.contentLayout)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {

            if (!loginIsValid(loginInputEditText.text.toString())) {
                loginInputLayout.isErrorEnabled = true
                loginInputLayout.error = getString(R.string.invalid_email_message)
                return@setOnClickListener
            }

            hideKeyboard(loginInputEditText)
//            loginButton.isEnabled = false
            contentLayout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            state = PROGRESS

            Handler(Looper.myLooper()!!).postDelayed({
                contentLayout.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                state = FAILED
                showDialog(contentLayout)
            }, 3000)
        }

        loginButton.isEnabled = false

        val termsCheckbox = findViewById<CheckBox>(R.id.termsCheckBox)
        termsCheckbox.setOnCheckedChangeListener { _, isChecked ->
            loginButton.isEnabled = isChecked
        }

        when (state) {
            FAILED -> showDialog(contentLayout)
            SUCCESS -> {
                Snackbar.make(contentLayout, "Success", Snackbar.LENGTH_SHORT).show()
                state = INITIAL
            }
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d(ACTIVITY_TAG, "onResume")
        loginInputEditText.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        Log.d(ACTIVITY_TAG, "onPause")
        loginInputEditText.removeTextChangedListener(textWatcher)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(ACTIVITY_TAG, "onSaveInstanceState")
        outState.putInt(KEY_SCREEN_STATE, state)
    }

    override fun onDestroy() {
        Log.d(ACTIVITY_TAG, "onDestroy")
        super.onDestroy()
    }

    private fun showDialog(viewGroup: ViewGroup) {
        val dialog = Dialog(this)
        val view =
            LayoutInflater.from(this).inflate(R.layout.dialog, viewGroup, false)
        dialog.setCancelable(false)
        view.findViewById<View>(R.id.closeDialogButton).setOnClickListener {
            state = INITIAL
            dialog.dismiss()
        }
        dialog.setContentView(view)
        dialog.show()
    }

    private fun loginIsValid(login: String): Boolean = EMAIL_ADDRESS.matcher(login).matches()

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