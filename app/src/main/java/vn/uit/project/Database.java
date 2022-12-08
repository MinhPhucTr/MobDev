package vn.uit.project;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "accountsManager";
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TEM = "temp_by_date";

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNTS
                + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_USERNAME + " TEXT, " + KEY_PASSWORD + " TEXT, "
                + KEY_TEM + " TEXT);";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        onCreate(sqLiteDatabase);
    }

    public void createAccount(String username, String password) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        sqLiteDatabase.insert(TABLE_ACCOUNTS, null, contentValues);
    }

    @SuppressLint("Range")
    public boolean isAccountExisted(String username) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS, null);
        String usernameRoot;
        while (cursor.moveToNext()) {
            usernameRoot = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
            if (username.equals(usernameRoot))
                return true;
        }
        return false;
    }

    @SuppressLint("Range")
    public boolean checkForSignIn(String username, String password) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ACCOUNTS, null);
        String usernameRoot, passwordRoot;
        while (cursor.moveToNext()) {
            usernameRoot = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
            Log.d("USERNAME", usernameRoot);
            passwordRoot = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            if (username.equals(usernameRoot) && password.equals(passwordRoot))
                return true;
        }
        return false;
    }

    public void deleteTable() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        onUpgrade(sqLiteDatabase, 1, 1);
    }

    public void addColumn() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_ACCOUNTS + " ADD COLUMN " + KEY_TEM + " TEXT");
    }

    public void updateTempByDate(String username, String temp_by_date) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TEM, temp_by_date);
        sqLiteDatabase.update(TABLE_ACCOUNTS, contentValues, KEY_USERNAME + " = '" + username + "'", null);
    }

    @SuppressLint("Range")
    public String getTempByDate(String username) {
        String query = "SELECT * FROM " + TABLE_ACCOUNTS;
        String tempByDate = "";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor mCursor = null;
        if (sqLiteDatabase != null) {
            mCursor = sqLiteDatabase.rawQuery(query, null);
        }
        while (mCursor.moveToNext()) {
            String usernameRoot = mCursor.getString(mCursor.getColumnIndex(KEY_USERNAME));
            if (username.equals(usernameRoot)) {
                tempByDate = mCursor.getString(mCursor.getColumnIndex(KEY_TEM));
                break;
            }
        }
        return tempByDate;
    }
}
