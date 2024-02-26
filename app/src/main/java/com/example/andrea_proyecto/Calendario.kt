package com.example.andrea_proyecto

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class Calendario : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var editTextCita: EditText
    private lateinit var buttonGuardar: Button
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario)

        calendarView = findViewById(R.id.calendarView)
        editTextCita = findViewById(R.id.editTextCita)
        buttonGuardar = findViewById(R.id.buttonGuardar)

        val dbHelper = DBHelper(this)
        database = dbHelper.writableDatabase

        buttonGuardar.setOnClickListener {
            val date = calendarView.date
            val cita = editTextCita.text.toString()
            if (cita.isNotBlank()) {
                if (verificarDisponibilidad(date)) {
                    guardarCita(date, cita)
                    editTextCita.text.clear()
                    Toast.makeText(this, "Cita guardada correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "La hora seleccionada ya está ocupada", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, introduce una cita", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun guardarCita(date: Long, cita: String) {
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_DATE, date)
            put(DBHelper.COLUMN_CITA, cita)
        }
        database.insert(DBHelper.TABLE_NAME, null, values)
    }

    private fun verificarDisponibilidad(date: Long): Boolean {
        val readableDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(date))
        val cursor: Cursor = database.rawQuery("SELECT * FROM ${DBHelper.TABLE_NAME} WHERE ${DBHelper.COLUMN_DATE} = ?", arrayOf(readableDate))
        val count = cursor.count
        cursor.close() // Cerrar el cursor después de usarlo
        return count == 0
    }


    override fun onDestroy() {
        super.onDestroy()
        database.close()
    }

    class DBHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            val createTableSQL = """
                CREATE TABLE $TABLE_NAME (
                    $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_DATE TEXT,
                    $COLUMN_CITA TEXT
                )
            """.trimIndent()
            db.execSQL(createTableSQL)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }

        companion object {
            const val DATABASE_NAME = "citas_db"
            const val DATABASE_VERSION = 1
            const val TABLE_NAME = "citas"
            const val COLUMN_ID = "id"
            const val COLUMN_DATE = "fecha"
            const val COLUMN_CITA = "cita"
        }
    }
}
