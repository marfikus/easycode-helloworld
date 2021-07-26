package com.github.marfikus.helloworld

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val textView = findViewById<TextView>(R.id.textView)

        val observable = TextObservable()
        observable.observe(object : TextCallback {
            override fun updateText(str: String) = runOnUiThread {
                textView.text = str
            }
        })

        val viewModel = ViewModel(observable)
        viewModel.init()
    }
}