package com.example.andrea_proyecto

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Formulario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

                val editTextName: EditText = findViewById(R.id.editTextName)
                val editTextEmail: EditText = findViewById(R.id.editTextEmail)
                val editTextPhone: EditText = findViewById(R.id.editTextPhone)
                val spinnerGender: Spinner = findViewById(R.id.spinnerGender)
                val checkBoxSubscribe: CheckBox = findViewById(R.id.checkBoxSubscribe)
                val btnSubmit: Button = findViewById(R.id.btnSubmit)

                // Configurar el adaptador para el spinner con las opciones de género
                val genderAdapter = ArrayAdapter.createFromResource(
                    this,
                    R.array.gender_array,
                    android.R.layout.simple_spinner_item
                )
                genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerGender.adapter = genderAdapter

                btnSubmit.setOnClickListener {
                    val name = editTextName.text.toString()
                    val email = editTextEmail.text.toString()
                    val phone = editTextPhone.text.toString()
                    val gender = spinnerGender.selectedItem.toString()
                    val subscribeToNewsletter = checkBoxSubscribe.isChecked

                    if (validateInputs(name, email, phone)) {
                        // Aquí puedes realizar la lógica de envío de datos (por ejemplo, a un servidor)
                        // Puedes agregar más lógica según tus requisitos
                        showMessage("Datos enviados:\nNombre: $name\nCorreo: $email\nTeléfono: $phone\nGénero: $gender\nSuscripción al boletín: $subscribeToNewsletter")
                    }
                }
            }

            private fun validateInputs(name: String, email: String, phone: String): Boolean {
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    showMessage("Por favor, completa todos los campos.")
                    return false
                }
                return true
            }

            private fun showMessage(message: String) {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            }
}
