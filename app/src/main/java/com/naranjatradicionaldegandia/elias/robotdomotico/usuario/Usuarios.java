package com.naranjatradicionaldegandia.elias.robotdomotico.usuario;

import android.app.Activity;
import android.preference.EditTextPreference;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.naranjatradicionaldegandia.elias.robotdomotico.presentacion.SesionActivity;

public class Usuarios {


    private static final String TAG = "CLASE USUARIOS";
    private static TextToSpeech textToSpeechSystem;
    public static void guardarUsuario(final FirebaseUser user) {
        Usuario usuario = new Usuario(user.getDisplayName(),user.getEmail(), user.getPhoneNumber(), "Sin nombre")
                ;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("usuarios").document(user.getEmail()).set(usuario);
    }
    public static void addNombreRobot(final FirebaseUser user, String robot) {
        Usuario usuario = new Usuario(user.getDisplayName(),user.getEmail(), user.getPhoneNumber(), robot)
                ;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("usuarios").document(user.getEmail()).set(usuario);
    }

    public static void actualizarUsuario(final FirebaseUser user, String name, String mail, String phone, String robot){
        Usuario usuario = new Usuario (name, mail, phone, robot);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("usuarios").document(user.getEmail()).set(usuario);
    }

    public static void getTelefono(final FirebaseUser user, final TextView texto) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("usuarios").document(user.getEmail()).get()
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                if (task.isSuccessful()) {
                                    String telefono = task.getResult().getString("telefono");
                                    texto.setText(telefono);
                                    Log.d("Firestore", "telefono de " + user.getEmail() +" : " + telefono);

                                } else {
                                    Log.e("Firestore", "Error al leer", task.getException());
                                }
                            }
                        });

    }
    public static void getNombre(final FirebaseUser user, final TextView texto ) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String nombre = "";

        db.collection("usuarios").document(user.getEmail()).get()
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                if (task.isSuccessful()) {
                                    String nombre = task.getResult().getString("nombre");
                                    texto.setText(nombre);
                                    Log.d("Firestore", "nombre:" + nombre);
                                } else {
                                    Log.e("Firestore", "Error al leer", task.getException());
                                }
                            }
                        });



    }
    public static void getNombreRobotParaTextView(final FirebaseUser user, final TextView name, final Activity actividad) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("usuarios").document(user.getEmail()).get()
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                if (task.isSuccessful()) {
                                    String nombre = task.getResult().getString("robot");
                                    name.setText("¡Hola! Soy " + nombre + " . Por favor, elige un modo. ¡Me encanta trabajar para tí!");

                                    Log.d("Firestore", "robot:" + nombre);
                                } else {
                                    Log.e("Firestore", "Error al leer", task.getException());
                                }
                            }
                        });



    }
    public static void decirNombreRobot(final FirebaseUser user, final Activity actividad) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("usuarios").document(user.getEmail()).get()
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                if (task.isSuccessful()) {
                                    String nombre = task.getResult().getString("robot");

                                  final String texto = ("¡Hola! Soy " + nombre + " . Por favor, elige un modo. ¡Me encanta trabajar para tí!");

                                        textToSpeechSystem = new TextToSpeech(actividad, new TextToSpeech.OnInitListener() {
                                            @Override
                                            public void onInit(int status) {
                                                if (status == TextToSpeech.SUCCESS) {
                                                    String textToSay = texto;
                                                    textToSpeechSystem.speak(textToSay, TextToSpeech.QUEUE_ADD, null);

                                                }
                                            }
                                        });


                                    Log.d("Firestore", "robot:" + nombre);
                                } else {
                                    Log.e("Firestore", "Error al leer", task.getException());
                                }
                            }
                        });



    }


}
