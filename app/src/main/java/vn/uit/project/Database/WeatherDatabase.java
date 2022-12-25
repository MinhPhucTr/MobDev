package vn.uit.project.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WeatherDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weatherManager";
    private static final String TABLE_WEATHER = "weather";
    private static final String KEY_ASSETNAME = "asset_name";
    private static final String KEY_TEMP = "temperature";
    private static final String KEY_HUM = "humidity";
    private static final String KEY_AIR = "air";
    private static final String KEY_ID= "id";
    public WeatherDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_WEATHER + " ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ASSETNAME + " TEXT NOT NULL UNIQUE,"
                + KEY_TEMP + " TEXT NOT NULL,"
                + KEY_HUM + " TEXT NOT NULL,"
                + KEY_AIR + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);
        onCreate(sqLiteDatabase);
    }

    @SuppressLint("Range")
    public boolean isAssetExisted(String assetName)
    {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_WEATHER, null);
        if(mCursor != null)
        {
            while (mCursor.moveToNext())
            {
                String nameRoot = mCursor.getString(mCursor.getColumnIndex(KEY_ASSETNAME));
                if(assetName.equals(nameRoot))
                    return true;
            }
        }
        return false;
    }

    public void addAsset(String assetName, String temp, String humi, String air)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ASSETNAME, assetName);
        contentValues.put(KEY_TEMP, temp);
        contentValues.put(KEY_HUM, humi);
        contentValues.put(KEY_AIR, air);
        sqLiteDatabase.insert(TABLE_WEATHER, null, contentValues);
    }

    public void updateData(String assetName, String temp, String humi, String air)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TEMP, temp);
        contentValues.put(KEY_HUM, humi);
        contentValues.put(KEY_AIR, air);
        sqLiteDatabase.update(TABLE_WEATHER, contentValues, KEY_ASSETNAME + " = '"+assetName+"'", null);
    }

    @SuppressLint("Range")
    public String getTempDetails(String assetName)
    {
        String result = "";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT " + KEY_TEMP + " FROM " + TABLE_WEATHER + " WHERE " + KEY_ASSETNAME + " = '"+assetName+"'", null);
        if(mCursor.moveToFirst())
            result = mCursor.getString(mCursor.getColumnIndex(KEY_TEMP));
        return result;
    }

    @SuppressLint("Range")
    public String getHumiDetails(String assetName)
    {
        String result = "";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT " + KEY_HUM + " FROM " + TABLE_WEATHER + " WHERE " + KEY_ASSETNAME + " = '"+assetName+"'", null);
        if(mCursor.moveToFirst())
            result = mCursor.getString(mCursor.getColumnIndex(KEY_HUM));
        return result;
    }

    @SuppressLint("Range")
    public String getAirDetails(String assetName)
    {
        String result = "";
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT " + KEY_AIR + " FROM " + TABLE_WEATHER + " WHERE " + KEY_ASSETNAME + " = '"+assetName+"'", null);
        if(mCursor.moveToFirst())
            result = mCursor.getString(mCursor.getColumnIndex(KEY_AIR));
        return result;
    }

    public void resetTable()
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        onUpgrade(sqLiteDatabase, 1, 1);
    }

    @SuppressLint("Range")
    public List<String> getAllAssetName()
    {
        List<String> listName = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor mCursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_WEATHER, null);
        while (mCursor.moveToNext())
            listName.add(mCursor.getString(mCursor.getColumnIndex(KEY_ASSETNAME)));
        return listName;
    }
}
