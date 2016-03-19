package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
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
    private LayoutInflater inflater;
    private Context context;

    public EventosAdapter(Context context, List<Evento> listaEventos) {
        this.context = context;
        inflater = LayoutInflater.from(context);
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
        EventosViewHolder eventosViewHolder = new EventosViewHolder(v);
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


    ///////////////////////////////////////////////////////////////////////////////////////


    class EventosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Campos de cada item
        //public ImageView imagenEvento;
        public TextView nombreEvento;
        public TextView distanciaEvento;
        public TextView lugarEvento;
        public TextView fechaEvento;

        public EventosViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            //imagenEvento = (ImageView) itemView.findViewById(R.id.imagen_evento);
            nombreEvento = (TextView) itemView.findViewById(R.id.lbl_nombre);
            distanciaEvento = (TextView) itemView.findViewById(R.id.lbl_distancia);
            lugarEvento = (TextView) itemView.findViewById(R.id.lbl_lugar);
            fechaEvento = (TextView) itemView.findViewById(R.id.lbl_fecha);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, InfoEventoActivity.class));
        }
    }//Termina EventosViewHolder

}
