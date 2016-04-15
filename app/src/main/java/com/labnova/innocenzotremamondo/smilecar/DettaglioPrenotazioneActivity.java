package com.labnova.innocenzotremamondo.smilecar;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class DettaglioPrenotazioneActivity extends AppCompatActivity implements View.OnClickListener {

    //PRENDERE LE ISTANZE
    TextView postoPrenotato;
    TextView orarioPrenotato;
    TextView commentiPrenotazione;

    Button cancellaPrenotazione;

    //BUTTON PER IL TESTING VOLLEY
    Button testaConnessione;
    TextView provaConnessione;

    //ISTANZA DELLA STRINGA POSTO
    String posto = "";
    String commenti = "";
    String orario = "";

    //BOOL PER CONFERMARE L'EVENTUALE CANCELLAZIONE
    Boolean prenotazioneCancellata = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_prenotazione);

        postoPrenotato = (TextView) findViewById(R.id.postoPrenotato);
        orarioPrenotato = (TextView) findViewById(R.id.orarioPrenotazione);
        cancellaPrenotazione = (Button) findViewById(R.id.cancellaPrenotazione);
        commentiPrenotazione = (TextView) findViewById(R.id.commentiPrenotazione);

        testaConnessione = (Button) findViewById(R.id.richiestaVolley);
        testaConnessione.setOnClickListener(this);
        provaConnessione = (TextView) findViewById(R.id.provaConnessioneText);


        cancellaPrenotazione.setOnClickListener(this);

        //PRENDERE GLI INTENT SPARATI DALLA MAIN
        posto = getIntent().getStringExtra("numeroPosto");
        commenti = getIntent().getStringExtra("commentiPrenotazione");
        orario = getIntent().getStringExtra("orarioPrenotazione");

        postoPrenotato.setText(posto);
        commentiPrenotazione.setText(commenti.toString());
        orarioPrenotato.setText(orario);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.richiestaVolley:
                sendRequest();
                break;

            case R.id.cancellaPrenotazione:

                //CREARE L'ALERT-DIALOG PER BUILDARE IL MESSAGGIO E I BUTTON POSITIVI & NEGATIVI
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Disdetta prenotazione")
                        .setMessage("Sei sicuro di procedere con la disdetta della prenotazione?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            //OVERRIDDARE L'ONCLICK
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //EFFETTUARE LA DISDETTA
                                prenotazioneCancellata = true;
                                Intent intentRitorno = new Intent();
                                prenotazioneCancellata = true;
                                intentRitorno.putExtra(MainActivity.REQUEST_RESULT, 42);
                                intentRitorno.putExtra("prenotazione", prenotazioneCancellata);
                                setResult(RESULT_OK, intentRitorno);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DettaglioPrenotazioneActivity.this, "disdetta cancellata!", Toast.LENGTH_SHORT).show();
                            }
                        });

                //LANCIARE IL BUILDER DELL'ALERT-DIALOG
                builder.create().show();


                break;
            default:
                break;


        }
    }

    //METODO PER LA CONNESSIONE VALLEY
    private void sendRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://ip.jsontest.com/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                provaConnessione.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                provaConnessione.setText("errore!");
            }
        });

        queue.add(jsonObjectRequest);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DettaglioPrenotazione Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.labnova.innocenzotremamondo.smilecar/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "DettaglioPrenotazione Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.labnova.innocenzotremamondo.smilecar/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
