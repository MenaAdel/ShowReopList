package com.example.menaadel.zadtask.caching;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.menaadel.zadtask.caching.TableData.TableInfo;
import com.example.menaadel.zadtask.data.model.Repository;

/**
 * Created by MenaAdel on 1/17/2018.
 */

public class DatabaseOperations extends SQLiteOpenHelper {

    public static final int DatabaseVersion=1;
    public String Query = "create table if not exists "+ TableInfo.TABLENAME + "("
            +TableInfo.NAME + " text,"
            +TableInfo.DESCRIPTION + " text,"
            +TableInfo.OWNERhtml_url + " text,"
            +TableInfo.html_url + " text,"
            +TableInfo.full_name + " text,"
            +TableInfo.fork + " text);";

    public DatabaseOperations(Context context){
        super(context, TableInfo.DATABASENAME, null, DatabaseVersion);
        Log.d("Database Operation>>>>","Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Query);
        Log.d("Database Operation>>>>","Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /*
    *Insert new row
     */
    public void INSERT(Repository repository){
        SQLiteDatabase sq=this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableInfo.NAME,repository.getRepo_name());
        cv.put(TableInfo.DESCRIPTION,repository.getDescription());
        cv.put(TableInfo.OWNERhtml_url,repository.getOwner().getHtml_url());
        cv.put(TableInfo.html_url,repository.getHtml_url());
        cv.put(TableInfo.full_name,repository.getUsername());
        cv.put(TableInfo.fork,repository.isFork());
        long k=sq.insert(TableInfo.TABLENAME,null,cv);
        Log.d("Database operation>>>>","One row inserted");
    }

    /*
    * select from sqlite
     */
    public Cursor SELECT(DatabaseOperations dbop){
        String query="SELECT * FROM "+TableInfo.TABLENAME+";";
        SQLiteDatabase sq=dbop.getReadableDatabase();
        Cursor cr=sq.rawQuery(query,null);
        if(cr!=null)
            cr.moveToFirst();
        return cr;
    }
}
