package com.example.andrea_proyecto

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var registrarButton: Button
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        nombreEditText = findViewById(R.id.nombreEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        registrarButton = findViewById(R.id.registrarButton)

        databaseHelper = DatabaseHelper(this)

        registrarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (nombre.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                if (insertarCliente(nombre, email, password)) {
                    Toast.makeText(this, "Cliente registrado correctamente", Toast.LENGTH_SHORT).show()
                    // Aquí podrías realizar alguna acción adicional, como abrir otra actividad
                    finish()
                } else {
                    Toast.makeText(this, "Error al registrar el cliente", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        var Volver = findViewById<Button>(R.id.volverButton)
        Volver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


    private fun insertarCliente(nombre: String, email: String, password: String): Boolean {
        val db = databaseHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NOMBRE, nombre)
            put(DatabaseHelper.COLUMN_EMAIL, email)
            put(DatabaseHelper.COLUMN_PASSWORD, password)
        }
        val result = db.insert(DatabaseHelper.TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    // Clase interna para la gestión de la base de datos SQLite
    class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            val createTableSQL = """
                CREATE TABLE $TABLE_NAME (
                    $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_NOMBRE TEXT,
                    $COLUMN_EMAIL TEXT,
                    $COLUMN_PASSWORD TEXT
                )
            """.trimIndent()
            db.execSQL(createTableSQL)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

        companion object {
            const val DATABASE_NAME = "clientes_db"
            const val DATABASE_VERSION = 1
            const val TABLE_NAME = "clientes"
            const val COLUMN_ID = "id"
            const val COLUMN_NOMBRE = "nombre"
            const val COLUMN_EMAIL = "email"
            const val COLUMN_PASSWORD = "password"
        }
    }
}
