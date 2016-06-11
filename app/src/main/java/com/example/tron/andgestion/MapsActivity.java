package com.example.tron.andgestion;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.tron.andgestion.modele.Client;
import com.example.tron.andgestion.modele.Facture;
import com.example.tron.andgestion.bddlocal.fonction.outils;
import com.example.tron.andgestion.modele.Parametre;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private outils ou;
    private ArrayList<Facture> liste_facture;
    private Parametre parametre;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ou = (outils) getIntent().getSerializableExtra("outils");
        ou.app = MapsActivity.this;

        ou = (outils) getIntent().getSerializableExtra("outils");
        position = getIntent().getIntExtra("position",0);

        liste_facture = (ArrayList<Facture>) getIntent().getSerializableExtra("liste_facture");
        final ArrayList<Client> lst_client = (ArrayList<Client>) getIntent().getSerializableExtra("liste_client");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(int i=0;i<liste_facture.size();i++) {
        Facture fact = liste_facture.get(i);
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(fact.getLatitude(),fact.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title(fact.getEntete()+" - "+fact.getId_client().getIntitule()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        if(liste_facture.size()>0) {
            Facture fact = liste_facture.get(position);
            LatLng sydney = new LatLng(fact.getLatitude(), fact.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}
