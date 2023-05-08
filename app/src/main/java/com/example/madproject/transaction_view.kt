package com.example.madproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madproject.Adapters.bkTransactAdapter
import com.example.madproject.databinding.ActivityTransactionViewBinding
import com.example.madproject.models.bankData
import com.example.madproject.models.bankTransactionData
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
    var creditAmount:Int = 0
    var debitAmount:Int = 0
    var bal:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bundle = intent.extras
        if(bundle != null){
            username = intent.getStringExtra("user").toString()
            binding.bankTransactName.text = bundle.getString("name")
            bankName = bundle.getString("name").toString()
            bankBal = bundle.getString("bal").toString()
            //bal = bankBal.toInt()
        }

        //Getting the Opening balance
        databaseReference = FirebaseDatabase.getInstance().getReference(username).child(bankName)
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var balanceStr = snapshot.child("balance").value.toString()
                bal = balanceStr.toInt()
            }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //Calculating the balance
        databaseReference = FirebaseDatabase.getInstance().getReference(username).child(bankName).child("Transactions")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(itemSnapshot in snapshot.children){
                    var transactType = itemSnapshot.child("type").value.toString()
                    if(transactType=="Credit"){
                        var creditAmountStr = itemSnapshot.child("amount").value.toString()
                        var credit = creditAmountStr.toInt()
                        creditAmount = creditAmount + credit
                    }else if(transactType=="Debit"){
                        var debitAmountStr = itemSnapshot.child("amount").value.toString()
                        var debit = debitAmountStr.toInt()
                        debitAmount = debitAmount + debit
                    }
                }
                binding.totCredits.text = creditAmount.toString()
                binding.totDebits.text = debitAmount.toString()
                bal = bal + (creditAmount-debitAmount)
                binding.totBalance.text = bal.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




        var listener = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform action when user submits query
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Perform action when user changes text in SearchView
                searchList(newText)
                return true
            }
        }

        binding.searchTransaction.setOnQueryTextListener(listener)

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference(username).child(bankName).child("Transactions")
            val LinearLayoutManager = LinearLayoutManager(this@transaction_view)
            binding.recyclerView.layoutManager = LinearLayoutManager

            dataList = ArrayList()
            adapter = bkTransactAdapter(this@transaction_view,dataList,username,bankName)
            binding.recyclerView.adapter = adapter

        }catch (e: Exception){
            Log.e("TAG","Error occured", e)
        }
        eventListener = databaseReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //To get the name of user so to pass as intent
                username= snapshot.ref.parent?.parent?.key.toString()
                //Toast.makeText(this@transaction_view,username,Toast.LENGTH_SHORT).show()
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
            var balance = binding.totBalance.text.toString()
            val intent = Intent(this@transaction_view,add_transaction::class.java)
            //intent.putExtra("Type", Credit)
            intent.putExtra("name", bankName)
            intent.putExtra("user", username)
            intent.putExtra("balance", balance)
            startActivity(intent)
            finish()
        }

        binding.backBtnTrans.setOnClickListener{
            var intent = Intent(this, MainBank::class.java)
            intent.putExtra("userName", username)
            startActivity(intent)
            finish()
        }

    }

    fun searchList(text: String){
        val searchList = ArrayList<bankTransactionData>()
        for(dataClass in dataList) {
            if (dataClass.id?.lowercase()?.contains(text.lowercase()) == true) {
                searchList.add(dataClass)
            } else if (dataClass.description?.lowercase()?.contains(text.lowercase()) == true) {
                searchList.add(dataClass)
            } else if (dataClass.type?.lowercase()?.contains(text.lowercase()) == true) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }

}



