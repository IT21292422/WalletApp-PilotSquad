package com.example.project_bank.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_bank.R
import com.example.project_bank.models.bankTransactionData

class bkTransactAdapter(private val context: Context, private val dataList:List<bankTransactionData>):
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.transact_view,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text = dataList[position].id
        holder.amount.text = dataList[position].amount.toString()
        holder.description.text = dataList[position].description
        holder.type.text = dataList[position].type
    }
}

class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var tableLayout: TableLayout
    var tableRow: TableRow
    var id: TextView
    var amount: TextView
    var description: TextView
    var type: TextView

    init{
        tableLayout = itemView.findViewById(R.id.tableLayout)
        tableRow = itemView.findViewById(R.id.tableRow)
        id = itemView.findViewById(R.id.tableId)
        amount = itemView.findViewById(R.id.tableAmount)
        description = itemView.findViewById(R.id.tableDescription)
        type = itemView.findViewById(R.id.tableType)
    }

}