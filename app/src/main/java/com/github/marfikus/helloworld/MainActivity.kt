package com.github.marfikus.helloworld

import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

/*        val agreementTextView: TextView = findViewById(R.id.agreementTextView)

        val fullText = getString(R.string.agreement_fully_text)
        val confidential = getString(R.string.confidential_info)
        val policy = getString(R.string.privacy_policy)
        val spannableString = SpannableString(fullText)

        val confidentialClickable = MyClickableSpan {
            Snackbar.make(it, getString(R.string.go_to_link_1), Snackbar.LENGTH_SHORT).show()
        }

        val policyClickable = MyClickableSpan {
            Snackbar.make(it, getString(R.string.go_to_link_2), Snackbar.LENGTH_SHORT).show()
        }

        spannableString.setSpan(
            confidentialClickable,
            fullText.indexOf(confidential),
            fullText.indexOf(confidential) + confidential.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannableString.setSpan(
            policyClickable,
            fullText.indexOf(policy),
            fullText.indexOf(policy) + policy.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        agreementTextView.run {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }*/

        val imageView = findViewById<ImageView>(R.id.iconImageView)
        imageView.setImageResource(R.drawable.flying_squirrel)

    }

    fun TextView.setColor(@ColorRes colorResId: Int, theme: Resources.Theme? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextColor(resources.getColor(colorResId, theme))
        } else {
            setTextColor(resources.getColor(colorResId))
        }
    }
}