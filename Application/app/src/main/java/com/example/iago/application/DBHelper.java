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

    public static final String FEAT_TABLE_NAME = "talentos";
    public static final String FEAT_COLUMN_ID = "talentoid";
    public static final String FEAT_COLUMN_NAME = "talentonome";
    public static final String FEAT_COLUMN_REPEAT = "talentorep";
    public static final String FEAT_COLUMN_ACT = "talentoact";

    public static final String PREREQ_TABLE_NAME = "prerequisitos";
    public static final String PREREQ_COLUMN_FID = "prereqtalid";
    public static final String PREREQ_COLUMN_TYPE = "prereqtype";
    public static final String PREREQ_COLUMN_EXTRA = "prereqesp";
    public static final String PREREQ_COLUMN_VALUE = "prereqval";

    public static final String CHARCLASSE_REL_NAME = "relaccharclass";
    public static final String CHARCLASSE_CHAR_ID = "relaccharclasschar";
    public static final String CHARCLASSE_CLASS_ID = "relaccharclassclass";
    public static final String CHARCLASSE_NÍVEL = "relaccharclassnvl";

    public static final String CHARFEAT_REL_NAME = "relaccharfeat";
    public static final String CHARFEAT_CHAR_ID = "relaccharfeatchar";
    public static final String CHARFEAT_FEAT_ID = "relaccharfeatfeat";
    public static final String CHARFEAT_TIMES = "relaccharfeatnum";
    public static final String CHARFEAT_AUX = "relaccharfeataux";

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
        db.execSQL(
                "create table " + CHARCLASSE_REL_NAME  +
                        "(relaccharclasschar integer, relaccharclassclass integer, " + CHARCLASSE_NÍVEL + " integer, foreign key(relaccharclasschar) references personagens(id), foreign key(relaccharclassclass) references classes(classid))"
        );
        db.execSQL(
                "create table " + FEAT_TABLE_NAME +
                        "(talentoid integer primary key, talentonome text, talentorep boolean, talentoact boolean)"
        );
        db.execSQL(
                "create table " + CHARFEAT_REL_NAME  +
                        "(relaccharfeatchar integer, relaccharfeatfeat integer, " + CHARFEAT_TIMES + " integer, " + CHARFEAT_AUX + " integer, foreign key(relaccharclasschar) references personagens(id), foreign key(relaccharclassclass) references classes(classid))"
        );
        db.execSQL(
                "create table " + PREREQ_TABLE_NAME +
                        "(prereqtalid integer, prereqtype text, prereqesp text, prereqval integer, foreign key(prereqtalid) references talentos(talentoid))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS personagens");
        db.execSQL("DROP TABLE IF EXISTS classes");
        db.execSQL("DROP TABLE IF EXISTS racas");
        db.execSQL("DROP TABLE IF EXISTS talentos");

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

     public int getClassLevel(int charid, int classid) {
         SQLiteDatabase db = this.getReadableDatabase();
         int level = 0;
         Cursor res = db.rawQuery("select * from " + CHARCLASSE_REL_NAME + " where " + CHARCLASSE_CLASS_ID + "=" +classid+ " and " + CHARCLASSE_CHAR_ID + "=" +charid+ "", null);
         if (res != null)
             level = res.getInt(res.getColumnIndex(CHARCLASSE_NÍVEL));
         return level;
      }

    public boolean levelUp (int charid, int classid) {
           SQLiteDatabase db = this.getReadableDatabase();
           int level = getClassLevel(charid, classid) + 1;
           ContentValues contentValues = new ContentValues();
           contentValues.put(CHARCLASSE_CLASS_ID, classid);
           contentValues.put(CHARCLASSE_CHAR_ID, charid);
           contentValues.put(CHARCLASSE_NÍVEL, level);
           Cursor res = null;
           if (level > 1)
               db.update(CHARCLASSE_REL_NAME, contentValues, CHARCLASSE_CLASS_ID + " = ? and " + CHARCLASSE_CHAR_ID + " = ?", new String[] { Integer.toString(classid), Integer.toString(charid)} );
           else
               db.insert(CHARCLASSE_REL_NAME, null, contentValues);
           return true;
       }

    public boolean featPrereq(int charid, int featid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String type, esp;
        int[] value = new int[6];
        Cursor res = db.rawQuery("select * from " + CHAR_TABLE_NAME + "where" + CHAR_COLUMN_ID + "=" +charid+ "", null);
        value[0] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_FOR));
        value[1] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_DES));
        value[2] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CON));
        value[3] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_INT));
        value[4] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_SAB));
        value[5] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CAR));
        boolean answer = true;
        res = db.rawQuery("select * from " + PREREQ_TABLE_NAME + " where " + PREREQ_COLUMN_FID + "=" +featid+ "", null);
        if (res != null) {
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                type = res.getString(res.getColumnIndex(PREREQ_COLUMN_TYPE));
                if (type == "Atributo") {
                    esp = res.getString(res.getColumnIndex(PREREQ_COLUMN_EXTRA));
                    if (esp == "FOR")
                        if (value[0] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                            answer = false;
                        else;
                    else if (esp == "DES")
                        if (value[1] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                            answer = false;
                        else;
                    else if (esp == "CON")
                        if (value[2] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                            answer = false;
                        else;
                    else if (esp == "INT")
                        if (value[3] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                            answer = false;
                        else;
                    else if (esp == "SAB")
                        if (value[4] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                            answer = false;
                        else;
                    else if (esp == "CAR")
                        if (value[5] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                            answer = false;
                        else;
                } else if (type == "Talento") {
                    if (hasFeat(charid, res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE))) == null)
                        answer = false;
                }
                res.moveToNext();
            }
        }
        return answer;
    }

    public boolean featBuy (int charid, int featid, int aux) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res = hasFeat(charid,featid);
            ContentValues contentValues = new ContentValues();
            contentValues.put(CHARFEAT_CHAR_ID, charid);
            contentValues.put(CHARFEAT_FEAT_ID, charid);
            contentValues.put(CHARFEAT_AUX, aux);
            if (res != null) {
                contentValues.put(CHARFEAT_TIMES, res.getInt(res.getColumnIndex(DBHelper.CHARFEAT_TIMES))+1);
                db.update(CHARFEAT_REL_NAME, contentValues, CHARFEAT_FEAT_ID + " = ? and " + CHARFEAT_CHAR_ID + " = ?", new String[]{Integer.toString(featid), Integer.toString(charid)});
            } else {
                contentValues.put(CHARFEAT_TIMES, 1);
                db.insert(CHARFEAT_REL_NAME, null, contentValues);
            }
            return true;
    }

    public Cursor hasFeat (int charid, int featid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CHARFEAT_REL_NAME + " where " + CHARFEAT_CHAR_ID + "=" +charid+ " and " + CHARFEAT_FEAT_ID + "=" +featid+ "", null);
        return res;
    }

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

    public int mod (int charid, String atributo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from personagens where id="+charid+"", null );
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
