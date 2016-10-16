package com.example.iago.myapplicationtest;

import java.util.LinkedList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "PokemonDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create Pokemon table
        String CREATE_Pokemon_TABLE = "CREATE TABLE Pokemons ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT)";

        // create Pokemons table
        db.execSQL(CREATE_Pokemon_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older Pokemons table if existed
        db.execSQL("DROP TABLE IF EXISTS Pokemons");

        // create fresh Pokemons table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) Pokemon + get all Pokemons + delete all Pokemons
     */

    // Pokemons table name
    private static final String TABLE_PokemonS = "Pokemons";

    // Pokemons Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_Nome = "nome";

    private static final String[] COLUMNS = {KEY_ID,KEY_Nome};

    public void addPokemon(Pokemon Pokemon){
        Log.d("addPokemon", Pokemon.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_Nome, Pokemon.getNome()); // get Nome

        // 3. insert
        db.insert(TABLE_PokemonS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Pokemon getPokemon(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_PokemonS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build Pokemon object
        Pokemon Pokemon = new Pokemon();
        Pokemon.setId(Integer.parseInt(cursor.getString(0)));
        Pokemon.setNome(cursor.getString(1));

        Log.d("getPokemon("+id+")", Pokemon.toString());

        // 5. return Pokemon
        return Pokemon;
    }

    // Get All Pokemons
    public List<Pokemon> getAllPokemons() {
        List<Pokemon> Pokemons = new LinkedList<Pokemon>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PokemonS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build Pokemon and add it to list
        Pokemon Pokemon = null;
        if (cursor.moveToFirst()) {
            do {
                Pokemon = new Pokemon();
                Pokemon.setId(Integer.parseInt(cursor.getString(0)));
                Pokemon.setNome(cursor.getString(1));

                // Add Pokemon to Pokemons
                Pokemons.add(Pokemon);
            } while (cursor.moveToNext());
        }

        Log.d("getAllPokemons()", Pokemons.toString());

        // return Pokemons
        return Pokemons;
    }

    // Updating single Pokemon
    public int updatePokemon(Pokemon Pokemon) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("Nome", Pokemon.getNome()); // get Nome

        // 3. updating row
        int i = db.update(TABLE_PokemonS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(Pokemon.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single Pokemon
    public void deletePokemon(Pokemon Pokemon) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_PokemonS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(Pokemon.getId()) });

        // 3. close
        db.close();

        Log.d("deletePokemon", Pokemon.toString());

    }
}
