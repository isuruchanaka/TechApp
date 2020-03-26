package mud.per.iw.techapp;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static mud.per.iw.techapp.AlbumsAdapter.albumList;


public class FragmentSixth extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    ImageView clbtn;
    View view;
    Button secondButton;
    private RecyclerView recyclerView;
    private RelativeLayout lpv;
    AutoCompleteTextView spinner;
    String[] stockArr;
    ArrayAdapter<String> adaptersit;
    private StationAdapter adapter;
    private List<Album> albumList;
    public static List<station> stationsList;
    public static List<station> stationsListn;
    public static List<String> sitetypList;
    public static List<String> siteList;
    public static List<String> spList;
    public static List<String> prodList;
    public static List<String> stsList;
    public static HashMap<Integer,String > sitetypList1 =new HashMap<>();
    public static HashMap<Integer,String > siteList1 =new HashMap<>();
    public static HashMap<Integer,String > spList1 =new HashMap<>();
    public static HashMap<Integer,String > prodList1 =new HashMap<>();
    private String changeres;
    private String tid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sixth, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view3);
        String strcd=getArguments().getString("siteuid");
//        sitetypList=new ArrayList<>();
//        siteList=new ArrayList<>();
//        spList=new ArrayList<>();
//        prodList=   new ArrayList<>();
//        getdmsg("");
//        getsitetp();
//        // getsite();
//        getspecies();
//        getprod();
        stsList=new ArrayList<>();
        getdmsg(strcd);
        stationsList = new ArrayList<>();
        stationsListn = new ArrayList<>();
        adapter = new StationAdapter(container.getContext(), stationsList);
        view.findViewById(R.id.nodt).setVisibility(View.GONE);
        lpv=(RelativeLayout) view.findViewById(R.id.loadingPaneln);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(container.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.GONE);


        spinner = (AutoCompleteTextView)view.findViewById(R.id.state);
        spinner.setOnItemSelectedListener(this);
        spinner.setOnItemClickListener(this);
        clbtn = (ImageView) view.findViewById(R.id.calc_clear_txt_Prise);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //  clbtn.setBackground(makeSelector(Color.parseColor("#ff7200")));
        }

        clbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                spinner.getText().clear();



            }
        });


        return view;
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

                String Code = we1.get("Code").toString();
                String SiteUId = we1.get("SiteUId").toString();
                String StationTypeUId = we1.get("StationTypeUId").toString();
                String Description = we1.get("Description").toString();
                String Lat = we1.get("Lat").toString();
                String Long = we1.get("Long").toString();
                String stype = we1.get("stype").toString();
                String UId = we1.get("UId").toString();
                String stypedes = we1.get("stypedes").toString();

                stsList.add(Description);
                station a = new station(Code,SiteUId,StationTypeUId,Description,Lat,Long,stype,UId,stypedes);
                stationsList.add(a);

                b++;
            }
            stockArr = new String[stsList.size()];
            stockArr = stsList.toArray(stockArr);

            adaptersit = new ArrayAdapter<String>(getContext(),R.layout.spinner_item, stockArr);

            spinner.setAdapter(adaptersit);
            lpv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);

        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }
        adapter.notifyDataSetChanged();

        if(stationsList.size()==0){
            view.findViewById(R.id.nodt).setVisibility(View.VISIBLE);

        }
        else {
            view.findViewById(R.id.nodt).setVisibility(View.GONE);
        }

        clbtn = (ImageView) view.findViewById(R.id.calc_clear_txt_Prise);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //  clbtn.setBackground(makeSelector(Color.parseColor("#ff7200")));
        }

        clbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                spinner.getText().clear();



            }
        });


    }
    private void prepareAlbums1() {


        stationsListn = new ArrayList<>();
        adapter = new StationAdapter(getContext(), stationsListn);
        try{

            JSONObject dbovh = null;

            dbovh = new JSONObject(changeres);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;

            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);

                String Code = we1.get("Code").toString();
                String SiteUId = we1.get("SiteUId").toString();
                String StationTypeUId = we1.get("StationTypeUId").toString();
                String Description = we1.get("Description").toString();
                String Lat = we1.get("Lat").toString();
                String Long = we1.get("Long").toString();
                String stype = we1.get("stype").toString();
                String UId = we1.get("UId").toString();
                String stypedes = we1.get("stypedes").toString();


                station a = new station(Code,SiteUId,StationTypeUId,Description,Lat,Long,stype,UId,stypedes);
                stationsListn.add(a);

                b++;
            }
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            //view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            lpv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);

        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }
        adapter.notifyDataSetChanged();

        if(stationsList.size()==0){
            view.findViewById(R.id.nodt).setVisibility(View.VISIBLE);

        }
        else {
            view.findViewById(R.id.nodt).setVisibility(View.GONE);
        }




    }
    public String getdmsg(String  val1 ){
        String url = getContext().getResources().getString( R.string.weburl8)+"?id="+val1;



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
    public String getdmsg1(String  val1 ){
        String url = getContext().getResources().getString( R.string.weburl44)+"?id="+val1;



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        changeres = response.toString();
                        prepareAlbums1();


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
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String itemName = adaptersit.getItem(position);
        String[] sdfgh= itemName.split("\n");
        spinner.setSelection(0);
        for(station carnet : stationsList) {
            if (carnet.getDescription().trim().contains(sdfgh[0].trim())) {
                tid = carnet.getUId();
              //  view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                lpv.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                getdmsg1(tid);
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




}