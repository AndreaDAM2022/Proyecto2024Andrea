package com.example.andrea_proyecto

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VerCitasActivity : AppCompatActivity() {

    private lateinit var listViewCitas: ListView
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas)

        listViewCitas = findViewById(R.id.listViewCitas)

        val dbHelper = DBHelper(this)
        database = dbHelper.readableDatabase

        mostrarCitas()
    }

    private fun mostrarCitas() {
        val cursor: Cursor = database.query(
            DBHelper.TABLE_NAME,
            arrayOf(DBHelper.COLUMN_CITA),
            null,
            null,
            null,
            null,
            null
        )

        val citasList = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val cita = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CITA))
            citasList.add(cita)
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, citasList)
        listViewCitas.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }
}
