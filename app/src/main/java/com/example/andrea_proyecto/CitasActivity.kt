package com.example.andrea_proyecto

import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class CitasActivity : AppCompatActivity() {

    private lateinit var listViewCitas: ListView
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        listViewCitas = findViewById(R.id.listViewCitas)

        val dbHelper = Calendario.DBHelper(this)
        database = dbHelper.readableDatabase

        val citas = getCitas()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, citas)
        listViewCitas.adapter = adapter
    }


    @SuppressLint("Range")
    private fun getCitas(): ArrayList<String> {
        val citas = ArrayList<String>()
        val cursor: Cursor = database.rawQuery("SELECT * FROM ${Calendario.DBHelper.TABLE_NAME}", null)
        if (cursor.moveToFirst()) {
            do {
                val cita = cursor.getString(cursor.getColumnIndex(Calendario.DBHelper.COLUMN_CITA))
                citas.add(cita)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return citas
    }

    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }
}
