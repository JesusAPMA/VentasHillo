package com.example.lenovo.ventashillo;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;

import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient googleApiClient;
     DatabaseReference dbPredicciones;
    FirebaseRecyclerAdapter mAdapter;
    ImageView image_porfile;
    TextView email_porfile;
    TextView txt_nombre_porfile;
    String id_porfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        image_porfile= (ImageView) findViewById(R.id.image_porfile);
        email_porfile=(TextView) findViewById(R.id.txt_email_porfile);
        txt_nombre_porfile=(TextView) findViewById(R.id.txt_porfile);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
       dbPredicciones =
                FirebaseDatabase.getInstance().getReference();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.rv_publication);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        mAdapter =
                new FirebaseRecyclerAdapter<Publication_Details, PublicationAdapter>(
                        Publication_Details.class, R.layout.cardview_publication, PublicationAdapter.class, dbPredicciones) {

                    @Override
                    protected void populateViewHolder(PublicationAdapter viewHolder, Publication_Details model, int position) {
                        final String post_key=getRef(position).getKey();
                        viewHolder.setPrice(model.getCost());
                        viewHolder.setTitle(model.getTitle());
                        viewHolder.setCondition(model.getCondition());
                        viewHolder.setImage(getApplicationContext(),model.getImage());
                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i= new Intent(MainActivity.this,Details.class);

                                i.putExtra("key",post_key);
                                startActivity(i);
                            }
                        });
                    }


                };

        recycler.setAdapter(mAdapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount account = result.getSignInAccount();

            //txt_nombre_porfile.setText(account.getDisplayName());
            //email_porfile.setText(account.getEmail());
            //id_porfile=account.getId();

            //Picasso.with(this).load(account.getPhotoUrl()).into(image_porfile);

        } else {
            goLogInScreen();
        }
    }

    private void goLogInScreen() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void revoke() {
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLogInScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo revocar el acceso", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_publicar) {


            Intent i = new Intent(MainActivity.this, Publicar_Anuncio.class);
            startActivity(i);


        }
        if (id == R.id.action_logout) {

logOut();


        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_acc_ref) {

        } else if (id == R.id.nav_agr_gan) {

        } else if (id == R.id.nav_ali) {

        } else if (id == R.id.nav_ani_mas) {

        } else if (id == R.id.nav_aut) {

        } else if (id == R.id.nav_beb_mat) {

        } else if (id == R.id.nav_bic) {

        } else if (id == R.id.nav_bol) {

        } else if (id == R.id.nav_cel_sma) {

        } else if (id == R.id.nav_clu_pas) {

        } else if (id == R.id.nav_col) {

        } else if (id == R.id.nav_com) {

        } else if (id == R.id.nav_dep_fit) {

        } else if (id == R.id.nav_edu_ase_cur) {

        } else if (id == R.id.nav_ele_aud_vid_fot) {

        } else if (id == R.id.nav_emp) {

        } else if (id == R.id.nav_far_equ) {

        } else if (id == R.id.nav_fer) {

        } else if (id == R.id.nav_han_ort) {

        } else if (id == R.id.nav_hog_dec) {

        } else if (id == R.id.nav_ind_ofi) {

        } else if (id == R.id.nav_inm_pro) {

        } else if (id == R.id.nav_ins_equ) {

        } else if (id == R.id.nav_jue_jug) {

        } else if (id == R.id.nav_lib_rev) {

        } else if (id == R.id.nav_mot) {

        } else if (id == R.id.nav_mus) {

        }else if (id == R.id.nav_nut_sup) {

        }else if (id == R.id.nav_org) {

        }else if (id == R.id.nav_pap) {

        }else if (id == R.id.nav_pes) {

        }else if (id == R.id.nav_rest) {

        }else if (id == R.id.nav_rop_acc) {

        }else if (id == R.id.nav_sal_bell) {

        }else if (id == R.id.nav_ser) {

        }else if (id == R.id.nav_sol) {

        }else if (id == R.id.nav_tecam) {

        }else if (id == R.id.nav_tecom) {

        }else if (id == R.id.nav_tur_vac) {

        }else if (id == R.id.nav_veh_acu) {

        }else if (id == R.id.nav_veh_aer) {

        }else if (id == R.id.nav_veh_ind) {

        }else if (id == R.id.nav_videojuegos) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}


