package com.example.andrea_proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var guestButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        guestButton = findViewById(R.id.guestButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (username.isNotBlank() && password.isNotBlank()) {
                // Aquí podrías realizar la autenticación con el nombre de usuario y contraseña
                // Aquí deberías realizar la autenticación con tu lógica de base de datos o autenticación
                // Si la autenticación es exitosa, podrías abrir la siguiente actividad o realizar otra acción
                iniciarSesion(username)
            } else {
                Toast.makeText(this, "Por favor, ingrese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            // Acción para pasar a la actividad de registro
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        guestButton.setOnClickListener {
            // Si el usuario hace clic en "Ingresar como Invitado", se inicia sesión como invitado
            // Esto podría simplemente abrir la actividad principal del calendario sin autenticación
            // o con una cuenta de invitado predeterminada
            // Aquí podrías abrir la siguiente actividad o realizar otra acción como invitado
            iniciarSesion("Invitado")
        }
    }

    private fun iniciarSesion(username: String) {
        // Aquí abrirías la actividad principal, pasando el nombre de usuario como extra si es necesario
        val intent = Intent(this, Calendario::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish() // Opcional, dependiendo de si deseas que el usuario pueda volver a esta actividad con el botón de retroceso
    }
}
