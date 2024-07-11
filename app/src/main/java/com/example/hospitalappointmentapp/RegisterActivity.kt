package com.example.hospitalappointmentapp


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.hospitalappointmentapp.Database.RegisterUserDBHelper
import com.example.hospitalappointmentapp.Model.User

class RegisterActivity : AppCompatActivity() {
    private var firstNameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var confirmPasswordEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var phoneNumberEditText: EditText? = null

    private var addUserRegisterButton: Button? = null

    var registerUserDBHelper: RegisterUserDBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        registerUserDBHelper = RegisterUserDBHelper(this)

        firstNameEditText = findViewById<View>(R.id.edit_text_first_name) as EditText
        lastNameEditText = findViewById<View>(R.id.edit_text_last_name) as EditText
        passwordEditText = findViewById<View>(R.id.edit_text_password_register) as EditText
        confirmPasswordEditText = findViewById<View>(R.id.edit_text_password_confirm) as EditText
        emailEditText = findViewById<View>(R.id.edit_text_email) as EditText
        phoneNumberEditText = findViewById<View>(R.id.edit_text_phone_register) as EditText

        addUserRegisterButton = findViewById<View>(R.id.button_register_add_user) as Button

        addUserRegisterButton!!.setOnClickListener {
            if (firstNameEditText!!.text.toString().isEmpty() || lastNameEditText!!.text.toString()
                    .isEmpty() || passwordEditText!!.text.toString()
                    .isEmpty() || confirmPasswordEditText!!.text.toString().isEmpty() || emailEditText!!.text.toString()
                    .isEmpty() || phoneNumberEditText!!.text.toString().isEmpty()
            ) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Field or fields can not be empty",
                    Toast.LENGTH_LONG
                ).show()
            } else if ((passwordEditText!!.text.toString()
                    .matches(confirmPasswordEditText!!.text.toString().toRegex()))
            ) {
                val firstName = firstNameEditText!!.text.toString()
                val lastName = lastNameEditText!!.text.toString()
                val password = passwordEditText!!.text.toString()
                val email = emailEditText!!.text.toString()
                val phoneNumber = phoneNumberEditText!!.text.toString()

                if (!registerUserDBHelper!!.isPhoneExists(phoneNumber)) {
                    registerUserDBHelper!!.addUser(User(null, firstName, lastName, password, email, phoneNumber))
                    Toast.makeText(this@RegisterActivity, "Register Successful", Toast.LENGTH_LONG).show()
                    val i = Intent(
                        this@RegisterActivity,
                        MainMenuActivity::class.java
                    )
                    startActivity(i)
                } else {
                    Toast.makeText(this@RegisterActivity, "User Already Exists !", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(this@RegisterActivity, "Passwords do not match !", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}