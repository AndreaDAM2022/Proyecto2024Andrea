package com.example.andrea_proyecto

import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VerCitas : AppCompatActivity() {

    private lateinit var listViewCitas: ListView
    private lateinit var dbHelper: Calendario.DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas)

        listViewCitas = findViewById(R.id.listViewCitas)
        dbHelper = Calendario.DBHelper(this)

        val citasList = getCitasFromDatabase()
        if (citasList.isNotEmpty()) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, citasList)
            listViewCitas.adapter = adapter
        } else {
            Toast.makeText(this, "No hay citas guardadas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCitasFromDatabase(): ArrayList<String> {
        val citasList = ArrayList<String>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${Calendario.DBHelper.TABLE_NAME}", null)

        if (cursor.moveToFirst()) {
            do {
                val fechaIndex = cursor.getColumnIndexOrThrow(Calendario.DBHelper.COLUMN_DATE) - 1
                val citaIndex = cursor.getColumnIndexOrThrow(Calendario.DBHelper.COLUMN_CITA) - 1
                val fecha = cursor.getLong(fechaIndex)
                val cita = cursor.getString(citaIndex)
                val citaFormatted = "Fecha: $fecha - Cita: $cita"
                citasList.add(citaFormatted)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return citasList
    }
}
