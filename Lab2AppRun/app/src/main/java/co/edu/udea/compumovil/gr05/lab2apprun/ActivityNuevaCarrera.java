package co.edu.udea.compumovil.gr05.lab2apprun;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBAppRun;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBHelper;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.TableColumnsEvents;

public class ActivityNuevaCarrera extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private final static int FOTO_GALERIA = 90;
    private final static int FOTO_CAMARA = 100;

    private EditText txtNombre;
    private EditText txtDescripcion;
    private EditText txtDistancia;
    private EditText txtLugar;
    private EditText txtFecha;
    private EditText txtTelefono;
    private EditText txtCorreo;
    private Button btnCrearCarrera;
    private Button btnSubirImagen;
    private RadioButton rbNoFoto;
    private RadioButton rbCamara;
    private RadioButton rbGaleria;
    private byte[] foto;
    private DBHelper dbHelper;
    private int mYear;
    private int mMonth;
    private int mDay;

    //Caracteres para validación de correo
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_carrera);

        setToolbar();
        foto = null;

        txtNombre = (EditText) findViewById(R.id.txt_nombre_carrera);
        txtNombre.requestFocus();
        txtDescripcion = (EditText) findViewById(R.id.txt_descripcion);
        txtDistancia = (EditText) findViewById(R.id.txt_distancia);
        txtLugar = (EditText) findViewById(R.id.txt_lugar);
        txtFecha = (EditText) findViewById(R.id.txt_fecha_carrera);
        txtTelefono = (EditText) findViewById(R.id.txt_telefono_carrera);
        txtCorreo = (EditText) findViewById(R.id.txt_correo_carrera);
        btnCrearCarrera = (Button) findViewById(R.id.btn_crear_carrera);
        btnSubirImagen = (Button) findViewById(R.id.btn_subir);
        rbNoFoto = (RadioButton) findViewById(R.id.rb_no_foto);
        rbCamara = (RadioButton) findViewById(R.id.rb_camara);
        rbGaleria = (RadioButton) findViewById(R.id.rb_galeria);
        actualizarCampoFecha(obtenerFechaActual());

        btnCrearCarrera.setOnClickListener(this);
        btnSubirImagen.setOnClickListener(this);
        txtFecha.setOnClickListener(this);
        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_crear_carrera:
                String nombreCarrera;
                String distanciaCarrera;
                String lugarCarrera;
                String fechaCarrera;
                String telefonoCarrera;
                String correoCarrera;
                String descripcionCarrera;
                boolean fechaValida=true;

                // Extraer los datos ingresados
                nombreCarrera = txtNombre.getText().toString();
                distanciaCarrera = txtDistancia.getText().toString();
                lugarCarrera = txtLugar.getText().toString();
                fechaCarrera = txtFecha.getText().toString();
                telefonoCarrera = txtTelefono.getText().toString();
                correoCarrera = txtCorreo.getText().toString();
                descripcionCarrera = txtDescripcion.getText().toString();

                //Obtiene el dato y valida el campo
                if (!validarCampo(nombreCarrera,R.string.nombre_carrera)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(distanciaCarrera,R.string.distancia_carrera)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(lugarCarrera,R.string.lugar_carrera)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(fechaCarrera,R.string.fecha_carrera)){
                    return;
                }

                try {
                    //Captura respuesta boolean del método validarFecha
                    fechaValida = validarFecha(obtenerFechaActual());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Determina si la fecha es válida
                if (!fechaValida){
                    Snackbar.make(v, getResources().getString(R.string.fecha_no_valida), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Obtiene el dato y valida el campo
                if (!validarCampo(telefonoCarrera,R.string.telefono_carrera)){
                    return;
                }
                //Obtiene el dato y valida el campo
                if (!validarCampo(correoCarrera,R.string.correo_carrera)){
                    return;
                }
                if (!checkEmail(correoCarrera)){
                    Snackbar.make(v, getResources().getString(R.string.correo_no_valido), Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // Se prepara el ContentValues para insertar la carrera
                ContentValues values = new ContentValues();
                values.put(TableColumnsEvents.NOMBRE, nombreCarrera);
                values.put(TableColumnsEvents.DISTANCIA, distanciaCarrera+" km");
                values.put(TableColumnsEvents.LUGAR, lugarCarrera);
                values.put(TableColumnsEvents.FECHA, fechaCarrera);
                values.put(TableColumnsEvents.TELEFONO, telefonoCarrera);
                values.put(TableColumnsEvents.EMAIL, correoCarrera);
                values.put(TableColumnsEvents.DESCRIPCION, descripcionCarrera);
                values.put(TableColumnsEvents.FOTO, foto);

                dbHelper.insertar(DBAppRun.TABLE_EVENTS, values);

                String mensaje = getResources().getString(R.string.carrera_creada);
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();

                this.finish();

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
                    // Verificar si hay aplicaciones disponibles
                    PackageManager packageManager = getPackageManager();
                    List activities = packageManager.queryIntentActivities(intent, 0);
                    boolean isIntentSafe = activities.size() > 0;

                    // Si hay, entonces ejecutamos la actividad
                    if (isIntentSafe) {
                        startActivityForResult(intent, codigo);
                    }
                } else {
                    foto = null;
                }
                break;

            case R.id.txt_fecha_carrera:
                DialogFragment datePickerFragment = new DateDialog();
                datePickerFragment.show(getFragmentManager(), "Date");
                break;
        }
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        //Referencia la ActionBar como Toolbar
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            //Atributos de la Toolbar
            ab.setTitle(R.string.title_nueva_carrera);
            ab.setIcon(R.mipmap.logo_grupo5);
        }
    }

    //Verifica si un campo de texto es vacío
    private boolean validarCampo(String campo, int referencia){
        String mensaje;
        boolean retorno = true;
        if (campo.trim().isEmpty()){
            mensaje = getResources().getString(R.string.no_texto)+ ": " + getResources().getString(referencia);
            Snackbar.make(findViewById(R.id.nuevo_evento), mensaje, Snackbar.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String mensaje;
            if (requestCode == FOTO_GALERIA) {
                if (data != null) {
                    Uri imagenSeleccionada = data.getData();
                    InputStream is = null;
                    try {
                        is = getContentResolver().openInputStream(imagenSeleccionada);
                        foto = getBytes(is);
                        mensaje = getResources().getString(R.string.foto_cargada);
                        Snackbar.make(btnSubirImagen, mensaje, Snackbar.LENGTH_LONG).show();
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
                mensaje = getResources().getString(R.string.foto_cargada);
                Snackbar.make(btnSubirImagen, mensaje, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear+1;
        mDay = dayOfMonth;
        int mDate[] = {mYear,mMonth,mDay};
        actualizarCampoFecha(mDate);
    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
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
    // Actualiza la fecha en el TextView
    private void actualizarCampoFecha(int[] date) {
        mYear = date[0];
        mMonth = date[1];
        mDay = date[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mYear);
        if (mMonth < 9){
            stringBuilder.append("-0");
        }else {
            stringBuilder.append("-");
        }
        stringBuilder.append(mMonth);
        if (mDay < 10){
            stringBuilder.append("-0");
        }else {
            stringBuilder.append("-");
        }
        stringBuilder.append(mDay);
        txtFecha.setText(stringBuilder);
    }

    //Obtiene la fecha actual del dispositivo
    public int[] obtenerFechaActual(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int date [] = {year,month+1,day};
        return date;
    }

    //Verifica que la fecha no sea superior a la actual
    public boolean validarFecha(int fecha[]) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = sdf.parse(fecha[0]+"-"+fecha[1]+"-"+fecha[2]);
        Date fechaIngresada = sdf.parse(mYear+"-"+mMonth+"-"+mDay);

        int i = fechaActual.compareTo(fechaIngresada);
        if (fechaActual.compareTo(fechaIngresada)!=1){
            return true;
        }else {
            return false;
        }
    }
}
