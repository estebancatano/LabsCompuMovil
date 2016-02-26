package co.edu.udea.compumovil.gr5.lab1ui;

import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    StringBuilder informacionContacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtiene la referencia de los widgets
        final EditText txtNombres = (EditText) findViewById(R.id.txt_nombres);
        final EditText txtApellidos = (EditText) findViewById(R.id.txt_apellidos);
        final AutoCompleteTextView txtPais = (AutoCompleteTextView) findViewById(R.id.txt_pais);
        final EditText txtTelefono = (EditText) findViewById(R.id.txt_telefono);
        final EditText txtDireccion = (EditText) findViewById(R.id.txt_direccion);
        final EditText txtCorreoElectronico = (EditText) findViewById(R.id.txt_correo_electronico);
        final EditText txtNacimiento = (EditText) findViewById(R.id.txt_nacimiento);
        final RadioButton rbMasculino = (RadioButton) findViewById(R.id.rb_masculino);
        final RadioButton rbFemenino = (RadioButton) findViewById(R.id.rb_femenino);
        final Spinner spinPasatiempos = (Spinner) findViewById(R.id.spin_pasatiempos);
        final CheckBox cbFavorito = (CheckBox) findViewById(R.id.cb_favorito);
        final Button btnMostrar = (Button) findViewById(R.id.btn_mostrar);
        final TextView lblInfo = (TextView) findViewById(R.id.lbl_info);


        // Obtiene el string array
        String[] paises = getResources().getStringArray(R.array.lista_paises);
        String[] pasatiempos = getResources().getStringArray(R.array.lista_pasatiempos);

        // Crea el adaptador y se asigna al AutoCompleteTextView
        ArrayAdapter<String> adaptadorPaises = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, paises);
        txtPais.setAdapter(adaptadorPaises);

        ArrayAdapter<String> adaptadorPasatiempos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pasatiempos);
        adaptadorPaises.setDropDownViewResource(R.layout.menu_spinner);
        spinPasatiempos.setAdapter(adaptadorPasatiempos);

        // Evento en el campo de fecha de nacimiento
        txtNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dialog = new DateDialog(v);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                dialog.show(ft, String.valueOf(R.string.seleccion_fecha));

            }
        });

        // Se le asigna el evento al botón
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
                String sexo;
                boolean favorito;

                nombres = String.valueOf(txtNombres.getText());
                if (!validarCampo(nombres,R.string.nombres)){
                     return;
                }
                apellidos = String.valueOf(txtApellidos.getText());
                if (!validarCampo(apellidos,R.string.apellidos)){
                    return;
                }
                pais = String.valueOf(txtPais.getText());
                if (!validarCampo(pais,R.string.pais)){
                    return;
                }
                telefono = String.valueOf(txtTelefono.getText());
                if (!validarCampo(telefono,R.string.telefono)){
                    return;
                }
                direccion = String.valueOf(txtDireccion.getText());
                if (!validarCampo(direccion,R.string.direccion)){
                    return;
                }
                correoElectronico = String.valueOf(txtCorreoElectronico.getText());
                if (!validarCampo(correoElectronico,R.string.direccion)){
                    return;
                }

                if(rbMasculino.isChecked()){
                    sexo = getResources().getString(R.string.masculino);
                }else{
                    if(rbFemenino.isChecked()){
                        sexo = getResources().getString(R.string.femenino);
                    }else{
                        String mensaje = getResources().getString(R.string.no_texto)+ ": " + getResources().getString(R.string.sexo);
                        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                nacimiento = String.valueOf(txtNacimiento.getText());
                if (!validarCampo(nacimiento,R.string.nacimiento)){
                    return;
                }

                favorito = cbFavorito.isChecked();
                pasatiempo = spinPasatiempos.getSelectedItem().toString();
                informacionContacto = new StringBuilder();
                llenarStringBuilder(nombres,R.string.nombres,false);
                llenarStringBuilder(apellidos,R.string.apellidos,false);
                llenarStringBuilder(pais,R.string.pais,false);
                llenarStringBuilder(telefono,R.string.telefono,false);
                llenarStringBuilder(direccion,R.string.direccion,false);
                llenarStringBuilder(correoElectronico,R.string.correo_electronico,false);
                llenarStringBuilder(nacimiento,R.string.nacimiento,false);
                llenarStringBuilder(sexo,R.string.sexo,false);

                llenarStringBuilder(pasatiempo,R.string.pasatiempo,false);
                if(favorito){
                    llenarStringBuilder(getResources().getString(R.string.verdadero),R.string.favorito,true);
                }else{
                    llenarStringBuilder(getResources().getString(R.string.falso),R.string.favorito,true);
                }
                lblInfo.setText(informacionContacto.toString());
            }
        });
    }
    private void llenarStringBuilder(String campo, int referencia, boolean ultimaLinea){
        informacionContacto.append(getResources().getText(referencia));
        informacionContacto.append(": ");
        informacionContacto.append(campo);
        if (!ultimaLinea){
            informacionContacto.append("\n");
        }
    }
    private boolean validarCampo(String campo, int referencia){
         String mensaje;
         boolean retorno = true;
         if (campo.isEmpty()){
            mensaje = getResources().getString(R.string.no_texto)+ ": " + getResources().getString(referencia);
            Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
            retorno = false;
        }
         return retorno;
    }

}
