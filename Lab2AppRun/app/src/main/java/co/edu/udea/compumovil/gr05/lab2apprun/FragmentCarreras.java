package co.edu.udea.compumovil.gr05.lab2apprun;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.List;

import co.edu.udea.compumovil.gr05.lab2apprun.dbapprun.DBHelper;
import co.edu.udea.compumovil.gr05.lab2apprun.model.Evento;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCarreras extends Fragment implements View.OnClickListener{

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageButton fab;
    private DBHelper dbHelper;

    public FragmentCarreras() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carreras, container, false);
        dbHelper = new DBHelper(getContext());

        List<Evento> listaCarreras;
        listaCarreras = dbHelper.consultarCarreras();

        // Obtener el Recycler
        recycler = (RecyclerView) view.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);

        // Crear un nuevo adaptador
        adapter = new AdapterCarreras(getContext(),listaCarreras);
        adapter.notifyDataSetChanged();
        recycler.setAdapter(adapter);

        // Obtener el botón flotante
        fab = (ImageButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.fab:
                intent = new Intent(getActivity(),ActivityNuevaCarrera.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
