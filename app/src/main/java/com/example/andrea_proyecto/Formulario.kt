package com.example.andrea_proyecto

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class Formulario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        private lateinit var viewModel: MainViewModel

        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextEmail: EditText = findViewById(R.id.editTextEmail)
        val editTextPhone: EditText = findViewById(R.id.editTextPhone)
        val spinnerServices: Spinner = findViewById(R.id.spinnerServices)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        // Configurar el adaptador para el spinner con los servicios de uñas
        val servicesAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.services_array,
            android.R.layout.simple_spinner_item
        )
        servicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerServices.adapter = servicesAdapter

        btnRegister.setOnClickListener {
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val phone = editTextPhone.text.toString()
            val selectedService = spinnerServices.selectedItem.toString()

            if (validateInputs(name, email, phone)) {
                val client = Client(name, email, phone, selectedService)
                if (viewModel.registerClient(client)) {
                    // Registro exitoso, puedes mostrar un mensaje de éxito
                    showMessage("Cliente registrado exitosamente.")
                } else {
                    // Fallo en el registro, puedes mostrar un mensaje de error
                    showMessage("Error al registrar al cliente. Inténtalo de nuevo.")
                }
            }
        }
    }

    private fun validateInputs(name: String, email: String, phone: String): Boolean {
        // Aquí puedes agregar validaciones adicionales según tus requisitos
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showMessage("Por favor, completa todos los campos.")
            return false
        }

        return true
    }

    private fun showMessage(message: String) {
        // Puedes mostrar el mensaje en un Toast, Snackbar, o cualquier otro componente de la interfaz de usuario
        // Por ejemplo, Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}