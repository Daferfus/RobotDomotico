package com.naranjatradicionaldegandia.elias.robotdomotico;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public StorageReference storageRef;
    private AppBarConfiguration mAppBarConfiguration;
    public TextView correo;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getBaseContext();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Inicializando perimetro", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(context, DibujarActivity.class);
                startActivity(i);
            }
        });

        Bundle extras = getIntent().getExtras();
        storageRef = FirebaseStorage.getInstance().getReference();



        DrawerLayout drawer = findViewById(R.id.drawer_layout);//da error pero es porque el android studio es gilipollas

        NavigationView navigationView = findViewById(R.id.nav_view);//da error pero es porque el android studio es gilipollas
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerView = navigationView.getHeaderView(0);
        TextView correo = (TextView) headerView.findViewById(R.id.nav_correo);
        TextView nombre = (TextView) headerView.findViewById(R.id.nav_nombre);
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        Usuarios.getNombre(usuario, nombre);
        String email = usuario.getEmail();
        if (usuario != null) {
            // Name, email address, and profile photo Url



            nombre.setText(usuario.getDisplayName());
            correo.setText(email);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}