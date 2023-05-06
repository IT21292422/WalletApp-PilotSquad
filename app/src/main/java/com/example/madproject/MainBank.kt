package com.example.madproject

import com.google.firebase.database.R

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madproject.Adapters.bankAdapter
import com.example.madproject.databinding.ActivityMainBankBinding
import com.example.madproject.models.bankData
import com.google.firebase.database.*


class MainBank : AppCompatActivity() {
    private lateinit var binding: ActivityMainBankBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dataList: ArrayList<bankData>
    private lateinit var adapter: bankAdapter
    var username: String = "Akmal"
    var eventListener: ValueEventListener?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra("user").toString()

        val LinearLayoutManager = LinearLayoutManager(this@MainBank)
        binding.recyclerViewBank.layoutManager = LinearLayoutManager

        dataList = ArrayList()
        adapter = bankAdapter(this@MainBank,dataList,username)
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
        binding.addBank.setOnClickListener {
            var intent = Intent(this,activity_add_bank_account::class.java)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }
    }

}