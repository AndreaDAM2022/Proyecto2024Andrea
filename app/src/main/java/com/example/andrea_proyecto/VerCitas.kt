package com.example.andrea_proyecto

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class VerCitas : AppCompatActivity() {

    private lateinit var listViewCitas: ListView
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas)

        listViewCitas = findViewById(R.id.listViewCitas)

        val dbHelper = Calendario.DBHelper(this)
        database = dbHelper.readableDatabase

        mostrarCitas()
    }

    private fun mostrarCitas() {
        val cursor: Cursor = database.query(
            Calendario.DBHelper.TABLE_NAME,
            arrayOf(
                Calendario.DBHelper.COLUMN_ID,
                Calendario.DBHelper.COLUMN_DATE,
                Calendario.DBHelper.COLUMN_CITA

            ),
            null,
            null,
            null,
            null,
            null
        )

        val citasList = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val columnIndexUsuario = cursor.getColumnIndex(Calendario.DBHelper.COLUMN_ID)
            val columnIndexDate = cursor.getColumnIndex(Calendario.DBHelper.COLUMN_DATE)
            val columnIndexCita = cursor.getColumnIndex(Calendario.DBHelper.COLUMN_CITA)

            if (columnIndexUsuario != -1 && columnIndexDate != -1 && columnIndexCita != -1) {
                val id = cursor.getString(columnIndexUsuario)
                val date = cursor.getLong(columnIndexDate)
                val cita = cursor.getString(columnIndexCita)
                val formattedDate = obtenerFechaFormateada(date)
                citasList.add("ID CLIENTE: $id - Fecha: $formattedDate - Cita: $cita")
            }
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, citasList)
        listViewCitas.adapter = adapter

        val buttonVolver = findViewById<Button>(R.id.buttonVolver)

        buttonVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonEliminar = findViewById<Button>(R.id.buttonVolver)

        buttonEliminar.setOnClickListener {
            val intent = Intent(this, EliminarCita::class.java)
            startActivity(intent)
        }
    }


    private fun obtenerFechaFormateada(date: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())
        val formattedDate = sdf.format(Date(date))
        return formattedDate
    }
}
