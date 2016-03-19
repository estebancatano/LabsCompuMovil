package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBAppRun;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBHelper;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.TableColumnsUser;
import co.edu.udea.compumovil.gr05.lab2apprun.model.Usuario;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int FOTO_GALERIA = 70;
    private final static int FOTO_CAMARA = 80;

    private Button btnGuardar;
    private Button btnSubir;
    private EditText txtUsuario;
    private EditText txtContrasena;
    private EditText txtCorreo;
    private RadioButton rbNoFoto;
    private RadioButton rbCamara;
    private RadioButton rbGaleria;
    private DBHelper dbHelper;

    private byte[] foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        foto = null;
        btnGuardar = (Button) findViewById(R.id.btn_guardar);
        btnSubir = (Button) findViewById(R.id.btn_subir);
        txtUsuario = (EditText) findViewById(R.id.txt_usuario);
        txtContrasena = (EditText) findViewById(R.id.txt_contrasena);
        txtCorreo = (EditText) findViewById(R.id.txt_correo);
        rbNoFoto = (RadioButton) findViewById(R.id.rb_no_foto);
        rbCamara = (RadioButton) findViewById(R.id.rb_camara);
        rbGaleria = (RadioButton) findViewById(R.id.rb_galeria);
        setToolbar();

        dbHelper = new DBHelper(this);

        btnGuardar.setOnClickListener(this);
        btnSubir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_guardar:
                String usuario;
                String contrasena;
                String correo;
                ArrayList<Usuario> listaUsuario;
                // Extraer los datos ingresados
                usuario = txtUsuario.getText().toString();
                contrasena = txtContrasena.getText().toString();
                correo = txtCorreo.getText().toString();
                boolean existeUsuario = false;
                // Verificar que se ingresen los datos
                if ("".compareTo(usuario) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.usuario);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if ("".compareTo(contrasena) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.contrasena);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if ("".compareTo(correo) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.correo);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                existeUsuario = dbHelper.consultarUsuarioRegistro(usuario);
                if (existeUsuario) {
                    String mensaje = getResources().getString(R.string.existe_usuario);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                } else {
                    // Se prepara el ContentValues para insertar el usuario
                    ContentValues values = new ContentValues();
                    values.put(TableColumnsUser.USUARIO, usuario);
                    values.put(TableColumnsUser.CONTRASEÑA, contrasena);
                    values.put(TableColumnsUser.EMAIL, correo);
                    values.put(TableColumnsUser.FOTO, foto);
                    dbHelper.insertar(DBAppRun.TABLE_USERS, values);

                    String mensaje = getResources().getString(R.string.usuario_registrado);
                    Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

                    Intent intentResult = new Intent();
                    intentResult.putExtra(InicioActivity.TAG_USUARIO, usuario);
                    intentResult.putExtra(InicioActivity.TAG_CONTRASENA, contrasena);
                    // Activity finished ok, return the data
                    setResult(RESULT_OK, intentResult);
                    this.finish();
                }
                break;
            case R.id.btn_subir:
                if (!rbNoFoto.isChecked()) {
                    Intent intent = null;
                    int codigo = 0;
                    if (rbCamara.isChecked()) {
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        codigo = FOTO_CAMARA;
                    } else if (rbGaleria.isChecked()) {
                        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        codigo = FOTO_GALERIA;
                    }
                    startActivityForResult(intent, codigo);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && (requestCode == FOTO_GALERIA || requestCode == FOTO_CAMARA)) {
            Uri imagenSeleccionada = data.getData();
            InputStream is = null;
            try {
                is = getContentResolver().openInputStream(imagenSeleccionada);
                foto = getBytes(is);
                String mensaje = getResources().getString(R.string.foto_cargada);
                Snackbar.make(btnSubir, mensaje, Snackbar.LENGTH_LONG).show();
                //Bitmap bitmap = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                //ImageView im = (ImageView) findViewById(R.id.img_view);
                //im.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if (toolbar != null) {
            //Referencia la ActionBar como Toolbar
            setSupportActionBar(toolbar);
            //Atributos de la Toolbar
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setIcon(R.mipmap.logo_grupo5);

        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
}
