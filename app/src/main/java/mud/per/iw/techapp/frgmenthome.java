package mud.per.iw.techapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.FileProvider.getUriForFile;

import static mud.per.iw.techapp.AlbumsAdapter.albumList;
import static mud.per.iw.techapp.Expandpr.prds;
import static mud.per.iw.techapp.Expandsps.sps;
import static mud.per.iw.techapp.GridViewImageAdapter2.data3;
import static mud.per.iw.techapp.MainActivity.qrcod;
import static mud.per.iw.techapp.MainActivity.svcid;

public class frgmenthome extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    ImageView firstButton;
    androidx.appcompat.widget.SearchView savebtn;
    ImageView spbtn;
    Button prbtn;
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    private GridView gridView;
    private int columnWidth;
    private GridViewImageAdapter2 adapter;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private String purl="";
    private String changeres;
    private FusedLocationProviderClient fusedLocationClient;
    private String lti;
    private String lngi;
    private String sdesc1="";
    private String visitdesc1="";
    private String sage1="0";
    private String stadress1="";
    private String savg1="0";
    private String sdead1="0";
    private String srmk1="0";
    private String sbread1="0";
    private String scnt1="0";
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private List<Species> spdata;
    private List<Products> prdata;
    private Expandsps spadapter2;
    private Expandpr spadapter3;
    public static List<Code> CodeList;
    public static List<String> sitetypList;
    public static List<String> siteList;
    public static List<String> spList;
    public static List<String> prodList;

    public static HashMap<Integer,String > sitetypList1 =new HashMap<>();
    public static HashMap<Integer,String > siteList1 =new HashMap<>();
    public static HashMap<Integer,String > spList1 =new HashMap<>();
    public static HashMap<Integer,String > prodList1 =new HashMap<>();
    private String rid;
    private String ctry;

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //  final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        ////File imagePath = new File(container.getContext().getFilesDir(), "images");
        // File newdir = new File(dir);
        // imagePath.mkdirs();
       // getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.frgmenthome, container, false);

        albumList = new ArrayList<>();

        CodeList = new ArrayList<>();
        sitetypList=new ArrayList<>();
        siteList=new ArrayList<>();
        spList=new ArrayList<>();
        prodList=   new ArrayList<>();

        SharedPreferences prefs = getContext().getSharedPreferences("userinfo", MODE_PRIVATE);
        String restoredText = prefs.getString("UserID", null);



        if (restoredText != null) {
            rid=  prefs.getString("RoleID", "no");
            ctry=  prefs.getString("Country", "no");
        }
        if(rid!=null){
        if(rid.equals("1003")){

            getdmsg2(restoredText);

        }
        else {
            getdmsg("",ctry);
        }

        }
        else {
            getdmsg("",ctry);
        }

        getstation();
        getsitetp();
        getspecies();
        getprod();

/////////////////////////////////////////////////////////////////////////////////////////
        firstButton = (ImageView) view.findViewById(R.id.imageView7);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            firstButton.setBackground(makeSelector(Color.parseColor("#ff7200")));
        }

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment  fragment1 = new SecondFragment();
                FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                ft1.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                ft1.replace(R.id.frameLayout, fragment1);
                ft1.commit();



                //Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
            }
        });


        spbtn = (ImageView) view.findViewById(R.id.imageView8);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spbtn.setBackground(makeSelector(Color.parseColor("#ff7200")));
        }
        spbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                try {
                    fragspecies fragobj=new fragspecies();

                    FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                    ft1.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                    ft1.replace(R.id.frameLayout, fragobj);

                    ft1.commit();
                }
                catch (Exception e)
                {

                }


                //Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
            }
        });
        savebtn = (SearchView) view.findViewById(R.id.searchView);

        savebtn.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            public boolean onQueryTextSubmit(String query) {
                savebtn.clearFocus();     // Close keyboard on pressing IME_ACTION_SEARCH option
                Log.d("", "QueryTextChange: "+ query);


                    Bundle bundle = new Bundle();
                    bundle.putString("message", query);

                SecondFragment fragobj=new SecondFragment();
                fragobj.setArguments(bundle);
                FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                ft1.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                ft1.replace(R.id.frameLayout, fragobj);

                ft1.commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("", "QueryTextChange: "+ newText);
                return false;
            }
        });
        return view;
    }

    public StateListDrawable makeSelector(int color) {
        StateListDrawable res = new StateListDrawable();
        res.setExitFadeDuration(400);
        res.setAlpha(200);
        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
        Drawable d = getResources().getDrawable(R.drawable.gmrnfbn);
        res.addState(new int[]{},d);


        return res;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            count=0;
            Log.wtf("cls1", ((Object) this).getClass().getSimpleName() + " is NOT on screen");
        }
        else
        {
            Log.wtf("cls2", ((Object) this).getClass().getSimpleName() + " is on screen");
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void prepareAlbums() {



        try{

            JSONObject dbovh = null;

            dbovh = new JSONObject(changeres);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;

            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);

                String CustomerEmail = we1.get("CustomerEmail").toString();
                String sitedesp = we1.get("sitedesp").toString();
                String Address = we1.get("Address").toString();
                String CustomerName = we1.get("CustomerName").toString();
                String CustomerContactNo = we1.get("CustomerContactNo").toString();
                String salu = we1.get("salu").toString();
                String surb = we1.get("surb").toString();
                String stat = we1.get("stat").toString();
                String uid = we1.get("UId").toString();
                String crdt="";
                if(we1.get("CreatedDate").toString()!="null"){
                    crdt =we1.get("CreatedDate").toString().split("T")[0];
                }

                siteList.add(sitedesp+"    \n"+salu+CustomerName+"    \n"+CustomerContactNo+"    \n"+CustomerEmail);
                siteList1.put(row,uid);
                Album a = new Album(CustomerEmail,sitedesp,Address,CustomerName,CustomerContactNo,salu,surb,stat,uid,crdt);
                albumList.add(a);

                b++;
            }

//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);

        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }







    }
    public String getdmsg(String  val1,String  val2 ){
        String url = getContext().getResources().getString( R.string.weburl4)+"?id="+val1+"&id1="+val2;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        changeres = response.toString();
                        prepareAlbums();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("CameraDemo", error.toString());



                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }
    public String getstation( ){
        String url = getContext().getResources().getString( R.string.weburl1);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String  suresb = response.toString();
                        // Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("Code").toString();
                                String SiteUId = we1.get("SiteUId").toString();
                                //String StationTypeUId = we1.get("StationTypeUId").toString();
                                String Description = we1.get("sitedesp").toString();
                                String Lat = we1.get("Lat").toString();
                                String Long = we1.get("Long").toString();
                                String stype = we1.get("sttype").toString();
                                String UId = we1.get("UId").toString();
                                String stypedes = we1.get("stdesp").toString();


                                Code a = new Code(Code,SiteUId,Description,Lat,Long,stype,UId,stypedes);



                                CodeList.add(a);
                                b++;
                            }




                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {




                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);



        return changeres;
    }
    public String getsitetp( ){
        String url = getContext().getResources().getString( R.string.weburl3);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String  suresb = response.toString();

                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("UId").toString();
                                String Description = we1.get("Description").toString();



                                sitetypList.add(Description);
                                sitetypList1.put(row,Code);
                                b++;
                            }




                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {




                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }
    public String getspecies( ){
        String url = getContext().getResources().getString( R.string.weburl5);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String  suresb = response.toString();
                        //Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("UId").toString();
                                String Description = we1.get("Description").toString();



                                spList.add(Description);
                                spList1.put(row,Code);
                                b++;
                            }




                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.wtf("CameraDemo", error.toString());

                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }
    public String getdmsg2(String  val1 ){
        String url = getContext().getResources().getString( R.string.weburl23)+"?id="+val1;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        changeres = response.toString();
                        prepareAlbums();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("CameraDemo", error.toString());



                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }

    public String getprod( ){
        String url = getContext().getResources().getString( R.string.weburl6);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String  suresb = response.toString();
                        Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("UId").toString();
                                String Description = we1.get("Description").toString();



                                prodList.add(Description);
                                prodList1.put(row,Code);
                                b++;
                            }


                            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.wtf("CameraDemo", error.toString());

                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }

    public String getsite( ){
        String url = getContext().getResources().getString( R.string.weburl4);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String  suresb = response.toString();
                        // Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("UId").toString();
                                String Description = we1.get("sitedesp").toString()+"    \n"+we1.get("CustomerName").toString()+"    \n"+we1.get("CustomerContactNo").toString();


                                siteList.add(Description);
                                siteList1.put(row,Code);
                                b++;
                            }




                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {




                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }
}