package co.edu.udea.compumovil.gr05.lab2apprun;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBHelper;
import co.edu.udea.compumovil.gr05.lab2apprun.model.Usuario;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfil extends Fragment {

    public static final String TAG_USER = "user";
    private ImageView ivPerfil;
    private TextView lblUsuario;
    private TextView lblCorreo;
    private DBHelper dbHelper;

    public FragmentPerfil() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        String usuario = getArguments().getString(TAG_USER);

        ivPerfil = (ImageView) view.findViewById(R.id.iv_usuario);
        lblUsuario = (TextView) view.findViewById(R.id.lbl_nombre_usuario);
        lblCorreo = (TextView) view.findViewById(R.id.lbl_email);

        dbHelper = new DBHelper(getContext());
        Usuario usuarioDB = dbHelper.consultarUsuario(usuario);
        if (usuarioDB.getFoto() != null) {
            ivPerfil.setImageBitmap(BitmapFactory.decodeByteArray(usuarioDB.getFoto(), 0, usuarioDB.getFoto().length));
        }
        lblUsuario.setText(usuarioDB.getUsuario());
        lblCorreo.setText(usuarioDB.getEmail());

        // Inflate the layout for this fragment
        return view;
    }

}
