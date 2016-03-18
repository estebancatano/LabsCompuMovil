package co.edu.udea.compumovil.gr05.lab2apprun;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.edu.udea.compumovil.gr05.lab2apprun.model.Evento;

/**
 * Created by joluditru on 18/03/2016.
 */
public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.EventosViewHolder> {

    private List<Evento> listaEventos;

    public static class EventosViewHolder extends RecyclerView.ViewHolder{

        //Campos de cada item
        //public ImageView imagenEvento;
        public TextView nombreEvento;
        public TextView distanciaEvento;
        public TextView lugarEvento;
        public TextView fechaEvento;

        public EventosViewHolder(View view) {
            super(view);

            //imagenEvento = (ImageView) view.findViewById(R.id.imagen_evento);
            nombreEvento = (TextView) view.findViewById(R.id.lbl_nombre);
            distanciaEvento = (TextView) view.findViewById(R.id.lbl_distancia);
            lugarEvento = (TextView) view.findViewById(R.id.lbl_lugar);
            fechaEvento = (TextView) view.findViewById(R.id.lbl_fecha);
        }
    }

    public EventosAdapter(List<Evento> listaEventos) {
        this.listaEventos = listaEventos;
    }


    @Override
    public int getItemCount() {
        return listaEventos.size();
    }

    @Override
    public EventosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_evento,viewGroup,false);
        return new EventosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventosViewHolder viewHolder, int i) {
        String distancia;
        //Convertir imagen byte[] a Bitmap
        /*
        byte[] byteArray;
        byteArray = listaEventos.get(i).getFoto();
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        viewHolder.imagenEvento.setImageBitmap(bmp);
        */
        distancia = listaEventos.get(i).getDistancia() + " km";
        viewHolder.nombreEvento.setText(listaEventos.get(i).getNombre());
        viewHolder.distanciaEvento.setText(distancia);
        viewHolder.lugarEvento.setText(listaEventos.get(i).getLugar());
        viewHolder.fechaEvento.setText(listaEventos.get(i).getFecha());


    }

}
