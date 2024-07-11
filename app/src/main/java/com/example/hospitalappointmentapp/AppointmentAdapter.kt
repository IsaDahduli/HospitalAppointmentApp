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

class AppointmentAdapter(context: Context, appointments: List<Appointment?>) : ArrayAdapter<Appointment>(context, 0, appointments) {
    @NonNull
    override fun getView(position: Int, @Nullable convertView: View?, @NonNull parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.appointment_list_item, parent, false)
        }

        val currentAppointment = getItem(position)

        val doctorTextView = listItemView!!.findViewById<TextView>(R.id.text_view_doctor)
        val departmentTextView = listItemView.findViewById<TextView>(R.id.text_view_department)
        val appointmentIdTextView = listItemView.findViewById<TextView>(R.id.text_view_appointment_id)
        val dateTextView = listItemView.findViewById<TextView>(R.id.text_view_date)
        val timeTextView = listItemView.findViewById<TextView>(R.id.text_view_time)

        doctorTextView.text = "Doctor: ${currentAppointment!!.doctor}"
        departmentTextView.text = "Department: ${currentAppointment.department}"
        appointmentIdTextView.text = "Appointment ID: ${currentAppointment.appointmentID}"
        dateTextView.text = "Date: ${currentAppointment.date}"
        timeTextView.text = "Time: ${currentAppointment.time}"

        return listItemView
    }
}
