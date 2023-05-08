package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madproject.databinding.ActivityUpdateBinding
import com.example.madproject.models.CashTrans
import com.google.firebase.database.*
import java.util.Objects

class Update : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference
    var transactID: String = ""
    var typeUpdt: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var username = intent.getStringExtra("user").toString()

        binding.Back.setOnClickListener{
            val intent = Intent(this@Update,Income_List::class.java)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }

        val bundle = intent.extras
        if(bundle != null){
            transactID = bundle.getString("id").toString()
            typeUpdt = bundle.getString("type").toString()
        }


        databaseReference = FirebaseDatabase.getInstance().getReference(username).child("Cash Transaction").child(typeUpdt).child(transactID)

        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override  fun onDataChange(dataSnapshot: DataSnapshot) {

                val transaction = dataSnapshot.getValue(CashTrans::class.java)

                binding.Description.setText(transaction?.description)
                binding.Date.setText(transaction?.date)
                binding.Amount.setText(transaction?.amount)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        binding.updateBtn.setOnClickListener{
            val id = transactID
            val amount = binding.Amount.text.toString()
            //val amount = amountStr.toInt()
            val date = binding.Date.text.toString()
            val description = binding.Description.text.toString()
            //val type = binding.Type.selectedItem.toString()
            if(typeUpdt=="Income"){
                updateIncome(id, description, amount, date,username)
            }else if(typeUpdt=="Expense"){
                updateExpense(id, description, amount, date,username)
            }
        }
    }

    private fun updateIncome(id: String, description: String, amount: String, date: String, user: String){
        databaseReference = FirebaseDatabase.getInstance().getReference(user).child("Cash Transaction").child("Income")
        val cashTransaction = mapOf("id" to id, "description" to description, "amount" to amount, "date" to date)
        databaseReference.child(id).updateChildren(cashTransaction).addOnSuccessListener {
            binding.Amount.text.clear()
            binding.Description.text.clear()
            binding.Date.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, Income_List::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateExpense(id: String, description: String, amount: String, date: String, user: String){
        databaseReference = FirebaseDatabase.getInstance().getReference(user).child("Cash Transaction").child("Expense")
        val cashTransaction = mapOf("id" to id, "description" to description, "amount" to amount, "date" to date)
        databaseReference.child(id).updateChildren(cashTransaction).addOnSuccessListener {
            binding.Amount.text.clear()
            binding.Description.text.clear()
            binding.Date.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, Expense_List::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
        }
    }
}
