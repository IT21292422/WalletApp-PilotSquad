package com.example.madproject.Adapters


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.models.bankTransactionData
import com.example.madproject.update_transaction

class bkTransactAdapter(private val context: Context, private var dataList:List<bankTransactionData>, private val myUser: String, private val bank: String):
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.transact_view,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun searchDataList(searchList: List<bankTransactionData>){
        dataList = searchList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val darkGreen = Color.parseColor("#43A047")
        val transactType = dataList[position].type
        if (transactType == "Debit") {
            (holder.type as TextView).setTextColor(Color.RED)
        } else if (transactType == "Credit") {
            (holder.type as TextView).setTextColor(darkGreen)
        } else {
            // Set default text color for other cases
            (holder.type as TextView).setTextColor(Color.BLACK)
        }
        holder.id.text = dataList[position].id
        holder.amount.text = dataList[position].amount.toString()
        holder.description.text = dataList[position].description
        holder.type.text = dataList[position].type
        holder.id.setOnClickListener{
            val intent = Intent(context, update_transaction::class.java)
            intent.putExtra("user", myUser)
            intent.putExtra("bank", bank)
            intent.putExtra("tid", dataList[position].id)
            context.startActivity(intent)
        }
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