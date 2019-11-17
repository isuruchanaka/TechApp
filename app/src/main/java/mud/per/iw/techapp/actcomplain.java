package mud.per.iw.techapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.gms.tasks.OnSuccessListener;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static mud.per.iw.techapp.Expandpr.prds;
import static mud.per.iw.techapp.Expandsps.sps;
import static mud.per.iw.techapp.GridViewImageAdapter2.data3;



public class actcomplain extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "FeedActivity";
    private static final int REQUEST_SIGNUP = 0;
    private String name;
    private int svcid;
    String[] menuArray;
    private String responseString;
    private String initials;
    private String Rnkname;
    private Menu menu;
    private String inBedMenuTitle = "Login";
    private String outOfBedMenuTitle = "Logout";
    private boolean inBed = false;
    private String smonth;
    private String yrval;
    EditText _emailText;
    EditText _passwordText;
    Button _loginButton;
    TextView _signupLink;
    String changeres;
    Spinner spinner2;
    private String lti;
    private String lngi;
    private FusedLocationProviderClient fusedLocationClient;
    private static MessageDigest md;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actcomplain);
       // _emailText=(EditText)findViewById(R.id.input_head);
        _passwordText=(EditText)findViewById(R.id.input_feed);
        _loginButton=(Button)findViewById(R.id.btn_feed);
         _signupLink=(TextView)findViewById(R.id.txtgps);

        spinner2 = (Spinner)findViewById(R.id.species);
        spinner2.setOnItemSelectedListener(this);
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        String[] stockArr2 = new String[frgmenthome.spList.size()];
        stockArr2 = frgmenthome.spList.toArray(stockArr2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.spinner_item, stockArr2);

        spinner2.setAdapter(adapter2);




        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        checkloc();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
////////////////////////////////////////////////////////////////
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            lti= String.valueOf(location.getLatitude());
                            lngi= String.valueOf(location.getLongitude());
                            Log.wtf("lti", lti);
                            if(lti!=null){
                                TextView ll = (TextView) findViewById(R.id.txtgps);
                                ll.setText("Latitude:  "+lti+"  Longitude:  "+lngi);
                            }
                        }
                    }
                });

    }

    public void login() throws Exception {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        // _loginButton.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(login.this,
//                R.style.AppTheme);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();
        int spcpos = spinner2.getSelectedItemPosition();
        String spcpos2 = frgmenthome.spList1.get(spcpos);

        String stpos2 = frgmenthome.siteList1.get(0);
        String txtgp=lti+","+lngi;
      //  String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        SharedPreferences prefs = getSharedPreferences("userinfo", MODE_PRIVATE);
        String restoredText = prefs.getString("UserID", null);
        if (restoredText != null) {

            svcid = Integer.parseInt(prefs.getString("UserID", "no")); //0 is the default value.
        }
        if(!_signupLink.getText().equals("Enable GPS!")) {
            isertsp(password, svcid, txtgp, spcpos2, stpos2);

        }
        else {

            Thread   thread = new Thread() {
                public void run() {
                    while (_signupLink.getText().equals("Enable GPS!")) {
                        try {


                            fusedLocationClient = LocationServices.getFusedLocationProviderClient(actcomplain.this);
                            fusedLocationClient.getLastLocation()
                                    .addOnSuccessListener(actcomplain.this, new OnSuccessListener<Location>() {

                                        @Override
                                        public void onSuccess(Location location) {
                                            // Got last known location. In some rare situations this can be null.
                                            if (location != null) {
                                                // Logic to handle location object

                                                lti= String.valueOf(location.getLatitude());
                                                lngi= String.valueOf(location.getLongitude());
                                                Log.wtf("lti", lti);
                                                if(lti!=null){
                                                    TextView ll = (TextView) findViewById(R.id.txtgps);
                                                    ll.setText("Latitude:  "+lti+"  Longitude:  "+lngi);
                                                }
                                            }
                                        }
                                    });

                            Thread.sleep(2000);

                        } catch (InterruptedException e) {
                            Log.e(TAG, "local Thread error", e);
                        }
                    }
                }
            };
            thread.start();

if(!_signupLink.getText().equals("Enable GPS!")){
    login();
            }
else {
    Toast.makeText(this, "Can't get GPS location please try again after opening Google Map App or Submit Again!",
            Toast.LENGTH_LONG).show();
}

        }

    }
    public String isertsp(String fs ,int ft ,String fd ,String fi ,String  fu){
        String url = this.getResources().getString( R.string.weburl30)+"?ft="+ft+"&fd="+fd+"&fi="+fi+"&fu="+fu+"&fs="+fs+"";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(actcomplain.this, "Complain Sent!",
                                Toast.LENGTH_LONG).show();

                        Intent intentl = new Intent(actcomplain.this, MainActivity.class);
                        startActivity(intentl);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("nwe1", error.toString());
                        Toast.makeText(actcomplain.this, "Error!",
                                Toast.LENGTH_LONG).show();


                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        singletongm.getInstance(this).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
        if(requestCode ==152){

            LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;
            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}
            if(gps_enabled) {
                findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                Location location1=null;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(actcomplain.this);
                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(actcomplain.this, new OnSuccessListener<Location>() {

                                    @Override
                                    public void onSuccess(Location location) {

                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            // Logic to handle location object

                                            lti= String.valueOf(location.getLatitude());
                                            lngi= String.valueOf(location.getLongitude());
                                            Log.wtf("lti", lti);
                                            if(lti!=null){
                                                TextView ll = (TextView) findViewById(R.id.txtgps);
                                                ll.setText("Latitude:  "+lti+"  Longitude:  "+lngi);
                                            }
                                        }
                                    }
                                });
                    }
                }, 1000);
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
       // moveTaskToBack(true);
        super.onBackPressed();
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);

        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

       // String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();


        if (password.isEmpty() ) {
            _passwordText.setError("Enter Feedback");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void checkloc(){

        LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(this)
                    .setMessage(R.string.gps_network_not_enabled)
                    .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            //getContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                            Intent gps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(gps, 152);





                        }
                    })
                    .setNegativeButton(R.string.Cancel,null)
                    .show();

        }

    }



}
