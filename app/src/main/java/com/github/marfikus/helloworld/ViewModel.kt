package com.github.marfikus.helloworld

class ViewModel(private val textObservable: TextObservable) {

    init {
        Model.init(object : TextCallback {
            override fun updateText(str: String) {
                textObservable.postValue(str)
            }
        })
    }

    fun init() {
        Model.start()
    }
}

class TextObservable {

    private lateinit var callback: TextCallback

    fun observe(callback: TextCallback) {
        this.callback = callback
    }

    fun postValue(text: String) {
        callback.updateText(text)
    }
}

interface TextCallback {
    fun updateText(str: String)
}