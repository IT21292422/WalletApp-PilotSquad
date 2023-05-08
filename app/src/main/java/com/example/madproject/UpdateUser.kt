package com.example.madproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.madproject.Validations.ValidationResult
import com.example.madproject.models.UserData
import com.example.madproject.models.UserModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateUser : AppCompatActivity() {
    lateinit var edtUpdName:EditText
    lateinit var edtUpdPassword:EditText
    lateinit var edtUpdPassword2:EditText
    lateinit var txtUserName: TextView
    lateinit var databaseRef: DatabaseReference
    var userName = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        txtUserName = findViewById(R.id.txtUserName1)
        edtUpdName = findViewById(R.id.edtUpdName)
        edtUpdPassword = findViewById(R.id.edtUpdPassword)
        edtUpdPassword2 = findViewById(R.id.edtUpdPassword2)

        userName = intent.getStringExtra("userName").toString()

        databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        databaseRef.child(userName).get().addOnSuccessListener {
            if(it.exists()){
                val name = it.child("name").value
                val password = it.child("password").value
                val userName = it.child("userName").value
                txtUserName.text = userName.toString()
                edtUpdPassword.setText(password.toString())
                edtUpdPassword2.setText(password.toString())
                edtUpdName.setText(name.toString())
            }
        }.addOnFailureListener {e ->
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    fun UpdateUserAccount(view: View) {
        var count = 0

        val updateData = UserData(edtUpdName.text.toString(), edtUpdPassword.text.toString(), edtUpdPassword2.text.toString())
        val nameValidation = updateData.validateName()
        val passwordValidation = updateData.validatePassword()
        val rePasswordValidation = updateData.validateRePassword()

        when(nameValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                edtUpdName.error = nameValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                edtUpdName.error = nameValidation.errorMessage
            }
        }

        when(passwordValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                edtUpdPassword.error = passwordValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                edtUpdPassword.error = passwordValidation.errorMessage
            }
        }

        when(rePasswordValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Invalid -> {
                edtUpdPassword2.error = rePasswordValidation.errorMessage
            }
            is ValidationResult.Empty -> {
                edtUpdPassword2.error = rePasswordValidation.errorMessage
            }
        }

        if(count == 3){
            databaseRef = FirebaseDatabase.getInstance().getReference("Users")
            val user1 = mapOf<String, String>("userName" to txtUserName.text.toString(), "name" to edtUpdName.text.toString(), "password" to edtUpdPassword.text.toString(),)
            databaseRef.child(txtUserName.text.toString()).updateChildren(user1).addOnSuccessListener {
                val intent = Intent(this, User::class.java)
                intent.putExtra("userName", userName)
                startActivity(intent)
                Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun backToUser(view: View) {
        val intent = Intent(this, User::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }
}
