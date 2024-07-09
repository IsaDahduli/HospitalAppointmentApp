package com.example.hospitalappointmentapp

import android.app.ListActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.hospitalappointmentapp.Database.BookingDBOperations
import com.example.hospitalappointmentapp.Model.Appointment

class ViewAllAppointments : ListActivity() {
    private var bookingOps: BookingDBOperations? = null
    private var appointments: List<Appointment?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_appointments)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bookingOps = BookingDBOperations(this)
        bookingOps!!.open()
        appointments = bookingOps!!.allAppointments
        bookingOps!!.close()

        val adapter = AppointmentAdapter(this, appointments!!)
        listAdapter = adapter
    }
}