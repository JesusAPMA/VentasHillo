package com.example.lenovo.ventashillo;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    private String key =null;
    private TextView tvPrice;
    private TextView tvTitle;
    private TextView tvTelefono;
    private TextView tvCondicion;
    private TextView tvDescripcion;
    private TextView tvFecha;
    private TextView tvCiudad;
    private TextView tvId;
    private TextView tvNombre;
    private ImageView imvFoto;
    private DatabaseReference database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        database= FirebaseDatabase.getInstance().getReference();

         key = getIntent().getExtras().getString("key");
       // Toast.makeText(Details.this,key,Toast.LENGTH_LONG).show();

         tvNombre= (TextView) findViewById(R.id.name);
        tvPrice= (TextView) findViewById(R.id.cost);
       tvTitle= (TextView) findViewById(R.id.title);
         tvTelefono= (TextView) findViewById(R.id.phone);
         tvCondicion= (TextView) findViewById(R.id.condition);
         tvDescripcion= (TextView) findViewById(R.id.description);
         tvFecha= (TextView) findViewById(R.id.date);
         imvFoto=(ImageView) findViewById(R.id.img_product_details);
         tvCiudad= (TextView) findViewById(R.id.country);
         tvId= (TextView) findViewById(R.id.id);


        database.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Titulo= (String) dataSnapshot.child("title").getValue();
                String Descripcion= (String) dataSnapshot.child("description").getValue();
                String Ciudad= (String) dataSnapshot.child("country").getValue();
                String Id= (String) dataSnapshot.child("id").getValue();
                String Imagen= (String) dataSnapshot.child("image").getValue();
                String Telefono= (String) dataSnapshot.child("phone").getValue();
                String Nombre= (String) dataSnapshot.child("name").getValue();
                String Fecha= (String) dataSnapshot.child("date").getValue();
                String Condicion= (String) dataSnapshot.child("condition").getValue();
                String Precio= (String) dataSnapshot.child("cost").getValue();

                tvNombre.setText(Nombre);
                tvPrice.setText(Precio);
                tvTitle.setText(Titulo);
                tvTelefono.setText(Telefono);
                tvCondicion.setText(Condicion);
                tvDescripcion.setText(Descripcion);
                tvFecha.setText(Fecha);
                tvCiudad.setText(Ciudad);
                tvId.setText(Id);
                Picasso.with(Details.this).load(Imagen).into(imvFoto);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }
}
