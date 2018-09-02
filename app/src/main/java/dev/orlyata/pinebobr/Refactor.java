package dev.orlyata.pinebobr;

import java.util.ArrayList;

public class Refactor {
    private String typesStr;
    private String valuesStr;
    private ArrayList<String> types = new ArrayList<>();
    private ArrayList<Integer> values = new ArrayList<>();

    public Refactor(String typesStr, String valuesStr){
        this.typesStr = typesStr;
        this.valuesStr = valuesStr;
    }
    public Refactor(ArrayList<String> types, ArrayList<Integer> values){
        this.types = types;
        this.values = values;
    }
    public String typesToStr(){
        String out = "";
        for(String s:types){
            out+=s+",";
        }
        return out;
    }
    public String valuesToStr(){
        String out = "";
        for(int s:values){
            out+=s+",";
        }
        return out;
    }
    public ArrayList<String> typesToArr(){
        ArrayList<String> out = new ArrayList<>();
        String[] temp = typesStr.split(",");
        for (int i = 0; i < temp.length; i++) {
            out.add(temp[i]);
        }
        return out;
    }
    public ArrayList<Integer> valuesToArr(){
        ArrayList<Integer> out = new ArrayList<>();
        String[] temp = valuesStr.split(",");
        for (int i = 0; i < temp.length; i++) {
            out.add(Integer.parseInt(temp[i]));
        }
        return out;
    }
}
