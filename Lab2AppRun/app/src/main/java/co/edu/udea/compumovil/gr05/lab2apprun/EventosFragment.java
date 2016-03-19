package co.edu.udea.compumovil.gr05.lab2apprun;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr05.lab2apprun.model.Evento;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventosFragment extends Fragment implements View.OnClickListener{

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public EventosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        ImageButton fab;

        // Inicializar Animes
        List<Evento> items = new ArrayList<>();

        items.add(new Evento("CARRERA 1","48","Medellín","01-04-2016"));
        items.add(new Evento("CARRERA 2","20","Bogotá","02-04-2016"));
        items.add(new Evento("CARRERA 3","5","Cali","03-04-2016"));
        items.add(new Evento("CARRERA 4", "9", "Florencia", "04-04-2016"));
        items.add(new Evento("CARRERA 5","17","Cali","05-04-2016"));
        items.add(new Evento("CARRERA 6", "32", "Medellín", "06-04-2016"));


        // Obtener el Recycler
        recycler = (RecyclerView) view.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);

        // Crear un nuevo adaptador
        adapter = new EventosAdapter(getContext(),items);
        recycler.setAdapter(adapter);

        fab = (ImageButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.fab:
                intent = new Intent(getActivity(),NuevoEventoActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
