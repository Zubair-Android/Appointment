package com.example.appointment.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.R
import com.example.appointment.network.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.appointment_list_item.view.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class AppointmentListAdapter (val appointList : ArrayList<String>,
                              val listner:AppointmentClickListener,
                              val formated:String): RecyclerView.Adapter<AppointmentListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentListViewHolder {
        return AppointmentListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.appointment_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return appointList.size
    }

      override fun onBindViewHolder(holder: AppointmentListViewHolder, position: Int){
        holder.nameList.text = appointList[position]

        holder.wholeView.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val tokenRef = db.collection("doctor").document(formated).collection("Token");
            val token = appointList.get(position)
            tokenRef.document(token)
                .get().addOnSuccessListener { document ->

                    val data = document.data ?: return@addOnSuccessListener
                   val userData =  data[token] as HashMap<String, Any>

                    val mobile = userData["mobile"] as String

                    val name = userData["name"] as String

                    val token = userData["token"] as String

                    val user = User(name, mobile, token)

                    val time = data["time"] as Timestamp


                    listner.onDocumentClicked(user!!)
                    Log.d(TAG, "Appointment token is ${document.id} data is ${document.data} ")
                }

            }

        }

}
class AppointmentListViewHolder(view: View): RecyclerView.ViewHolder(view){

    val nameList = view.tv_tokenNumber

    val wholeView = view.whole_view


}

interface AppointmentClickListener{
    fun onDocumentClicked(user: User)
}

