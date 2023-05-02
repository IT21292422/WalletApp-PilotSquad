package com.example.project_bank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.Toast
import com.example.project_bank.databinding.ActivityAddTransactionBinding
import com.example.project_bank.models.bankTransactionData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class add_transaction : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val Type = intent.getStringExtra("Type").toString()
        val bankName = intent.getStringExtra("bank").toString()
        var position: Int = 1
        if (Type=="Credit"){
            position = 1
        }else if (Type=="Debit"){
            position = 2
        }
        val addType: Spinner = findViewById(R.id.addType)
        addType.setSelection(position)

        binding.addBtn.setOnClickListener {
            val id = binding.addTransId.text.toString()
            val amountStr = binding.addAmount.text.toString()
            val amount = amountStr.toInt()
            val description = binding.addDescription.text.toString()
            val type = binding.addType.getSelectedItem().toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Akmal")
            val bankTransaction = bankTransactionData(id, amount, description,type)
            //databaseReference.child(id).setValue(bankTransaction).addOnSuccessListener
            databaseReference.child(bankName).child(id).setValue(bankTransaction).addOnSuccessListener {
                binding.addAmount.text.clear()
                binding.addDescription.text.clear()
                //binding.addType.selectedItem.clear()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

                var intent = Intent(this, transaction_view::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
            }

        }
    }
