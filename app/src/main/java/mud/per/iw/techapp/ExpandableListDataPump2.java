package mud.per.iw.techapp;



import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class ExpandableListDataPump2 {
    public static HashMap<String, List<String>> getData(String getvisituid, List<atdatas> getspsdata) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        HashMap<String, List<String>> e1 = new HashMap<String, List<String>>();
        List<String> cricket=null;
        String vtsdes = "";
        if(getspsdata!=null) {
            for (int i = 0; i < getspsdata.size(); i++) {
                try{
                cricket = new ArrayList<String>();
                cricket.add( getspsdata.get(i).getpdesc());

               // cricket.add("Quantity:" + getspsdata.get(i).getunit()+" "+ getspsdata.get(i).getunitid());
                vtsdes = getspsdata.get(i).getpdesc();


                expandableListDetail.put(vtsdes, cricket);
            }
                catch (Exception ex){
                    Log.wtf("ljkll", ex);
            }

            }
        }


//        List sortedKeys=new ArrayList(expandableListDetail.keySet());
//        Collections.sort(sortedKeys);
//        for (int i = 0; i < sortedKeys.size(); i++) {
//       e1.put( sortedKeys.get(i).toString()   ,expandableListDetail.get(sortedKeys.get(i)));

      //  }
        return expandableListDetail;
    }

}
