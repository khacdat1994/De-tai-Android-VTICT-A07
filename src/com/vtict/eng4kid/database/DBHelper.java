package com.vtict.eng4kid.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "English4KidDB";

	// table name

	private static final String TABLE_TOPIC = "tblTopic";
	private static final String TABLE_VOCABULARY = "tblVocabulary";

	// Topic Table Columns names
	private static final String KEY_TOPIC_ID = "TopicID";
	private static final String KEY_TOPIC_NAME = "TopicName";
	private static final String KEY_TOPIC_ICON = "TopicIcon";

	// Vocabulary Table Columns names
	private static final String KEY_VO_ID = "VoID";
	private static final String KEY_VO_TOPIC_ID = "TopicID";
	private static final String KEY_VO_NAME = "VoName";
	private static final String KEY_VO_IMAGE = "VoImage";
	private static final String KEY_VO_SOUND = "VoSound";
	private static final String KEY_VO_MEANING = "VoMeaning";

	//
	private SQLiteDatabase db;

	//
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// create tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String create_tblTopic = "CREATE TABLE " + TABLE_TOPIC + "("
				+ KEY_TOPIC_ID + " INTEGER PRIMARY KEY, " + KEY_TOPIC_NAME
				+ " TEXT NOT NULL, " + KEY_TOPIC_ICON + " TEXT NOT NULL" + ")";
		db.execSQL(create_tblTopic);

		String create_tblVocabulary = "CREATE TABLE " + TABLE_VOCABULARY + "("
				+ KEY_VO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ KEY_VO_NAME + " TEXT NOT NULL UNIQUE, " + KEY_VO_IMAGE
				+ " TEXT NOT NULL, " + KEY_VO_SOUND + " TEXT NOT NULL, "
				+ KEY_VO_MEANING + " TEXT NOT NULL, " + KEY_VO_TOPIC_ID
				+ " INTEGER NOT NULL CONSTRAINT " + KEY_TOPIC_ID
				+ " REFERENCES tblTopic(TopicID) ON DELETE CASCADE" + ")";
		db.execSQL(create_tblVocabulary);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOCABULARY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPIC);
		// Create tables again
		onCreate(db);

	}

	// insert data into tblTopic

	public void insertTopic(Topic _topic) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues valuesTopic = new ContentValues();
		// insert data into tblTopic
		valuesTopic.put(KEY_TOPIC_ID, _topic.getTopicID());
		valuesTopic.put(KEY_TOPIC_NAME, _topic.getTopicName());
		valuesTopic.put(KEY_TOPIC_ICON, _topic.getTopicIcon());
		db.insert(TABLE_TOPIC, null, valuesTopic);
		db.close();
	}

	public void insertVocab(Topic _topic, Vocabulary _vocab) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues valuesVocab = new ContentValues();
		// insert data into tblVocabulary
		valuesVocab.put(KEY_VO_TOPIC_ID, _topic.getTopicID()); // add khoa ngoai
		valuesVocab.put(KEY_VO_NAME, _vocab.getVoName());
		valuesVocab.put(KEY_VO_IMAGE, _vocab.getVoImage());
		valuesVocab.put(KEY_VO_SOUND, _vocab.getVoSound());
		valuesVocab.put(KEY_VO_MEANING, _vocab.getVoMeaning());

		db.insert(TABLE_VOCABULARY, null, valuesVocab);
		db.close();
	}

	public int delete(int _id) {
		db = this.getWritableDatabase();

		int count2 = db.delete(TABLE_VOCABULARY, KEY_VO_TOPIC_ID + "=?",
				new String[] { String.valueOf(_id) });
		int count = db.delete(TABLE_TOPIC, KEY_TOPIC_ID + "=?",
				new String[] { String.valueOf(_id) });

		db.close();

		return count;
	}

	

}
