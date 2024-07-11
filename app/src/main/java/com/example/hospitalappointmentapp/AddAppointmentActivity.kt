package com.example.hospitalappointmentapp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.hospitalappointmentapp.Database.BookingDBOperations
import com.example.hospitalappointmentapp.Model.Appointment
import java.text.SimpleDateFormat
import java.util.*

class AddAppointmentActivity : AppCompatActivity()
{
    val EXTRA_APPOINTMENT_ID: String = "com.example.AId"

    val EXTRA_ADD_UPDATE: String = "com.example.add_update"

    val DIALOG_DATE: String = "DialogDate"

    private var calendarImage: ImageView? = null

    private var AppointmentDate: EditText? = null

    private var DepartmentsEditText: EditText? = null
    private var DoctorsEditText: EditText? = null
    private var TimesEditText: EditText? = null


    private var newAppointment: Appointment? = null
    private var oldAppointment: Appointment? = null
    private var mode: String? = null
    private var appId: Long = 0
    private var bookingData: BookingDBOperations? = null
    private var addUpdateButton: Button? = null

    val appointmentCalendar: Calendar = Calendar.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_appointment)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);



        newAppointment = Appointment()
        oldAppointment = Appointment()
        calendarImage = findViewById<View>(R.id.image_view_date) as ImageView
        addUpdateButton = findViewById<View>(R.id.button_add_appointment) as Button
        AppointmentDate = findViewById<View>(R.id.edit_text_appointment_date) as EditText
        DepartmentsEditText = findViewById<View>(R.id.edit_text_departments) as EditText
        DoctorsEditText = findViewById<View>(R.id.edit_text_doctors) as EditText
        TimesEditText = findViewById<View>(R.id.edit_text_times) as EditText

        bookingData = BookingDBOperations(this)
        bookingData!!.open()


        val adapterDepartments = ArrayAdapter.createFromResource(this, R.array.departments, R.layout.spinner_item)
        adapterDepartments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val departments = findViewById<View>(R.id.spinner_departments) as Spinner
        departments.adapter = adapterDepartments
        departments.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val departmentsText = parent.getItemAtPosition(position).toString()
                DepartmentsEditText!!.setText(departmentsText)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }


        val adapterDoctors = ArrayAdapter.createFromResource(this, R.array.doctors, R.layout.spinner_item)
        adapterDoctors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val doctors = findViewById<View>(R.id.spinner_doctors) as Spinner
        doctors.adapter = adapterDoctors
        doctors.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val doctorsText = parent.getItemAtPosition(position).toString()
                DoctorsEditText!!.setText(doctorsText)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }


        val adapterTimes = ArrayAdapter.createFromResource(this, R.array.times, R.layout.spinner_item)
        adapterTimes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val times = findViewById<View>(R.id.spinner_times) as Spinner
        times.adapter = adapterTimes
        times.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val timesText = parent.getItemAtPosition(position).toString()
                TimesEditText!!.setText(timesText)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }
        }



        mode = intent.getStringExtra(EXTRA_ADD_UPDATE)
        if (mode == "Update") {
            addUpdateButton!!.text = "Update Appointment"
            appId = intent.getLongExtra(EXTRA_APPOINTMENT_ID, 0)
            initializeAppointment(appId)
        }

        addUpdateButton!!.setOnClickListener {
            if (mode == "Add") {
                newAppointment!!.department = DepartmentsEditText!!.text.toString()
                newAppointment!!.doctor = DoctorsEditText!!.text.toString()
                newAppointment!!.date = AppointmentDate!!.text.toString()
                newAppointment!!.time = TimesEditText!!.text.toString()

                if (AppointmentDate!!.text.toString().isEmpty()) {
                    Toast.makeText(this@AddAppointmentActivity, "Select a date please", Toast.LENGTH_LONG).show()
                } else {
                    bookingData!!.addAppointment(newAppointment)
                    Toast.makeText(this@AddAppointmentActivity, "Appointment succssful", Toast.LENGTH_LONG).show()
                    val i: Intent = Intent(
                        this@AddAppointmentActivity,
                        MainUserActivity::class.java
                    )
                    startActivity(i)
                }
            } else {
                oldAppointment!!.department = DepartmentsEditText!!.text.toString()
                oldAppointment!!.doctor = DoctorsEditText!!.text.toString()
                oldAppointment!!.date = AppointmentDate!!.text.toString()
                oldAppointment!!.time = TimesEditText!!.text.toString()

                if (AppointmentDate!!.text.toString().isEmpty()) {
                    Toast.makeText(this@AddAppointmentActivity, "Select a date please", Toast.LENGTH_LONG).show()
                } else {
                    bookingData!!.updateAppointment(oldAppointment)
                    Toast.makeText(this@AddAppointmentActivity, "Select a date please", Toast.LENGTH_LONG).show()
                    val i: Intent = Intent(
                        this@AddAppointmentActivity,
                        MainUserActivity::class.java
                    )
                    startActivity(i)
                }
            }
        }


        val date =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                appointmentCalendar[Calendar.YEAR] = year
                appointmentCalendar[Calendar.MONTH] = monthOfYear
                appointmentCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel()
            }

        calendarImage!!.setOnClickListener {
            DatePickerDialog(
                this@AddAppointmentActivity, date,
                appointmentCalendar[Calendar.YEAR],
                appointmentCalendar[Calendar.MONTH], appointmentCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        AppointmentDate!!.setText(sdf.format(appointmentCalendar.time))
    }

    private fun initializeAppointment(appId: Long) {
        oldAppointment = bookingData!!.getAppointment(appId)

        DepartmentsEditText!!.setText(oldAppointment!!.getDepartment())
        DoctorsEditText!!.setText(oldAppointment!!.getDoctor())
        AppointmentDate!!.setText(oldAppointment!!.getDate())
        TimesEditText!!.setText(oldAppointment!!.getTime())
    }

}