import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Calendario : AppCompatActivity() {
    private lateinit var datePicker: DatePicker
    private lateinit var editTextName: EditText
    private lateinit var textViewCitas: TextView
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)

        datePicker = findViewById(R.id.datePicker)
        editTextName = findViewById(R.id.editTextName)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)
        textViewCitas = findViewById(R.id.textViewCitas)

        buttonAdd.setOnClickListener {
            addCita()
        }

        buttonDelete.setOnClickListener {
            deleteCita()
        }

        viewCitas()
    }

    private fun addCita() {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(CitasContract.CitasEntry.COLUMN_DATE, getDateFromDatePicker())
            put(CitasContract.CitasEntry.COLUMN_NAME, editTextName.text.toString())
        }

        val newRowId = db?.insert(CitasContract.CitasEntry.TABLE_NAME, null, values)

        editTextName.setText("")
        viewCitas()
    }

    private fun deleteCita() {
        val db = dbHelper.writableDatabase
        val selection = "${CitasContract.CitasEntry.COLUMN_DATE} = ?"
        val selectionArgs = arrayOf(getDateFromDatePicker())

        val deletedRows = db.delete(CitasContract.CitasEntry.TABLE_NAME, selection, selectionArgs)

        viewCitas()
    }

    private fun getDateFromDatePicker(): String {
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1 // El índice de los meses empieza desde 0
        val year = datePicker.year
        return "$year-$month-$day"
    }

    private fun viewCitas() {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            CitasContract.CitasEntry._ID,
            CitasContract.CitasEntry.COLUMN_DATE,
            CitasContract.CitasEntry.COLUMN_NAME
        )

        val cursor: Cursor = db.query(
            CitasContract.CitasEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val citas = StringBuilder()
        while (cursor.moveToNext()) {
            val date = cursor.getString(cursor.getColumnIndexOrThrow(CitasContract.CitasEntry.COLUMN_DATE))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(CitasContract.CitasEntry.COLUMN_NAME))
            citas.append("Fecha: $date, Cliente: $name\n")
        }
        cursor.close()

        textViewCitas.text = citas.toString()
    }
}
