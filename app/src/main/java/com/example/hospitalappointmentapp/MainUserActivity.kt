package com.example.hospitalappointmentapp

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hospitalappointmentapp.Database.BookingDBHandler
import com.example.hospitalappointmentapp.Database.BookingDBOperations

class MainUserActivity : AppCompatActivity() {
    private var GoToAddAppointmentButton: Button? = null
    private var deleteAppointmentButton: Button? = null
    private var ViewAllAppointmentsButton: Button? = null

    private var bookingOps: BookingDBOperations? = null


    val EXTRA_APPOINTMENT_ID: String = "com.example.AId"

    val EXTRA_ADD_UPDATE: String = "com.example.add_update"


    val TAG: String = "Appointment Exits"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)

        GoToAddAppointmentButton = findViewById<View>(R.id.button_go_to_add_appointment) as Button
        deleteAppointmentButton = findViewById<View>(R.id.button_remove_appointment) as Button
        ViewAllAppointmentsButton = findViewById<View>(R.id.button_view_all_appointments) as Button


        deleteAppointmentButton!!.setOnClickListener { getAppointmentIDAndRemoveAppointment() }
    }

    fun check_existance(appoint_ID: String): Boolean {
        val db: SQLiteOpenHelper = BookingDBHandler(this)
        val database = db.writableDatabase

        val select = "SELECT * FROM appointments WHERE appointmentID =$appoint_ID"

        val c = database.rawQuery(select, null)

        if (c!!.moveToNext()) {
            Log.d(TAG, "Appointment Exists")
            return true
        }
        if (c != null) {
            c.close()
        }

        database.close()
        return false
    }

    fun goToAddAppointment(view: View?) {
        GoToAddAppointmentButton!!.setOnClickListener {
            val i: Intent =
                Intent(this@MainUserActivity, AddAppointmentActivity::class.java)
            i.putExtra(EXTRA_ADD_UPDATE, "Add")
            startActivity(i)
        }
    }


    fun goToViewAllAppointments(view: View?) {
        ViewAllAppointmentsButton!!.setOnClickListener {
            val i = Intent(
                this@MainUserActivity,
                ViewAllAppointments::class.java
            )
            startActivity(i)
        }
    }

    fun getAppointmentIDAndUpdateAppointment() {
        val li = LayoutInflater.from(this)
        val getAppointmentIdView = li.inflate(R.layout.dialog_get_appointment_id, null)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(getAppointmentIdView)

        val userInput = getAppointmentIdView.findViewById<View>(R.id.editTextDialogUserInput) as EditText

        alertDialogBuilder.setCancelable(false).setPositiveButton(
            "OK"
        ) { dialog, id ->
            if (userInput.text.toString().isEmpty()) {
                Toast.makeText(this@MainUserActivity, "User Input is Invalid", Toast.LENGTH_LONG).show()
            } else {
                if (check_existance(userInput.text.toString()) == true) {
                    val i: Intent = Intent(
                        this@MainUserActivity,
                        AddAppointmentActivity::class.java
                    )
                    i.putExtra(EXTRA_ADD_UPDATE, "Update")
                    i.putExtra(EXTRA_APPOINTMENT_ID, userInput.text.toString().toLong())
                    startActivity(i)
                } else {
                    Toast.makeText(this@MainUserActivity, "Input is invalid", Toast.LENGTH_SHORT).show()
                }
            }
        }.create().show()
    }

    fun getAppointmentIDAndRemoveAppointment() {
        val li = LayoutInflater.from(this)
        val getAppointmentIdView = li.inflate(R.layout.dialog_get_appointment_id, null)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(getAppointmentIdView)

        val userInput = getAppointmentIdView.findViewById<View>(R.id.editTextDialogUserInput) as EditText

        alertDialogBuilder.setCancelable(false).setPositiveButton(
            "OK"
        ) { dialog, id ->
            if (userInput.text.toString().isEmpty()) {
                Toast.makeText(this@MainUserActivity, "User Input is Invalid", Toast.LENGTH_LONG).show()
            } else {
                if (check_existance(userInput.text.toString()) == true) {
                    bookingOps!!.removeAppointment(bookingOps!!.getAppointment(userInput.text.toString().toLong()))
                    Toast.makeText(
                        this@MainUserActivity,
                        "Appointment has been canceled successfully",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this@MainUserActivity, "Input is invalid", Toast.LENGTH_SHORT).show()
                }
            }
        }.create().show()
    }


    override fun onResume() {
        super.onResume()
        bookingOps = BookingDBOperations(this@MainUserActivity)
        bookingOps!!.open()
    }

    override fun onPause() {
        super.onPause()
        bookingOps!!.close()
    }

}