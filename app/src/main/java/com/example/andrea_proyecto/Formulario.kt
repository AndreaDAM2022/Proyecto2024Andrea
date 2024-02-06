package com.example.andrea_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Formulario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

                val editTextName: EditText = findViewById(R.id.editTextName)
                val editTextEmail: EditText = findViewById(R.id.editTextEmail)
                val editTextPhone: EditText = findViewById(R.id.editTextPhone)
                val btnSubmit: Button = findViewById(R.id.btnSubmit)
                val btnVolver: Button = findViewById(R.id.btnVolver)

                btnSubmit.setOnClickListener {
                    val name = editTextName.text.toString()
                    val email = editTextEmail.text.toString()
                    val phone = editTextPhone.text.toString()

                    // Puedes realizar acciones con los datos ingresados aquí, por ejemplo, enviarlos a un servidor
                    // En este ejemplo, simplemente mostraremos un mensaje con la información ingresada
                    showMessage("Nombre: $name\nCorreo: $email\nTeléfono: $phone")
                }

        btnVolver.setOnClickListener () {
            val intent = Intent(this@Formulario, MainActivity::class.java)
            startActivity(intent)
        }
            }

            private fun showMessage(message: String) {
                // Puedes mostrar el mensaje en un Toast, Snackbar, o cualquier otro componente de la interfaz de usuario
                Toast.makeText(this, "Usuario y Contraseña no válidos", Toast.LENGTH_SHORT).show()
            }
        }

