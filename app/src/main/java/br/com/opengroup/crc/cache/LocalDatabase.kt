package br.com.opengroup.crc.cache

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class LocalDatabase(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "localDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "UserEmail"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_EMAIL TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun saveEmail(email: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_EMAIL, email)
        }

        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_ID), null, null, null, null, null)
        if (cursor.moveToFirst()) {
            db.update(
                TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf(
                    cursor.getString(
                        cursor.getColumnIndexOrThrow(
                            COLUMN_ID
                        )
                    )
                )
            )
        } else {
            db.insert(TABLE_NAME, null, contentValues)
        }
        cursor.close()
        db.close()
    }

    fun clearEmail() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun getEmail(): String? {
        val db = this.readableDatabase
        val cursor = db.query(TABLE_NAME, arrayOf(COLUMN_EMAIL), null, null, null, null, null)
        var email: String? = null
        if (cursor.moveToFirst()) {
            email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
        }
        cursor.close()
        db.close()
        return email
    }
}