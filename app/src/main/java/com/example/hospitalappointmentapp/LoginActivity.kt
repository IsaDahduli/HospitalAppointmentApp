package com.example.hospitalappointmentapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hospitalappointmentapp.Database.RegisterUserDBHelper
import com.example.hospitalappointmentapp.Model.User

class LoginActivity : AppCompatActivity() {
    private var phoneNumberLoginEditText: EditText? = null
    private var passwordLoginEditText: EditText? = null

    private var LoginUserButton: Button? = null

    var registerUserDBHelper: RegisterUserDBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        registerUserDBHelper = RegisterUserDBHelper(this)

        phoneNumberLoginEditText = findViewById<View>(R.id.edit_text_phone_login) as EditText
        passwordLoginEditText = findViewById<View>(R.id.edit_text_password_login) as EditText

        LoginUserButton = findViewById<View>(R.id.button_login_enter_user) as Button

        LoginUserButton!!.setOnClickListener {
            if (phoneNumberLoginEditText!!.text.toString().isEmpty() || passwordLoginEditText!!.text.toString()
                    .isEmpty()
            ) {
                Toast.makeText(this@LoginActivity, "Wrong Login", Toast.LENGTH_LONG).show()
            } else {
                val PhoneNum = phoneNumberLoginEditText!!.text.toString()
                val Password = passwordLoginEditText!!.text.toString()

                val currentUser: User =
                    registerUserDBHelper!!.Authenticate(User(null, null, null, Password, null, PhoneNum))

                if (currentUser != null) {
                    val i = Intent(
                        this@LoginActivity,
                        MainUserActivity::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(this@LoginActivity, "Wrong Login", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}