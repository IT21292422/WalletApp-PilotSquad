package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.madproject.Validations.ValidationResult
import com.example.madproject.models.UserAccess
import com.example.madproject.models.UserData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//add admin access class
class AddAdminAccess : AppCompatActivity() {
    lateinit var edtAccUserName:EditText
    lateinit var databaseRef: DatabaseReference
    lateinit var databaseRef1: DatabaseReference
    var userName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_admin_access)

        edtAccUserName = findViewById(R.id.edtAccUserName)

        userName = intent.getStringExtra("userName").toString()
    }

    //onclick method for add access
    fun AddAccess(view: View) {
        var count = 0

        val addAccessData = UserData(edtAccUserName.text.toString())
        val userNameValidation = addAccessData.validateUserName()

        //check username validation
        when(userNameValidation) {
            is ValidationResult.Valid -> {
                count++
            }
            is ValidationResult.Empty -> {
                edtAccUserName.error = userNameValidation.errorMessage
            }
            is ValidationResult.Invalid -> {
                edtAccUserName.error = userNameValidation.errorMessage
            }
        }

        //add user to add access
        if(count == 1) {
            databaseRef1 = FirebaseDatabase.getInstance().getReference("Users")
            databaseRef1.child(edtAccUserName.text.toString()).get().addOnSuccessListener {
                //check whether same account or not
                if(edtAccUserName.text.toString() == userName) {
                    Toast.makeText(this, "Can't add same account to access", Toast.LENGTH_SHORT).show()
                }
                else {
                    // if account exist
                    if (it.exists()) {
                        addAccess()
                    }
                    else {
                        //not exist
                        var intent = Intent(this, User::class.java)
                        intent.putExtra("userName", userName)
                        startActivity(intent)
                        Toast.makeText(this, "Account not available", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }.addOnFailureListener {e ->
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }


            val intent = Intent(this, User::class.java)
            intent.putExtra("userName", userName)
            startActivity(intent)
            finish()
        }
    }

    //function for add access
    fun addAccess() {
        databaseRef = FirebaseDatabase.getInstance().getReference("UserAccess")
        databaseRef.child(userName).get().addOnSuccessListener {
            //check whether already exist or not
            if (it.exists()) {
                val acc1 = it.child("access1").value.toString()
                val acc2 = it.child("access2").value.toString()
                val acc3 = it.child("access3").value.toString()

                var user1: Map<String, String>

                // check whether account already added or not
                if((acc1 == edtAccUserName.text.toString()) || (acc2 == edtAccUserName.text.toString()) || (acc3 == edtAccUserName.text.toString())) {
                    Toast.makeText(this, "account already have access", Toast.LENGTH_SHORT).show()
                }
                else//check whether access 1 free or not if free write access 1
                    if(acc1 == ""){
                        user1 = mapOf<String, String>("access1" to edtAccUserName.text.toString())
                        databaseRef.child(userName).updateChildren(user1).addOnSuccessListener {
                            val intent = Intent(this, User::class.java)
                            intent.putExtra("userName", userName)
                            startActivity(intent)
                            Toast.makeText(this, "Successfully access added", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                else//check whether access 2 free or not if free write access 2
                    if(acc2 == "") {
                        user1 = mapOf<String, String>("access2" to edtAccUserName.text.toString())
                        databaseRef.child(userName).updateChildren(user1).addOnSuccessListener {
                            val intent = Intent(this, User::class.java)
                            intent.putExtra("userName", userName)
                            startActivity(intent)
                            Toast.makeText(this, "Successfully access added", Toast.LENGTH_SHORT)
                                .show()
                            finish()
                        }
                    }
                else//check whether access 3 free or not if free write access 3
                    if(acc3 == "") {
                        user1 = mapOf<String, String>("access3" to edtAccUserName.text.toString())
                        databaseRef.child(userName).updateChildren(user1).addOnSuccessListener {
                            val intent = Intent(this, User::class.java)
                            intent.putExtra("userName", userName)
                            startActivity(intent)
                            Toast.makeText(this, "Successfully access added", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                else {//maximum number of access reached
                        Toast.makeText(this, "maximum number access reached", Toast.LENGTH_SHORT).show()
                    }


            }
            else { // for first time adding access
                databaseRef = FirebaseDatabase.getInstance().getReference("UserAccess")
                val user1 = mapOf<String, String>("access1" to edtAccUserName.text.toString(), "access2" to "", "access3" to "")
                databaseRef.child(userName).setValue(user1).addOnSuccessListener {
                    var intent = Intent(this, User::class.java)
                    intent.putExtra("userName", userName)
                    startActivity(intent)
                    Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }.addOnFailureListener {e ->
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    //onclick method for back to user
    fun backToUser(view: View) {
        val intent = Intent(this, User::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }
}