package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madproject.Adapters.cashTransactAdapter
import com.example.madproject.databinding.ActivityIncomeListBinding
import com.example.madproject.models.CashTrans
import com.google.firebase.database.*

class Income_List : AppCompatActivity() {
    private lateinit var binding: ActivityIncomeListBinding
    private lateinit var dataList: ArrayList<CashTrans>
    private lateinit var adapter: cashTransactAdapter
    private lateinit var databaseReference: DatabaseReference
    var eventListener: ValueEventListener?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncomeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("user").toString()

        binding.Back.setOnClickListener{
            val intent = Intent(this@Income_List,MainHomePage::class.java)
            startActivity(intent)
            finish()
        }

        binding.AddTranscation.setOnClickListener{
            val intent = Intent(this@Income_List,Create::class.java)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }

        binding.Expense.setOnClickListener{
            val intent = Intent(this@Income_List,Expense_List::class.java)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }

        val LinearLayoutManager = LinearLayoutManager(this@Income_List)
        binding.recyclerViewIncome.layoutManager = LinearLayoutManager

        dataList = ArrayList()
        adapter = cashTransactAdapter(this@Income_List,dataList,username)
        binding.recyclerViewIncome.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference(username).child("Cash Transaction").child("Income")

        eventListener = databaseReference!!.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for(itemSnapshot in snapshot.children){
                    val dataClass = itemSnapshot.getValue(CashTrans::class.java)
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
    }
}