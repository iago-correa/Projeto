package com.example.iago.myapplication2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "pokemonDB.db";

    public static final String CHAR_TABLE_NAME = "personagens";
    public static final String CHAR_COLUMN_ID = "id";
    public static final String CHAR_COLUMN_NAME = "nome";
    public static final String CHAR_COLUMN_FOR = "força";
    public static final String CHAR_COLUMN_DES = "destreza";
    public static final String CHAR_COLUMN_CON = "constituição";
    public static final String CHAR_COLUMN_INT = "inteligência";
    public static final String CHAR_COLUMN_SAB = "sabedoria";
    public static final String CHAR_COLUMN_CAR = "carisma";

    public static final String CLASS_TABLE_NAME = "classes";
    public static final String CLASS_COLUMN_ID = "classid";
    public static final String CLASS_COLUMN_NAME = "classnome";
    public static final String CLASS_TABLE_BBA = "bbaclasse";
    public static final String CLASS_TABLE_PVS = "pvsclasse";
    public static final String CLASS_TABLE_PERÍCIAS = "perclasse";
    public static final String CLASS_TABLE_PRESTÍGIO = "prestígio";
    public static final String CLASS_TABLE_NÍVEL = "nívelmax";
    public static final String CLASS_TABLE_ORIGEM = "classorigem";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table personagens " +
                        "(id integer primary key, nome text, força integer, destreza integer, constituição integer, inteligência integer, sabedoria integer, carisma integer)"
        );
        db.execSQL(
                "create table classes " +
                        "(classid integer primary key, classnome text, classenome text, bbaclasse integer, pvsclasse integer, perclasse integer, prestígio boolean, nívelmax integer, classorigem text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS personagens");
        db.execSQL("DROP TABLE IF EXISTS classes");
        onCreate(db);
    }

    public boolean insertPersonagem (String name, int força, int destreza, int constituição, int inteligência, int sabedoria, int carisma){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("força", força);
        contentValues.put("destreza", destreza);
        contentValues.put("constituição", constituição);
        contentValues.put("inteligência", inteligência);
        contentValues.put("sabedoria", sabedoria);
        contentValues.put("carisma", carisma);
        db.insert("personagens", null, contentValues);
        return true;
    }

    public boolean insertClasse(String name, int bba, int pvs, int pericias, boolean prestígio, int nivelmax, String origem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("bbaclasse",bba);
        contentValues.put("pvsclasse",pvs);
        contentValues.put("perclasse",pericias);
        contentValues.put("prestígio",prestígio);
        contentValues.put("nívelmax",nivelmax);
        contentValues.put("classorigem",origem);
        db.insert("classes", null, contentValues);
        return true;
    }

    public Cursor getData(String table, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        if  (table == "personagem")
            res =  db.rawQuery( "select * from personagens where id="+id+"", null );
        else if  (table == "classe")
            res =  db.rawQuery( "select * from classes where id="+id+"", null );
        return res;
    }

    public int mod (String atributo, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from personagens where id="+id+"", null );
        Integer value;
        if (atributo == "For")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_FOR));
        else if (atributo == "Des")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_DES));
        else if (atributo == "Con")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CON));
        else if (atributo == "Int")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_INT));
        else if (atributo == "Sab")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_SAB));
        else if (atributo == "Car")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CAR));
        else
            value = 0;
        return (int)(value - 10)/2;
    }

    public int numberOfRows(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = 0;
        if  (table == "personagem")
            numRows = (int) DatabaseUtils.queryNumEntries(db, CHAR_TABLE_NAME);
        else if  (table == "classe")
            numRows = (int) DatabaseUtils.queryNumEntries(db, CLASS_TABLE_NAME);
        return numRows;
    }


    public boolean updatePersonagem (Integer id, String name, int força, int destreza, int constituição, int inteligência, int sabedoria, int carisma){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("força", força);
        contentValues.put("destreza", destreza);
        contentValues.put("constituição", constituição);
        contentValues.put("inteligência", inteligência);
        contentValues.put("sabedoria", sabedoria);
        contentValues.put("carisma", carisma);
        db.update("personagens", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deletePersonagem (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("personagens",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllPersonagens(){
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from personagens", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CHAR_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getAllClasses(){
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from classes", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CLASS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

}
