package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madproject.Adapters.cashTransactAdapter
import com.example.madproject.databinding.ActivityIncomeListBinding
import com.example.madproject.models.CashTrans
import com.google.firebase.database.*

class Expense_List : AppCompatActivity() {
    private lateinit var binding: ActivityIncomeListBinding
    private lateinit var dataList: ArrayList<CashTrans>
    private lateinit var adapter: cashTransactAdapter
    private lateinit var databaseReference: DatabaseReference
    var eventListener: ValueEventListener?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIncomeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AddTranscation.setOnClickListener{
            val intent = Intent(this@Expense_List,Create::class.java)
            startActivity(intent)
            finish()
        }

        binding.Income.setOnClickListener{
            val intent = Intent(this@Expense_List,Income_List::class.java)
            startActivity(intent)
            finish()
        }

        val LinearLayoutManager = LinearLayoutManager(this@Expense_List)
        binding.recyclerViewIncome.layoutManager = LinearLayoutManager

        dataList = ArrayList()
        adapter = cashTransactAdapter(this@Expense_List,dataList)
        binding.recyclerViewIncome.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference("Mayuran").child("Cash Transaction").child("Expense")

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