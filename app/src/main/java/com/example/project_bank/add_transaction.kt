package com.example.project_bank

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        val bankName = intent.getStringExtra("name").toString()
        val username = intent.getStringExtra("user").toString()

//        var position: Int = 1
//        if (Type=="Credit"){
//            position = 1
//        }else if (Type=="Debit"){
//            position = 2
//        }
//        val addType: Spinner = findViewById(R.id.addType)
//        addType.setSelection(position)

        binding.backBtnAddTrans.setOnClickListener{
            var intent = Intent(this, transaction_view::class.java)
            intent.putExtra("name",bankName)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }

        binding.addBtn.setOnClickListener {
            val id = binding.addTransId.text.toString()
            val amountStr = binding.addAmount.text.toString()
            val amount = amountStr.toInt()
            val description = binding.addDescription.text.toString()
            val type = binding.addType.getSelectedItem().toString()

            databaseReference = FirebaseDatabase.getInstance().getReference(username).child(bankName).child("Transactions")
            val bankTransaction = bankTransactionData(id, amount, description,type)
            //databaseReference.child(id).setValue(bankTransaction).addOnSuccessListener
            databaseReference.child(id).setValue(bankTransaction).addOnSuccessListener {
                binding.addAmount.text.clear()
                binding.addDescription.text.clear()
                //binding.addType.selectedItem.clear()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

                var intent = Intent(this, transaction_view::class.java)
                intent.putExtra("name",bankName)
                intent.putExtra("user", username)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
            }

        }
    }
