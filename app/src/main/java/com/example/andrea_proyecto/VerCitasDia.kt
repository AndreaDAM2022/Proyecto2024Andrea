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

class VerCitasDia : AppCompatActivity() {

    private lateinit var listViewCitas: ListView
    private lateinit var database: SQLiteDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas_dia)

        val buttonVolver = findViewById<Button>(R.id.buttonVolver)
        buttonVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val buttonVolverCalendario = findViewById<Button>(R.id.buttonVolverCalendario)

        buttonVolverCalendario.setOnClickListener {
            val intent = Intent(this, Calendario::class.java)
            startActivity(intent)
        }

        listViewCitas = findViewById(R.id.listViewCitas)
        val dbHelper = Calendario.DBHelper(this)
        database = dbHelper.readableDatabase

        // Obtener la fecha seleccionada del intent
        val selectedDate = intent.getLongExtra("fecha_seleccionada", 0)

        // Obtener todas las citas para la fecha seleccionada
        val citas = obtenerCitasPorFecha(selectedDate)
        if (citas.isNotEmpty()) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, citas)
            listViewCitas.adapter = adapter
        } else {
            Toast.makeText(this, "No hay citas para esta fecha", Toast.LENGTH_SHORT).show()
        }
    }



    private fun obtenerCitasPorFecha(selectedDate: Long): List<String> {
        val citas = mutableListOf<String>()
        val cursor = database.query(
            Calendario.DBHelper.TABLE_NAME,
            arrayOf(Calendario.DBHelper.COLUMN_CITA),
            "${Calendario.DBHelper.COLUMN_DATE} = ?",
            arrayOf(selectedDate.toString()),
            null,
            null,
            null
        )
        while (cursor.moveToNext()) {
            val cita = cursor.getString(cursor.getColumnIndexOrThrow(Calendario.DBHelper.COLUMN_CITA))
            citas.add(cita)
        }
        cursor.close()
        return citas
    }
}
