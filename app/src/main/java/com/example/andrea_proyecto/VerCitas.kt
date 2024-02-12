package com.example.andrea_proyecto

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class VerCitas : AppCompatActivity() {

    private lateinit var listViewCitas: ListView
    private lateinit var dbHelper: Calendario.DBHelper
    private lateinit var citasAdapter: ArrayAdapter<String>
    var Volver = findViewById<Button>(R.id.buttonVolver)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas)

        listViewCitas = findViewById(R.id.listViewCitas)
        dbHelper = Calendario.DBHelper(this)

        cargarCitas()

        listViewCitas.setOnItemClickListener { _, _, position, _ ->
            eliminarCita(position)
        }
    }

    private fun cargarCitas() {
        val citasList = ArrayList<String>()

        val db = dbHelper.readableDatabase
        val cursor: Cursor? = db.query(
            Calendario.DBHelper.TABLE_NAME,
            arrayOf(Calendario.DBHelper.COLUMN_CITA),
            null,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val cita = it.getString(it.getColumnIndexOrThrow(Calendario.DBHelper.COLUMN_CITA))
                citasList.add(cita)
            }
        }

        citasAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, citasList)
        listViewCitas.adapter = citasAdapter
    }

    private fun eliminarCita(position: Int) {
        val cita = listViewCitas.getItemAtPosition(position) as String

        val db = dbHelper.writableDatabase
        val selection = "${Calendario.DBHelper.COLUMN_CITA} = ?"
        val selectionArgs = arrayOf(cita)
        val deletedRows = db.delete(Calendario.DBHelper.TABLE_NAME, selection, selectionArgs)

        if (deletedRows > 0) {
            Toast.makeText(this, "Cita eliminada correctamente", Toast.LENGTH_SHORT).show()
            cargarCitas()
        } else {
            Toast.makeText(this, "No se pudo eliminar la cita", Toast.LENGTH_SHORT).show()
        }

        Volver.setOnClickListener () {
            val intent = Intent(this@VerCitas, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
