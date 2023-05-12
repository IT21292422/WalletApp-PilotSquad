package com.example.madproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.madproject.models.Stock
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.DecimalFormat

class UpdateInvestment :AppCompatActivity(){

    private lateinit var database: DatabaseReference
    private lateinit var mEditTextStockName: EditText
    private lateinit var mEditTextValue: EditText
    private lateinit var mEditTextCurrentlyHeld: EditText
    private lateinit var mRadioGroupBuySell: RadioGroup
    private lateinit var mRadioButtonBuy: RadioButton
    private lateinit var mRadioButtonSell: RadioButton
    private lateinit var mEditTextQuantity: EditText
    private lateinit var mEditTextTotalValue: EditText
    private lateinit var stock: Stock
    var userName = ""
    var tran = ""
    var id =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_investment_)
        var stockName = intent.getStringExtra("tv_stock_name").toString()
        id = intent.getStringExtra(("id").toString()).toString()
        Log.d("STOCKNAME", "$stockName $id")
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
        database = Firebase.database.reference
        val extras = intent.extras ?: return
        findViewById<TextView>(R.id.edit_text_stock_name).text = extras.getString("tv_stock_name")
        findViewById<TextView>(R.id.edit_text_value).text = extras.getString("tv_value")
        findViewById<TextView>(R.id.edit_text_quantity).text = extras.getString("tv_quantity")
        findViewById<TextView>(R.id.edit_text_total_value).text = extras.getString("tv_total_value")
        findViewById<TextView>(R.id.edit_text_currently_held).text = extras.getString("tv_currently_held")
        val transactionType = extras.getString("tv_transaction_type")
        findViewById<RadioGroup>(R.id.radio_group_buy_sell).apply {
            when (transactionType) {
                "BUY" -> check(R.id.radio_button_buy)
                "SELL" -> check(R.id.radio_button_sell)
            }
        }
        findViewById<View>(R.id.button_update).setOnClickListener {
            updateForm()
        }
        findViewById<View>(R.id.button_delete).setOnClickListener {
            deleteForm()
        }

    }
    private fun updateForm() {
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
        stock = Stock(stockName, value,currentlyHeld,tran, quantity, totalValue,id.toInt())
        database.child("StockTransaction").child(userName).child(id).setValue(stock)
        val intent = Intent(this, InvestmentList::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }

    private fun deleteForm() {
        database.child("StockTransaction").child(userName).child(id).removeValue()
        val intent = Intent(this, InvestmentList::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }
    fun goback(view: View){
        val intent = Intent(this, InvestmentList::class.java)
        intent.putExtra("userName", userName)
        startActivity(intent)
        finish()
    }
}