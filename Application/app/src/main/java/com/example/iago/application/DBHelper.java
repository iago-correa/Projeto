package com.example.iago.application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private Context contex;

    public static final String DATABASE_NAME = "TormentaDB.db";

    public static final String CHAR_TABLE_NAME = "personagens";
    public static final String CHAR_COLUMN_ID = "id";
    public static final String CHAR_COLUMN_NAME = "nome";
    public static final String CHAR_COLUMN_FOR = "força";
    public static final String CHAR_COLUMN_DES = "destreza";
    public static final String CHAR_COLUMN_CON = "constituição";
    public static final String CHAR_COLUMN_INT = "inteligência";
    public static final String CHAR_COLUMN_SAB = "sabedoria";
    public static final String CHAR_COLUMN_CAR = "carisma";
    public static final String CHAR_COLUMN_RAÇA = "racaid";

    public static final String CLASS_TABLE_NAME = "classes";
    public static final String CLASS_COLUMN_ID = "classid";
    public static final String CLASS_COLUMN_NAME = "classnome";
    public static final String CLASS_COLUMN_BBA = "bbaclasse";
    public static final String CLASS_COLUMN_PVS = "pvsclasse";
    public static final String CLASS_COLUMN_PERÍCIAS = "perclasse";
    public static final String CLASS_COLUMN_PRESTÍGIO = "prestígio";
    public static final String CLASS_COLUMN_NÍVEL = "nívelmax";
    public static final String CLASS_COLUMN_ORIGEM = "classorigem";
    public static final String CLASS_COLUMN_DESCRIPTION = "classdescricao";

    public static final String RACE_TABLE_NAME = "racas";
    public static final String RACE_COLUMN_ID = "racaid";
    public static final String RACE_COLUMN_NAME = "racanome";
    public static final String RACE_COLUMN_ORIGEM = "racaorigem";
    public static final String RACE_COLUMN_DESCRIPTION = "racadescricao";

    public static final String CHARCLASSE_REL_NAME = "relaccharclass";
    public static final String CHARCLASSE_CHAR_ID = "relaccharclasschar";
    public static final String CHARCLASSE_CLASS_ID = "relaccharclassclass";
    public static final String CHARCLASSE_NÍVEL = "relaccharclassnvl";

    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        contex = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        FileReader file = null;

        db.execSQL(
                "create table " + RACE_TABLE_NAME  +
                        "(racaid integer primary key, racanome text, racaorigem text, racadescricao text)"
        );
        db.execSQL(
                "create table " + CHAR_TABLE_NAME  +
                        "(id integer primary key, nome text, força integer, destreza integer, constituicao integer, inteligência integer, sabedoria integer, carisma integer, racaid integer, foreign key(racaid) references racas(racaid))"
        );
        db.execSQL(
                "create table " + CLASS_TABLE_NAME  +
                        "(classid integer primary key, classnome text, bbaclasse integer, pvsclasse integer, perclasse integer, prestígio boolean, nívelmax integer, classorigem text, classdescricao text)"
        );
        //db.execSQL(
        //        "create table " + CHARCLASSE_REL_NAME  +
        //                "(" + CHARCLASSE_CHAR_ID + "integer, foreign key(" + CHARCLASSE_CHAR_ID + ") references personagens(id)," + CHARCLASSE_CLASS_ID + "integer, foreign key(" + CHARCLASSE_CLASS_ID + ") references classes(classid), integer" + CHARCLASSE_NÍVEL + ")"
        //);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS personagens");
        db.execSQL("DROP TABLE IF EXISTS classes");
        db.execSQL("DROP TABLE IF EXISTS racas");
        onCreate(db);
    }

    public void inicializaDB(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+RACE_TABLE_NAME);
        db.execSQL("delete from "+CLASS_TABLE_NAME);

        String line = "";

        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(contex.getAssets().open("classes.csv")));

            while ((line = buffer.readLine()) != null) {
                String[] str = line.split(";");
                this.insertClasse(str[0],Integer.parseInt(str[1]),Integer.parseInt(str[2]),Integer.parseInt(str[3]),Boolean.parseBoolean(str[4]),Integer.parseInt(str[5]),str[6],str[7]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            BufferedReader buffer = new BufferedReader(new InputStreamReader(contex.getAssets().open("racas.csv")));
            while ((line = buffer.readLine()) != null) {
                String[] str = line.split(";");
                this.insertRaça(str[0],"",str[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean insertPersonagem (String name, int força, int destreza, int constituição, int inteligência, int sabedoria, int carisma, int raçaid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHAR_COLUMN_NAME, name);
        contentValues.put(CHAR_COLUMN_FOR, força);
        contentValues.put(CHAR_COLUMN_DES, destreza);
        contentValues.put(CHAR_COLUMN_CON, constituição);
        contentValues.put(CHAR_COLUMN_INT, inteligência);
        contentValues.put(CHAR_COLUMN_SAB, sabedoria);
        contentValues.put(CHAR_COLUMN_CAR, carisma);
        contentValues.put(CHAR_COLUMN_RAÇA, raçaid);
        db.insert(CHAR_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertClasse(String name, int bba, int pvs, int pericias, boolean prestígio, int nivelmax, String origem, String descrição){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLASS_COLUMN_NAME, name);
        contentValues.put(CLASS_COLUMN_BBA,bba);
        contentValues.put(CLASS_COLUMN_PVS,pvs);
        contentValues.put(CLASS_COLUMN_PERÍCIAS,pericias);
        contentValues.put(CLASS_COLUMN_PRESTÍGIO,prestígio);
        contentValues.put(CLASS_COLUMN_NÍVEL,nivelmax);
        contentValues.put(CLASS_COLUMN_ORIGEM,origem);
        contentValues.put(CLASS_COLUMN_DESCRIPTION,descrição);
        System.out.println(contentValues);
        db.insert(CLASS_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean insertRaça(String name, String origem, String descricao){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RACE_COLUMN_NAME, name);
        contentValues.put(RACE_COLUMN_ORIGEM,origem);
        contentValues.put(RACE_COLUMN_DESCRIPTION,descricao);
        System.out.println(contentValues);
        db.insert(RACE_TABLE_NAME, null, contentValues);
        return true;
    }
    public Cursor getData(String table, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        if  (table == "personagem")
            res =  db.rawQuery( "select * from personagens where id="+id+"", null );
        else if  (table == "classe")
            res =  db.rawQuery( "select * from classes where id="+id+"", null );
        else if  (table == "raca")
            res =  db.rawQuery( "select * from racas where id="+id+"", null );
        return res;
    }

    //   public int getClassLevel(int charid, int classid) {
    //SQLiteDatabase db = this.getReadableDatabase();
    //       long res = DatabaseUtils.queryNumEntries(db, CHARCLASSE_REL_NAME, CHARCLASSE_CHAR_ID + "=? AND" + CHARCLASSE_CLASS_ID + "=?", new String[] {Integer.toString(charid),Integer.toString(classid)});
    //       if (res > 0)
    //
    //  }

    //   public boolean LevelUp (int charid, int classid) {
    //      SQLiteDatabase db = this.getWritableDatabase();
    //      ContentValues contentValues = new ContentValues();
    //      contentValues.put(CHARCLASSE_CHAR_ID, charid);
    //      contentValues.put(CHARCLASSE_CLASS_ID,classid);
    //  }
    public String getDescr(String table, int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        String desc;
        if (table == "classe") {
            res = db.rawQuery("select * from classes where id=" + id + "", null);
            desc = res.getString(res.getColumnIndex(DBHelper.CLASS_COLUMN_DESCRIPTION));
        } else if (table == "raca") {
            res = db.rawQuery("select * from racas where id=" + id + "", null);
            desc = res.getString(res.getColumnIndex(DBHelper.RACE_COLUMN_DESCRIPTION));
        }
        else
            desc = "ERRO: Descrição não-encontrada.";
        return desc;
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
        else if  (table == "raca")
            numRows = (int) DatabaseUtils.queryNumEntries(db, RACE_TABLE_NAME);
        return numRows;
    }


    public boolean updatePersonagem (Integer id, String name, int força, int destreza, int constituição, int inteligência, int sabedoria, int carisma){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHAR_COLUMN_NAME, name);
        contentValues.put(CHAR_COLUMN_FOR, força);
        contentValues.put(CHAR_COLUMN_DES, destreza);
        contentValues.put(CHAR_COLUMN_CON, constituição);
        contentValues.put(CHAR_COLUMN_INT, inteligência);
        contentValues.put(CHAR_COLUMN_SAB, sabedoria);
        contentValues.put(CHAR_COLUMN_CAR, carisma);
        db.update(CHAR_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deletePersonagem (Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CHAR_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAll(String table){
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        String column = null;
        if  (table == "personagens") {
            res = db.rawQuery("select * from personagens", null);
            column = CHAR_COLUMN_NAME;
        }
        else if  (table == "classes") {
            res = db.rawQuery("select * from classes", null);
            column = CLASS_COLUMN_NAME;
        }
        else if  (table == "racas") {
            res = db.rawQuery("select * from racas", null);
            column = RACE_COLUMN_NAME;
        }
        if (res != null) {
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                array_list.add(res.getString(res.getColumnIndex(column)));
                res.moveToNext();
            }
        }
        return array_list;
    }

}
