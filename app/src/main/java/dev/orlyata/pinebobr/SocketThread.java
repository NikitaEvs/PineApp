package dev.orlyata.pinebobr;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketExtension;
import com.neovisionaries.ws.client.WebSocketFactory;

import org.json.JSONException;

import java.io.IOException;


public class SocketThread extends AsyncTask<Void, Void, Void> {
    private final String serverT = "ws://104.248.27.91:8080"; //104.248.27.91
    private final int timeoutT = 50000;
    private String server;
    private int timeout;
    private WebSocket webSocket;
    private Handler handler;
    private Application application;
    private ProcessMessage processMessage;
    private Authentication authentication;
    private boolean connect = false;
    private String customMsgOut = "";

    public SocketThread(String server, int timeout, Handler handler, Application application){
        this.server = server;
        this.timeout = timeout;
        this.handler = handler;
        this.application = application;
        processMessage = new ProcessMessage(application);
    }

    public SocketThread(Application application) {
        this.application = application;
        processMessage = new ProcessMessage(application);
    }

    public SocketThread(String server, int timeout, Application application){
        this.server = server;
        this.timeout = timeout;
        this.application = application;
        processMessage = new ProcessMessage(application);
    }

    public SocketThread(Handler handler, Application application) {
        this.handler = handler;
        this.application = application;
        processMessage = new ProcessMessage(application);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            webSocket = new WebSocketFactory()
                    .setConnectionTimeout(timeoutT)
                    .createSocket(serverT)
                    .addListener(new WebSocketAdapter() {
                        public void onTextMessage(WebSocket websocket, String message) {
                            Log.d("Socket", "Give msg");
                            Log.d("Socket", "In msg: " + message);
                            send("Text", "In msg: " + message);
                            processMessage.setMessageIn(message);
                            try{
                                processMessage.analyzeInMsg();
                            } catch (JSONException e){
                                e.printStackTrace();
                            }
                            String msgOut = processMessage.getMessageOut();
                            Log.d("Socket", "Out " + msgOut);
                            if(msgOut != null){
                                webSocket.sendText(msgOut);
                            }
                            if(processMessage.isAuthResp()){
                                send("Toast", "Auth success");
                                Log.d("Socket", "Auth success");
                                send("Status", "success");
                            } else if(processMessage.isHaveAuthResp()) {
                                send("Toast", "Auth failed");
                                send("Status", "fail");
                            }
                            if(processMessage.isRegResp()){
                                send("Toast", "Reg success");
                                send("Status", "success");
                            } else if(processMessage.isHaveRegResp()){
                                send("Toast", "Reg failed");
                                send("Status", "fail");
                            }
                        }
                    })
                    .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                    .connect();
            if(!customMsgOut.equals("")){
                Log.d("Socket", "Send custom msg: "+customMsgOut);
                webSocket.sendText(customMsgOut);
                customMsgOut = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WebSocketException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

    }

    public void send(String tag, String msg){
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putString(tag, msg);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    public void kill(){
        webSocket.disconnect();
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public ProcessMessage getProcessMessage() {
        return processMessage;
    }

    public void setProcessMessage(ProcessMessage processMessage) {
        this.processMessage = processMessage;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
        processMessage.setAuthentication(authentication);
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    public String getCustomMsgOut() {
        return customMsgOut;
    }

    public void setCustomMsgOut(String customMsgOut) {
        this.customMsgOut = customMsgOut;
    }
}
