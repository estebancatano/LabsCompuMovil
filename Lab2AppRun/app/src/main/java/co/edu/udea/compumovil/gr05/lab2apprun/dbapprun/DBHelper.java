package co.edu.udea.compumovil.gr05.lab2apprun.dbapprun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Vector;

import co.edu.udea.compumovil.gr05.lab2apprun.model.Usuario;

/**
 * Created by asus on 11/03/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    public DBHelper(Context context) {
        super(context, DBAppRun.DB_NAME, null, DBAppRun.DB_VERSION);

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
        sql = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, " +
                        "%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s BLOB)",
                DBAppRun.TABLE_EVENTS, TableColumnsEvents.ID, TableColumnsEvents.NOMBRE,
                TableColumnsEvents.DESCRIPCION, TableColumnsEvents.DISTANCIA, TableColumnsEvents.LUGAR,
                TableColumnsEvents.FECHA, TableColumnsEvents.TELEFONO, TableColumnsEvents.EMAIL,
                TableColumnsEvents.FOTO);

        //Sentencia para crear la tabla
        Log.d(TAG, "onCreate with SQL: " + sql);
        db.execSQL(sql);

        insertInitialDates();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql;

        sql = String.format("DROP TABLE IF EXISTS %s", DBAppRun.TABLE_USERS);
        // Sentencia para borrar la tabla
        Log.d(TAG, "onUpdrage with SQL: " + sql);
        db.execSQL(sql);

        sql = String.format("DROP TABLE IF EXISTS %s", DBAppRun.TABLE_EVENTS);
        // Sentencia para borrar la tabla
        Log.d(TAG, "onUpdrage with SQL: " + sql);
        db.execSQL(sql);

        onCreate(db);
        insertInitialDates();
    }

    public void insertInitialDates() {
        ContentValues values = new ContentValues();
        values.put(TableColumnsUser.USUARIO, "xx");
        values.put(TableColumnsUser.CONTRASEÑA, "yy");
        values.put(TableColumnsUser.EMAIL, "xx@a.com");
        values.put(TableColumnsUser.FOTO, (byte[]) null);
        insertar(DBAppRun.TABLE_USERS, values);
    }

    public void insertar(String tabla, ContentValues valores) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Se guarda la fila en la base de datos
        try {
            db.insertWithOnConflict(tabla, null, valores, SQLiteDatabase.CONFLICT_IGNORE);
            Log.d(TAG, "Insertado en la base de datos");
        } catch (Exception ex) {
            Log.e(TAG, "Error al insertar en la base de datos");
        } finally {
            db.close();
        }
    }

    public ArrayList<Usuario> consultarCarreras(String user, String pass) {
        String[] campos = new String[]{TableColumnsUser.EMAIL, TableColumnsUser.FOTO};
        String[] argumentos = new String[]{user, pass};
        String consulta = TableColumnsUser.USUARIO + "=? AND " + TableColumnsUser.CONTRASEÑA + "=?";
        ArrayList<Usuario> retornoConsulta = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            Cursor c = db.query(DBAppRun.TABLE_USERS, campos, consulta, argumentos, null, null, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    Usuario usuario = new Usuario();
                    usuario.setUsuario(user);
                    usuario.setContrasena(pass);
                    usuario.setEmail(c.getString(c.getColumnIndex(TableColumnsUser.EMAIL)));
                    usuario.setFoto(c.getBlob(c.getColumnIndex(TableColumnsUser.FOTO)));
                    retornoConsulta.add(usuario);
                } while (c.moveToNext());
                Log.d(TAG, "Se ha consultado en la base de datos");
            }else{
                Log.d(TAG,"No hay registro");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error al consultar en la base de datos");
        } finally {
            db.close();
        }
        return retornoConsulta;
    }

    public Usuario consultarUsuarioInicio(String user, String pass) {
        String[] campos = new String[]{TableColumnsUser.EMAIL, TableColumnsUser.FOTO};
        String[] argumentos = new String[]{user, pass};
        String consulta = TableColumnsUser.USUARIO + "=? AND " + TableColumnsUser.CONTRASEÑA + "=?";
        SQLiteDatabase db = this.getWritableDatabase();
        Usuario usuario = null;
        try {
            Cursor c = db.query(DBAppRun.TABLE_USERS, campos, consulta, argumentos, null, null, null);
            //Nos aseguramos de que existe al menos un registro
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    usuario = new Usuario();
                    usuario.setUsuario(user);
                    usuario.setContrasena(pass);
                    usuario.setEmail(c.getString(c.getColumnIndex(TableColumnsUser.EMAIL)));
                    usuario.setFoto(c.getBlob(c.getColumnIndex(TableColumnsUser.FOTO)));
                } while (c.moveToNext());
                Log.d(TAG, "Se ha consultado en la base de datos");
            }else{
                Log.d(TAG,"No hay registro");
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error al consultar en la base de datos");
        } finally {
            db.close();
        }
        return usuario;
    }

    public boolean consultarUsuarioRegistro(String user){
        return false;
    }
}
