package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madproject.Adapters.cashTransactAdapter
import com.example.madproject.databinding.ActivityExpenseListBinding
import com.example.madproject.databinding.ActivityIncomeListBinding
import com.example.madproject.models.CashTrans
import com.google.firebase.database.*

class Expense_List : AppCompatActivity() {
    private lateinit var binding: ActivityExpenseListBinding
    private lateinit var dataList: ArrayList<CashTrans>
    private lateinit var adapter: cashTransactAdapter
    private lateinit var databaseReference: DatabaseReference
    var eventListener: ValueEventListener?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("user").toString()

        binding.Back.setOnClickListener{
            val intent = Intent(this@Expense_List,MainHomePage::class.java)
            intent.putExtra("userName", username)
            startActivity(intent)
            finish()
        }

        binding.AddTranscation.setOnClickListener{
            val intent = Intent(this@Expense_List,Create::class.java)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }

        binding.IncomeExp.setOnClickListener{
            val intent = Intent(this@Expense_List,Income_List::class.java)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }

        val LinearLayoutManager = LinearLayoutManager(this@Expense_List)
        binding.recyclerViewIncome.layoutManager = LinearLayoutManager

        dataList = ArrayList()
        adapter = cashTransactAdapter(this@Expense_List,dataList,username)
        binding.recyclerViewIncome.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference(username).child("Cash Transaction").child("Expense")

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

        binding.searchExpense.setOnQueryTextListener(listener)
    }
    fun searchList(text: String) {
        val searchList = ArrayList<CashTrans>()
        for (dataClass in dataList) {
            if (dataClass.description?.lowercase()?.contains(text.lowercase()) == true) {
                searchList.add(dataClass)
            }
        }
        adapter.searchDataList(searchList)
    }
}