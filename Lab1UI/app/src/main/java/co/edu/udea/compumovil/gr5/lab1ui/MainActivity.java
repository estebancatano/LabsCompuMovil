package co.edu.udea.compumovil.gr5.lab1ui;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    StringBuilder informacionContacto;

    private TextView txtNacimiento;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Crea el widget Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        if(toolbar != null) {
            //Referencia la ActionBar como Toolbar
            setSupportActionBar(toolbar);
            //Atributos de la Toolbar
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setIcon(R.mipmap.logo_grupo5);
        }

        // Obtiene la referencia de los elementos de la vista
        final EditText txtNombres = (EditText) findViewById(R.id.txt_nombres);
        txtNombres.requestFocus();
        final EditText txtApellidos = (EditText) findViewById(R.id.txt_apellidos);
        final AutoCompleteTextView txtPais = (AutoCompleteTextView) findViewById(R.id.txt_pais);
        final EditText txtTelefono = (EditText) findViewById(R.id.txt_telefono);
        final EditText txtDireccion = (EditText) findViewById(R.id.txt_direccion);
        final EditText txtCorreoElectronico = (EditText) findViewById(R.id.txt_correo_electronico);
        txtNacimiento = (TextView) findViewById(R.id.txt_nacimiento);
        final RadioButton rbMasculino = (RadioButton) findViewById(R.id.rb_masculino);
        final RadioButton rbFemenino = (RadioButton) findViewById(R.id.rb_femenino);
        final Spinner spinPasatiempos = (Spinner) findViewById(R.id.spin_pasatiempos);
        final CheckBox cbFavorito = (CheckBox) findViewById(R.id.cb_favorito);
        final Button btnMostrar = (Button) findViewById(R.id.btn_mostrar);
        final TextView lblInfo = (TextView) findViewById(R.id.lbl_info);

        // Obtiene el string array
        String[] paises = getResources().getStringArray(R.array.lista_paises);
        String[] pasatiempos = getResources().getStringArray(R.array.lista_pasatiempos);

        // Crea el adaptador para los países
        ArrayAdapter<String> adaptadorPaises = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paises);
        txtPais.setAdapter(adaptadorPaises);

        // Crea el adaptador para los pasatiempos
        ArrayAdapter<String> adaptadorPasatiempos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pasatiempos);
        adaptadorPaises.setDropDownViewResource(R.layout.menu_spinner);

        //Establece el adaptador para el widget Spinner
        spinPasatiempos.setAdapter(adaptadorPasatiempos);

        //Carga la fecha actual en txtNacimiento
        //actualizarCampoFecha(obtenerFechaActual());

        // Evento en el campo de fecha de nacimiento
        txtNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePickerFragment = new DateDialog();
                datePickerFragment.show(getFragmentManager(), "Date");

            }
        });

        // Se le asigna el evento al botón de mostrar información
        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombres;
                String apellidos;
                String pais;
                String telefono;
                String direccion;
                String correoElectronico;
                String pasatiempo;
                String nacimiento;
                String genero;
                boolean favorito;
                boolean fechaValida=true;

                //Obtiene el dato y valida el campo
                nombres = String.valueOf(txtNombres.getText());
                if (!validarCampo(nombres,R.string.nombres)){
                    return;
                }

                //Obtiene el dato y valida el campo
                apellidos = String.valueOf(txtApellidos.getText());
                if (!validarCampo(apellidos,R.string.apellidos)){
                    return;
                }

                //Obtiene el dato y valida el campo
                pais = String.valueOf(txtPais.getText());
                if (!validarCampo(pais,R.string.pais)){
                    return;
                }

                //Obtiene el dato y valida el campo
                telefono = String.valueOf(txtTelefono.getText());
                if (!validarCampo(telefono,R.string.telefono)){
                    return;
                }

                //Obtiene el dato y valida el campo
                direccion = String.valueOf(txtDireccion.getText());
                if (!validarCampo(direccion,R.string.direccion)){
                    return;
                }

                //Obtiene el dato y valida el campo
                correoElectronico = String.valueOf(txtCorreoElectronico.getText());
                if (!validarCampo(correoElectronico,R.string.correo_electronico)){
                    return;
                }

                //Obtiene el dato y valida el campo
                nacimiento = String.valueOf(txtNacimiento.getText());
                if (!validarCampo(nacimiento,R.string.nacimiento)){
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
                    Toast.makeText(getApplicationContext(),R.string.fecha_invalida,Toast.LENGTH_SHORT).show();
                    return;
                }

                //Verifica selección del widget RadioGroup
                if(rbMasculino.isChecked()){
                    genero = getResources().getString(R.string.masculino);
                }else{
                    if(rbFemenino.isChecked()){
                        genero = getResources().getString(R.string.femenino);
                    }else{
                        String mensaje = getResources().getString(R.string.no_texto)+ ": " + getResources().getString(R.string.genero);
                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                //Obtiene item del widget Spinner
                pasatiempo = spinPasatiempos.getSelectedItem().toString();

                //Se pasan los datos al método que construye el texto a mostrar
                informacionContacto = new StringBuilder(getResources().getString(R.string.info_contacto) + "\n\n");
                llenarStringBuilder(nombres,R.string.nombres,false);
                llenarStringBuilder(apellidos,R.string.apellidos,false);
                llenarStringBuilder(pais,R.string.pais,false);
                llenarStringBuilder(telefono,R.string.telefono,false);
                llenarStringBuilder(direccion,R.string.direccion,false);
                llenarStringBuilder(correoElectronico,R.string.correo_electronico,false);
                llenarStringBuilder(nacimiento,R.string.nacimiento,false);
                llenarStringBuilder(genero,R.string.genero,false);
                llenarStringBuilder(pasatiempo,R.string.pasatiempo,false);

                //Obtiene selección del widget CheckBox
                favorito = cbFavorito.isChecked();

                //Verifica el estado del widget CheckBox
                if(favorito){
                    llenarStringBuilder(getResources().getString(R.string.verdadero),R.string.favorito,true);
                }else{
                    llenarStringBuilder(getResources().getString(R.string.falso),R.string.favorito,true);
                }
                lblInfo.setText(informacionContacto.toString());
            }
        });
    }

    //Construye el texto a mostrar
    private void llenarStringBuilder(String campo, int referencia, boolean ultimaLinea){
        String referenciaMayus = getResources().getText(referencia).toString().toUpperCase();
        informacionContacto.append(referenciaMayus);
        informacionContacto.append(": "+"\n");
        informacionContacto.append(campo);
        if (!ultimaLinea){
            informacionContacto.append("\n\n");
        }
    }

    //Verifica si un campo de texto es vacío
    private boolean validarCampo(String campo, int referencia){
        String mensaje;
        boolean retorno = true;
        if (campo.trim().isEmpty()){
            mensaje = getResources().getString(R.string.no_texto)+ ": " + getResources().getString(referencia);
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
            retorno = false;
        }
        return retorno;
    }

    //Obtiene la fecha seleccionada del widget DatePicker
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
        txtNacimiento.setText(stringBuilder);
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
