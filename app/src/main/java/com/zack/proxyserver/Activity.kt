package com.zack.proxyserver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.startService(Intent(this, ProxyService::class.java))

    }
}