package co.edu.udea.compumovil.gr05.lab4weather;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInformation extends DialogFragment{

    private static final String URL_IMAGEN = "http://openweathermap.org/img/w/";
    private TextView lblCiudad;
    private TextView lblTemperatura;
    private TextView lblHumedad;
    private TextView lblDescripcion;
    private ImageView imagenClima;
    private RequestQueue requestQueue;



    public FragmentInformation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        getDialog().setTitle(getResources().getString(R.string.estado_clima));

        lblCiudad = (TextView) view.findViewById(R.id.lbl_ciudad);
        lblTemperatura = (TextView) view.findViewById(R.id.lbl_temperatura);
        lblHumedad = (TextView) view.findViewById(R.id.lbl_humedad);
        lblDescripcion = (TextView) view.findViewById(R.id.lbl_descripcion);
        imagenClima = (ImageView) view.findViewById(R.id.imagen_clima);

        Bundle parametros = getArguments();

        lblCiudad.setText(parametros.getString(MainActivity.TAG_CIUDAD));
        lblTemperatura.setText(parametros.getString(MainActivity.TAG_TEMP)+"°C");
        lblHumedad.setText(parametros.getString(MainActivity.TAG_HUMEDAD)+"%");
        lblDescripcion.setText(parametros.getString(MainActivity.TAG_DESCRIPCION));

        obtenerImagen(parametros.getString(MainActivity.TAG_IMAGEN));
        return view;
    }

    private void obtenerImagen(String icon) {
        requestQueue = Volley.newRequestQueue(getContext());
        String urlImagen = URL_IMAGEN + icon + ".png";

        ImageRequest imageRequest = new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imagenClima.setImageBitmap(response);
            }
        }, 0, 0, null, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),getResources().getString(R.string.no_image),Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(imageRequest);
    }


}
