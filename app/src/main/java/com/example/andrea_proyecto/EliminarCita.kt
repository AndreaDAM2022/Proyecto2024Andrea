package com.example.andrea_proyecto

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EliminarCita : AppCompatActivity() {

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
                Calendario.DBHelper.COLUMN_DATE,
                Calendario.DBHelper.COLUMN_CITA,
                Calendario.DBHelper.COLUMN_USUARIO
            ),
            null,
            null,
            null,
            null,
            null
        )

        val citasList = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val columnIndexUsuario = cursor.getColumnIndex(Calendario.DBHelper.COLUMN_USUARIO)
            if (columnIndexUsuario != -1) {
                val usuario = cursor.getString(columnIndexUsuario)
                val columnIndexDate = cursor.getColumnIndex(Calendario.DBHelper.COLUMN_DATE)
                val columnIndexCita = cursor.getColumnIndex(Calendario.DBHelper.COLUMN_CITA)
                if (columnIndexDate != -1 && columnIndexCita != -1) {
                    val date = cursor.getLong(columnIndexDate)
                    val cita = cursor.getString(columnIndexCita)
                    val formattedDate = obtenerFechaFormateada(date)
                    citasList.add("Usuario: $usuario - Fecha: $formattedDate - Cita: $cita")
                }
            }
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, citasList)
        listViewCitas.adapter = adapter
    }

    private fun obtenerFechaFormateada(date: Long): String {
        // Implementa aquí la lógica para formatear la fecha según tus necesidades
        return date.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }
}
