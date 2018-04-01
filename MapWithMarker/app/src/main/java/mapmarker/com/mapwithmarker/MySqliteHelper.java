package mapmarker.com.mapwithmarker;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MarkersDb";
    public static final String TABLE_NAME = "LocationTable";
    private static final int DATABASE_VERSION = 2;
    public static final String COLUMN_NAME = "gpselement";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_ID = "_id";
    // Database creation sql statement
    private final String CREATE_TABLE = "create table "
            + TABLE_NAME + " ("+COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
            + " VARCHAR(255) not null, " + COLUMN_LAT + " REAL not null, "
            + COLUMN_LNG + " REAL not null);";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
//                Toast.makeText(context, "onCreate Called", Toast.LENGTH_SHORT).show();

            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
//            Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
//               System.out.print( e.printStackTrace());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
//                Toast.makeText(context, "onUpgrade Called", Toast.LENGTH_SHORT).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
//                Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
