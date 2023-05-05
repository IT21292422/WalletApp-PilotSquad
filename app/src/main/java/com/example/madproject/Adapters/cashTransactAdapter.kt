package com.example.madproject.Adapters

import android.content.Context
import android.content.Intent
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.Update
import com.example.madproject.models.CashTrans
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class cashTransactAdapter(private val context: Context, private val dataList:List<CashTrans>):
    RecyclerView.Adapter<CashViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cash_transact_view,parent,false)
        return CashViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: CashViewHolder, position: Int) {
        holder.name.text = dataList[position].description
        holder.amount.text = dataList[position].amount
        holder.date.text = dataList[position].date
        holder.delete.setOnClickListener{
            Toast.makeText(context,dataList[position].cid.toString(), Toast.LENGTH_SHORT).show()
            if(dataList[position].type=="Income"){
                deleteIncome(dataList[position].cid.toString())
            }else if(dataList[position].type=="Expense"){
                deleteExpense(dataList[position].cid.toString())
            }
        }
        holder.edit.setOnClickListener{
            val intent =Intent(context, Update::class.java)
            intent.putExtra("id",dataList[position].cid)
            intent.putExtra("type",dataList[position].type)
            context.startActivity(intent)
        }

    }
}

class CashViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var name: TextView
    var amount: TextView
    var date: TextView
    var delete: Button
    var edit: Button

    init{
        amount = itemView.findViewById(R.id.viewAmount)
        name = itemView.findViewById(R.id.viewName)
        date = itemView.findViewById(R.id.viewDate)
        delete = itemView.findViewById(R.id.deleteBtn)
        edit = itemView.findViewById(R.id.EditBtn)

    }

}

fun deleteIncome(id: String){
    var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Mayuran").child("Cash Transaction").child("Income")
    databaseReference.child(id).removeValue().addOnSuccessListener {
        //Toast.makeText("Successfully Deleted", Toast.LENGTH_SHORT).show()
    }.addOnFailureListener {
        //Toast.makeText("Failure", Toast.LENGTH_SHORT).show()
    }
}

fun deleteExpense(id: String){
    var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("Mayuran").child("Cash Transaction").child("Expense")
    databaseReference.child(id).removeValue().addOnSuccessListener {
        //Toast.makeText("Successfully Deleted", Toast.LENGTH_SHORT).show()
    }.addOnFailureListener {
        //Toast.makeText("Failure", Toast.LENGTH_SHORT).show()
    }
}

