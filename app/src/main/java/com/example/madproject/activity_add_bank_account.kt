package com.example.madproject

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.databinding.ActivityAddBankAccountBinding
import com.example.madproject.models.bankData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class activity_add_bank_account : AppCompatActivity() {

    private lateinit var binding: ActivityAddBankAccountBinding
    private lateinit var databaseReference: DatabaseReference
    var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBankAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle = intent.extras
        if(bundle != null){
            username = bundle.getString("user").toString()
        }

//        val Type = intent.getStringExtra("Type").toString()
//        var position: Int = 1
//       if (Type=="Credit"){
//            position = 1
//        }else if (Type=="Debit"){
//            position = 2
//        }
//        val addType: Spinner = findViewById(R.id.addType)
//        addType.setSelection(position)
        binding.backBtnBnk.setOnClickListener{
            var intent = Intent(this, MainBank::class.java)
            intent.putExtra("userName", username)
            startActivity(intent)
            finish()
        }

        binding.addBankBtn.setOnClickListener {
            //val id = "T001"
            val name = binding.addBankName.text.toString()
            //val amount = amountStr.toInt()
            val accNo = binding.addAccNo.text.toString()
            val bal = binding.addBankBal.text.toString()
            val balance = bal.toInt()
            //val type = binding.addType.getSelectedItem().toString()

            databaseReference = FirebaseDatabase.getInstance().getReference(username)
            val bankTransaction = bankData(accNo, balance, name)
            //databaseReference.child(id).setValue(bankTransaction).addOnSuccessListener
            // databaseReference.child(name).child("Bank Data").setValue(bankTransaction)
            databaseReference.child(name).setValue(bankTransaction).addOnSuccessListener {
                binding.addBankName.text.clear()
                binding.addAccNo.text.clear()
                binding.addBankBal.text.clear()
                //binding.addType.selectedItem.clear()
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

                var intent = Intent(this, MainBank::class.java)
                intent.putExtra("userName", username)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

//class activity_add_bank_account : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_bank_account)
//    }
//}