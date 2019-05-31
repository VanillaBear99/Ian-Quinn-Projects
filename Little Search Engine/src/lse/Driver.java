package lse;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Driver {

    public static void main(String[]args){
        LittleSearchEngine LSE = new LittleSearchEngine();

        try {
            LSE.makeIndex("docs.txt","noisewords.txt");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        System.out.println(LSE.top5search("two", "eyes").toString());
        HashMap<String,ArrayList<Occurrence>> kws = LSE.keywordsIndex;
        String[]keys = new String[kws.size()];
        keys = kws.keySet().toArray(keys);
        for(String k:keys){
            ArrayList<Occurrence> occList = LSE.keywordsIndex.get(k);
            System.out.println("Word:"+k+" Occurrence Array:");
            String occString="";//For debugging purposes
            for(int i = 0; i<occList.size();i++){
                occString+=(occList.get(i).toString()+",");
            }
            System.out.println(occString.substring(0,occString.length()-1));
            System.out.println();
        }

    }
}