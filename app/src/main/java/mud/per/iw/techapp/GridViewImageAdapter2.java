package mud.per.iw.techapp;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.RecyclerView;


public class GridViewImageAdapter2 extends BaseAdapter {

    private Context _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private ArrayList<Bitmap> imgmb = new ArrayList<Bitmap>();
    private ArrayList<Integer> cnt = new ArrayList<Integer>();
    private int imageWidth;
    private static LayoutInflater inflater = null;
    GridViewImageAdapter2 adapter = this;
    //private int _postion;

    public static HashMap<Integer,String > data3 =new HashMap<>();


    public GridViewImageAdapter2(Context activity, ArrayList<String> filePaths,
                                int imageWidth) {
        this._activity = activity;
        this._filePaths = filePaths;
        this.imageWidth = imageWidth;
        inflater = (LayoutInflater) _activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this._filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return this._filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        final ViewHolder holder;

        if (convertView == null) {
//            imageView = new ImageView(_activity);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,imageWidth));
            convertView = inflater.inflate(R.layout.griditem, null);

            holder = new ViewHolder();
            holder.poster = (ImageView) convertView.findViewById(R.id.upcoming_image);

            holder.editbutton = (ImageView) convertView.findViewById(R.id.delete_item);
            // The tag can be any Object, this just happens to be the ViewHolder
            convertView.setTag(holder);

        } else {
           // imageView = (ImageView) convertView;
            holder = (ViewHolder) convertView.getTag();

        }

        //imageView.setImageResource(R.drawable.loading);
        final View finalConvertView = convertView;
        String url = _filePaths.get(position);
        Log.wtf("tte", String.valueOf(url));

       // imageView.set(bitmap);
        //imgmb.add(bitmap);
        //data2.put(position, bitmap);
        String rt= null;
//        try {
//            rt = getFilePath(_activity, Uri.parse(url));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
        try {
        InputStream is =_activity.getContentResolver().openInputStream( Uri.parse(url));
        Bitmap bitmap = BitmapFactory.decodeStream(is);
            Bitmap bitmap2 =  Bitmap.createScaledBitmap(bitmap,100, 100, true);
           // imageView.setImageBitmap(bitmap2);
            holder.poster.setImageBitmap(bitmap2);
            data3.put(position, url);
            Log.wtf("ttx", String.valueOf(position));
            is.close();
        } catch (IOException e) {
            Log.wtf("ttx", e.toString());
            e.printStackTrace();
        }






        //imageView.setOnClickListener(new OnImageClickListener(position));
        holder.poster.setOnClickListener(new OnImageClickListener(position));


        holder.editbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                Object firstKey=data3.keySet().toArray()[position];
             String rfty=   data3.get(firstKey);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    fmtargettrmnt.imagePaths.removeIf(st -> st.equalsIgnoreCase(data3.get(position)));
                }
                //data3.remove(firstKey);

//                for (Map.Entry<Integer, String> entry : data3.entrySet()) {
//                    data3.values().remove(entry.getValue());
//                    // Do things with the list
//                }
                Iterator it = data3.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    //System.out.println(pair.getKey() + " = " + pair.getValue());
                    if(rfty.equals(pair.getValue())){
                        it.remove(); // avoids a ConcurrentModificationException
                    }

                }
                notifyDataSetChanged();


            }
        });




        //this._postion = position;
        //return imageView;
        return convertView;
    }

    class OnImageClickListener implements OnClickListener {

        int _postion;

        // constructor
        public OnImageClickListener(int position) {
            this._postion = position;
        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Log.wtf("ttr", String.valueOf(_postion));
            InputStream is = null;
            try {
                is = _activity.getContentResolver().openInputStream( Uri.parse(data3.get(_postion)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            showImage(bitmap);

        }

    }

    /*
     * Resizing image size
     */

     class ViewHolder {
        ImageView poster;
        ImageView editbutton;
    }


    public void showImage(Bitmap nm) {
        Dialog builder = new Dialog(_activity);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(R.layout.custom_dialog);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;

            }
        });
        ImageView dialogButton = (ImageView) builder.findViewById(R.id.imageView_close);

        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                builder.dismiss();
            }
        });

        ImageView imageView = (ImageView) builder.findViewById(R.id.imageView3);
        // ImageView imageView = new ImageView(_activity);





        imageView.setImageBitmap(nm);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //imageView.setLayoutParams(new ViewGroup.LayoutParams(1000,1000));
//        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
//               1000,
//              1000));
        builder.show();
    }

}