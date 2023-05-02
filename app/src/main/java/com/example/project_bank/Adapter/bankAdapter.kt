package com.example.project_bank.Adapter

import android.content.Context
import android.content.Intent
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_bank.R
import com.example.project_bank.models.bankData
import com.example.project_bank.transaction_view

class bankAdapter(private val context: Context, private val dataList:List<bankData>):
    RecyclerView.Adapter<BankViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.bank_view,parent,false)
        return BankViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        holder.accNo.text = dataList[position].accNo
        holder.name.text = dataList[position].name
        holder.bal.text = dataList[position].balance.toString()
        holder.name.setOnClickListener{
            val intent = Intent(context, transaction_view::class.java)
            intent.putExtra("name", dataList[position].name)
            intent.putExtra("bal", dataList[position].balance)
            context.startActivity(intent)
        }
        //holder.description.text = dataList[position].description
        //holder.type.text = dataList[position].type
    }
}

class BankViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var tableLayout: TableLayout
    var tableRow: TableRow
    var accNo: TextView
    var name: TextView
    var bal: TextView
    //var description: TextView
    //var type: TextView

    init{
        tableLayout = itemView.findViewById(R.id.bankLayout)
        tableRow = itemView.findViewById(R.id.bankRow)
        accNo = itemView.findViewById(R.id.accNo)
        name = itemView.findViewById(R.id.bankName)
        bal = itemView.findViewById(R.id.bankBal)
        //description = itemView.findViewById(R.id.tableDescription)
        //type = itemView.findViewById(R.id.tableType)
    }

}
