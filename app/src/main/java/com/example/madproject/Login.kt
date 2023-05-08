package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.Validations.ValidationResult
import com.example.madproject.models.UserData
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {
    lateinit var edtUserName:EditText
    lateinit var edtPassword:EditText
    lateinit var databaseRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtUserName = findViewById(R.id.edtUserName)
        edtPassword = findViewById(R.id.edtPassword)
    }

    //onclick method for open register
    fun openRegisterPage(view: View) {
        val intent = Intent(this, RegisterUser::class.java)
        startActivity(intent)
        finish()
    }

    //onclick method for login
    fun loginFunction(view:View) {
        /*val intent = Intent(this, User::class.java)
        startActivity(intent)
        finish()*/
        var count = 0
        val loginData = UserData(edtUserName.text.toString(), edtPassword.text.toString())
        val userNameValidation = loginData.validateUserName()
        val passwordValidation = loginData.validatePassword()

        //check username validation
        when(userNameValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                edtUserName.error = userNameValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                edtUserName.error = userNameValidation.errorMessage
            }
        }

        //check password validation
        when(passwordValidation) {
            is ValidationResult.Valid ->{
                count++
            }
            is ValidationResult.Invalid ->{
                edtPassword.error = passwordValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                edtPassword.error = passwordValidation.errorMessage
            }
        }

        //check user name and password for login
        if(count == 2) {
            databaseRef = FirebaseDatabase.getInstance().getReference("Users")
            databaseRef.child(edtUserName.text.toString()).get().addOnSuccessListener {
                if(it.exists()){
                    val intent = Intent(this, MainHomePage::class.java)
                    intent.putExtra("userName", edtUserName.text.toString())
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this, "Invalid user name or password", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        }
    }
}