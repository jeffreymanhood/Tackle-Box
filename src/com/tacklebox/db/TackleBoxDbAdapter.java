package com.tacklebox.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * User: Call me Ismail
 * Date: 2/13/11
 * Time: 8:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class TackleBoxDbAdapter
{
    private Context context;
	private SQLiteOpenHelper dbHelper;
	private SQLiteDatabase database;

    private static final String TAG = "TackleBoxDbAdapter";
	private static final String DATABASE_LURE_TABLE = "lures_tbl";
    private static final String DATABASE_ROD_SETUP_TABLE = "rod_setup_tbl";
    private static final String DATABASE_RODS_TBL = "rods_tbl";

    private static final String KEY_ROWID = "_id";
	public static final String KEY_LURE_BRAND = "brand";
	public static final String KEY_LURE_MODEL = "model";
	public static final String KEY_LURE_CATEGORY = "category";
	public static final String KEY_LURE_SIZE = "size";
	public static final String KEY_LURE_COLOR = "color";
	public static final String KEY_LURE_SPECIES = "species";

    public static final String COL_ROD_SETUP_ROD_NAME = "rod_name";
    public static final String COL_ROD_SETUP_REEL_NAME = "reel_name";
    public static final String COL_ROD_SETUP_LINE_NAME = "line_name";
    private static final String COL_ROD_SETUP_IS_SELECTED = "is_selected";

    public static final String COL_ROD_BRAND = "brand";
    public  static final String COL_ROD_MODEL = "model";
    public static final String COL_ROD_CATEGORY = "category";
    public static final String COL_ROD_SIZE = "size";
    public static final String COL_ROD_LINE_WEIGHT = "line_weight";
    public static final String COL_ROD_POWER_RATING = "power_rating";

    public TackleBoxDbAdapter(Context context)
        {
            this.context = context;
        }

        public TackleBoxDbAdapter open()
        {
            dbHelper = new DataBaseHelper(context);
            database = dbHelper.getWritableDatabase();
            return this;
        }

        public Cursor fetchAllLures()
        {
            return database.query(DATABASE_LURE_TABLE,
                    new String[] {KEY_ROWID,
                            KEY_LURE_BRAND,
                            KEY_LURE_MODEL,
                            KEY_LURE_CATEGORY,
                            KEY_LURE_SIZE,
                            KEY_LURE_COLOR,
                            KEY_LURE_SPECIES},
                    null, null, null, null, null);
        }

        public void addLure(String lureBrand)
        {
            ContentValues values = new ContentValues();
            values.put(KEY_LURE_BRAND, lureBrand);
            values.put(KEY_LURE_MODEL, "_BRAND_");
            values.put(KEY_LURE_CATEGORY, "_CATEGORY_");
            values.put(KEY_LURE_SIZE, "_SIZE_");
            values.put(KEY_LURE_COLOR, "_COLOR_");
            values.put(KEY_LURE_SPECIES, "_SPECIES_");
            database.insert(DATABASE_LURE_TABLE, null, values);
        }



    public Cursor fetchRodSelection()
    {
        return database.query(DATABASE_ROD_SETUP_TABLE,
                new String[] {
                        COL_ROD_SETUP_ROD_NAME,
                        COL_ROD_SETUP_REEL_NAME,
                        COL_ROD_SETUP_LINE_NAME,
                        COL_ROD_SETUP_IS_SELECTED
                        },
                        COL_ROD_SETUP_IS_SELECTED+"=1",
                        null, null, null, null);
    }

    public Cursor fetchAllRods()
    {
        return database.query(DATABASE_RODS_TBL,
                new String[] {
                    KEY_ROWID,
                    COL_ROD_BRAND,
                    COL_ROD_MODEL,
                    COL_ROD_CATEGORY,
                    COL_ROD_SIZE,
                    COL_ROD_LINE_WEIGHT,
                    COL_ROD_POWER_RATING
                },
                null, null, null, null, null);
    }

    public void createRod(String brand, String model, String category, String size, String lineWeight, String powerRating)
    {
        ContentValues newRodValues = new ContentValues();
        newRodValues.put(COL_ROD_BRAND, brand);
        newRodValues.put(COL_ROD_MODEL, model);
        newRodValues.put(COL_ROD_CATEGORY, category);
        newRodValues.put(COL_ROD_SIZE, size);
        newRodValues.put(COL_ROD_LINE_WEIGHT, lineWeight);
        newRodValues.put(COL_ROD_POWER_RATING, powerRating);
        database.insert(DATABASE_RODS_TBL, null, newRodValues);
    }

    public Cursor fetchRodNameById(long id)
    {
        return database.query(DATABASE_RODS_TBL,
                new String[]{
                        COL_ROD_BRAND,
                        COL_ROD_MODEL
                },
                KEY_ROWID+"="+id,
                null, null, null, null);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper
	{

		private static final String DB_NAME = "fish_tracker_data";
		private static final int DB_VERSION = 2;				//TODO: what does this mean?
		static final String CREATE_DATABASE_LURE_TABLE =
			"create table " + DATABASE_LURE_TABLE +
			"	("+ KEY_ROWID + " integer primary key autoincrement, " +		//TODO: is this wise to have them all named _id?
			"	" + KEY_LURE_BRAND + " text not null, " +
			"	" + KEY_LURE_MODEL+ " text not null, " +
			"	" + KEY_LURE_CATEGORY + " text not null, " +
			"	" + KEY_LURE_SIZE + " text not null, " +
			"	" + KEY_LURE_COLOR + " text not null, " +
			"	" + KEY_LURE_SPECIES + " text not null);";

        static final String CREATE_DATABASE_ROD_SETUP_TABLE =
			"create table " + DATABASE_ROD_SETUP_TABLE +
			"   (" + KEY_ROWID + " integer primary key autoincrement, " +
			"   " + COL_ROD_SETUP_ROD_NAME + " text not null, " +
			"   " + COL_ROD_SETUP_REEL_NAME + " text not null, " +
			"   " + COL_ROD_SETUP_LINE_NAME + " text not null, " +
			"   " + COL_ROD_SETUP_IS_SELECTED + " integer not null);";

        static final String CREATE_DATABASE_RODS_TABLE =
			"create table " + DATABASE_RODS_TBL +
			"	(" + KEY_ROWID + " integer primary key autoincrement, " +		//TODO: is this wise to have them all named _id?
			"	" + COL_ROD_BRAND + " text not null, " +
			"	" + COL_ROD_MODEL + " text not null, " +
			"	" + COL_ROD_CATEGORY + " text not null, " +
			"	" + COL_ROD_SIZE + " text not null, " +
			"	" + COL_ROD_LINE_WEIGHT + " text not null, " +
			"	" + COL_ROD_POWER_RATING + " text not null);";
			/*+
			"create table reels_tbl " +
			"	(_id integer primary key autoincrement, " +		//TODO: is this wise to have them all named _id?
			"	brand text not null, " +
			"	model text not null, " +
			"	category text not null, " +
			"	gear_ratio text not null, " +
			"	reel_weight text not null, " +
			"	max_line_weight text not null); " +
			"create table lines_tbl " +
			"	(_id integer primary key autoincrement, " +		//TODO: is this wise to have them all named _id?
			"	brand text not null, " +
			"	type text not null, " +
			"	weight text not null, " +
			"	description text not null);";     */

		public DataBaseHelper(Context context, String name,
				SQLiteDatabase.CursorFactory factory, int version)
		{
			super(context, name, factory, version);
		}

		public DataBaseHelper(Context context)
		{
			this(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(CREATE_DATABASE_LURE_TABLE);
            db.execSQL(CREATE_DATABASE_ROD_SETUP_TABLE);
            db.execSQL(CREATE_DATABASE_RODS_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
		}

	}
}
