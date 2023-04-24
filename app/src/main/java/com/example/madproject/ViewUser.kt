package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.madproject.models.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class ViewUser : AppCompatActivity() {
    lateinit var databaseRef: DatabaseReference
    lateinit var txtUserName: TextView
    lateinit var txtName: TextView
    lateinit var txtPassword: TextView
    var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user)

        txtUserName = findViewById(R.id.txtUserName)
        txtName = findViewById(R.id.txtName)
        txtPassword = findViewById(R.id.txtPassword)
        userName = intent.getStringExtra("userName").toString()

        databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        databaseRef.child(userName).get().addOnSuccessListener {
            if(it.exists()){
                val name = it.child("name").value
                val password = it.child("password").value
                val userName = it.child("userName").value
                txtUserName.text = userName.toString()
                txtPassword.text = password.toString()
                txtName.text =  name.toString()
            }
        }.addOnFailureListener {e ->
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun backToUser(view: View) {
        val intent = Intent(this, User::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }
}

