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

public class ExpandableListDataPump3 {
    public static HashMap<String, List<String>> getData(String getvisituid,  List<rcdatas> getrcdata) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        HashMap<String, List<String>> e1 = new HashMap<String, List<String>>();
        List<String> cricket=null;
        String vtsdes = "";


        if(getrcdata!=null) {
            for (int i = 0; i < getrcdata.size(); i++) {
                try {
                    cricket = new ArrayList<String>();
                    cricket.add(getrcdata.get(i).getpdesc());

                    // cricket.add("Quantity:" + getspsdata.get(i).getunit()+" "+ getspsdata.get(i).getunitid());
                    vtsdes = getrcdata.get(i).getpdesc();

                    Log.wtf("ljkll", vtsdes);
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
