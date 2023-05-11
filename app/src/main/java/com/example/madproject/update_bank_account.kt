package com.example.madproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.databinding.ActivityUpdateBankAccountBinding
import com.example.madproject.models.bankData
import com.google.firebase.database.*

class update_bank_account : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBankAccountBinding
    private lateinit var databaseReference: DatabaseReference
    var bankName:String = ""
    var username:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBankAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.updateBankName.isEnabled = false

        var bundle = intent.extras
        if(bundle != null){
            username = intent.getStringExtra("user").toString()
            bankName = bundle.getString("name").toString()
            //bal = bankBal.toInt()
        }

        databaseReference = FirebaseDatabase.getInstance().getReference(username).child(bankName)
        databaseReference.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val bank = snapshot.getValue(bankData::class.java)
                binding.updateBankName.setText(bank?.name)
                binding.updateAccNo.setText(bank?.accNo)
                binding.updateBankBal.setText(bank?.balance.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })



        binding.backBtnBnk.setOnClickListener{
            var intent = Intent(this, MainBank::class.java)
            intent.putExtra("userName", username)
            startActivity(intent)
            finish()
        }

        binding.updateBankBtn.setOnClickListener{
            //val id = transactId
            val accNo = binding.updateAccNo.text.toString()
            val balanceStr = binding.updateBankBal.text.toString()
            val balance = balanceStr.toInt()
            val name = binding.updateBankName.text.toString()
            updateData(accNo,balance,name)
        }
    }

    private fun updateData(accNo: String, balance: Int, name: String){
        databaseReference = FirebaseDatabase.getInstance().getReference(username)
        val bankTransaction = mapOf("accNo" to accNo, "balance" to balance, "name" to name)
        databaseReference.child(bankName).updateChildren(bankTransaction).addOnSuccessListener {
            binding.updateAccNo.text.clear()
            binding.updateBankBal.text.clear()
            binding.updateBankName.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, MainBank::class.java)
            intent.putExtra("userName", username)
            startActivity(intent)
            finish()
        }.addOnFailureListener{
            Toast.makeText(this, "Failed to Update", Toast.LENGTH_SHORT).show()
        }
    }

}