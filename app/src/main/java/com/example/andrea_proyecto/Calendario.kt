package com.example.andrea_proyecto

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

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
            val usuario = obtenerNombreUsuario()

            if (cita.isNotBlank()) {
                if (esHoraDisponible(date)) {
                    guardarCita(date, cita, usuario)
                    editTextCita.text.clear()
                    Toast.makeText(this, "Cita guardada correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "La hora seleccionada ya está ocupada, por favor elija otra", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, introduce una cita", Toast.LENGTH_SHORT).show()
            }
        }


        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            val selectedDate = cal.timeInMillis

            // Pasar la fecha seleccionada a la actividad VerCitas
            val intent = Intent(this@Calendario, VerCitas::class.java)
            intent.putExtra("fecha_seleccionada", selectedDate)
            startActivity(intent)
        }
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            val selectedDate = cal.timeInMillis

            // Abrir la actividad VerCitasDia y pasar la fecha seleccionada
            val intent = Intent(this@Calendario, VerCitasDia::class.java)
            intent.putExtra("fecha_seleccionada", selectedDate)
            startActivity(intent)
        }

        val buttonVolver = findViewById<Button>(R.id.buttonVolver)

        buttonVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    private fun esHoraDisponible(date: Long): Boolean {
        val dbHelper = DBHelper(this)
        val database = dbHelper.readableDatabase

        val query = "SELECT COUNT(*) FROM ${DBHelper.TABLE_NAME} WHERE ${DBHelper.COLUMN_DATE} = ?"
        val cursor = database.rawQuery(query, arrayOf(date.toString()))

        var count = 0
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0)
            }
            cursor.close()
        }

        database.close()

        // Si count es mayor que 0, significa que hay citas para esa hora
        return count == 0
    }

    private fun guardarCita(date: Long, cita: String, usuario: String) {
        val values = ContentValues().apply {
            put(DBHelper.COLUMN_DATE, date) // Almacena la fecha en milisegundos
            put(DBHelper.COLUMN_CITA, cita)
            put(DBHelper.COLUMN_USUARIO, usuario) // Almacena el nombre del usuario
        }
        database.insert(DBHelper.TABLE_NAME, null, values)

        // Pasar la fecha seleccionada y el nombre del usuario a la actividad VerCitas
        val intent = Intent(this, VerCitas::class.java)
        intent.putExtra("fecha_seleccionada", date) // Pasar la fecha en milisegundos
        intent.putExtra("nombre_usuario", usuario) // Pasar el nombre del usuario
        startActivity(intent)
    }
    private fun obtenerNombreUsuario(): String {
        val editTextNombreUsuario = findViewById<EditText>(R.id.editTextNombreUsuario)
        return editTextNombreUsuario.text.toString()
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
                    $COLUMN_DATE INTEGER,
                    $COLUMN_CITA TEXT,
                    $COLUMN_USUARIO TEXT
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
            const val COLUMN_USUARIO = "usuario" // Definición de la constante COLUMN_USUARIO
        }
    }
}
