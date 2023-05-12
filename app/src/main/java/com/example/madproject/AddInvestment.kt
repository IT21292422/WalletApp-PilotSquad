package com.example.madproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.madproject.models.Stock
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat

class AddInvestment : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var mEditTextStockName: EditText
    private lateinit var mEditTextValue: EditText
    private lateinit var mEditTextCurrentlyHeld: EditText
    private lateinit var mRadioGroupBuySell: RadioGroup
    private lateinit var mRadioButtonBuy: RadioButton
    private lateinit var mRadioButtonSell: RadioButton
    private lateinit var mEditTextQuantity: EditText
    private lateinit var mEditTextTotalValue: EditText
    private lateinit var databaseRef: DatabaseReference
    var childCount: Long? = 0;
    var userName = ""
    private lateinit var stock:Stock
    private var tran= ""
    val childValues: MutableLiveData<List<String>> by lazy{
        MutableLiveData<List<String>>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_investment)

        mEditTextStockName = findViewById(R.id.edit_text_stock_name)
        mEditTextValue = findViewById(R.id.edit_text_value)
        mEditTextCurrentlyHeld = findViewById(R.id.edit_text_currently_held)
        mRadioGroupBuySell = findViewById(R.id.radio_group_buy_sell)
        mRadioButtonBuy = findViewById(R.id.radio_button_buy)
        mRadioButtonSell = findViewById(R.id.radio_button_sell)
        mEditTextQuantity = findViewById(R.id.edit_text_quantity)
        mEditTextTotalValue = findViewById(R.id.edit_text_total_value)
        mEditTextTotalValue.isEnabled = false
        userName = intent.getStringExtra("userName").toString()
        databaseRef = FirebaseDatabase.getInstance().getReference("StockTransaction").child(userName)
        database = Firebase.database.reference



        findViewById<View>(R.id.button_clear_all).setOnClickListener {
            clearForm()
        }

        findViewById<View>(R.id.button_submit).setOnClickListener {
            submitForm()
        }
    }
        private fun clearForm() {
            mEditTextStockName.setText("")
            mEditTextValue.setText("")
            mEditTextCurrentlyHeld.setText("")
            mRadioGroupBuySell.clearCheck()
            mEditTextQuantity.setText("")
            mEditTextTotalValue.setText("")
        }

        private fun submitForm() {
            val stockName = mEditTextStockName.text.toString()
            val valueString = mEditTextValue.text.toString()
            val currentlyHeldString = mEditTextCurrentlyHeld.text.toString()
            val selectedBuySellId = mRadioGroupBuySell.checkedRadioButtonId
            val quantityString = mEditTextQuantity.text.toString()

            if (stockName.isEmpty() || valueString.isEmpty() || currentlyHeldString.isEmpty()
                || selectedBuySellId == -1 || quantityString.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return
            }

            val value = valueString.toDouble()
            val currentlyHeld = currentlyHeldString.toInt()
            val quantity = quantityString.toInt()
            val totalValue = value * quantity

            tran = if (selectedBuySellId == mRadioButtonBuy.id) {
                mEditTextCurrentlyHeld.setText((currentlyHeld + quantity).toString())
                "BUY"
            } else {
                mEditTextCurrentlyHeld.setText((currentlyHeld - quantity).toString())
                "SELL"
            }

            mEditTextTotalValue.setText(DecimalFormat("#.##").format(totalValue))
             stock = Stock(stockName, value,currentlyHeld,tran, quantity, totalValue)
            updateDatabaseWithStockTransaction(userName)
        }

    private fun updateDatabaseWithStockTransaction(userName: String) {
        try {
            val snapshot = databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var childCount = 0
                    for (childSnapshot in snapshot.children) {
                        childCount += childSnapshot.childrenCount.toInt()
                    }
                    childCount = (childCount / 7) + 1
                    Log.d("TEST", "Child Count: $childCount")
                    stock.id=childCount
                    database.child("StockTransaction").child(userName).child(childCount.toString()).setValue(stock)
                    updateUIWithDataFromDatabase(userName)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TEST", "Failed to read value.", error.toException())
                }
            })
        } catch (e: Exception) {
            Log.e("TEST", "Error: ${e.message}")
        }
    }

    fun goback(view: View){
        val intent = Intent(this, InvestmentList::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }
    fun updateUIWithDataFromDatabase(userName: String) {
        // Retrieve the updated data from the database
        database.child("StockTransaction").child(userName).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Process the retrieved data and update the UI accordingly
                val intent = Intent(this@AddInvestment, InvestmentList::class.java)
                intent.putExtra("userName", userName)
                startActivity(intent)
                finish()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("TEST", "Failed to read value.", error.toException())
            }
        })
    }

        private fun readDataFromDatabase() {
            databaseRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val childValueList = mutableListOf<String>()
                    for (data in snapshot.children) {
//                        val key = data.key
//                        val value = data.value as String
                        childValueList.add(data.value.toString())
                        Log.d("TEST", data.value.toString())

                    }
                    childValues.postValue(childValueList)
                    Log.d("TEST",childValues.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TEST","Failed to read value.", error.toException())
                }
            })
        }


}
