package com.example.appointment.fragment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appointment.databinding.FragmentAppointmentoptionBinding
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.appointment.R
import com.example.appointment.adaptor.AppointmentListAdapter
import com.example.appointment.network.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_appointment_calendar.*
import kotlinx.android.synthetic.main.fragment_appointment_list.*
import kotlinx.android.synthetic.main.fragment_appointmentoption.*
import java.text.SimpleDateFormat
import java.util.*


class AppointmentOption : Fragment() {
   // private val PERMISSION_REQUEST_CODE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingAO = FragmentAppointmentoptionBinding.inflate(inflater)
 /*       val formated = arguments?.getString("selectedDate")

        val tokenRef = FirebaseFirestore.getInstance().collection("doctor").document(formated.toString()).collection("Token")
        val bindingAO = FragmentAppointmentoptionBinding.inflate(inflater)

        val selectYear = arguments!!.getInt("selectYear")
        val selectMonth = arguments!!.getInt("selectMonth")
        val selectDay = arguments!!.getInt("selectDay")
        val calendar = java.util.Calendar.getInstance()
        val yearToday =  calendar.get(java.util.Calendar.YEAR)
        val monthToday =  calendar.get(java.util.Calendar.MONTH)
        val dayToday =  calendar.get(java.util.Calendar.DAY_OF_MONTH)

        //========================Compare with today date ================
    if (selectYear == yearToday){
        if (selectMonth == monthToday){
            if (selectDay == dayToday){
                bindingAO.btnBookAppointment.setOnClickListener(View.OnClickListener {
                    popupForm()
            })
            }else if (selectDay > dayToday){
                bindingAO.btnBookAppointment.setOnClickListener(View.OnClickListener {
                    popupForm()
            })
            }else if (selectDay < dayToday){
                bindingAO.btnBookAppointment.isInvisible = true
            }else{
                Toast.makeText(context,"please enter valid day",Toast.LENGTH_SHORT).show()
            }
        }else if (selectMonth > monthToday){
            bindingAO.btnBookAppointment.setOnClickListener(View.OnClickListener {
                popupForm()
        })
        }else if (selectMonth < monthToday){
            bindingAO.btnBookAppointment.isInvisible = true
        }else{
            Toast.makeText(context,"please enter valid Month",Toast.LENGTH_SHORT).show()
        }

    }else if (selectYear > yearToday){
        bindingAO.btnBookAppointment.setOnClickListener(View.OnClickListener {
            popupForm()
    })
    } else if (selectYear < yearToday ){
        bindingAO.btnBookAppointment.isInvisible = true
    }else {

        Toast.makeText(context,"please enter valid year",Toast.LENGTH_SHORT).show()
    }
    //========================= end compare date ==========================================================================
            bindingAO.btnSeeCurrentAppointment.setOnClickListener(View.OnClickListener {

                val bundle = bundleOf("formated" to formated,"selectYear" to selectYear,
                    "selectMonth" to selectMonth, "selectDay" to selectDay)

                findNavController().navigate(R.id.action_appointmentOption_to_appointmentListFragment, bundle)


            })*/

        return bindingAO.root/*constraintAppointmentOption*/
        }


        /*fun sendText(Mobile : String , Name :String ){

            if (Build.VERSION.SDK_INT >= 23){
                if (checkPermission()){
                    Log.e("Permission","Permission already granted")
                    Toast.makeText(context, "permissson is granted already",Toast.LENGTH_SHORT).show()
                }else{
                    requestPermission()
                }

            }else{
                Toast.makeText(context,"SDk version is less then 23",Toast.LENGTH_LONG).show()
            }

            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(Mobile,null,Name,null,null)

        }
        private fun requestPermission(){
            Toast.makeText(context,"request for permission",Toast.LENGTH_LONG).show()
            ActivityCompat.requestPermissions(parentFragment!!.requireActivity(), arrayOf(Manifest.permission.SEND_SMS),PERMISSION_REQUEST_CODE)
        }
        fun checkPermission():Boolean{
            val result = ContextCompat.checkSelfPermission(context!!, Manifest.permission.SEND_SMS)
            return  if (result == PackageManager.PERMISSION_GRANTED){
                true
            }else{
                false
            }
        }*/
    //==========================funtion for popup Registration form =========
   /* fun popupForm() {
        val formated = arguments?.getString("selectedDate")
        val tokenRef = FirebaseFirestore.getInstance().collection("doctor").document(formated.toString()).collection("Token")
        val inflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popupwinow, null)
        val popupWindow =
            PopupWindow(view, 750, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindow.showAtLocation(constraintAppointmentOption, Gravity.CENTER, 0, 0)

        val btnSubmit = view?.findViewById<Button>(R.id.btn_submit)
        val btnClose = view?.findViewById<Button>(R.id.btn_close)

        btnClose?.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "close btn click listener", Toast.LENGTH_LONG).show()
            popupWindow.dismiss()
        })
        btnSubmit?.setOnClickListener(View.OnClickListener {


            val nameEditText = view.findViewById<EditText>(R.id.paitnt_name_textInput)
            val numberEditText = view.findViewById<EditText>(R.id.patient_number_editText)

            val Name = nameEditText.text.toString()
            val Mobile = numberEditText.text.toString()
            val user = User(Name, Mobile)

            tokenRef.get()
                .addOnSuccessListener {
                    var i: Int = it.documents.size

                    tokenRef.document(i.inc().toString()).set(user)
                    val patientDetail =
                        "Skin care\nToken no : ${i.inc()}\n Date : ${formated}\n Patient Name : ${Name}\n Mobile : ${Mobile}\n"
                    sendText(Mobile, patientDetail)
                    Toast.makeText(
                        context,
                        "Successfully inserted Token # ${i.inc()}",
                        Toast.LENGTH_LONG
                    ).show()

                }

                .addOnFailureListener { e ->


                    Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
                }
            popupWindow.dismiss()


        })

    }*/
    //============================close ========================================

}