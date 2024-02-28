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
    private lateinit var databaseHelper: RegistroActivity.DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        guestButton = findViewById(R.id.guestButton)

        databaseHelper = RegistroActivity.DatabaseHelper(this)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (username.isNotBlank() && password.isNotBlank()) {
                if (validarCredenciales(username, password)) {
                    iniciarSesion(username)
                } else {
                    Toast.makeText(this, "Credenciales inválidas. Por favor, inténtelo de nuevo o regístrese.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, ingrese nombre de usuario y contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        guestButton.setOnClickListener {
            iniciarSesion("Invitado")
        }

        var sobreNosotros = findViewById<Button>(R.id.NosotrosButton)
        sobreNosotros.setOnClickListener {
            val intent = Intent(this, SobreNosotros::class.java)
            startActivity(intent)
        }
    }

    private fun validarCredenciales(username: String, password: String): Boolean {
        val db = databaseHelper.readableDatabase

        val projection = arrayOf(RegistroActivity.DatabaseHelper.COLUMN_ID)

        val selection = "${RegistroActivity.DatabaseHelper.COLUMN_NOMBRE} = ? AND ${RegistroActivity.DatabaseHelper.COLUMN_PASSWORD} = ?"
        val selectionArgs = arrayOf(username, password)

        val cursor = db.query(
            RegistroActivity.DatabaseHelper.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        val count = cursor.count
        cursor.close()
        db.close()

        return count > 0
    }

    private fun iniciarSesion(username: String) {
        val intent = Intent(this, Calendario::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
        finish()
    }
}
