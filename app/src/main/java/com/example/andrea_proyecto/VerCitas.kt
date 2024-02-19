import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.andrea_proyecto.R

class VerCitas : AppCompatActivity() {

    private lateinit var listViewCitas: ListView
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_citas)

        listViewCitas = findViewById(R.id.listViewCitas)
        dbHelper = DBHelper(this)

        mostrarCitas()
    }


    private fun mostrarCitas() {
        val citasList = ArrayList<String>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM ${DBHelper.TABLE_NAME}", null)

        val columnIndexDate = cursor.getColumnIndex(DBHelper.COLUMN_DATE)
        val columnIndexCita = cursor.getColumnIndex(DBHelper.COLUMN_CITA)

        if (columnIndexDate != -1 && columnIndexCita != -1) {
            while (cursor.moveToNext()) {
                val fecha = cursor.getLong(columnIndexDate)
                val cita = cursor.getString(columnIndexCita)
                citasList.add("Fecha: $fecha - Cita: $cita")
            }
        } else {
            // Columnas no encontradas en el cursor
            // Manejar la situación, como lanzar una excepción o imprimir un mensaje de error
        }

        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, citasList)
        listViewCitas.adapter = adapter

        if (citasList.isEmpty()) {
            Toast.makeText(this, "No hay citas para mostrar", Toast.LENGTH_SHORT).show()
        }
    }

    // Definición de la clase DBHelper
    class DBHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            val createTableSQL = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE INTEGER,
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
