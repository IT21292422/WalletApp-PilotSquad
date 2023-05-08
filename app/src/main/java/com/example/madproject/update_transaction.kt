package com.example.madproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.databinding.ActivityUpdateTransactionBinding
import com.example.madproject.models.bankTransactionData
import com.google.firebase.database.*

class update_transaction : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTransactionBinding
    private lateinit var databaseReference: DatabaseReference
    var transactId:String = ""
    var username:String = ""
    var bankName:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle = intent.extras
        if(bundle != null){
            transactId = bundle.getString("tid").toString()
            username = bundle.getString("user").toString()
            bankName = bundle.getString("bank").toString()
            binding.transID.text = bundle.getString("tid").toString()
        }

        databaseReference = FirebaseDatabase.getInstance().getReference(username).child(bankName).child("Transactions").child(transactId)
        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bank = snapshot.getValue(bankTransactionData::class.java)
                binding.addAmount.setText(bank?.amount.toString())
                binding.addDescription.setText(bank?.description)
                //binding.addType.setSelection(2)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.updateBtn.setOnClickListener{
            val id = transactId
            val amountStr = binding.addAmount.text.toString()
            val amount = amountStr.toInt()
            val description = binding.addDescription.text.toString()
            val type = binding.addType.getSelectedItem().toString()
            updateData(id,amount, description, type)
        }

        binding.deleteBtn.setOnClickListener{
            databaseReference = FirebaseDatabase.getInstance().getReference(username).child(bankName).child("Transactions")
            databaseReference.child(transactId).removeValue().addOnSuccessListener {
                Toast.makeText(this,"Successfully Deleted", Toast.LENGTH_SHORT).show()
                var intent = Intent(this, transaction_view::class.java)
                intent.putExtra("name",bankName)
                intent.putExtra("user", username)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Unable To Delete", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backBtnUpdt.setOnClickListener{
            var intent = Intent(this, transaction_view::class.java)
            intent.putExtra("name",bankName)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }
    }

    private fun updateData(id: String, amount: Int, description: String, type: String){
        databaseReference = FirebaseDatabase.getInstance().getReference(username).child(bankName).child("Transactions")
        val bankTransaction = mapOf("id" to id, "amount" to amount, "description" to description, "type" to type)
        databaseReference.child(id).updateChildren(bankTransaction).addOnSuccessListener {
            binding.addAmount.text.clear()
            binding.addDescription.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, transaction_view::class.java)
            intent.putExtra("name",bankName)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
        }
    }
}