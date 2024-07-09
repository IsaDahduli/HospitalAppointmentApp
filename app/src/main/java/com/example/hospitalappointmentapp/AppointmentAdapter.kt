package com.example.hospitalappointmentapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.example.hospitalappointmentapp.Model.Appointment

class AppointmentAdapter(context: Context, employees: List<Appointment?>) : ArrayAdapter<Appointment>(context, 0, employees) {
    @NonNull
    override fun getView(position: Int, @Nullable convertView: View?, @NonNull parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.simple_list_item_1, parent, false)
        }

        val currentAppointment = getItem(position)

        val textView = listItemView!!.findViewById<TextView>(android.R.id.text1)
        textView.text = currentAppointment!!.doctor + " " + currentAppointment.department + " " + "Appointment ID = " +  currentAppointment!!.appointmentID + " " + "Date: " + currentAppointment.date + " " + "Time: " + currentAppointment.time

        return listItemView
    }
}
