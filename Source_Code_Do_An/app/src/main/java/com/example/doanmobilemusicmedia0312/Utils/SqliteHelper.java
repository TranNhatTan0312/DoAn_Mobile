package com.example.doanmobilemusicmedia0312.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.doanmobilemusicmedia0312.Model.MusicModel;
import com.example.doanmobilemusicmedia0312.Model.SearchSongModel;

import java.util.ArrayList;

public class SqliteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "s2playdb";

    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "playlist";
    private static final String ID_COL = "id";
    private static final String ID_SONG_COL = "id_song";

    private static final String NAME_COL = "name";
    private static final String SINGER_COL = "singer";
    private static final String LENGTH_COL = "length";
    private static final String GENRE_COL = "genre";
    private static final String URL_COL = "url";
    private static final String DATE_RELEASE_COL = "date_release";
    private static final String COVER_IMAGE_COL = "cover_image";



    public SqliteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE "+TABLE_NAME);

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_SONG_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + SINGER_COL + " TEXT,"
                + LENGTH_COL + " TEXT,"
                + GENRE_COL + " TEXT,"
                + URL_COL + " TEXT,"
                + DATE_RELEASE_COL + " TEXT,"
                + COVER_IMAGE_COL + " TEXT)";


        db.execSQL(query);
    }


    public boolean addMusicToPlaylist(MusicModel song) {
        if (getMusicFromPlaylist(song.getId()) == null){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(ID_SONG_COL, song.getId());
            values.put(NAME_COL, song.getSongName());
            values.put(COVER_IMAGE_COL, song.getImageUrl());
            values.put(DATE_RELEASE_COL, song.getDateRelease());
            values.put(LENGTH_COL, song.getLength());
            values.put(GENRE_COL, song.getGenre());
            values.put(URL_COL, song.getSourceUrl());
            values.put(SINGER_COL, song.getSinger());

            long result = db.insert(TABLE_NAME, null, values);

            db.close();
            return result > 0;
        }
        return false;
    }
    public boolean deleteMusicFromPlaylist(String songId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, ID_SONG_COL + "=?", new String[]{songId});
        db.close();
        return result > 0;
    }
    public MusicModel getMusicFromPlaylist(String songId){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ ID_SONG_COL +"='"+songId +"'";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            MusicModel music = new MusicModel();
            music.setId(cursor.getString(1));
            music.setSongName(cursor.getString(2));
            music.setSinger(cursor.getString(3));
            music.setLength(cursor.getString(4));
            music.setGenre(cursor.getString(5));
            music.setSourceUrl(cursor.getString(6));
            music.setDateRelease(cursor.getString(7));
            music.setImageUrl(cursor.getString(8));

            return music;
        }
        return null;
    }
    public ArrayList<MusicModel> showPlaylist(){
        ArrayList<MusicModel>  playList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            MusicModel music = new MusicModel();
            music.setId(cursor.getString(1));
            music.setSongName(cursor.getString(2));
            music.setSinger(cursor.getString(3));
            music.setLength(cursor.getString(4));
            music.setGenre(cursor.getString(5));
            music.setSourceUrl(cursor.getString(6));
            music.setDateRelease(cursor.getString(7));
            music.setImageUrl(cursor.getString(8));

            playList.add(music);
            cursor.moveToNext();
        }

        return playList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
