package com.example.project_bank

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_bank.Adapter.bankAdapter
import com.example.project_bank.Adapter.bkTransactAdapter
import com.example.project_bank.databinding.ActivityMainBinding
import com.example.project_bank.databinding.ActivityTransactionViewBinding
import com.example.project_bank.models.bankData
import com.example.project_bank.models.bankTransactionData
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dataList: ArrayList<bankData>
    private lateinit var adapter: bankAdapter
    var username: String = "Akmal"
    var eventListener: ValueEventListener?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val LinearLayoutManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerViewBank.layoutManager = LinearLayoutManager

        dataList = ArrayList()
        adapter = bankAdapter(this@MainActivity,dataList)
        binding.recyclerViewBank.adapter = adapter
        databaseReference = FirebaseDatabase.getInstance().getReference(username)

        eventListener = databaseReference!!.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for(itemSnapshot in snapshot.children){
                    val dataClass = itemSnapshot.getValue(bankData::class.java)
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

    fun addBank(v: View){
        var addBank = findViewById<ImageView>(R.id.add_bank)
        addBank.setOnClickListener {
            var intent = Intent(this,activity_add_bank_account::class.java)
            startActivity(intent)
            finish()
        }
    }

//    fun transact(v: View){
//        var transact = findViewById<TextView>(R.id.combank)
//        transact.setOnClickListener {
//            var intent = Intent(this,transaction_view::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }

}