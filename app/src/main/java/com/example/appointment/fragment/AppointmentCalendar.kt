package com.example.appointment.fragment

import android.content.Context
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.os.bundleOf
import androidx.core.view.get
import com.example.appointment.databinding.FragmentAppointmentCalendarBinding
import androidx.navigation.fragment.findNavController
import com.example.appointment.R
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_appointment_calendar.*
import kotlinx.coroutines.selects.select
import java.text.Format
import java.text.SimpleDateFormat
import java.time.*
import java.util.*

class AppointmentCalendar : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val calanderBinding = FragmentAppointmentCalendarBinding.inflate(inflater)
        calanderBinding.calendarView.setOnDateChangeListener(CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "" + year + "." + (month + 1) + "." + dayOfMonth

            val selectYear  = year
            val selectMonth = month
            val selectDay = dayOfMonth


           val dbCalendar = FirebaseFirestore.getInstance().collection("doctor").document(selectedDate).collection("Token")
                dbCalendar.get().addOnSuccessListener {
                    val TokenSize=it.documents.size
                    Log.d(TAG,"this date ${selectedDate} document size is ${TokenSize}")
                    Toast.makeText(context,"this date ${selectedDate} document size is ${TokenSize}",Toast.LENGTH_LONG).show()
                 }


                    val bundle = bundleOf("selectedDate" to selectedDate,"selectYear" to selectYear,"selectMonth" to selectMonth,"selectDay" to selectDay)
                    findNavController().navigate(R.id.action_appointmentCalendar_to_appointmentListFragment,bundle)

        })

        return  calanderBinding.linearLayoutAppointmentCalender
    }

}
