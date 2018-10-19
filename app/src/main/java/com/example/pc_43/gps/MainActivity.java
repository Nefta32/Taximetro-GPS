package com.example.pc_43.gps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    //TextView txtGPS;
    LocationManager gps;

    EditText eTBanderazo, eTHora;
    Button BInicio, BLlegada;
    TextView tVTotal;
    ImageButton iBNuevo;

    float Distancias[] = new float[3];

    int contador; //Para saber cuantas veces sensamos
    int Puedo_Sensar;
    double D_E_Latitud, D_E_Longitud, A_D_L_Latitud, A_D_L_Longitud; //Variables donde guardamos longitud y latitud
                                                    //Iniciales D_E_ = DONDE ESTUVE XD
                                                    //Actuales A_D_L_ = A DONDE LLEGUE XD
    float Kilometraje_final, kilometraje_ahora;
    float Bandera, Hora, Precio_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //txtGPS = (TextView) findViewById(R.id.txtGPS);
        gps = (LocationManager) getSystemService(LOCATION_SERVICE); //<-PONEMOS UN MONITO AL LADO
        eTBanderazo = (EditText) findViewById(R.id.eTBanderazo);
        eTHora = (EditText) findViewById(R.id.eTHora);
        BInicio = (Button) findViewById(R.id.BInicio);
        BLlegada = (Button) findViewById(R.id.BLlegada);
        iBNuevo = (ImageButton) findViewById(R.id.iBNuevo);
        tVTotal = (TextView) findViewById(R.id.tVTotal);
        tVTotal.setVisibility(View.GONE);
        iBNuevo.setVisibility(View.GONE);

        BInicio.setOnClickListener(this);
        BLlegada.setOnClickListener(this);
        iBNuevo.setOnClickListener(this);

        //PRUEBA DE "COMO FUNCIONA" distanceBetween
        /*D_E_Latitud = 19.4757307;
        D_E_Longitud = -99.0446951;
        A_D_L_Latitud = 40.6643;
        A_D_L_Longitud = -73.9385;
        Location.distanceBetween( D_E_Latitud, D_E_Longitud, A_D_L_Latitud, A_D_L_Longitud, Distancias);
        tVTotal.setText(" "+Distancias[0]);//EL BUENO XD
        tVTotal.append(" "+Distancias[1]);
        tVTotal.append(" "+Distancias[2]);*/

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 1, this);*/

    }

    @Override
    public void onLocationChanged(Location location) {//CUANDO CAMBIE LA LOCALIZACION
        if (Puedo_Sensar == 0)
        {
            Toast.makeText(this, "Se ha sensado, KmAh: "+kilometraje_ahora+", kmFi: "+Kilometraje_final+" c "+contador, Toast.LENGTH_LONG).show();
            if (contador == 0)
            {
                D_E_Latitud = location.getLatitude();
                D_E_Longitud = location.getLongitude();
            }
            else if (contador >= 1)
            {
                A_D_L_Latitud = location.getLatitude();
                A_D_L_Longitud = location.getLongitude();

                Location.distanceBetween( D_E_Latitud, D_E_Longitud, A_D_L_Latitud, A_D_L_Longitud, Distancias);

                kilometraje_ahora = Distancias [0];

                Kilometraje_final = Kilometraje_final + kilometraje_ahora;

                D_E_Latitud = A_D_L_Latitud;
                D_E_Longitud = A_D_L_Longitud;
            }
            contador++;
        }

        /*txtGPS.setText("Longitud: "+location.getLongitude()+" grados");
        txtGPS.append("\nLatitud: "+location.getLatitude()+"grados");
        txtGPS.append("\nAltitud: "+location.getAltitude()+" metros");
        txtGPS.append("\nVelocidad: "+location.getSpeed()+" m/s");
        txtGPS.append("\nHora: "+location.getTime()+" hrs");
        txtGPS.append("\nBearing: "+location.getBearing()+" grados");
        txtGPS.append("\nPrecision: "+location.getAccuracy()+" metros");*/

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        //txtGPS.setText("Por favor prende tu GPS"); //SI SE PRENDE EL GPS
        Toast.makeText(this, "Gracias por prenderlo GPS", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderDisabled(String provider) {

        //txtGPS.setText("Por favor prende tu GPS"); //POR SI NO TIENE ENCENDIDO EL GPS
        Toast.makeText(this, "Por favor prende tu GPS", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.BInicio:
                Puedo_Sensar=0;
                Toast.makeText(this, "Se inicio el viaje", Toast.LENGTH_LONG).show();
                BInicio.setBackgroundColor(Color.green(255));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                gps.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
                                                    //Pensando positivamente que el carro tiene una velocidad constante de
                                                    //70 km/h
                break;

            case R.id.BLlegada:
                Puedo_Sensar=1;
                Toast.makeText(this, "Termino el viaje", Toast.LENGTH_LONG).show();
                BInicio.setBackgroundColor(Color.BLACK);
                BLlegada.setBackgroundColor(Color.RED);
                Bandera = Float.parseFloat(eTBanderazo.getText().toString());
                Hora = Float.parseFloat(eTHora.getText().toString());
                Precio_final = ((Kilometraje_final/1000)*Hora)+Bandera;
                tVTotal.setVisibility(View.VISIBLE);
                tVTotal.setText("Total a pagar: \n"+Math.round(Precio_final));
                iBNuevo.setVisibility(View.VISIBLE);
                break;

            case R.id.iBNuevo:
                contador=0;
                Kilometraje_final=0;
                        kilometraje_ahora=0;
                Bandera=0;
                        Hora=0;
                            Precio_final=0;
                BLlegada.setBackgroundColor(Color.BLACK);
                Toast.makeText(this, "Generando nuevo viaje", Toast.LENGTH_LONG).show();
                eTBanderazo.setText("");
                eTHora.setText("");
                tVTotal.setVisibility(View.GONE);
                iBNuevo.setVisibility(View.GONE);
                break;
        }
    }
}
