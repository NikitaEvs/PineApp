package dev.orlyata.pinebobr;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProcessMessage {
    private String messageIn;
    private String messageOut;
    /**
     * Type of reason for input message:
     * auth_req_a - request for authentication
     * auth_resp_a - response from authentication
     * data_a - data from PineBox
     */
    private String event;
    private JSONObject data;
    private boolean authResp = false;
    private boolean haveAuthResp = false;
    private boolean regResp = false;
    private boolean haveRegResp = false;
    private Authentication authentication;
    private Application application;
    private int val;

    public ProcessMessage(String messageIn, Application application){
        this.messageIn = messageIn;
        this.application = application;
        try{
            analyzeInMsg();
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public ProcessMessage(Application application){
        this.application = application;
    }


    void analyzeInMsg() throws JSONException {
        Log.d("Socket", "Analyze input msg");
        JSONObject jsonObject = new JSONObject(messageIn);
        event = jsonObject.getString("event");
        data = jsonObject.getJSONObject("data");
        haveAuthResp = false;
        switch (event){
            case "auth_req_s_m":
                Log.d("Socket", "Take auth request from server");
                if(!authentication.getLogin().equals("")){
                    messageOut = authentication.getAuthOut();
                } else {
                    messageOut = null;
                }
                break;
            case "auth_resp_s_m":
                Log.d("Socket", "Take auth response from server: ");
                String responseAuth = data.getString("response");
                Log.d("Socket", "Response: "+responseAuth);
                haveAuthResp = true;
                if(responseAuth.equals("ok")){
                    authResp = true;
                    messageOut = null;
                } else {
                    authResp = false;
                    messageOut = null;
                }
                break;
            case "data_m":
                Log.d("Socket", "Take data from server");
                val = Integer.parseInt(data.getString("val"));
                authentication.setVal(val);
                JSONArray typesJA = data.getJSONArray("types");
                JSONArray valuesJA = data.getJSONArray("values");
                String timestamp = data.getString("timestamp");
                timestamp = timestamp.substring(2, timestamp.length());
                Log.d("Input: ", timestamp);
                int val = data.getInt("val");
                ArrayList<String> types = new ArrayList<>();
                ArrayList<Integer> values = new ArrayList<>();
                double allValuesD = Integer.parseInt(valuesJA.getString(0))*0.22;
                int allValues = (int) allValuesD;
                for (int i = 0; i < typesJA.length(); i++) {
                    Log.d("Socket", "In type: " + typesJA.getInt(i));
                    switch (typesJA.getInt(i)){
                        case 0:
                            types.add("null");
                            values.add(1);
                            break;
                        case 1:
                            types.add("kettle");
                            values.add(allValues);
                            break;
                        case 2:
                            types.add("bulb");
                            values.add(allValues);
                            break;
                        case 3:
                            types.add("phone");
                            values.add(allValues);
                            break;
                        case 4:
                            types.add("laptop");
                            values.add(allValues);
                            break;
                        case 5:
                            types.add("kettle");
                            types.add("bulb");
                            values.add((int)(allValuesD*0.819));
                            values.add((int)(allValuesD*0.18));
                            break;
                        case 6:
                            types.add("kettle");
                            types.add("phone");
                            values.add((int)(allValuesD*0.939));
                            values.add((int)(allValuesD*0.06));
                            break;
                        case 7:
                            types.add("kettle");
                            types.add("laptop");
                            values.add((int)(allValuesD*0.833));
                            values.add((int)(allValuesD*0.1667));
                            break;
                        case 8:
                            types.add("bulb");
                            types.add("phone");
                            values.add((int)(allValuesD*0.77));
                            values.add((int)(allValuesD*0.226));
                            break;
                        case 9:
                            types.add("bulb");
                            types.add("laptop");
                            values.add((int)(allValuesD*0.524));
                            values.add((int)(allValuesD*0.476));
                            break;
                        case 10:
                            types.add("phone");
                            types.add("laptop");
                            values.add((int)(allValuesD*0.244));
                            values.add((int)(allValuesD*0.7558));
                            break;
                        case 11:
                            types.add("kettle");
                            types.add("bulb");
                            types.add("phone");
                            values.add((int)(allValuesD*0.819));
                            values.add((int)(allValuesD*0.18));
                            break;
                        case 12:
                            types.add("kettle");
                            types.add("bulb");
                            types.add("laptop");
                            values.add((int)(allValuesD*0.703));
                            values.add((int)(allValuesD*0.155));
                            values.add((int)(allValuesD*0.14));
                            break;
                        case 13:
                            types.add("bulb");
                            types.add("bulb");
                            values.add((int)(allValuesD*0.5));
                            values.add((int)(allValuesD*0.5));
                            break;
                        case 14:
                            types.add("bulb");
                            types.add("phone");
                            types.add("laptop");
                            values.add((int)(allValuesD*0.454));
                            values.add((int)(allValuesD*0.133));
                            values.add((int)(allValuesD*0.412));
                            break;
                        case 15:
                            types.add("bulb");
                            types.add("bulb");
                            types.add("phone");
                            values.add((int)(allValuesD*0.436));
                            values.add((int)(allValuesD*0.436));
                            values.add((int)(allValuesD*0.1277));
                            break;
                        case 16:
                            types.add("bulb");
                            types.add("bulb");
                            types.add("laptop");
                            values.add((int)(allValuesD*0.344));
                            values.add((int)(allValuesD*0.344));
                            values.add((int)(allValuesD*0.312));
                            break;
                        case 17:
                            types.add("bulb");
                            types.add("bulb");
                            types.add("phone");
                            types.add("laptop");
                            values.add((int)(allValuesD*0.3125));
                            values.add((int)(allValuesD*0.3125));
                            values.add((int)(allValuesD*0.091));
                            values.add((int)(allValuesD*0.2834));
                            break;
                    }
                }
                Refactor refactor = new Refactor(types, values);
                updateDatabase(refactor.typesToStr(),refactor.valuesToStr(),timestamp,val);
                Log.d("Socket", "In val: "+val);
                break;
            case "reg_resp_s_m":
                Log.d("Socket", "Take reg response from server");
                String responseReg = data.getString("response");
                haveRegResp = true;
                if(responseReg.equals("ok")){
                    regResp = true;
                    messageOut = null;
                } else {
                    regResp = false;
                    messageOut = null;
                }
                break;
        }
    }

    public void updateDatabase(String types, String values, String timestamp, int val){
        DaoSession daoSession = ((App) application).getDaoSession();
        Value value = new Value(types, values, timestamp, val);
        Log.d("Socket", "Add element to db, StrVal: "+ values+" val: "+val);
        daoSession.insert(value);
    }

    public String getMessageIn() {
        return messageIn;
    }

    public void setMessageIn(String messageIn) {
        this.messageIn = messageIn;
    }

    public String getMessageOut() {
        return messageOut;
    }

    public void setMessageOut(String messageOut) {
        this.messageOut = messageOut;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public boolean isAuthResp() {
        return authResp;
    }

    public void setAuthResp(boolean authResp) {
        this.authResp = authResp;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public boolean isHaveAuthResp() {
        return haveAuthResp;
    }

    public void setHaveAuthResp(boolean haveAuthResp) {
        this.haveAuthResp = haveAuthResp;
    }

    public boolean isRegResp() {
        return regResp;
    }

    public void setRegResp(boolean regResp) {
        this.regResp = regResp;
    }

    public boolean isHaveRegResp() {
        return haveRegResp;
    }

    public void setHaveRegResp(boolean haveRegResp) {
        this.haveRegResp = haveRegResp;
    }
}
