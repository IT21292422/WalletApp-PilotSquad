package com.example.madproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.Validations.ValidationResult
import com.example.madproject.models.UserData
import com.example.madproject.models.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterUser : AppCompatActivity() {
    lateinit var edtRegUserName:EditText
    lateinit var edtRegPassword:EditText
    lateinit var edtRegName:EditText
    lateinit var edtRegPassword2:EditText
    lateinit var databaseRef:DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        edtRegUserName = findViewById(R.id.edtUpdUserName)
        edtRegName = findViewById(R.id.edtUpdName)
        edtRegPassword = findViewById(R.id.edtUpdPassword)
        edtRegPassword2 = findViewById(R.id.edtRegPassword2)
    }

    fun openLoginPage(view: View) {
        var intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }
    @SuppressLint("NotConstructor")
    fun RegisterUser(view: View) {
        var count = 0

        val registerData = UserData(edtRegUserName.text.toString(), edtRegName.text.toString(), edtRegPassword.text.toString(), edtRegPassword2.text.toString())
        val userNameValidation = registerData.validateUserName()
        val passwordValidation = registerData.validatePassword()
        val nameValidation = registerData.validateName()
        val rePasswordValidation = registerData.validateRePassword()

        when(userNameValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                edtRegUserName.error = userNameValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                edtRegUserName.error = userNameValidation.errorMessage
            }
        }

        when(passwordValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                edtRegPassword.error = passwordValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                edtRegPassword.error = passwordValidation.errorMessage
            }
        }

        when(nameValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Empty -> {
                edtRegName.error = nameValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                edtRegPassword.error = nameValidation.errorMessage
            }
        }

        when(rePasswordValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Empty -> {
                edtRegPassword2.error = rePasswordValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                edtRegPassword2.error = rePasswordValidation.errorMessage
            }
        }

        if(count == 4) {
            databaseRef = FirebaseDatabase.getInstance().getReference("Users")
            databaseRef.child(edtRegUserName.text.toString()).get().addOnSuccessListener {
                if (it.exists()) {
                    var intent = Intent(this, Login::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Account already available", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else {
                    val user1 = UserModel(edtRegUserName.text.toString(), edtRegName.text.toString(), edtRegPassword.text.toString() )
                    databaseRef.child(edtRegUserName.text.toString()).setValue(user1).addOnSuccessListener {
                            var intent = Intent(this, Login::class.java)
                            startActivity(intent)
                            Toast.makeText(this, "Successfully created", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                }
            }.addOnFailureListener {e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}