package najla.anatomy;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.util.ArrayList;


public class DB_Sqlite extends SQLiteOpenHelper {

    public static final String DBNAME = "appanatomy.db";
    public static final String DBLOCATION = Environment.getDataDirectory() + "/data/najla.anatomy/databases/";
    private Context mcontext;
    private SQLiteDatabase mDatabase;


    public DB_Sqlite(Context context) {
        super(context, DBNAME, null, 1);
        this.mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mcontext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }

        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }

    }

    public ArrayList  get_All_organName(int Sysid ) {
        ArrayList array = new ArrayList();

        openDatabase();
        Cursor rs = mDatabase.rawQuery("select * from Organ where SysId like '"+Sysid+"'", null);

        rs.moveToFirst();
        while (!rs.isAfterLast()) {
            String OrganName = rs.getString(rs.getColumnIndex("OrganName"));
            array.add(OrganName);
            rs.moveToNext();

        }
        rs.close();
        closeDatabase();
        return array;


        }


    public String get_All_sysInfo(int sysid) {
        String info;
        openDatabase();
        Cursor rs = mDatabase.rawQuery("select * from System where id like '"+sysid+"'", null);
        rs.moveToFirst();

        info = rs.getString(rs.getColumnIndex("SysInfo"));

        rs.close();
        closeDatabase();
        return info;
    }

    public String get_sysname(int sysid) {
        String info;
        openDatabase();
        Cursor rs = mDatabase.rawQuery("select * from System where id like '"+sysid+"'", null);
        rs.moveToFirst();

        info = rs.getString(rs.getColumnIndex("SysName"));

        rs.close();
        closeDatabase();
        return info;
    }

    public String get_sysImg(int id) {
        String  img;
        openDatabase();
        Cursor rs = mDatabase.rawQuery("select * from System where id like '"+id+"'", null);
        rs.moveToFirst();

        img = rs.getString(rs.getColumnIndex("SysImg"));

        rs.close();
        closeDatabase();
        return img;
    }

    public String  get_All_organImg(int id ) {

            String  img;
            openDatabase();
            Cursor rs = mDatabase.rawQuery("select * from Organ where id like '"+id+"'", null);
            rs.moveToFirst();

            img = rs.getString(rs.getColumnIndex("organImg1"));

            rs.close();
            closeDatabase();
            return img;
        }




        public String get_All_organInfo(String Organ_name) {
        String info;
        openDatabase();
        Cursor rs = mDatabase.rawQuery("select * from Organ where OrganName like '"+Organ_name+"'", null);
        rs.moveToFirst();

            info = rs.getString(rs.getColumnIndex("OrganInfo"));

        rs.close();
        closeDatabase();
        return info;
    }

    public String get_organImg(String Organ_name) {
        String  img;
        openDatabase();
        Cursor rs = mDatabase.rawQuery("select * from Organ where OrganName like '"+Organ_name+"'", null);
        rs.moveToFirst();

        img = rs.getString(rs.getColumnIndex("OrganImg"));

        rs.close();
        closeDatabase();
        return img;
    }

}