package com.example.appointment.adaptor

import android.app.Activity
import android.app.LauncherActivity
import android.content.ContentProvider
import android.content.ContentProviderClient
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.R
import com.example.appointment.fragment.AppointmentListFragment
import com.example.appointment.network.User
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import kotlinx.android.synthetic.main.appointment_list_item.view.*
import kotlinx.android.synthetic.main.fragment_appointment_list.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class AppointmentListAdapter (val appointList : List<String?>,
                              val listner:AppointmentClickListener,
                              val formated:String): RecyclerView.Adapter<AppointmentListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentListViewHolder {
        return AppointmentListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.appointment_list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return appointList.size
    }

    override fun onBindViewHolder(holder: AppointmentListViewHolder, position: Int) {
        holder.nameList.text = appointList[position]

        holder.wholeView.setOnClickListener {
            val db = FirebaseFirestore.getInstance();
            val tokenRef = db.collection("doctor").document(formated).collection("Token");
            val token = appointList[position]
            tokenRef.document(token!!)
                .get().addOnSuccessListener { document ->

                    val user = document.toObject(User::class.java)

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

