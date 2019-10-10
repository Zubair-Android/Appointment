package com.example.appointment.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.appointment.R
import com.example.appointment.databinding.AppointmentListItemBinding
import com.example.appointment.viewmodel.AppointmentListViewModel


class AppointmentListFragment : Fragment() {
    private val appointmentListViewModel: AppointmentListViewModel by lazy {
        ViewModelProviders.of(this).get(AppointmentListViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: AppointmentListItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.appointment_list_item, container, false)
        binding.lifeCyclerOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}
