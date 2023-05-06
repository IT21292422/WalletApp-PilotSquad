package com.example.madproject.Adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.models.bankData
import com.example.madproject.transaction_view
import com.example.madproject.update_bank_account
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class bankAdapter(private val context: Context, private val dataList:List<bankData>,private val myUser: String):
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
        holder.bal.text = "Rs."+dataList[position].balance.toString()+"/="
        holder.name.setOnClickListener{
            val intent = Intent(context, transaction_view::class.java)
            intent.putExtra("name", dataList[position].name)
            intent.putExtra("user", myUser)
            intent.putExtra("bal", dataList[position].balance.toString())
            context.startActivity(intent)
        }
        holder.accNo.setOnClickListener{
            val intent = Intent(context, transaction_view::class.java)
            intent.putExtra("name", dataList[position].name)
            intent.putExtra("user", myUser)
            intent.putExtra("bal", dataList[position].balance.toString())
            context.startActivity(intent)
        }
        holder.bal.setOnClickListener{
            val intent = Intent(context, transaction_view::class.java)
            intent.putExtra("name", dataList[position].name)
            intent.putExtra("user", myUser)
            intent.putExtra("bal", dataList[position].balance.toString())
            context.startActivity(intent)
        }
        holder.edit.setOnClickListener{
            val intent = Intent(context, update_bank_account::class.java)
            intent.putExtra("name", dataList[position].name)
            intent.putExtra("user", myUser)
            intent.putExtra("bal", dataList[position].balance.toString())
            context.startActivity(intent)
        }

        holder.delete.setOnClickListener{
            lateinit var databaseReference: DatabaseReference
            databaseReference = FirebaseDatabase.getInstance().getReference(myUser)
            databaseReference.child(dataList[position].name.toString()).removeValue().addOnSuccessListener {
                Toast.makeText(context,"Successfully Deleted", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context,"Unable To Delete", Toast.LENGTH_SHORT).show()
            }

        }

    }
}

class BankViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var tableLayout: TableLayout
    var tableRow: TableRow
    var accNo: TextView
    var name: TextView
    var bal: TextView
    var edit: ImageView
    var delete: ImageView
    //var description: TextView
    //var type: TextView

    init{
        tableLayout = itemView.findViewById(R.id.bankLayout)
        tableRow = itemView.findViewById(R.id.bankRow)
        accNo = itemView.findViewById(R.id.accNo)
        name = itemView.findViewById(R.id.bankName)
        bal = itemView.findViewById(R.id.bankBal)
        edit = itemView.findViewById(R.id.editBankBtn)
        delete = itemView.findViewById(R.id.deleteBankBtn)
        //description = itemView.findViewById(R.id.tableDescription)
        //type = itemView.findViewById(R.id.tableType)
    }

}
