package com.github.marfikus.helloworld

import android.content.res.Resources
import android.graphics.Bitmap
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
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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

        val imageView = findViewById<ImageView>(R.id.imageView)
//        imageView.setImageResource(R.drawable.flying_squirrel)

/*        val netImage = NetImage(URL, object : ImageCallback {
            override fun success(bitmap: Bitmap) = runOnUiThread {
                imageView.setImageBitmap(bitmap)
            }

            override fun failed() = runOnUiThread {
                Snackbar.make(imageView, "failed", Snackbar.LENGTH_SHORT).show()
            }
        })
        netImage.start()*/

        imageView.load(URL)


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

    }

    private fun ImageView.load(url: String) {
/*        Picasso.get()
            .load(URL)
            .centerCrop()
            .resize(720, 1280)
            .placeholder(android.R.drawable.ic_media_pause)
            .error(android.R.drawable.ic_dialog_alert)
            .transform(CircleTransformation())
            .into(this)*/

        Glide.with(this)
            .load(url)
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