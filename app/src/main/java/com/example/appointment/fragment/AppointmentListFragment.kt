package com.example.appointment.fragment

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.LocaleData
import android.icu.util.TimeZone
import android.media.session.MediaSession
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import android.widget.Toast.makeText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints.TAG
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.core.util.rangeTo
import androidx.core.view.isInvisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appointment.R
import com.example.appointment.adaptor.AppointmentClickListener
import com.example.appointment.adaptor.AppointmentListAdapter
import com.example.appointment.databinding.FragmentAppointmentListBinding
import com.example.appointment.network.User
import com.google.firebase.database.collection.ArraySortedMap
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firestore.v1.StructuredQuery
import io.grpc.Server
import io.opencensus.metrics.export.Summary
import kotlinx.android.synthetic.main.appointment_list_item.*
import kotlinx.android.synthetic.main.fragment_appointment_list.*
import kotlinx.android.synthetic.main.fragment_appointmentoption.*
import kotlinx.android.synthetic.main.popupwinow.*
import java.io.DataInput
import java.lang.Exception
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*
import java.util.Collections.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.emptyList as emptyList1

class AppointmentListFragment : Fragment()  {
    private val PERMISSION_REQUEST_CODE = 1
    private var constraintLayout : ConstraintLayout ? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAppointmentListBinding.inflate(inflater)
        binding.rvAppoint.layoutManager = GridLayoutManager(context,2);
            retrivedocumentid()

        constraintLayout= constraintLayout?.findViewById<ConstraintLayout>(R.id.constraintlayoutMainActivity)

        val selectYear = arguments!!.getInt("selectYear")
        val selectMonth = arguments!!.getInt("selectMonth")
        val selectDay = arguments!!.getInt("selectDay")
        val calendar = java.util.Calendar.getInstance()
        val yearToday =  calendar.get(java.util.Calendar.YEAR)
        val monthToday =  calendar.get(java.util.Calendar.MONTH)
        val dayToday =  calendar.get(java.util.Calendar.DAY_OF_MONTH)

        if (selectYear == yearToday){
            if (selectMonth == monthToday){
                if (selectDay == dayToday){
                    binding.btInsertNewRecord.setOnClickListener(View.OnClickListener {
                        popupForm()

                    })
                }else if (selectDay > dayToday){
                    binding.btInsertNewRecord.setOnClickListener(View.OnClickListener {
                        popupForm()

                    })
                }else if (selectDay < dayToday){
                    binding.btInsertNewRecord.isInvisible = true
                }else{
                    Toast.makeText(context,"please enter valid day",Toast.LENGTH_SHORT).show()
                }
            }else if (selectMonth > monthToday){
                binding.btInsertNewRecord.setOnClickListener(View.OnClickListener {
                    popupForm()

                })
            }else if (selectMonth < monthToday){
                binding.btInsertNewRecord.isInvisible = true
            }else{
                Toast.makeText(context,"please enter valid Month",Toast.LENGTH_SHORT).show()
            }

        }else if (selectYear > yearToday){
            binding.btInsertNewRecord.setOnClickListener(View.OnClickListener {
                popupForm()

            })
        } else if (selectYear < yearToday ){
            binding.btInsertNewRecord.isInvisible = true
        }else {

            Toast.makeText(context,"please enter valid year",Toast.LENGTH_SHORT).show()
        }

        return binding.root

    }

        //========== testing phase step 1===========
    /*fun retrivedocumentid(){
            val  formated = arguments?.getString("selectedDate");
            val db = FirebaseFirestore.getInstance();
            val doctorRef = db.collection("doctor");
            val dateRef = doctorRef.document(formated.toString());

            val tokenRef = dateRef.collection("Token");



            dateRef.get().addOnSuccessListener {snap ->
                val list = ArrayList<String>()


                 snap.data?.toSortedMap()?.forEach{mapentry->
                     list.add(mapentry.key)

                }




            *//*val AppTimeFire = ""
            val list = ArrayList<String>()
                tokenRef.get().addOnSuccessListener {querySnap ->
                querySnap.documents.forEach {docSnap ->
                    list.add(docSnap.id)




                }


            }*//*

//        doctorRef.orderBy("time",Query.Direction.ASCENDING)
//            .get()
//            .addOnSuccessListener { QuerySnap->
//                val list = ArrayList<String>()
//                QuerySnap.documents.forEach { DocSnap->
//                    DocSnap.data?.forEach {fieldMap->
//                        fieldMap.key.forEach {fielkeychar->
//
//                        }
//
//
//
//                    }
//                }
//
//                Log.d(TAG,"field list is ${list}")



                *//*val sortedUsers = snap.documents.map {documents ->
                    documents.toObject(User::class.java)
                }.sortedBy {user ->
                    user?.userToken
                }.map { user ->
                    user?.token
                }*//*



//               // Log.d(TAG,"users ${sortedUsers}")
                tokenProgressbar.isInvisible = true
               // Toast.makeText(context,"documetn on = ${sortedUsers}",Toast.LENGTH_SHORT).show()


                rv_appoint.adapter = AppointmentListAdapter(list, object:AppointmentClickListener{
                    override fun onDocumentClicked(user: User) {

                        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                        val view = inflater.inflate(R.layout.viewpatientdetail,null)
                        val popupViewWindow =PopupWindow(view,600,800)
                        popupViewWindow.showAtLocation(constraintlayoutMainActivity, Gravity.CENTER,0,0)
                        val viewClose = view.findViewById<Button>(R.id.viewClose)
                        val appName = view.findViewById<TextView>(R.id.appsName)
                        val viewDate = view.findViewById<TextView>(R.id.viewDate)
                        val patientName = view.findViewById<TextView>(R.id.patientName)
                        val patientNumber = view.findViewById<TextView>(R.id.patientNumber)
                        val tokenNumber = view.findViewById<TextView>(R.id.tokenNumber)


                        appName.text = "Clinical Appointment"
                        viewDate.text = ""

                        patientName.text = user.name
                        patientNumber.text = user.mobile
                        tokenNumber.text = user.token.toString()

                        viewClose.setOnClickListener(View.OnClickListener {
                            popupViewWindow.dismiss()
                        })

                    }

                },formated!!)
                Toast.makeText(context,"Totel issued Token  ${list.size}",Toast.LENGTH_LONG).show()

            }
            .addOnFailureListener {e->
                Log.i("Appointment", "data fetching failed with ${e.message}")

                Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
            }

    }*/
    //===============end testing step 1 ===============
    //============ WOrking fine 11:52 , 8/11/19 step 1======
     /*fun retrivedocumentid(){

         formated = arguments?.getString("selectedDate")
        val tokenRef = FirebaseFirestore.getInstance()
            .collection("doctor")
            .document(formated.toString())
            .collection("Token");

        tokenRef.get()
        .addOnSuccessListener {snap->
            val list = ArrayList<String>()

            val sortedUsers = snap.documents.map {documents ->
                documents.toObject(User::class.java)
            }.sortedBy {user ->
                user?.userToken
            }.map { user ->
                user?.token
            }



            Log.i("users", sortedUsers.toString())
            tokenProgressbar.isInvisible = true


            rv_appoint.adapter = AppointmentListAdapter(sortedUsers, object:AppointmentClickListener{
                override fun onDocumentClicked(user: User) {

                    val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    val view = inflater.inflate(R.layout.viewpatientdetail,null)
                    val popupViewWindow =PopupWindow(view,600,800)
                    popupViewWindow.showAtLocation(constraintlayoutMainActivity, Gravity.CENTER,0,0)
                    val viewClose = view.findViewById<Button>(R.id.viewClose)
                    val appName = view.findViewById<TextView>(R.id.appsName)
                    val viewDate = view.findViewById<TextView>(R.id.viewDate)
                    val patientName = view.findViewById<TextView>(R.id.patientName)
                    val patientNumber = view.findViewById<TextView>(R.id.patientNumber)
                    val tokenNumber = view.findViewById<TextView>(R.id.tokenNumber)

                    appName.text = "Clinical Appointment"
                    viewDate.text = formated
                    patientName.text = user.name
                    patientNumber.text = user.mobile
                    tokenNumber.text = user.token.toString()

                    viewClose.setOnClickListener(View.OnClickListener {
                        popupViewWindow.dismiss()
                    })

                }

            },formated!!)
                Toast.makeText(context,"Totel issued Token  ${snap.documents.size}",Toast.LENGTH_LONG).show()

        }
        .addOnFailureListener {e->
            Log.i("Appointment", "data fetching failed with ${e.message}")

            Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
        }

    }
    fun sendText(Mobile : String , Name :String ){

        if (Build.VERSION.SDK_INT >= 23){
            if (checkPermission()){
                Log.e("Permission","Permission already granted")
                Toast.makeText(context, "permissson is granted already",Toast.LENGTH_SHORT).show()
            }else{
                requestPermission()
            }

        }
        val send : String = "SMS_SENT"
        val deliver :String ="SMS_DELIVERD"
        val sendPI = PendingIntent.getBroadcast(context,0, Intent(send),0)
        val deliveredPI = PendingIntent.getBroadcast(context,0, Intent(deliver),0)



                val smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(Mobile,null,Name,null,null)
                Toast.makeText(context,"message send= ${Name}",Toast.LENGTH_LONG).show()




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
    }

    fun popupForm() {
       val  formated = arguments?.getString("selectedDate");
        val db = FirebaseFirestore.getInstance();
        val doctorRef = db.collection("doctor");
        val dateRef = doctorRef.document(formated.toString());
        val tokenRef = dateRef.collection("Token");

        val inflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popupwinow, null)
        val popupWindow =
            PopupWindow(view, 750, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindow.showAtLocation(constraintlayoutMainActivity, Gravity.CENTER, 0, 0)

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
                    val timestamp = serverTimestamp();


                    tokenRef.get()
                        .addOnSuccessListener {
                            val documentSize: Int = it.documents.size
                            val user = User(Name, Mobile, documentSize.inc().toString(), timestamp)
                            tokenRef.document(documentSize.inc().toString()).set(user)
                            val patientDetail =
                                "Skin care\nToken no : ${documentSize.inc()}\n Date : ${formated}\n Patient Name : ${Name}\n Mobile : ${Mobile}\n"
                          //  sendText(Mobile, patientDetail)
                            Toast.makeText(
                                context,
                                "Successfully inserted Token # ${documentSize.inc()}",
                                Toast.LENGTH_LONG
                            ).show()
                            /*retrivedocumentid()*/

                        }

                        .addOnFailureListener { e ->


                            Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
                        }



        })

    }
    */
    //===========end step 1 =================================
    //============  start step 2 custom data put in field successfully ==========
    /*fun popupForm() {
       val  formated = arguments?.getString("selectedDate");
        val db = FirebaseFirestore.getInstance();
        val doctorRef = db.collection("doctor");
        val dateRef = doctorRef.document(formated.toString());
        val tokenRef = dateRef.collection("Token");

        val inflater =
            context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popupwinow, null)
        val popupWindow =
            PopupWindow(view, 750, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)
        popupWindow.showAtLocation(constraintlayoutMainActivity, Gravity.CENTER, 0, 0)

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
                    val timestamp = serverTimestamp();


                    dateRef.get()
                        .addOnSuccessListener {docSnap->
                            val docInc :Int
                            val documentSize = docSnap.data?.size
                            if (documentSize == null){
                                 docInc = 1
                            }else{
                                 docInc = documentSize.inc()
                            }


                            val user = User(Name, Mobile, docInc.toString(), timestamp)
                            val userHahmap = HashMap<String,Any>()
                            userHahmap.put(docInc.toString(),user)

                            doctorRef.document().set(userHahmap, SetOptions.merge())
                            popupWindow.dismiss()
                            val patientDetail =
                                "Skin care\nToken no : ${documentSize}\n Date : ${formated}\n Patient Name : ${Name}\n Mobile : ${Mobile}\n"
                          //  sendText(Mobile, patientDetail)
                            Toast.makeText(
                                context,
                                "Successfully inserted Token # ${docInc}",
                                Toast.LENGTH_LONG
                            ).show()
                            retrivedocumentid()

                        }

                        .addOnFailureListener { e ->


                            Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
                        }



        })

    }*/ // data set by custom
    //============ end step 2 custom data put in field successfully ==========

    fun retrivedocumentid(){
        val  formated = arguments?.getString("selectedDate");
        val db = FirebaseFirestore.getInstance();
        val tokenRef = db.collection("doctor").document(formated.toString()).collection("Token")

        tokenRef.orderBy("time",Query.Direction.ASCENDING)
            .get().addOnSuccessListener {
                val list = ArrayList<String>()
                it.documents.forEach {document->
                    list.add(document.id)
                }


                tokenProgressbar.isInvisible = true
                rv_appoint.adapter = AppointmentListAdapter(list, object:AppointmentClickListener{
                    override fun onDocumentClicked(user: User) {

                        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                        val view = inflater.inflate(R.layout.viewpatientdetail,null)
                        val popupViewWindow =PopupWindow(view,600,800)
                        popupViewWindow.showAtLocation(constraintlayoutMainActivity, Gravity.CENTER,0,0)
                        val viewClose = view.findViewById<Button>(R.id.viewClose)
                        val appName = view.findViewById<TextView>(R.id.appsName)
                        val viewDate = view.findViewById<TextView>(R.id.viewDate)
                        val patientName = view.findViewById<TextView>(R.id.patientName)
                        val patientNumber = view.findViewById<TextView>(R.id.patientNumber)
                        val tokenNumber = view.findViewById<TextView>(R.id.tokenNumber)


                        appName.text = "Clinical Appointment"
                        viewDate.text = formated

                        patientName.text = user.name
                        patientNumber.text = user.mobile
                        tokenNumber.text = user.token.toString()

                        viewClose.setOnClickListener(View.OnClickListener {
                            popupViewWindow.dismiss()
                        })

                    }

                },formated!!)
                Toast.makeText(context,"Totel issued Token ",Toast.LENGTH_LONG).show()


            }


    }

    fun popupForm() {
           val  formated = arguments?.getString("selectedDate");
            val db = FirebaseFirestore.getInstance();
            val doctorRef = db.collection("doctor");
            val dateRef = doctorRef.document(formated.toString());
            val tokenRef = dateRef.collection("Token");

            val inflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.popupwinow, null)
            val popupWindow =
                PopupWindow(view, 750, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)
            popupWindow.showAtLocation(constraintlayoutMainActivity, Gravity.CENTER, 0, 0)

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
                        val timestamp = serverTimestamp()


                        tokenRef.get()
                            .addOnSuccessListener {QuerySnap->
                                val docInc :Int
                                val documentSize = QuerySnap.size()
                                if (documentSize == null){
                                     docInc = 1
                                }else{
                                     docInc = documentSize.inc()
                                }


                                val user = User(Name, Mobile, docInc.toString())
                                val userHahmap = HashMap<String,Any>()
                                userHahmap.put(docInc.toString(),user)
                                userHahmap.put("time",timestamp)

                                tokenRef.document(docInc.toString()).set(userHahmap)
                                popupWindow.dismiss()

                                val patientDetail =
                                    "Skin care\nToken no : ${documentSize}\n Date : ${formated}\n Patient Name : ${Name}\n Mobile : ${Mobile}\n"
                              //  sendText(Mobile, patientDetail)
                                Toast.makeText(
                                    context,
                                    "Successfully inserted Token # ${docInc}",
                                    Toast.LENGTH_LONG
                                ).show()
                                retrivedocumentid()

                            }

                            .addOnFailureListener { e ->


                                Toast.makeText(context, "failed", Toast.LENGTH_LONG).show()
                            }



            })

        }
}





