package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainHomePage : AppCompatActivity() {
    var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_home_page)

        userName = intent.getStringExtra("userName").toString()
    }

    fun redirectUser(view: View) {
        val intent = Intent(this, User::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }

    fun redirectSearch(view: View) {
        val intent = Intent(this, SearchAccessDetails::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }
}