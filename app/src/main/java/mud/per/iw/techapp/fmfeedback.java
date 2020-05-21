package mud.per.iw.techapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;
import static mud.per.iw.techapp.frgmenthome.pairdata;
import static mud.per.iw.techapp.frgmenthome.prodList;


public class fmfeedback extends Fragment {


    View view;
    private String changeres;
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
   // String changeres;
    private static MessageDigest md;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fr_fmfeedback, container, false);
        _emailText=(EditText)view.findViewById(R.id.input_head);
        _passwordText=(EditText)view.findViewById(R.id.input_feed);
        _loginButton=(Button)view.findViewById(R.id.btn_feed);
        // _signupLink=(TextView)findViewById(R.id.link_signup);

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

        return view;
    }

    public void onLoginFailed() {
        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
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

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        SharedPreferences prefs = getActivity().getSharedPreferences("userinfo", MODE_PRIVATE);
        String restoredText = prefs.getString("UserID", null);
        if (restoredText != null) {

            svcid = Integer.parseInt(prefs.getString("UserID", "no")); //0 is the default value.
        }
        isertsp(email,password," ",svcid);



    }
    public String isertsp(String ft ,String fd ,String fi ,int  fu){
        String url = this.getResources().getString( R.string.weburl29)+"?ft="+ft+"&fd="+fd+"&fi="+fi+"&fu="+fu+"";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(getActivity(), "Feedback Sent!",
                                Toast.LENGTH_LONG).show();

                        Intent intentl = new Intent(getActivity(), MainActivity.class);
                        startActivity(intentl);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("nwe1", error.toString());
                        Toast.makeText(getActivity(), "Error!",
                                Toast.LENGTH_LONG).show();


                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                5,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        singletongm.getInstance(getActivity()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ) {
            _emailText.setError("Enter Subject");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() ) {
            _passwordText.setError("Enter Feedback");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }



}

