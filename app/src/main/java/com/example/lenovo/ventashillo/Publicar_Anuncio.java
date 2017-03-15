package com.example.lenovo.ventashillo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;




public class Publicar_Anuncio extends AppCompatActivity {
    ImageView image;
Spinner categoria;
    String DOWNLOAD_URL="";
    Uri imageuir=null;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference().child("Publication_Image");
    private static final int gallery_request=1;
    String titulo,nombre,telefono,precio,ciudad,fecha,condicion,id,descripcion;
    EditText ed_titulo,ed_nombre,ed_telefono,ed_precio,ed_ciudad,ed_fecha,ed_condicion,ed_id,ed_descripcion;
    String [] categorias={"Categoria","Accesorios y Refacciones","Agricultura y Ganadería","Alimentos","Animales y Mascotas","Autos","Bebé y Maternidad","Bicicletas","Boletos",
            "Celulares y Smartphones","Clubs y Pasatiempos","Coleccionables","Computación","Deportes y Fitness","Educación y Asesorias","Electrónica Audio/Video/Foto",
            "Empleos","Farmacia y Equipo Medico","Ferreteria","Handicap/Ortopedia",
            "Hogar y Decoración","Industrias y Oficinas","Inmuebles y Propiedades","Instrumentos y Equipo Musical","Juegos y Jueguetes","Libros y Revistas",
            "Motos y Vehículos Todo Terreno","Música, Peliculas y Series TV","Nutrición y Suplementos","Organiza Tu Evento","Papelería","Pesca","Restaurantes","Ropa y Accesorios",
            "Salud y Belleza","Servicios","Solo Arte","Te lo Cambio","Te lo Compro","Turismo y Vacaciones","Vehículos Acuaticos","Vehículos Aereos","Vehículos Industriales","Videojuegos"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar__anuncio);
        image =(ImageView) findViewById(R.id.btn_camera);



        ed_titulo=(EditText)findViewById(R.id.edt_title);
        ed_nombre=(EditText)findViewById(R.id.edt_name);
        ed_telefono=(EditText)findViewById(R.id.edt_phone);
        ed_precio=(EditText)findViewById(R.id.edt_price);
        ed_ciudad=(EditText)findViewById(R.id.edt_country);
        ed_fecha=(EditText)findViewById(R.id.edt_date);
        ed_condicion=(EditText)findViewById(R.id.edt_condition);
        ed_id=(EditText)findViewById(R.id.edt_id);
        ed_descripcion=(EditText)findViewById(R.id.edt_description);


        categoria=(Spinner) findViewById(R.id.spinner_categoria);
        ArrayAdapter<String> adaptadordos= new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,categorias);
        categoria.setAdapter(adaptadordos);
        categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery  = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery,gallery_request);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==gallery_request && resultCode==RESULT_OK){
             imageuir= data.getData();
            image.setImageURI(imageuir);




        }
    }

    public void enviar(View v){
            titulo=ed_titulo.getText().toString();
            nombre=ed_nombre.getText().toString();
            telefono=ed_telefono.getText().toString();
            precio=ed_precio.getText().toString();
            ciudad=ed_ciudad.getText().toString();
            fecha=ed_fecha.getText().toString();
            condicion=ed_condicion.getText().toString();
            id=ed_id.getText().toString();
            descripcion=ed_descripcion.getText().toString();

            StorageReference myfileRef = storageRef.child(imageuir.getLastPathSegment());
            myfileRef.putFile(imageuir).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl =taskSnapshot.getDownloadUrl();
                    FirebaseDatabase database= FirebaseDatabase.getInstance();
                    DatabaseReference myref= database.getReference();

                    Publication_Details publication= new Publication_Details(titulo,descripcion,ciudad,id,downloadUrl.toString(),telefono,nombre,fecha,condicion,precio);
                    myref.push().setValue(publication);

                }
            });










        }
}
