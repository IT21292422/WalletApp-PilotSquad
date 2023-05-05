package com.example.project_bank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_bank.Adapters.bkTransactAdapter
import com.example.project_bank.models.bankTransactionData
import com.example.project_bank.databinding.ActivityTransactionViewBinding
import com.google.firebase.database.*

class transaction_view : AppCompatActivity() {
    private lateinit var binding: ActivityTransactionViewBinding
    private lateinit var dataList: ArrayList<bankTransactionData>
    private lateinit var adapter: bkTransactAdapter
    private lateinit var databaseReference: DatabaseReference
    var eventListener: ValueEventListener?= null

    var bankBal: String = ""
    var bankName: String = ""
    var username:String = ""
    val Debit: String ="Debit"
    val Credit: String ="Credit"
    var bal:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle = intent.extras
        if(bundle != null){
            username = intent.getStringExtra("user").toString()
            binding.bankTransactName.text = bundle.getString("name")
            binding.totBalance.text = bundle.getString("bal")
            bankName = bundle.getString("name").toString()
            bankBal = bundle.getString("bal").toString()
            //bal = bankBal.toInt()
        }

        //userName = intent.getStringExtra("userName").toString()

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference(username).child(bankName).child("Transactions")
            val LinearLayoutManager = LinearLayoutManager(this@transaction_view)
            binding.recyclerView.layoutManager = LinearLayoutManager

            dataList = ArrayList()
            adapter = bkTransactAdapter(this@transaction_view,dataList)
            binding.recyclerView.adapter = adapter

        }catch (e: Exception){
            Log.e("TAG","Error occured", e)
        }
        eventListener = databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //To get the name of user so to pass as intent
                username= snapshot.ref.parent?.parent?.key.toString()
                Toast.makeText(this@transaction_view,username,Toast.LENGTH_SHORT).show()
                dataList.clear()
                for(itemSnapshot in snapshot.children){
                    val dataClass = itemSnapshot.getValue(bankTransactionData::class.java)
                    if(dataClass!=null){
                        dataList.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        binding.addTransactBtn.setOnClickListener{
            val intent = Intent(this@transaction_view,add_transaction::class.java)
            intent.putExtra("Type", Credit)
            intent.putExtra("bank", bankName)
            startActivity(intent)
            finish()
        }

        binding.editBtn.setOnClickListener{
            val searchID: String = binding.searchTransaction.query.toString()
            if(searchID.isNotEmpty()) {
                val intent = Intent(this@transaction_view, update_transaction::class.java)
                intent.putExtra("tid", searchID)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"Please enter the ID",Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteBtn.setOnClickListener{
            val searchID: String = binding.searchTransaction.query.toString()
            if(searchID.isNotEmpty()){
                deleteData(searchID)
            }else{
                Toast.makeText(this,"Please enter the ID",Toast.LENGTH_SHORT).show()
            }
        }

        binding.searchBtn.setOnClickListener{
            //val searchID: String = binding.searchTransaction.text.toString()
            val searchID: String = binding.searchTransaction.query.toString()
            if(searchID.isNotEmpty()){
                readData(searchID)
            }else{
                Toast.makeText(this,"Please enter the ID",Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun readData(id: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Akmal")
        databaseReference.child(bankName).child(id).get().addOnSuccessListener {
            if (it.exists()) {
                val amount = it.child("amount").value
                val description = it.child("description").value
                Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
//                binding.searchTransaction.text.clear()
//                binding.tableId.text = id.toString()
//                binding.tableAmount.text = amount.toString()
//                binding.tableDescription.text = description.toString()
            } else {
                Toast.makeText(this, "Transaction does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
        }

    private fun deleteData(id: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Akmal").child("Commercial Bank").child("Transactions")
        databaseReference.child(id).removeValue().addOnSuccessListener {
            Toast.makeText(this,"Successfully Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this,"Unable To Delete", Toast.LENGTH_SHORT).show()
        }
    }

}

