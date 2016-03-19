package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    private String name;
    private Uri fileUri;

    private Uri mCapturedImageURI;

    private byte[] foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        foto = null;
        name = Environment.getExternalStorageDirectory() + "/test.jpg";
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
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, codigo);
                    }
                } else {
                    foto = null;
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FOTO_GALERIA) {
                if (data != null) {
                    Uri imagenSeleccionada = data.getData();
                    InputStream is = null;
                    try {
                        is = getContentResolver().openInputStream(imagenSeleccionada);
                        foto = getBytes(is);
                        String mensaje = getResources().getString(R.string.foto_cargada);
                        Snackbar.make(btnSubir, mensaje, Snackbar.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == FOTO_CAMARA) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if (photo != null) {
                    photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                }
                byte[] byteArray = stream.toByteArray();
                String mensaje = getResources().getString(R.string.foto_cargada);
                Snackbar.make(btnSubir, mensaje, Snackbar.LENGTH_LONG).show();
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
            getSupportActionBar().setTitle(R.string.title_registro);
            getSupportActionBar().setIcon(R.mipmap.logo_grupo5);

        }
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        name = "file:" + image.getAbsolutePath();
        return image;
    }
}
