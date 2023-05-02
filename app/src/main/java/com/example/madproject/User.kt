package com.example.madproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class User : AppCompatActivity() {
    lateinit var databaseRef: DatabaseReference
    lateinit var txtViewUserName: TextView
    lateinit var confirmationBox: AlertDialog.Builder
    var userName = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        userName = intent.getStringExtra("userName").toString()
        txtViewUserName = findViewById(R.id.txtViewUserName)
        txtViewUserName.text = userName
    }

    fun openAddAccess(view: View) {
        val intent = Intent(this, AddAdminAccess::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }

    fun openViewUser(view: View) {
        val intent = Intent(this, ViewUser::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }

    fun openUpdateUser(view: View) {
        val intent = Intent(this, UpdateUser::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }

    fun openManageAccess(view: View) {
        val intent = Intent(this, ManageAccess::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }

    fun openSearchUser(view: View) {
        val intent = Intent(this, SearchAccessDetails::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }

    fun deleteCurrentUser(view: View) {
        confirmationBox = AlertDialog.Builder(this)
        confirmationBox.setTitle("Delete").setMessage("Do you want to delete this account ? ")
            .setPositiveButton("Yes") { dialogInterface, it ->
                deleteUser()
            }.setNegativeButton("No") { dialogInterface, it ->

        }
        val dialog = confirmationBox.create()
        dialog.show()
    }

    fun deleteUser() {
        databaseRef = FirebaseDatabase.getInstance().getReference("Users")
        databaseRef.child(userName).removeValue().addOnSuccessListener {
            var intent = Intent(this, Login::class.java)
            startActivity(intent)
            Toast.makeText(this, "Your account successfully deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun logout(view: View) {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
        finish()
    }


    fun backToHome(view: View) {
        val intent = Intent(this, MainHomePage::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()

    }

}