package com.example.andrea_proyecto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

                val editTextUsername1 = findViewById<EditText>(R.id.User1)
                val editTextPassword2 = findViewById<EditText>(R.id.Password1)
                val btnLogin = findViewById<Button>(R.id.button1)

                btnLogin.setOnClickListener {
                    val username = editTextUsername1.text.toString()
                    val password = editTextPassword2.text.toString()

                    // Verificar las credenciales (en este ejemplo, usuario: "user", contraseña: "password")

                    if (username == "Andrea" && password == "QAZpnm123@") {

                        // Credenciales correctas, iniciar la actividad del formulario

                        val intent = Intent(
                            this@MainActivity,
                            Calendario::class.java
                        )
                        startActivity(intent)
                    } else {
                        // Credenciales incorrectas, mostrar un mensaje de error
                        // (en una aplicación real, debes manejar las credenciales de forma segura)
                        showMessage("Credenciales incorrectas. Inténtalo de nuevo.")
                    }
                }
            }

            private fun showMessage(message: String) {
                // Puedes mostrar el mensaje en un Toast, Snackbar, o cualquier otro componente de la interfaz de usuario
              Toast.makeText(this, "Usuario y Contraseña no válidos", Toast.LENGTH_SHORT).show();
            }
        }





















