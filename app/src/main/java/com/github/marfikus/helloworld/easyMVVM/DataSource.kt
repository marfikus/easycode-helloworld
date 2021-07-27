package com.github.marfikus.helloworld.easyMVVM

interface DataSource {

    fun saveInt(key: String, value: Int)

    fun getInt(key: String): Int
}