package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.Validations.ValidationResult
import com.example.madproject.models.UserData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SearchAccessDetails : AppCompatActivity() {
    lateinit var edtSrhUserName: EditText
    var userName = ""
    lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_access_details)
        edtSrhUserName = findViewById(R.id.edtSrhUserName)

        userName = intent.getStringExtra("userName").toString()
    }

    fun SearchUser(view: View) {
        var count = 0;

        val searchData = UserData(edtSrhUserName.text.toString())
        val userNameValidation = searchData.validateUserName()

        when(userNameValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                edtSrhUserName.error = userNameValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                edtSrhUserName.error = userNameValidation.errorMessage
            }
        }

        if(count == 1) {
            databaseRef = FirebaseDatabase.getInstance().getReference("UserAccess")
            databaseRef.child(userName).get().addOnSuccessListener {
                val searchUserName = edtSrhUserName.text.toString()
                if(it.exists()){
                    val access1 = it.child("access1").value
                    val access2 = it.child("access2").value
                    val access3 = it.child("access3").value

                    if((access1.toString() == searchUserName) || (access2.toString() == searchUserName) || (access3.toString() == searchUserName)){
                        Toast.makeText(this, "have access", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "Do not have access to view", Toast.LENGTH_SHORT).show()
                    }

                }
                else {
                    Toast.makeText(this, "Do not have access to view", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun backToHome(view: View) {
        val intent = Intent(this, User::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }
}