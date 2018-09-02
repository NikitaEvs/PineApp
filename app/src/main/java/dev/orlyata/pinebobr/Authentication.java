package dev.orlyata.pinebobr;

import android.app.Application;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Authentication {
    private String login = "cat";
    private String password = "meow";
    private String email;
    private String authOut;
    private Application application;
    private long val;

    public Authentication(String login, String password, String email){
        this.login = login;
        this.email = email;
        this.password = password;
        authOut = createSignInResp();
    }

    public Authentication(String login, String password, Application application){
        this.login = login;
        this.password = password;
        this.application = application;
        authOut = createSignInResp();
    }

    public Authentication(String login, String password, int val){
        this.login = login;
        this.password = password;
        this.val = val;
        authOut = createSignInResp();
    }

    public Authentication(){
        authOut = createSignInResp();
    }

    public String createSignInResp() {
        getValFromDatabase();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonData = new JSONObject();
        try{
            jsonObject.put("event", "auth_resp_m_s");
            jsonData.put("login", login);
            jsonData.put("password", password);
            jsonData.put("val", val);
            jsonObject.put("data", jsonData);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public void getValFromDatabase(){
        DaoSession daoSession = ((App) application).getDaoSession();
        List<Value> values = daoSession.getValueDao().loadAll();
        if(values.size() == 0){
            val = 0;
        } else {
            Value value = values.get(values.size()-1);
            val = value.getVal();
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthOut() {
        return authOut;
    }

    public void setAuthOut(String authOut) {
        this.authOut = authOut;
    }

    public long getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
