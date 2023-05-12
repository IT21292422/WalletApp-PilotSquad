package com.example.madproject
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.Adapters.StockAdapter
import com.example.madproject.models.Stock
import com.google.firebase.database.*
import java.util.*

class InvestmentList : AppCompatActivity(){

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<Stock>()
    private lateinit var adapter: StockAdapter
    var userName = ""
    private lateinit var databaseRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_investment_list)
        userName = intent.getStringExtra("userName").toString()
        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        databaseRef = FirebaseDatabase.getInstance().getReference("StockTransaction").child(userName)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addDataToList()
        adapter = StockAdapter(mList)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<Stock>()
            for (i in mList) {
                if (i.stock_name?.lowercase(Locale.ROOT)?.contains(query) == true) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToList() {

        databaseRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val childValueList = mutableListOf<String>()
                for (data in snapshot.children) {
//                        val key = data.key
//                        val value = data.value as String
                    Log.d("KEY", data.key.toString())
                    childValueList.add(data.value.toString())
                    Log.d("", data.value.toString())
                }


                for (i in 0 until childValueList.size) {
                    var stock = Stock()
                    Log.d("FLOW",i.toString())
                    Log.d("SIZE", childValueList.size.toString())
                    val childValueMap = childValueList[i]
                        .replace("{", "")
                        .replace("}", "")
                        .split(", ")
                        .map { it.split("=") }
                        .associate { it[0] to it[1] }

                    stock.stock_name = childValueMap["stock_name"]
                    stock.value = childValueMap["value"]?.toDouble()
                    stock.currently_held = childValueMap["currently_held"]?.toInt()
                    stock.buyorsell = childValueMap["buyorsell"]
                    stock.quantity = childValueMap["quantity"]?.toInt()
                    stock.total_value = childValueMap["total_value"]?.toDouble()
                    stock.id=childValueMap["id"]?.toInt()
                    mList.add(stock)

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST","Failed to read value.", error.toException())
            }
        })
    }
    fun addinvestment(view: View) {
        val intent = Intent(this, AddInvestment::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()

    }
    fun goback(view: View){
        val intent = Intent(this, MainHomePage::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }
    fun editinvestment(view: View) {
        val intent =Intent(this,UpdateInvestment::class.java)
        intent.putExtra("userName", userName)
        intent.putExtra("tv_stock_name",findViewById<TextView>(R.id.tv_stock_name).text)
        intent.putExtra("tv_value",findViewById<TextView>(R.id.tv_value).text)
        intent.putExtra("tv_quantity",findViewById<TextView>(R.id.tv_quantity).text)
        intent.putExtra("tv_total_value",findViewById<TextView>(R.id.tv_total_value).text)
        intent.putExtra("tv_currently_held",findViewById<TextView>(R.id.tv_currently_held).text)
        intent.putExtra("tv_transaction_type",findViewById<TextView>(R.id.tv_transaction_type).text)
        intent.putExtra("id",findViewById<TextView>(R.id.hidden_label).text)
        startActivity(intent)
        finish()
    }

}