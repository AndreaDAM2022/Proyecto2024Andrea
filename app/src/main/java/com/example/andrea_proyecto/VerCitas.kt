package com.example.andrea_proyecto

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class VerCitas : AppCompatActivity() {

    private lateinit var listViewCitas: ListView
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas)

        val buttonVolver: Button = findViewById(R.id.buttonVolver)
        buttonVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

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
            val cita = cursor.getString(with(cursor) { getColumnIndex(DBHelper.COLUMN_CITA) })
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
