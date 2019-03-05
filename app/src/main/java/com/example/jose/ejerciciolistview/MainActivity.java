package com.example.jose.ejerciciolistview;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int posSelec=-1;
    View itemSelec=null;
    List<Queso> listaQuesos;
    ArrayAdapter<Queso> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_adapter);

        // Instancia objetos view
        final ListView listViewQuesos = findViewById(R.id.listaView);
        Button btnBorrar = findViewById(R.id.btnBorrar);
        Button btnInsertar = findViewById(R.id.btnInsertar);
        Button btnModificar = findViewById(R.id.btnModificar);

        // Obtengo instancia de lista de quesos

        listaQuesos =  ListaQuesos.getInstance().getQuesos();

        // Creo array adapter
        // También puede crearme una clase adaptodor extendiendo de ArrayAdapter y reescribirle
        // el getView

        adaptador = new ArrayAdapter<Queso>(this,0,listaQuesos){
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                Log.i("listaquesos","Estoy en el getview. Posición: " + position);
                if(convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.layout_item_queso, null, false);
                }

                // Poblamos los views con sus valores
                TextView queso = convertView.findViewById(R.id.nombre_queso_texview);
                queso.setText(listaQuesos.get(position).getNombre());

                TextView origen = convertView.findViewById(R.id.origen);
                origen.setText(listaQuesos.get(position).getOrigen());

                // Layout que contiene el item
                final View item_layout = convertView.findViewById(R.id.layoutItemQueso);

                // Si se trata del item de la posición seleccionada lo pinta con fondo gris
                // en otro caso con fondo blanco
                item_layout.setBackgroundColor( position != posSelec ?
                           getResources().getColor(R.color.ColorBlanco) : getResources().getColor(R.color.ColorGris));

                // Boton de menu popup
                ImageButton boton = convertView.findViewById(R.id.botonMenu);

               // Listener para el boton
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(posSelec != position) {
                            posSelec = position;  // Actualiza posicion seleccionada
                            if (itemSelec != null) {  // El item que estaba antes sel ahora lo pinta de blanco
                                itemSelec.setBackgroundColor(getResources().getColor(R.color.ColorBlanco));
                            }
                            // pinta item actual de gris y lo marca como itemSelec
                            item_layout.setBackgroundColor(getResources().getColor(R.color.ColorGris));
                            itemSelec = item_layout;
                        }

                        abreMenu(v); // abre menu popup
                    }
                });

                // Listener para layout del item
                item_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Si item clicado no es el seleccionado, lo marca como seleccionado
                        // y le pone color gris. Al que estaba antes seleccionado lo pinta de blanco
                        if(posSelec != position){
                            posSelec = position;
                            if(itemSelec != null) {
                                itemSelec.setBackgroundColor(getResources().getColor(R.color.ColorBlanco));
                            }
                            v.setBackgroundColor(getResources().getColor(R.color.ColorGris));
                            itemSelec = v;
                        }
                    }
                });
                
                return convertView;
            }
        };



        // Vicula adapter a la ListView
        listViewQuesos.setAdapter(adaptador);

        // Color del item seleccionado en la listView
        // Esto también se puede establecer en el xml con la propiedad android:listSelector

        //listViewQuesos.setSelector(R.color.ColorGris);



      /*  // Listener para click en item de la lista
       listViewQuesos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                posSelec = position;


          }


        });*/



        // Cramos Listeners de los botones

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Borra el view seleccionado

                borraElemento();



            }
        });

        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nuevoElemento();

            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificaElemento();

            }
        });

    }



    private void abreMenu(View v) {
        PopupMenu menu = new PopupMenu(this, v);


        // Listeners para las opciones del menu.
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_borrar:
                        borraElemento();
                        return true;
                    case R.id.menu_nuevo:
                        nuevoElemento();
                        return true;
                    case R.id.menu_modificar:
                        modificaElemento();
                        return true;
                    default:
                        return false;
                }

            }
        });

        // Mete las opciones del menu desde el fichero de recurso menu.
        menu.inflate(R.menu.menu_popup);

        // Otra forma de poder hacer el inflate del menu usando MenuInflater. Tendríamos que hacerlo
        // así si la app tuviera que funcionar también en versiónes de API inferiores a la 14.

        //menu.getMenuInflater().inflate(R.menu.menu_popup,menu.getMenu());


        // Muestra el menu
        menu.show();

    }

    private void borraElemento() {
        listaQuesos.remove(posSelec);
        adaptador.notifyDataSetChanged();

        // Si borramos el último elemento de la lista actualizamos la ultima posición

        posSelec = posSelec == listaQuesos.size() ? posSelec-1 : posSelec;
    }

    private void modificaElemento() {
        Queso q = adaptador.getItem(posSelec);
        q.setNombre(q.getNombre() + "(modificado)");
        adaptador.notifyDataSetChanged();
    }

    private void nuevoElemento() {
        //listaQuesos.add(posSelec,new Queso("Nuevo Queso_"+ posSelec, "Villa Quesos" ));
        //adaptador.notifyDataSetChanged();

        // Esto es equivalente a las dos lineas anteriores
        adaptador.insert(new Queso("Nuevo Queso_"+ posSelec, "Villa Quesos" ),posSelec);
    }


}
