package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ManageAccess : AppCompatActivity() {
    var userName = ""
    lateinit var databaseRef: DatabaseReference
    lateinit var txtAcc1: TextView
    lateinit var txtAcc2: TextView
    lateinit var txtAcc3: TextView
    lateinit var btnDelAcc1: Button
    lateinit var btnDelAcc2: Button
    lateinit var btnDelAcc3: Button
    lateinit var confirmationBox:AlertDialog.Builder
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_access)

        userName = intent.getStringExtra("userName").toString()
        txtAcc1 = findViewById(R.id.txtAcc1)
        txtAcc2 = findViewById(R.id.txtAcc2)
        txtAcc3 = findViewById(R.id.txtAcc3)
        btnDelAcc1 = findViewById(R.id.btnDelAcc1)
        btnDelAcc2 = findViewById(R.id.btnDelAcc2)
        btnDelAcc3 = findViewById(R.id.btnDelAcc3)

        databaseRef = FirebaseDatabase.getInstance().getReference("UserAccess")
        databaseRef.child(userName).get().addOnSuccessListener {
            if(it.exists()){
                val access1 = it.child("access1").value
                val access2 = it.child("access2").value
                val access3 = it.child("access3").value

                txtAcc1.text = access1.toString()
                txtAcc2.text = access2.toString()
                txtAcc3.text = access3.toString()

                if(txtAcc1.text == "")
                    btnDelAcc1.visibility  = View.INVISIBLE
                if(txtAcc2.text == "")
                    btnDelAcc2.visibility = View.INVISIBLE
                if(txtAcc3.text == "")
                    btnDelAcc3.visibility = View.INVISIBLE

            }
        }.addOnFailureListener {e ->
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAcc1(view:View) {
        confirmationBox = AlertDialog.Builder(this)
        confirmationBox.setTitle("Delete").setMessage("Do you want to remove this access account ? ").setPositiveButton("Yes") {
                dialogInterface, it -> deleteAccess1()
        }.setNegativeButton("No") { dialogInterface, it ->

        }
        val dialog = confirmationBox.create()
        dialog.show()
    }

    fun deleteAccess1() {
        databaseRef = FirebaseDatabase.getInstance().getReference("UserAccess")
        val user1 = mapOf<String, String>("access1" to txtAcc2.text.toString(), "access2" to txtAcc3.text.toString(), "access3" to "")
        databaseRef.child(userName).updateChildren(user1).addOnSuccessListener {
            val intent = Intent(this, User::class.java)
            intent.putExtra("userName", userName)
            startActivity(intent)
            Toast.makeText(this, "Successfully access removed", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {e ->
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAcc2(view:View) {
        confirmationBox = AlertDialog.Builder(this)
        confirmationBox.setTitle("Delete").setMessage("Do you want to remove this access account ? ").setPositiveButton("Yes") {
                dialogInterface, it -> deleteAccess2()
        }.setNegativeButton("No") { dialogInterface, it ->

        }
        val dialog = confirmationBox.create()
        dialog.show()
    }

    fun deleteAccess2() {
        databaseRef = FirebaseDatabase.getInstance().getReference("UserAccess")
        val user1 = mapOf<String, String>("access2" to txtAcc3.text.toString(), "access3" to "")
        databaseRef.child(userName).updateChildren(user1).addOnSuccessListener {
            val intent = Intent(this, User::class.java)
            intent.putExtra("userName", userName)
            startActivity(intent)
            Toast.makeText(this, "Successfully access removed", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {e ->
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAcc3(view:View) {
        confirmationBox = AlertDialog.Builder(this)
        confirmationBox.setTitle("Delete").setMessage("Do you want to remove this access account ? ").setPositiveButton("Yes") {
                dialogInterface, it -> deleteAccess2()
        }.setNegativeButton("No") { dialogInterface, it ->

        }
        val dialog = confirmationBox.create()
        dialog.show()
    }
    fun deleteAccess3() {
        databaseRef = FirebaseDatabase.getInstance().getReference("UserAccess")
        val user1 = mapOf<String, String>("access3" to "")
        databaseRef.child(userName).updateChildren(user1).addOnSuccessListener {
            val intent = Intent(this, User::class.java)
            intent.putExtra("userName", userName)
            startActivity(intent)
            Toast.makeText(this, "Successfully access removed", Toast.LENGTH_SHORT).show()
            finish()
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