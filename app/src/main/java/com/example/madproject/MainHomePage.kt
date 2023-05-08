package com.example.madproject

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.madproject.databinding.ActivityMainHomePageBinding
import com.example.madproject.databinding.ActivityTransactionViewBinding

class MainHomePage : AppCompatActivity() {
    private lateinit var binding: ActivityMainHomePageBinding
    var userName = ""
//    val packageName = "com.example.project_bank"
//    val activityName = "com.example.project_bank.MainBank"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userName = intent.getStringExtra("userName").toString()

//        binding.mainBank.setOnClickListener{
//            val intent = Intent()
//            intent.component = ComponentName("com.example.project_bank", "com.example.project_bank.MainBank")
//            startActivity(intent)
//        }
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
    fun investmentpage(view: View) {
        val intent = Intent(this, InvestmentList::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()

    }
    fun cashpage(view: View) {
        val intent = Intent(this, Income_List::class.java)
        intent.putExtra("user", userName)
        startActivity(intent)
        finish()

    }

    fun bankPage(view: View){
        val intent = Intent(this, MainBank::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }

}