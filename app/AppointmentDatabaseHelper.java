import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

class AppointmentDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object {
private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "Appointments.db"
private const val TABLE_APPOINTMENTS = "appointments"
private const val COLUMN_ID = "id"
private const val COLUMN_DATE = "date"
        }

        override fun onCreate(db: SQLiteDatabase) {
        val createTableSQL = ("CREATE TABLE $TABLE_APPOINTMENTS ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_DATE INTEGER)")
        db.execSQL(createTableSQL)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_APPOINTMENTS")
        onCreate(db)
        }

        fun insertAppointment(date: Date): Long {
        val values = ContentValues()
        values.put(COLUMN_DATE, date.time)

        val db = this.writableDatabase
        val id = db.insert(TABLE_APPOINTMENTS, null, values)
        db.close()
        return id
        }
        }
