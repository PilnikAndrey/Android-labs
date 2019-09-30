package pilnik.belstu.lab4;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactt.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "contactt"; // название таблицы в бд
    // названия столбцов
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_LINK = "link";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE contactt (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_EMAIL
                + " TEXT, " + COLUMN_LOCATION + " TEXT,"+COLUMN_PHONE_NUMBER+" INTEGER,"+COLUMN_LINK+" TEXT);");
        // добавление начальных данных
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_EMAIL
                + ", " + COLUMN_LOCATION  + ","+COLUMN_PHONE_NUMBER+","+COLUMN_LINK+") VALUES ('SDFSDF', '1981',534534,'HFGHFGFG');");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }
    public void insert(SQLiteDatabase db, String email, String location, int number, String link){
        db.execSQL("INSERT INTO "+ TABLE +" (" + COLUMN_EMAIL
                + ", " + COLUMN_LOCATION  + ","+COLUMN_PHONE_NUMBER+","+COLUMN_LINK+") VALUES ("+email+","+ location+","+ number+","+ link+");");
    }
}