package com.example.project_bank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project_bank.databinding.ActivityUpdateTransactionBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class update_transaction : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateTransactionBinding
    private lateinit var databaseReference: DatabaseReference
    var transactId:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle = intent.extras
        if(bundle != null){
            transactId = bundle.getString("tid").toString()
            binding.transID.text = bundle.getString("tid").toString()
        }

        binding.updateBtn.setOnClickListener{
            val id = transactId
            val amountStr = binding.addAmount.text.toString()
            val amount = amountStr.toInt()
            val description = binding.addDescription.text.toString()
            val type = binding.addType.getSelectedItem().toString()
            updateData(id,amount, description, type)
        }
    }

    private fun updateData(id: String, amount: Int, description: String, type: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Akmal").child("Commercial Bank").child("Transactions")
        val bankTransaction = mapOf("id" to id, "amount" to amount, "description" to description, "type" to type)
        databaseReference.child(id).updateChildren(bankTransaction).addOnSuccessListener {
            binding.addAmount.text.clear()
            binding.addDescription.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
        }
    }
}