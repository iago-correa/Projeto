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
    public static final String CHAR_COLUMN_NIVEL = "nivel";
    public static final String CHAR_COLUMN_FCLASS = "primclasse";

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

    public static final String FEATPOINTS_TABLE_NAME = "pontostalento";
    public static final String FEATPOINTS_COMBAT = "pontostalentocombate";
    public static final String FEATPOINTS_MAGIC = "pontostalentomagia";
    public static final String FEATPOINTS_SKILL = "pontostalentopericia";
    public static final String FEATPOINTS_DIVINE = "pontostalentodivino";
    public static final String FEATPOINTS_TORMENTA = "pontostalentotormenta";
    public static final String FEATPOINTS_GENERAL = "pontostalentogeral";
    public static final String FEATPOINTS_CHARID = "pontostalentochar";

    public static final String FEAT_TABLE_NAME = "talentos";
    public static final String FEAT_COLUMN_ID = "talentoid";
    public static final String FEAT_COLUMN_NAME = "talentonome";
    public static final String FEAT_COLUMN_TYPE = "talentotipo";
    public static final String FEAT_COLUMN_REPEAT = "talentorep";
    public static final String FEAT_COLUMN_ACT = "talentoact";

    public static final String SKILL_TABLE_NAME = "pericias";
    public static final String SKILL_COLUMN_ID = "periciaid";
    public static final String SKILL_COLUMN_NAME = "pericianome";
    public static final String SKILL_COLUMN_TRAIN = "periciatr";
    public static final String SKILL_COLUMN_ARMOR = "periciapen";
    public static final String SKILL_COLUMN_ATT = "periciatt";

    public static final String WEAPON_TABLE_NAME = "armas";
    public static final String WEAPON_COLUMN_NAME = "armanome";
    public static final String WEAPON_COLUMN_ID = "armaid";
    public static final String WEAPON_COLUMN_HAND = "armamao";
    public static final String WEAPON_COLUMN_DAMAGE = "armadano";
    public static final String WEAPON_COLUMN_CRITICAL = "armacrit";
    public static final String WEAPON_COLUMN_MULTIPLIER = "armamult";
    public static final String WEAPON_COLUMN_DTYPE = "armadtipo";
    public static final String WEAPON_COLUMN_DES = "armades";
    public static final String WEAPON_COLUMN_LONG = "armahaste";
    public static final String WEAPON_COLUMN_DISTANCE = "armadist";

    public static final String SPECWEAPON_TABLE_NAME = "armaespecifica";
    public static final String SPECWEAPON_COLUMN_NAME = "armaespecificanome";
    public static final String SPECWEAPON_COLUMN_MAGIC = "armaespecificamagia";
    public static final String SPECWEAPON_COLUMN_BASE = "armaespecificabase";
    public static final String SPECWEAPON_COLUMN_BONUS = "armaespecificabonus";
    public static final String SPECWEAPON_CHAR_ID = "armaespecificachar";

    public static final String SPECARMOR_TABLE_NAME = "armaduraespecifica";
    public static final String SPECARMOR_COLUMN_NAME = "armaduraespecificanome";
    public static final String SPECARMOR_COLUMN_MAGIC = "armaduraespecificamagia";
    public static final String SPECARMOR_COLUMN_BASE = "armaduraespecificabase";
    public static final String SPECARMOR_COLUMN_BONUS = "armaduraespecificabonus";
    public static final String SPECARMOR_CHAR_ID = "armaduraespecificachar";

    public static final String ARMOR_TABLE_NAME = "armaduras";
    public static final String ARMOR_COLUMN_NAME = "armaduranome";
    public static final String ARMOR_COLUMN_ID = "armaduraid";
    public static final String ARMOR_COLUMN_TYPE = "armaduratipo";
    public static final String ARMOR_COLUMN_CA = "armaduraca";
    public static final String ARMOR_COLUMN_BMD = "armadurabmd";
    public static final String ARMOR_COLUMN_PA = "armadurapen";

    public static final String PREREQ_TABLE_NAME = "prerequisitos";
    public static final String PREREQ_COLUMN_FID = "prereqtalid";
    public static final String PREREQ_COLUMN_TYPE = "prereqtype";
    public static final String PREREQ_COLUMN_EXTRA = "prereqesp";
    public static final String PREREQ_COLUMN_VALUE = "prereqval";

    public static final String CHARCLASSE_REL_NAME = "relaccharclass";
    public static final String CHARCLASSE_CHAR_ID = "relaccharclasschar";
    public static final String CHARCLASSE_CLASS_ID = "relaccharclassclass";
    public static final String CHARCLASSE_NÍVEL = "relaccharclassnvl";

    public static final String CHARSKILL_REL_NAME = "relaccharskill";
    public static final String CHARSKILL_CHAR_ID = "relaccharskillchar";
    public static final String CHARSKILL_SKILL_ID = "relaccharskillskill";

    public static final String CHARFEAT_REL_NAME = "relaccharfeat";
    public static final String CHARFEAT_CHAR_ID = "relaccharfeatchar";
    public static final String CHARFEAT_FEAT_ID = "relaccharfeatfeat";
    public static final String CHARFEAT_TIMES = "relaccharfeatnum";
    public static final String CHARFEAT_AUX = "relaccharfeataux";

    public static final String ABILITY_TABLE_NAME = "habilidades";
    public static final String ABILITY_COLUMN_ID = "habilidadeid";
    public static final String ABILITY_COLUMN_NAME = "habilidadenome";
    public static final String ABILITY_COLUMN_VALUE = "habilidadevalor";

    public static final String CHARABILITY_REL_NAME = "relaccharab";
    public static final String CHARABILITY_CHAR_ID = "relaccharabchar";
    public static final String CHARABILITY_SKILL_ID = "relaccharabab";
    public static final String CHARABILITY_AUX = "relaccharabaux";

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
                        "(id integer primary key, nome text, força integer, destreza integer, constituicao integer, inteligência integer, sabedoria integer, carisma integer, racaid integer, primclasse integer, foreign key(racaid) references racas(racaid), foreign key(primclasse) references classes(classid))"
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
                "create table " + SKILL_TABLE_NAME +
                        "(periciaid integer primary key, pericianome text, periciatr boolean, periciapen boolean, periciatt integer)"
        );

        db.execSQL(
                "create table " + ABILITY_TABLE_NAME +
                        "(habilidadeid integer primary key, habilidadenome text, habilidadevalor integer)"
        );

        db.execSQL(
                "create table " + WEAPON_TABLE_NAME +
                        "(armaid integer primary key, armanome text, armatipo integer, armamao integer, armadano text, armamult integer, armacrit integer, armadtipo integer, armades integer, armalong integer, armadist integer)"
        );

        db.execSQL(
                "create table " + SPECWEAPON_TABLE_NAME +
                        "(armaespecificanome text, armaespecificamagia text, armaespecificabonus integer,armaespecificabase integer, armaespecificachar integer, foreign key(armaespecificabase) references armas(armaid), foreign key(armaespecificachar) references personagens(id))"
        );

        db.execSQL(
                "create table " + SPECARMOR_TABLE_NAME +
                        "(armaduraespecificanome text, armaduraespecificamagia text, armaduraespecificabonus integer,armaduraespecificabase integer, armaduraespecificachar integer, foreign key(armaduraespecificabase) references armaduras(armaduraid), foreign key(armaduraespecificachar) references personagens(id))"
        );

        db.execSQL(
                "create table " + ARMOR_TABLE_NAME +
                        "(armaduraid integer primary key, armaduranome text, armaduratipo integer, armaduraca integer, armadurabmd text, armadurapen integer)"
        );

        db.execSQL(
                "create table " + CHARFEAT_REL_NAME  +
                        "(relaccharfeatchar integer, relaccharfeatfeat integer, " + CHARFEAT_TIMES + " integer, " + CHARFEAT_AUX + " integer, foreign key(relaccharfeatchar) references personagens(id), foreign key(relaccharfeatfeat) references talentos(talentoid))"
        );
        db.execSQL(
                "create table " + PREREQ_TABLE_NAME +
                        "(prereqtalid integer, prereqtype text, prereqesp text, prereqval integer, foreign key(prereqtalid) references talentos(talentoid))");

        db.execSQL(
                "create table " + CHARSKILL_REL_NAME  +
                        "(relaccharskillchar integer, relaccharskillskill integer, foreign key(relaccharskillchar) references personagens(id), foreign key(relaccharskillskill) references pericias(periciaid))"
        );

        db.execSQL(
                "create table " + CHARABILITY_REL_NAME  +
                        "(relaccharabchar integer, relaccharabab integer, relaccharabaux integer, foreign key(relaccharabchar) references personagens(id), foreign key(relaccharabab) references habilidades(habilidadeid))"
        );
        db.execSQL(
                "create table " + FEATPOINTS_TABLE_NAME +
                        "(pontostalentogeral integer, pontostalentocombate integer, pontostalentomagia integer, pontostalentopericia integer, pontostalentodivino integer, pontostalentotormenta integer, pontostalentochar integer, foreign key(pontostalentochar) references personagens(id))"
        );

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
        contentValues.put(CHAR_COLUMN_NIVEL, raçaid);
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

    public int getClassLevel(int charid, int classid) {
        SQLiteDatabase db = this.getReadableDatabase();
        int level = 0;
        Cursor res = db.rawQuery("select * from " + CHARCLASSE_REL_NAME + " where " + CHARCLASSE_CLASS_ID + "=" +classid+ " and " + CHARCLASSE_CHAR_ID + "=" +charid+ "", null);
        if (res != null)
            level = res.getInt(res.getColumnIndex(CHARCLASSE_NÍVEL));
        return level;
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
            res =  db.rawQuery( "select * from classes where classid="+id+"", null );
        else if  (table == "raca")
            res =  db.rawQuery( "select * from racas where racaid="+id+"", null );
        else if  (table == "talento")
            res =  db.rawQuery( "select * from talentos where talentoid="+id+"", null );
        else if  (table == "pericia")
            res =  db.rawQuery( "select * from pericias where periciaid="+id+"", null );
        else if  (table == "armadura")
            res =  db.rawQuery( "select * from armaduras where armaduraid="+id+"", null );
        else if  (table == "arma")
            res =  db.rawQuery( "select * from armas where armaid="+id+"", null );
        return res;
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
        contentValues = null;
        res = getData("personagem",charid);
        level = res.getInt(res.getColumnIndex("nivel")) + 1;
        contentValues.put(CHAR_COLUMN_NIVEL, level);
        if (level == 1)
            contentValues.put(CHAR_COLUMN_FCLASS, classid);
        db.update(CHAR_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(charid) } );
        return true;
    }

    public boolean featPrereq(int charid, int featid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String featype, type, esp;
        boolean answer = true;
        int[] value = new int[6];
        Cursor res = getData("talento",featid);
        featype = res.getString(res.getColumnIndex(FEAT_COLUMN_TYPE));
        int pontos = featPoints(featype,charid);
        if ((pontos > 0) || (featPoints("Geral",charid) > 0)) {
            res = db.rawQuery("select * from " + CHAR_TABLE_NAME + "where" + CHAR_COLUMN_ID + "=" + charid + "", null);
            value[0] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_FOR));
            value[1] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_DES));
            value[2] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CON));
            value[3] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_INT));
            value[4] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_SAB));
            value[5] = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CAR));

            res = db.rawQuery("select * from " + PREREQ_TABLE_NAME + " where " + PREREQ_COLUMN_FID + "=" + featid + "", null);
            if (res != null) {
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    type = res.getString(res.getColumnIndex(PREREQ_COLUMN_TYPE));
                    if (type == "Atributo") {
                        esp = res.getString(res.getColumnIndex(PREREQ_COLUMN_EXTRA));
                        if (esp == "FOR")
                            if (value[0] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                                answer = false;
                            else ;
                        else if (esp == "DES")
                            if (value[1] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                                answer = false;
                            else ;
                        else if (esp == "CON")
                            if (value[2] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                                answer = false;
                            else ;
                        else if (esp == "INT")
                            if (value[3] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                                answer = false;
                            else ;
                        else if (esp == "SAB")
                            if (value[4] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                                answer = false;
                            else ;
                        else if (esp == "CAR")
                            if (value[5] < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                                answer = false;
                            else ;
                    } else if (type == "Talento") {
                        if (hasFeat(charid, res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE))) == null)
                            answer = false;
                    } else if (type == "BBA") {
                        if (BBA(charid) < res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE)))
                            answer = false;
                    }
                    res.moveToNext();
                }
            }
        } else
            answer = false;
        return answer;
    }

    public int featPoints (String feat, int charid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String type = "";
        if (feat == "Combate")
            type = FEATPOINTS_COMBAT;
        else if (feat == "Magia")
            type = FEATPOINTS_MAGIC;
        else if (feat == "Perícia")
            type = FEATPOINTS_SKILL;
        else if (feat == "Poder Concedido")
            type = FEATPOINTS_DIVINE;
        else if (feat == "Tormenta")
            type = FEATPOINTS_TORMENTA;
        else
            type = FEATPOINTS_GENERAL;
        Cursor res = db.rawQuery("select * from " + FEATPOINTS_TABLE_NAME + " where " + FEATPOINTS_CHARID + "=" +charid+ "", null);
        int answer = res.getInt(res.getColumnIndex(type));
        return answer;
    }

    public boolean featPointTaken (String feat, int charid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String type = "";
        if (feat == "Combate")
            type = FEATPOINTS_COMBAT;
        else if (feat == "Magia")
            type = FEATPOINTS_MAGIC;
        else if (feat == "Perícia")
            type = FEATPOINTS_SKILL;
        else if (feat == "Poder Concedido")
            type = FEATPOINTS_DIVINE;
        else if (feat == "Tormenta")
            type = FEATPOINTS_TORMENTA;
        else
            type = FEATPOINTS_GENERAL;
        int points = featPoints(feat,charid) - 1;
        ContentValues contentValues = new ContentValues();
        contentValues.put(type, points);
        db.update(FEATPOINTS_TABLE_NAME, contentValues, FEATPOINTS_CHARID + " = ?", new String[]{Integer.toString(charid)});
        return true;
    }

    public boolean featBuy (int charid, int featid, int aux) {
        SQLiteDatabase db = this.getReadableDatabase();
        String type = "";
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
        res = getData("Talento",featid);
        type = res.getString(res.getColumnIndex(FEAT_COLUMN_TYPE));
        if (featPoints(type,charid) > 0)
            featPointTaken(type, charid);
        else
            featPointTaken("Geral",charid);
        return true;
    }

    public boolean abilityGet (int charid, int abilityid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = hasAbility(charid,abilityid);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHARABILITY_CHAR_ID, charid);
        contentValues.put(CHARABILITY_SKILL_ID, abilityid);
        if (res != null) {
            contentValues.put(CHARABILITY_AUX, res.getInt(res.getColumnIndex(DBHelper.CHARABILITY_AUX))+1);
            db.update(CHARABILITY_REL_NAME, contentValues, CHARABILITY_SKILL_ID + " = ? and " + CHARABILITY_CHAR_ID + " = ?", new String[]{Integer.toString(abilityid), Integer.toString(charid)});
        } else {
            contentValues.put(CHARABILITY_AUX, 1);
            db.insert(CHARABILITY_REL_NAME, null, contentValues);
        }
        return true;
    }

    public Cursor hasFeat (int charid, int featid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CHARFEAT_REL_NAME + " where " + CHARFEAT_CHAR_ID + "=" +charid+ " and " + CHARFEAT_FEAT_ID + "=" +featid+ "", null);
        return res;
    }

    public Cursor hasAbility (int charid, int abilityid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CHARABILITY_REL_NAME + " where " + CHARABILITY_CHAR_ID + "=" +charid+ " and " + CHARABILITY_SKILL_ID+ "=" +abilityid+ "", null);
        return res;
    }

    public Cursor hasSkill (int charid, int skillid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + CHARSKILL_REL_NAME + " where " + CHARSKILL_CHAR_ID + "=" +charid+ " and " + CHARSKILL_SKILL_ID + "=" +skillid+ "", null);
        return res;
    }

    public Cursor hasArmor (int charid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + SPECARMOR_TABLE_NAME + " where " + SPECARMOR_CHAR_ID + "=" +charid+ "", null);
        return res;
    }

    public String getDescr(String nome, String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        String desc;
        ArrayList<String> saida = new ArrayList<String>();
        if (table == "classe") {
            res = db.rawQuery("select classdescricao from classes where "+DBHelper.CLASS_COLUMN_NAME+" LIKE '" + nome + "'", null);
            if(res != null){
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    saida.add(res.getString(res.getColumnIndex(DBHelper.CLASS_COLUMN_DESCRIPTION)));
                    res.moveToNext();
                }
                desc = saida.toString().substring(1,saida.toString().length()-1);
                return desc;
            }else{
                return "Nada encontrado";
            }
        } else if (table == "raca") {
            res = db.rawQuery("select racadescricao from racas where "+DBHelper.RACE_COLUMN_NAME+" LIKE '" + nome + "'", null);
            if(res != null){
                res.moveToFirst();
                while (res.isAfterLast() == false) {
                    saida.add(res.getString(res.getColumnIndex(DBHelper.RACE_COLUMN_DESCRIPTION)));
                    res.moveToNext();
                }
                desc = saida.toString().substring(1,saida.toString().length()-1);
                return desc;
            }else{
                return "Nada encontrado";
            }
        }

        return "Tabela não encontrada";

    }

    public String getName (String type, int id) {
        String coluna= "";
        boolean error = false;
        Cursor res = getData(type,id);
        if (type == "talento")
            coluna = "talentonome";
        else if (type == "pericia")
            coluna = "pericianome";
        else if (type == "classe")
            coluna = "classnome";
        else if (type == "raca")
            coluna = "racanome";
        else if (type == "arma")
            coluna = "armanome";
        else if (type == "armadura")
            coluna = "armaduranome";
        else
            error = true;
        if(!error)
            return res.getString(res.getColumnIndex(coluna));
        else
            return "";
    }

    public String getPrereq (int featid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String type, prereq = "";
        Cursor res = db.rawQuery("select * from " + PREREQ_TABLE_NAME + " where " + PREREQ_COLUMN_FID + "=" +featid+ "", null);
        if (res != null) {
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                type = res.getString(res.getColumnIndex(PREREQ_COLUMN_TYPE));
                if (type == "Atributo") {
                    prereq += res.getString(res.getColumnIndex(PREREQ_COLUMN_EXTRA)) + " " + res.getString(res.getColumnIndex(PREREQ_COLUMN_VALUE)) + "; ";
                } else if (type == "Talento") {
                    prereq += getName("Talento",res.getInt(res.getColumnIndex(PREREQ_COLUMN_VALUE))) + "; ";
                }
                else if (type == "BBA") {
                    prereq += "BBA " + res.getString(res.getColumnIndex(PREREQ_COLUMN_VALUE)) + "; ";
                }
                else if (type == "Classe") {
                    prereq += res.getString(res.getColumnIndex(PREREQ_COLUMN_EXTRA)) + " de " + res.getString(res.getColumnIndex(PREREQ_COLUMN_VALUE)) + " nível; ";
                }
                else if ((type == "Perícia")&&((res.getString(res.getColumnIndex(PREREQ_COLUMN_VALUE)))=="1")) {
                    prereq += "Treinado em " + res.getString(res.getColumnIndex(PREREQ_COLUMN_EXTRA)) + "; ";
                }
                res.moveToNext();
            }
        }
        return prereq;
    }

    public int mod (int charid, String atributo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from personagens where id="+charid+"", null );
        Integer value;
        if (atributo == "FOR")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_FOR));
        else if (atributo == "DES")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_DES));
        else if (atributo == "CON")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CON));
        else if (atributo == "INT")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_INT));
        else if (atributo == "SAB")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_SAB));
        else if (atributo == "CAR")
            value = res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CAR));
        else
            value = 0;
        return (int)(value - 10)/2;
    }

    public int CA (int charid) {
        int CA = 10;
        int aux, aux2 = mod(charid, "DES");
        Cursor res = hasArmor(charid);
        if (res==null) {
            CA += aux2;
            if (hasFeat(charid,18)!=null)
                CA += mod(charid,"CON");
        }
        else {
            res = getData("armadura",res.getInt(res.getColumnIndex(SPECARMOR_COLUMN_BASE)));
            aux = res.getInt(res.getColumnIndex(ARMOR_COLUMN_BMD));
            if (aux < aux2)
                CA += aux;
            else
                CA += aux2;
        }
        res = getData("personagem",charid);
        CA += (int)(res.getInt(res.getColumnIndex("nivel"))/2);
        if (hasFeat(charid,33)!=null)
            CA++;
        if (hasFeat(charid,17)!=null)
            CA++;
        return CA;
    }

    public int BBA (int charid) {
        int BBA = 0;
        int BBAtype = 0;
        int nível;
        Cursor res;
        for (int i = 1; i < numberOfRows("classe"); i++) {
            nível = getClassLevel(charid,i);
            if (nível > 0) {
                res = getData("classe", i);
                BBAtype = res.getInt(res.getColumnIndex("bbaclasse"));
                if (BBAtype == 1)
                    BBA += (int) (nível/2);
                else if (BBAtype == 2)
                    BBA += (int) (3*nível/4);
                else if (BBAtype == 3)
                    BBA += nível;
            }
        }
        return BBA;
    }

    public int PVs (int charid) {
        int PVs = 0;
        int nível;
        Cursor res = getData("personagem",charid);
        PVs += (res.getInt(res.getColumnIndex("nível"))*res.getInt(res.getColumnIndex("constituição")));
        res = getData("classe", res.getInt(res.getColumnIndex("primclasse")));
        PVs += res.getInt(res.getColumnIndex("pvsclasse"))*3;
        for (int i = 1; i < numberOfRows("classe"); i++) {
            nível = getClassLevel(charid,i);
            if (nível > 0) {
                res = getData("classe", i);
                PVs += res.getInt(res.getColumnIndex("pvsclasse"));
            }
        }
        return PVs;
    }

    public int BonusAtaque (String nomearma) {
        SQLiteDatabase db = this.getReadableDatabase();
        int bonus = 0;
        Cursor res = db.rawQuery( "select * from armaespecifica where armaespecificanome="+nomearma+"", null );
        int charid = res.getInt(res.getColumnIndex(SPECWEAPON_CHAR_ID));
        bonus += BBA(charid);
        bonus += res.getInt(res.getColumnIndex(SPECWEAPON_COLUMN_BONUS));
        res = getData("arma",res.getInt(res.getColumnIndex(SPECWEAPON_COLUMN_BASE)));
        if ((res.getInt(res.getColumnIndex(WEAPON_COLUMN_HAND))==4) || ((hasFeat(charid,2)!=null)&&((res.getInt(res.getColumnIndex(WEAPON_COLUMN_DES)))>0))) {
            bonus += mod(charid, "DES");
        } else
            bonus += mod(charid, "FOR");
        return bonus;
    }

    public String BonusDano (String nomearma) {
        SQLiteDatabase db = this.getReadableDatabase();
        int bonus = 0;
        Cursor res = db.rawQuery( "select * from armaespecifica where armaespecificanome="+nomearma+"", null );
        int charid = res.getInt(res.getColumnIndex(SPECWEAPON_CHAR_ID));
        bonus += res.getInt(res.getColumnIndex(SPECWEAPON_COLUMN_BONUS));
        res = getData("arma",res.getInt(res.getColumnIndex(SPECWEAPON_COLUMN_BASE)));
        int hand = res.getInt(res.getColumnIndex(WEAPON_COLUMN_HAND));
        if ((hand==3)&&(hasFeat(charid,39)!=null)) {
            bonus += 2 * mod(charid, "FOR");
        } else if ((hand==4)&&(hasFeat(charid,49)!=null)) {
            bonus += mod(charid, "DES");
        }
        if (((res.getInt(res.getColumnIndex(WEAPON_COLUMN_DES)))>0)&&(hasFeat(charid,15)!=null)) {
            bonus += mod(charid, "INT");
        }
        String end = (res.getString(res.getColumnIndex(WEAPON_COLUMN_DAMAGE))) + "+" + Integer.toString(bonus);
        return end;
    }

    public String Crítico (int armaid) {
        Cursor res = getData("arma",armaid);
        String result = "";
        int margem = res.getInt(res.getColumnIndex(WEAPON_COLUMN_CRITICAL));
        int mult = res.getInt(res.getColumnIndex(WEAPON_COLUMN_CRITICAL));
        if (margem == 3)
            result += "18-20";
        else if (margem == 2)
            result += "19-20";
        if ((margem > 1)&&(mult!=1))
            result += "/";
        if (mult == 2)
            result += "x3";
        else if (mult == 3)
            result += "x4";
        else if (mult == 0)
            result += "esp";
        else if ((mult == 1)&&(margem==1))
            result = "x2";
        return result;


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
        else if  (table == "talento")
            numRows = (int) DatabaseUtils.queryNumEntries(db, FEAT_TABLE_NAME);
        else if  (table == "pericia")
            numRows = (int) DatabaseUtils.queryNumEntries(db, SKILL_TABLE_NAME);
        return numRows;
    }

    public int periciaBonus (int charid, int periciaid)
    {
        String att;
        int bonus = 0, train, pen;
        Cursor res = getData("pericia",periciaid);
        train = res.getInt(res.getColumnIndex("periciatr"));
        pen = res.getInt(res.getColumnIndex("periciapen"));
        att = res.getString(res.getColumnIndex("periciatt"));
        bonus += mod(charid,att);
        if (pen > 0) {
            res = hasArmor(charid);
            if (res != null)
                res = getData("armadura",res.getInt(res.getColumnIndex("armaduraespecificabase")));
            bonus = bonus - (res.getInt(res.getColumnIndex("armadurapen")));
        }
        res = getData("personagem",charid);
        if (hasSkill(charid,periciaid) != null)
            bonus += res.getInt(res.getColumnIndex("nivel"))+3;
        else if (train == 0)
            bonus += (int)(res.getInt(res.getColumnIndex("nivel"))/2);
        else
            bonus = 0;
        return bonus;
    }

    public int pontosPericia (int charid) {
        SQLiteDatabase db = this.getReadableDatabase();
        int pontos = 0;
        Cursor res = getData("personagem",charid);
        if (res.getInt(res.getColumnIndex("racaid")) == 5)
            pontos += 2;
        res = getData("classe",res.getInt(res.getColumnIndex("primclasse")));
        pontos += res.getInt(res.getColumnIndex("perclasse"));
        pontos += mod(charid,"INT");
        res = db.rawQuery("select * from " +CHARSKILL_REL_NAME + "", null);
        if (res != null) {
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                if (res.getInt(res.getColumnIndex(CHARSKILL_CHAR_ID)) == charid)
                    pontos--;
                res.moveToNext();
            }
        }
        return pontos;

    }

    public boolean raceUpdate (int charid) {
        Cursor res = getData("personagem",charid);
        int racaid = res.getInt(res.getColumnIndex(CHAR_COLUMN_RAÇA));
        if (racaid == 1) {
            attChange(charid, "CON", 4);
            attChange(charid, "SAB", 2);
            attChange(charid, "DES", -2);
        } else if (racaid == 2) {
            attChange(charid, "DES", 4);
            attChange(charid, "INT", 2);
            attChange(charid, "CON", -2);
        } else if (racaid == 3) {
            attChange(charid, "DES", 4);
            attChange(charid, "CON", 2);
            attChange(charid, "CHA", -2);
        } else if (racaid == 4) {
            attChange(charid, "DES", 4);
            attChange(charid, "CHA", 2);
            attChange(charid, "FOR", -2);
        } else if (racaid == 7) {
            attChange(charid, "FOR", 4);
            attChange(charid, "CON", 2);
            attChange(charid, "CHA", -2);
        } else if (racaid == 8) {
            attChange(charid, "CHA", 4);
            attChange(charid, "INT", 2);
            attChange(charid, "SAB", -2);
        }
        return true;
    }

    public boolean AbilityTable (int charid, int classid, int level) {
        if (classid == 1) {
            if ((level%4 == 0)  || (level == 1))
                abilityGet(charid, 1);
            if (level == 1)
                abilityGet(charid, 2);
            if (level == 2)
                abilityGet(charid, 3);
            if (level%6 == 3)
                abilityGet(charid, 4);
            if (level == 5)
                abilityGet(charid, 5);
            if ((level>6)&&(level%3==1))
                abilityGet(charid, 6);
            if (level == 11)
                abilityGet(charid, 7);
            if (level == 14)
                abilityGet(charid, 8);
            if (level == 17)
                abilityGet(charid, 9);
            if (level == 20)
                abilityGet(charid, 10);
        } else if (classid == 2) {
            if ((level%2 == 1)  || (level == 2))
                abilityGet(charid, 12);
            if (level == 1)
                abilityGet(charid, 11);
        } else if (classid == 3) {
            if ((level%2 == 1)  || (level == 2))
                abilityGet(charid, 14);
            if (level == 1)
                abilityGet(charid, 13);
        } else if (classid == 5) {
            if ((level%5 == 0)  || (level == 1))
                abilityGet(charid, 24);
        }
        return true;
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

    public boolean attChange (int charid, String att, int val) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor res = getData("Personagem",charid);
        if (att == "FOR")
            contentValues.put(CHAR_COLUMN_FOR,res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_FOR))+val);
        else if (att == "DES")
            contentValues.put(CHAR_COLUMN_FOR,res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_DES))+val);
        else if (att == "CON")
            contentValues.put(CHAR_COLUMN_FOR,res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CON))+val);
        else if (att == "INT")
            contentValues.put(CHAR_COLUMN_FOR,res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_INT))+val);
        else if (att == "SAB")
            contentValues.put(CHAR_COLUMN_FOR,res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_SAB))+val);
        else if (att == "CAR")
            contentValues.put(CHAR_COLUMN_FOR,res.getInt(res.getColumnIndex(DBHelper.CHAR_COLUMN_CAR))+val);
        db.update(CHAR_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(charid) } );
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
        else if  (table == "pericias") {
            res = db.rawQuery("select * from pericias", null);
            column = SKILL_COLUMN_NAME;
        }
        else if  (table == "talentos") {
            res = db.rawQuery("select * from talentos", null);
            column = FEAT_COLUMN_NAME;
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
