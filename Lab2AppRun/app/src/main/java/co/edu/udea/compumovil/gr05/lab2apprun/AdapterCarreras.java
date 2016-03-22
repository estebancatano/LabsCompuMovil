package co.edu.udea.compumovil.gr05.lab2apprun;

import android.content.Context;
import android.content.Intent;
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
public class AdapterCarreras extends RecyclerView.Adapter<AdapterCarreras.EventosViewHolder> {

    private List<Evento> listaCarreras;
    private Context context;

    public AdapterCarreras(Context context, List<Evento> listaCarreras) {
        this.context = context;
        this.listaCarreras = listaCarreras;
    }

    @Override
    public int getItemCount() {
        return listaCarreras.size();
    }

    @Override
    public EventosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.card_carrera,viewGroup,false);
        EventosViewHolder eventosViewHolder = new EventosViewHolder(v);
        return new EventosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EventosViewHolder viewHolder, int i) {

        viewHolder.position = i;

        viewHolder.nombreEvento.setText(listaCarreras.get(i).getNombre());
        viewHolder.distanciaEvento.setText(listaCarreras.get(i).getDistancia());
        viewHolder.lugarEvento.setText(listaCarreras.get(i).getLugar());
        viewHolder.fechaEvento.setText(listaCarreras.get(i).getFecha());

        //Convertir imagen byte[] a Bitmap
        byte[] byteArray;
        byteArray = listaCarreras.get(i).getFoto();
        if (byteArray != null){
            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            viewHolder.imagenEvento.setImageBitmap(bmp);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////

    class EventosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Campos de cada item
        public TextView nombreEvento;
        public TextView distanciaEvento;
        public TextView lugarEvento;
        public TextView fechaEvento;
        public ImageView imagenEvento;

        public int position;

        public EventosViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            nombreEvento = (TextView) itemView.findViewById(R.id.lbl_nombre);
            distanciaEvento = (TextView) itemView.findViewById(R.id.lbl_distancia);
            lugarEvento = (TextView) itemView.findViewById(R.id.lbl_lugar);
            fechaEvento = (TextView) itemView.findViewById(R.id.lbl_fecha);
            imagenEvento = (ImageView) itemView.findViewById(R.id.imagen_evento);
        }

        @Override
        public void onClick(View v) {
            Intent intentInfo;
            intentInfo = getDatos();
            context.startActivity(intentInfo);
        }

        public Intent getDatos(){
            Intent intent = new Intent(context,ActivityInfoCarrera.class);
            intent.putExtra(ActivityInfoCarrera.TAG_NOMBRE_CARRERA,nombreEvento.getText());
            intent.putExtra(ActivityInfoCarrera.TAG_DISTANCIA_CARRERA,distanciaEvento.getText());
            intent.putExtra(ActivityInfoCarrera.TAG_LUGAR_CARRERA,lugarEvento.getText());
            intent.putExtra(ActivityInfoCarrera.TAG_FECHA_CARRERA,fechaEvento.getText());
            intent.putExtra(ActivityInfoCarrera.TAG_DESCRIPCION_CARRERA, listaCarreras.get(position).getDescripcion());
            intent.putExtra(ActivityInfoCarrera.TAG_TELEFONO_CARRERA, listaCarreras.get(position).getTelefono());
            intent.putExtra(ActivityInfoCarrera.TAG_CORREO_CARRERA, listaCarreras.get(position).getCorreo());
            return intent;
        }
    }
}
