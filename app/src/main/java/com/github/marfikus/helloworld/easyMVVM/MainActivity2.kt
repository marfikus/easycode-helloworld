package com.github.marfikus.helloworld.easyMVVM

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.github.marfikus.helloworld.MyApplication
import com.github.marfikus.helloworld.R
import com.github.marfikus.helloworld.passwordChecker.PasswordChecker
import com.github.marfikus.helloworld.passwordChecker.PasswordCheckerChain

class MainActivity2 : AppCompatActivity() {

    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        viewModel = (application as MyApplication).viewModel
        val textView = findViewById<TextView>(R.id.textView)
        val observable = TextObservable()
        observable.observe(object : TextCallback {
            override fun updateText(str: String) = runOnUiThread {
                textView.text = str
            }
        })
        viewModel.init(observable)
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeCounting()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseCounting()
    }

    override fun onDestroy() {
        viewModel.clear()
        super.onDestroy()
    }
}