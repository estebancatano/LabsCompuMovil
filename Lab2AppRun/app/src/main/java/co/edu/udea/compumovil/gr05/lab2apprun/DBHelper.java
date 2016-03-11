package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBAppRun;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.TableColumnsEvents;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.TableColumnsUser;

/**
 * Created by asus on 11/03/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    public DBHelper(Context context) {
        super(context, DBAppRun.DB_NAME, null , DBAppRun.DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        //Crear la tabla users
        sql = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT, %s TEXT, %s BLOB)",
                DBAppRun.TABLE_USERS, TableColumnsUser.USUARIO, TableColumnsUser.CONTRASEÑA,
                TableColumnsUser.EMAIL, TableColumnsUser.FOTO);

        //Sentencia para crear la tabla
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);

        //Crear la tabla events
        sql = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, " +
                        "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s BLOB)",
                DBAppRun.TABLE_EVENTS, TableColumnsEvents.ID, TableColumnsEvents.NOMBRE,
                TableColumnsEvents.DESCRIPCION, TableColumnsEvents.DISTANCIA, TableColumnsEvents.LUGAR,
                TableColumnsEvents.FECHA, TableColumnsEvents.TELEFONO, TableColumnsEvents.EMAIL,
                TableColumnsEvents.FOTO);

        //Sentencia para crear la tabla
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;

        sql = String.format("DROP TABLE IF EXISTS %s", DBAppRun.TABLE_USERS);
        // Sentencia para borrar la tabla
        Log.d(TAG,"onUpdrage with SQL: " + sql);
        db.execSQL(sql);

        sql = String.format("DROP TABLE IF EXISTS %s", DBAppRun.TABLE_EVENTS);
        // Sentencia para borrar la tabla
        Log.d(TAG,"onUpdrage with SQL: " + sql);
        db.execSQL(sql);

        onCreate(db);
        insertInitialDates();
    }

    private void insertInitialDates(){
        ContentValues values = new ContentValues();
        values.put(TableColumnsUser.USUARIO,"root");
        values.put(TableColumnsUser.CONTRASEÑA, "rootpass");

        insert(this,DBAppRun.TABLE_USERS, values);
    }

    public void insert(DBHelper dbHelper, String table, ContentValues values){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Se guarda la fila en la base de datos
        db.insertWithOnConflict(table,null,values,SQLiteDatabase.CONFLICT_IGNORE);

        db.close();
    }
}
