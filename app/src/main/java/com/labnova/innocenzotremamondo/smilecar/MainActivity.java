package com.labnova.innocenzotremamondo.smilecar;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMESSO = 1;
    private static final String TAG = "PREFERENZA_POSTO:" ;
    //DICHIARARE LE ISTANZE
    Button prenotazione;
    Button confermaButton;
    TextView numeroPosto;
    EditText commentiPrenotazione;
    EditText numeroPostiCustom;
    TextView postiTotali;
    TextView orarioPosti;

    Intent intentPhone;

    //SETTARE UNA PROVA NUMERICA
    String prova = "";
    Integer postiTotaliCont = 0;

    //VARIABILI PER ORA E MINUTI
    public int oraPosto = 0;
    int minutoPosto = 0;
    String orarioFinale = "";

    //SETTARE UN'ALTRA COSTANTE PER I POSTI TOTALI
    String postiTotaliFinal;

    //BOOLEAN PER UN'EVENTUALE CANCELLAZIONE DI RITORNO
    // Boolean prenotazioneCancellata = false;


    /* FORMULA PER L'ASSEGNAZIONE POSTI
    - EQUAZIONE CARTESIANA: y = (ax)ˆ2 + bx + c

    a = posto preferito (0=interno, 1=accanto la finestra)
    x = numero di posti richiesti

    b = preferenza sulla base dei vecchi posti (se non esiste sceglierla random)
    c = valore alto per storpiare l'eventuale numero alto risultante

    se risulta > 100 ridurlo del 50%
    se risulta > 200 ridurlo del 70%
    se risulta > 400 ridurlo del 90%

    - FUOCO DELL'EQUAZIONE: - b / 2a, (1-bˆ2 + 4ac) - 4a
    - ASSE DELL'EQUAZIONE: x = - (b/ 2a)
    - DIRETTRICE: y = (1 + bˆ2 - 4ac) / 4a

     */

    //COSTANTE PER CONTROLLARE IL RISULTATO DI DETTAGLIO PRENOTAZIONE
    public static final Boolean prenotazioneCancellata = false;
    public static final String REQUEST_RESULT = "REQUEST_RESULT";


    @Override
    protected void onResume() {
        super.onResume();

        // Log.d("Resume:", "sei in onResume!");



    }


    public int preferenzaPosto(String ciao) {
        int preferenza = 0;
        Log.i(TAG, "preferenzaPosto: ");

        return preferenza;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //DEFINRE UNA VARIABILE CONTEGGIO-POSTO
        int conteggioPosto = 0;

        //DEFINIRE E PRENDERE I COMPONENTI DEL LAYOUT
        prenotazione = (Button) findViewById(R.id.buttonPrenotazione);
        numeroPosto = (TextView) findViewById(R.id.postoAssegnato);
        commentiPrenotazione = (EditText) findViewById(R.id.commentiPrenotazione);
        numeroPostiCustom = (EditText) findViewById(R.id.numeroPostiCustom);
        confermaButton = (Button) findViewById(R.id.confermaButton);
        postiTotali = (TextView) findViewById(R.id.postiTotali);

        for (int i = 0; i < conteggioPosto; i++) {
            conteggioPosto = conteggioPosto + 1;
        }




        //SETTARE LA TEXTVIEW RELATIVA ALL'ORARIO E PRENDERE I VARI DATI RELATIVI A ORA E MINUTI
        orarioPosti = (TextView) findViewById(R.id.orarioDaSettare);


        //SETTARE L'INTEGER DEI POSTI TOTALI
        postiTotaliCont = Integer.getInteger(postiTotali.getText().toString());
        postiTotaliFinal = postiTotali.getText().toString();

        //SETTARE NEI BUTTON L'ON-CLICK-LISTENER
        prenotazione.setOnClickListener(this);
        confermaButton.setOnClickListener(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //CLASSE PUBBLICA PER IMPLEMENTARE IL TIME-PICKER-FRAGMENT
    public class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        //OVERRIDDARE L'ON-CREATE-DIALOG
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            oraPosto = hourOfDay;
            minutoPosto = minute;
            orarioFinale = String.valueOf(oraPosto) + " : " + String.valueOf(minutoPosto);
            Log.d("Ora: ", String.valueOf(oraPosto));
            Log.d("Minuto: ", String.valueOf(minutoPosto));
            Log.d("orarioFinale: ", orarioFinale);

            try {
                if (orarioFinale != "") {
                    orarioPosti.setText(orarioFinale);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        }
    }

    //METODO PER MOSTRARE IL TIME-PICKER
    public void showTimePickerDialog(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        //PRENDERE L'ID DELL'ITEM MENU' RELATIVO AL POSTO RANDOM
        if (id == R.id.posto_random) {
            Toast.makeText(this, "Posto n.54", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.buttonPrenotazione:
                // Toast.makeText(this, "premuto Button prenotazione!", Toast.LENGTH_SHORT).show();

                Log.d("orarioPrenotazione:", orarioPosti.getText().toString());
                if (prova.toString() == "") {
                    alertProvaNulla("numeroPosti");
                } else if (orarioPosti.getText().toString() == "orarioDaSettare") {
                    alertProvaNulla("orarioPosti");
                } else {

                    Intent intent = new Intent(this, DettaglioPrenotazioneActivity.class);
                    intent.putExtra("numeroPosto", prova.toString());
                    intent.putExtra("commentiPrenotazione", commentiPrenotazione.getText().toString());
                    intent.putExtra("orarioPrenotazione", orarioFinale);
                    startActivityForResult(intent, 1);
                }


                break;
            case R.id.confermaButton:

                //SETTARE LA VARIABILE PROVA CON IL NUMERO POSTI RICHIESTO
                prova = numeroPostiCustom.getText().toString();

                if (Integer.parseInt(prova) >= 10) {
                    alertNumeroMassimoPrenotazioni();
                } else {

                    int conteggio = Integer.parseInt(prova);

                    //Log.d("conteggio", String.valueOf(conteggio));
                    //SETTARE L'INTEGER DEI POSTI TOTALI
                    postiTotaliCont = Integer.parseInt(postiTotali.getText().toString());

                    numeroPosto.setText(prova);
                    postiTotaliCont = postiTotaliCont - conteggio;
                    postiTotali.setText(postiTotaliCont.toString());
                }


        }
    }

    //METODO PER CONTROLLARE IL PERMESSO DI GESTIONE TELEFONICA
    private void checkPermission() {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)) {
                Toast.makeText(MainActivity.this, "Lo richiedo di nuovo", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMESSO);

            return;


        } else {
            dialPhone();
        }




    }


    //METODO PER ATTIVARE LA CHIAMATA AL RISTORANTE
    public void dialPhone() {

        ;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(MainActivity.this, "vaffanculo", Toast.LENGTH_SHORT).show();

        } else {
            intentPhone = new Intent(Intent.ACTION_CALL);
            intentPhone.setData(Uri.parse("tel:555-555-5555"));
            Log.d("CHIAMATAAAAAAA", "YES");
            startActivity(intentPhone);
        }






    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMESSO: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permesso Accettato", Toast.LENGTH_SHORT).show();
                    dialPhone();

                } else {
                    Toast.makeText(MainActivity.this, "Permesso Negato!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    //METODO PER GESTIRE IL NUMERO MASSIMO DI PRENOTAZIONI
    private void alertNumeroMassimoPrenotazioni() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                        .setTitle("Numero Massimo Prenotazioni")
                                        .setMessage("Si è raggiunto il numero massimo per la gestione automatica delle prenotazioni, " +
                                                "chiamare direttamente il ristorante premendo il tasto OK")
                                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        })
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Log.d("DENTRO-ON-CLICK", "YES");
                                                checkPermission();
                                            }
                                        });

        builder.create().show();

    }

    private void alertProvaNulla(String stringa) {
        Log.d("motivazione?", stringa);
        String motivazione = "";
        if (stringa == "numeroPosti") {
            motivazione =  "Non hai inserito dei numeri di posto validi, riprova.";
        } else {
            motivazione =  "Non hai inserito un orario di prenotazione valido, riprova.";
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Attenzione")
                .setMessage(motivazione)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.create().show();
    }

    public class ExtendedEditText extends EditText {

        public ExtendedEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);

        }

        public ExtendedEditText(Context context, AttributeSet attrs) {
            super(context, attrs);

        }

        public ExtendedEditText(Context context) {
            super(context);

        }

        @Override
        public boolean onKeyPreIme(int keyCode, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

                dispatchKeyEvent(event);
                return false;
            }
            return super.onKeyPreIme(keyCode, event);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //CONTROLLARE CHE IL RESULT-CODE SIA OK
        if(resultCode == RESULT_OK) {

            //PRENDERE IL VALORE BOOL DAL DATA INTENT DI RITORNO
            Boolean value = data.getBooleanExtra("prenotazione", false);
            Log.d("controlloData", value.toString() );
            if (value == true) {
                numeroPosto.setText("");
                postiTotali.setText(postiTotaliFinal);
                numeroPostiCustom.setText("");
                commentiPrenotazione.setText("");
                orarioPosti.setText("");
            }

        }
    }
}

