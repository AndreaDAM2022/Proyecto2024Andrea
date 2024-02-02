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

        val username = findViewById<EditText>(R.id.editTextUsername)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val butnRegistro  = findViewById<Button>(R.id.buttonRegister)

        butnRegistro.setOnClickListener(){
            if (username.text.toString() == password.text.toString()){ //El "toString" lo utilizamos para poder acceder mediante números y caracteres
                val intent = Intent(this@MainActivity, Formulario::class.java)
                startActivity(intent)
            } else{
                Toast.makeText(this, "Usuario y Contraseña no válidos", Toast.LENGTH_LONG).show()
            }
        }
    }
}