package com.example.madproject.Adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madproject.R
import com.example.madproject.models.Stock

class StockAdapter(var mList: List<Stock>) :
    RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    inner class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val stock_name : TextView = itemView.findViewById(R.id.tv_stock_name)
        val quantity : TextView = itemView.findViewById(R.id.tv_quantity)
        val currently_held : TextView = itemView.findViewById(R.id.tv_currently_held)
        val value : TextView = itemView.findViewById(R.id.tv_value)
        val total_value : TextView = itemView.findViewById(R.id.tv_total_value)
        val transaction_type : TextView = itemView.findViewById(R.id.tv_transaction_type)
        val id :TextView=itemView.findViewById(R.id.hidden_label)

    }

    fun setFilteredList(mList: List<Stock>){
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_investment_item , parent , false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.stock_name.text=(mList[position].stock_name)
        holder.value.text=(mList[position].value.toString())
        holder.total_value.text=(mList[position].total_value.toString())
        holder.currently_held.text=(mList[position].currently_held.toString())
        holder.transaction_type.text=(mList[position].buyorsell)
        holder.quantity.text=(mList[position].quantity.toString())
        holder.id.text=(mList[position].id.toString())
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}