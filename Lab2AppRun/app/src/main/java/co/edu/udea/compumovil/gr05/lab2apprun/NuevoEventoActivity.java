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

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBAppRun;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBHelper;
import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.TableColumnsEvents;

public class NuevoEventoActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private final static int FOTO_GALERIA = 90;
    private final static int FOTO_CAMARA = 100;

    private EditText txtNombre;
    private EditText txtDescripcion;
    private EditText txtDistancia;
    private EditText txtLugar;
    private EditText txtFecha;
    private EditText txtTelefono;
    private EditText txtCorreo;
    private Button btnCrearEvento;
    private Button btnSubirImagen;
    private RadioButton rbNoFoto;
    private RadioButton rbCamara;
    private RadioButton rbGaleria;
    private byte[] foto;
    private DBHelper dbHelper;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_evento);

        setToolbar();
        foto = null;

        txtNombre = (EditText) findViewById(R.id.txt_nombre_evento);
        txtNombre.requestFocus();
        txtDescripcion = (EditText) findViewById(R.id.txt_descripcion);
        txtDistancia = (EditText) findViewById(R.id.txt_distancia);
        txtLugar = (EditText) findViewById(R.id.txt_lugar);
        txtFecha = (EditText) findViewById(R.id.txt_fecha_Evento);
        txtTelefono = (EditText) findViewById(R.id.txt_telefono_evento);
        txtCorreo = (EditText) findViewById(R.id.txt_correo_evento);
        btnCrearEvento = (Button) findViewById(R.id.btn_crear_evento);
        btnSubirImagen = (Button) findViewById(R.id.btn_subir);
        rbNoFoto = (RadioButton) findViewById(R.id.rb_no_foto);
        rbCamara = (RadioButton) findViewById(R.id.rb_camara);
        rbGaleria = (RadioButton) findViewById(R.id.rb_galeria);

        btnCrearEvento.setOnClickListener(this);
        btnSubirImagen.setOnClickListener(this);
        txtFecha.setOnClickListener(this);
        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_crear_evento:
                String nombreEvento;
                String distanciaEvento;
                String lugarEvento;
                String fechaEvento;
                String telefonoEvento;
                String correoEvento;
                String descripcionEvento = "";

                // Extraer los datos ingresados
                nombreEvento = txtNombre.getText().toString();
                distanciaEvento = txtDistancia.getText().toString();
                lugarEvento = txtLugar.getText().toString();
                fechaEvento = txtFecha.getText().toString();
                telefonoEvento = txtTelefono.getText().toString();
                correoEvento = txtCorreo.getText().toString();
                descripcionEvento = txtDescripcion.getText().toString();

                // Verificar que se ingresen los datos
                if ("".compareTo(nombreEvento) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.nombre_evento);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if ("".compareTo(distanciaEvento) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.distancia_evento);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if ("".compareTo(lugarEvento) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.lugar_evento);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("".compareTo(fechaEvento) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.fecha_evento);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("".compareTo(lugarEvento) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.lugar_evento);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("".compareTo(telefonoEvento) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.telefono_evento);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if ("".compareTo(correoEvento) == 0) {
                    String mensaje = getResources().getString(R.string.no_texto) + ": " +
                            getResources().getString(R.string.correo_evento);
                    Snackbar.make(v, mensaje, Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // Se prepara el ContentValues para insertar el usuario
                ContentValues values = new ContentValues();
                values.put(TableColumnsEvents.NOMBRE, nombreEvento);
                values.put(TableColumnsEvents.DISTANCIA, distanciaEvento);
                values.put(TableColumnsEvents.LUGAR, lugarEvento);
                values.put(TableColumnsEvents.FECHA, fechaEvento);
                values.put(TableColumnsEvents.TELEFONO, telefonoEvento);
                values.put(TableColumnsEvents.EMAIL, correoEvento);
                values.put(TableColumnsEvents.DESCRIPCION, descripcionEvento);
                values.put(TableColumnsEvents.FOTO, foto);

                dbHelper.insertar(DBAppRun.TABLE_EVENTS, values);

                String mensaje = getResources().getString(R.string.evento_creado);
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

            case R.id.txt_fecha_Evento:
                DialogFragment datePickerFragment = new DateDialog();
                datePickerFragment.show(getFragmentManager(), "Date");
                break;
        }
    }

    private void setToolbar() {
        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if (toolbar != null) {
            //Referencia la ActionBar como Toolbar
            setSupportActionBar(toolbar);
            //Atributos de la Toolbar
            getSupportActionBar().setTitle(R.string.title_nuevo_evento);
            getSupportActionBar().setIcon(R.mipmap.logo_grupo5);
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
                String mensaje = getResources().getString(R.string.foto_cargada);
                Snackbar.make(btnSubirImagen, mensaje, Snackbar.LENGTH_LONG).show();
            }
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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear+1;
        mDay = dayOfMonth;
        int mDate[] = {mYear,mMonth,mDay};
        actualizarCampoFecha(mDate);
    }

    // Actualiza la fecha en el TextView
    private void actualizarCampoFecha(int[] date) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(date[0]);
        if (date[1] < 9){
            stringBuilder.append("-0");
        }else {
            stringBuilder.append("-");
        }
        stringBuilder.append(date[1]);
        if (date[2] < 10){
            stringBuilder.append("-0");
        }else {
            stringBuilder.append("-");
        }
        stringBuilder.append(date[2]);
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

        if (fechaActual.compareTo(fechaIngresada)<0){
            return false;
        }else {
            return true;
        }
    }
}
