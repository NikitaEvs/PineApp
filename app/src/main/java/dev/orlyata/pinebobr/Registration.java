package dev.orlyata.pinebobr;

import org.json.JSONException;
import org.json.JSONObject;

public class Registration {
    private String name;
    private String email;
    private String login;
    private String password;
    private String secondPassword;
    private String msgOut;

    public Registration(String name, String email, String login, String password, String secondPassword){
        this.login = login;
        this.password = password;
        this.secondPassword = secondPassword;
        this.name = name;
        this.email = email;
    }

    boolean generateMsg(){
        if(password.equals(secondPassword)){
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonData = new JSONObject();
            try{
                jsonObject.put("event", "reg_req_m_s");
                jsonData.put("name", name);
                jsonData.put("login", login);
                jsonData.put("password", password);
                jsonData.put("email", email);
                jsonObject.put("data", jsonData);
                msgOut = jsonObject.toString();
                return true;
            } catch (JSONException e){
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public String getMsgOut() {
        return msgOut;
    }

    public void setMsgOut(String msgOut) {
        this.msgOut = msgOut;
    }
}
