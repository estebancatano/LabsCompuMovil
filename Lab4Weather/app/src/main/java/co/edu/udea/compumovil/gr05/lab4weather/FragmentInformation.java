package co.edu.udea.compumovil.gr05.lab4weather;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInformation extends DialogFragment {

    private TextView lblTemperatura;
    private TextView lblHumedad;
    private TextView lblDescripcion;
    private ImageView imagenClima;


    public FragmentInformation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        getDialog().setTitle(getResources().getString(R.string.estado_clima));

        lblTemperatura = (TextView) view.findViewById(R.id.lbl_temperatura);
        lblHumedad = (TextView) view.findViewById(R.id.lbl_humedad);
        lblDescripcion = (TextView) view.findViewById(R.id.lbl_descripcion);
        imagenClima = (ImageView) view.findViewById(R.id.imagen_clima);

        return view;
    }

}
