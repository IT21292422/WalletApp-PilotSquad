package com.example.madproject
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast
import com.example.madproject.databinding.ActivityCreateBinding
import com.example.madproject.models.CashTrans
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Create : AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("user").toString()

        binding.Back.setOnClickListener{
            val intent = Intent(this@Create,Income_List::class.java)
            intent.putExtra("user", username)
            startActivity(intent)
            finish()
        }

        binding.updateBtn.setOnClickListener{
            val cid = binding.cid.text.toString()
            val type = binding.Type.selectedItem.toString()
            val description = binding.Description.text.toString()
            val date = binding.Date.text.toString()
            val amount = binding.Amount.text.toString()


            databaseReference = FirebaseDatabase.getInstance().getReference(username).child("Cash Transaction")
            val cashTransact = CashTrans(cid,type,description,amount,date)
            databaseReference.child(type).child(cid).setValue(cashTransact).addOnSuccessListener {
                binding.Amount.text.clear()
                //binding.Type.text.clear()
                binding.Description.text.clear()
                binding.Date.text.clear()
                Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                var intent = Intent(this, Income_List::class.java)
                intent.putExtra("user", username)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,"Failure",Toast.LENGTH_SHORT).show()
            }

        }

    }

}


