package dev.orlyata.pinebobr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DayActivity extends AppCompatActivity {

    /* USED FONTS */
    Typeface SF_regular;
    Typeface SF_light;

    /* USED UI ELEMENTS */
    TextView monday;
    TextView tuesday;
    TextView wednesday;
    TextView thursday;
    TextView friday;
    TextView saturday;
    TextView sunday;
    TextView dateDay;
    TextView vatt;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button toDay;
    Button toNow;
    Button toMonth;
    Button toWeek;
    PieChart nowChart;

    SocketThread socketThread;
    Thread threadData;
    Handler handler;

    Drawable bobrIC;
    Drawable bulbIC;
    Drawable phoneIC;
    Drawable kettleIC;
    Drawable laptopIC;

    Map<Integer, Calendar> btnDate = new HashMap<>();
    String dayNumber;
    String vattOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        /* SET FONTS */
        SF_regular = Typeface.createFromAsset(getAssets(),  "fonts/SFUIText-Regular.ttf");
        SF_light = Typeface.createFromAsset(getAssets(), "fonts/SFUIText-Light.ttf");

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                Bundle bundle = message.getData();
                String status = bundle.getString("Status");
                try{
                    if(status.equals("success")){

                    } else {
                        toSign();
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }

            }
        };
        initConnection();
        configUI();
        configListeners();
        configNowChart();
    }

    public void configUI(){
        Date currentTime = Calendar.getInstance().getTime();
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(currentTime);
        int day = currentCalendar.get(Calendar.DAY_OF_WEEK);
        dayNumber = currentCalendar.get(Calendar.DAY_OF_MONTH) + "";
        monday = findViewById(R.id.monday);
        tuesday = findViewById(R.id.tuesday);
        wednesday = findViewById(R.id.wednesday);
        thursday = findViewById(R.id.thursday);
        friday = findViewById(R.id.friday);
        saturday = findViewById(R.id.saturday);
        sunday = findViewById(R.id.sunday);
        dateDay = findViewById(R.id.dateDay);
        vatt = findViewById(R.id.vatt);
        bobrIC = getDrawable(R.drawable.bobr);
        bulbIC = getDrawable(R.drawable.bulb);
        phoneIC = getDrawable(R.drawable.phone);
        kettleIC = getDrawable(R.drawable.ic_tea);
        laptopIC = getDrawable(R.drawable.laptop);
        Drawable drawable = getResources().getDrawable(R.drawable.btn_day_background);
        Log.d("Day", day+"");
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        toDay = findViewById(R.id.btnDay);
        toNow = findViewById(R.id.btnNow);
        toMonth = findViewById(R.id.btnMonth);
        toWeek = findViewById(R.id.btnWeek);
        switch (day){
            case 1:
                btn7.setBackground(drawable);
                btn7.setText(dayNumber);
                btn7.setTextColor(0xFFFFFFFF);
                btnDate.put(7, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, -6);
                String btn1Text1 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btnDate.put(1, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                String btn2Text1 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btnDate.put(2, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                String btn3Text1 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btnDate.put(3, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                String btn4Text1 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btnDate.put(4, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                String btn5Text1 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btnDate.put(5, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                String btn6Text1 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btnDate.put(6, copy(currentCalendar));
                btn1.setText(btn1Text1);
                btn2.setText(btn2Text1);
                btn3.setText(btn3Text1);
                btn4.setText(btn4Text1);
                btn5.setText(btn5Text1);
                btn6.setText(btn6Text1);
                setData(7);
                break;
            case 2:
                btn1.setBackground(drawable);
                btn1.setText(dayNumber);
                btn1.setTextColor(0xFFFFFFFF);
                btnDate.put(1, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(2, copy(currentCalendar));
                String btn2Text = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(3, copy(currentCalendar));
                String btn3Text = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(4, copy(currentCalendar));
                String btn4Text = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(5, copy(currentCalendar));
                String btn5Text = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(6, copy(currentCalendar));
                String btn6Text = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(7, copy(currentCalendar));
                String btn7Text = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btn2.setText(btn2Text);
                btn3.setText(btn3Text);
                btn4.setText(btn4Text);
                btn5.setText(btn5Text);
                btn6.setText(btn6Text);
                btn7.setText(btn7Text);
                setData(1);
                break;
            case 3:
                btn2.setBackground(drawable);
                btn2.setText(dayNumber);
                btn2.setTextColor(0xFFFFFFFF);
                btnDate.put(2, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(3, copy(currentCalendar));
                String btn3Text2 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(4, copy(currentCalendar));
                String btn4Text2 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(5, copy(currentCalendar));
                String btn5Text2 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(6, copy(currentCalendar));
                String btn6Text2 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(7, copy(currentCalendar));
                String btn7Text2 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, -6);
                btnDate.put(1, copy(currentCalendar));
                String btn1Text2 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btn1.setText(btn1Text2);
                btn3.setText(btn3Text2);
                btn4.setText(btn4Text2);
                btn5.setText(btn5Text2);
                btn6.setText(btn6Text2);
                btn7.setText(btn7Text2);
                setData(2);
                break;
            case 4:
                btn3.setBackground(drawable);
                btn3.setText(dayNumber);
                btn3.setTextColor(0xFFFFFFFF);
                btnDate.put(3, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(4, copy(currentCalendar));
                String btn4Text3 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(5, copy(currentCalendar));
                String btn5Text3 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(6, copy(currentCalendar));
                String btn6Text3 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(7, copy(currentCalendar));
                String btn7Text3 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, -6);
                btnDate.put(1, copy(currentCalendar));
                String btn1Text3 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(2, copy(currentCalendar));
                String btn2Text3 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btn1.setText(btn1Text3);
                btn2.setText(btn2Text3);
                btn4.setText(btn4Text3);
                btn5.setText(btn5Text3);
                btn6.setText(btn6Text3);
                btn7.setText(btn7Text3);
                setData(3);
                break;
            case 5:
                btn4.setBackground(drawable);
                btn4.setText(dayNumber);
                btn4.setTextColor(0xFFFFFFFF);
                btnDate.put(4, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(5, copy(currentCalendar));
                String btn5Text4 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(6, copy(currentCalendar));
                String btn6Text4 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(7, copy(currentCalendar));
                String btn7Text4 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, -6);
                btnDate.put(1, copy(currentCalendar));
                String btn1Text4 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(2, copy(currentCalendar));
                String btn2Text4 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(3, copy(currentCalendar));
                String btn3Text4 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btn1.setText(btn1Text4);
                btn2.setText(btn2Text4);
                btn3.setText(btn3Text4);
                btn5.setText(btn5Text4);
                btn6.setText(btn6Text4);
                btn7.setText(btn7Text4);
                setData(4);
                break;
            case 6:
                btn5.setBackground(drawable);
                btn5.setText(dayNumber);
                btn5.setTextColor(0xFFFFFFFF);
                btnDate.put(5, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(6, copy(currentCalendar));
                String btn6Text5 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(7, copy(currentCalendar));
                String btn7Text5 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, -6);
                btnDate.put(1, copy(currentCalendar));
                String btn1Text5 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(2, copy(currentCalendar));
                String btn2Text5 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(3, copy(currentCalendar));
                String btn3Text5 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(4, copy(currentCalendar));
                String btn4Text5 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btn1.setText(btn1Text5);
                btn2.setText(btn2Text5);
                btn3.setText(btn3Text5);
                btn4.setText(btn4Text5);
                btn6.setText(btn6Text5);
                btn7.setText(btn7Text5);
                setData(5);
                break;
            case 7:
                btn6.setBackground(drawable);
                btn6.setTextColor(0xFFFFFFFF);
                btn6.setText(dayNumber);
                btnDate.put(6, copy(currentCalendar));
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(7, copy(currentCalendar));
                String btn7Text6 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, -6);
                btnDate.put(1, copy(currentCalendar));
                String btn1Text6 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(2, copy(currentCalendar));
                String btn2Text6 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(3, copy(currentCalendar));
                String btn3Text6 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(4, copy(currentCalendar));
                String btn4Text6 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                currentCalendar.add(Calendar.DATE, 1);
                btnDate.put(5, copy(currentCalendar));
                String btn5Text6 = currentCalendar.get(Calendar.DAY_OF_MONTH)+"";
                btn1.setText(btn1Text6);
                btn2.setText(btn2Text6);
                btn3.setText(btn3Text6);
                btn4.setText(btn4Text6);
                btn5.setText(btn5Text6);
                btn7.setText(btn7Text6);
                setData(6);
                break;
        }
        monday.setTypeface(SF_light);
        tuesday.setTypeface(SF_light);
        wednesday.setTypeface(SF_light);
        thursday.setTypeface(SF_light);
        friday.setTypeface(SF_light);
        saturday.setTypeface(SF_light);
        sunday.setTypeface(SF_light);
        dateDay.setTypeface(SF_regular);
        btn1.setTypeface(SF_regular);
        btn2.setTypeface(SF_regular);
        btn3.setTypeface(SF_regular);
        btn4.setTypeface(SF_regular);
        btn5.setTypeface(SF_regular);
        btn6.setTypeface(SF_regular);
        btn7.setTypeface(SF_regular);
        toNow.setTypeface(SF_light);
        toDay.setTypeface(SF_light);
        toMonth.setTypeface(SF_light);
        toWeek.setTypeface(SF_light);
        vatt.setTypeface(SF_regular);
    }

    public void setDayColor(){
        if(btn1.getText().toString().equals(dayNumber)){
            btn1.setTextColor(0xFF22D0BD);
        } else if (btn2.getText().toString().equals(dayNumber)){
            btn2.setTextColor(0xFF22D0BD);
        } else if (btn3.getText().toString().equals(dayNumber)){
            btn3.setTextColor(0xFF22D0BD);
        } else if (btn4.getText().toString().equals(dayNumber)){
            btn4.setTextColor(0xFF22D0BD);
        } else if (btn5.getText().toString().equals(dayNumber)){
            btn5.setTextColor(0xFF22D0BD);
        } else if (btn6.getText().toString().equals(dayNumber)){
            btn6.setTextColor(0xFF22D0BD);
        } else if (btn7.getText().toString().equals(dayNumber)){
            btn7.setTextColor(0xFF22D0BD);
        }
    }

    public void setDefaultBtn(){
        btn1.setTextColor(0xFF4A4A4A);
        btn1.setBackgroundColor(0xFFFAFAFA);
        btn2.setTextColor(0xFF4A4A4A);
        btn2.setBackgroundColor(0xFFFAFAFA);
        btn3.setTextColor(0xFF4A4A4A);
        btn3.setBackgroundColor(0xFFFAFAFA);
        btn4.setTextColor(0xFF4A4A4A);
        btn4.setBackgroundColor(0xFFFAFAFA);
        btn5.setTextColor(0xFF4A4A4A);
        btn5.setBackgroundColor(0xFFFAFAFA);
        btn6.setTextColor(0xFF4A4A4A);
        btn6.setBackgroundColor(0xFFFAFAFA);
        btn7.setTextColor(0xFF4A4A4A);
        btn7.setBackgroundColor(0xFFFAFAFA);
        setDayColor();
    }

    public void configListeners(){
        Log.d("UI", "Push");
        final Drawable drawable = getResources().getDrawable(R.drawable.btn_day_background);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBtn();
                btn1.setTextColor(0xFFFFFFFF);
                btn1.setBackground(drawable);
                setData(1);
                Log.d("UI", "Btn1 push");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBtn();
                btn2.setTextColor(0xFFFFFFFF);
                btn2.setBackground(drawable);
                setData(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBtn();
                btn3.setTextColor(0xFFFFFFFF);
                btn3.setBackground(drawable);
                setData(3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBtn();
                btn4.setTextColor(0xFFFFFFFF);
                btn4.setBackground(drawable);
                setData(4);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBtn();
                btn5.setTextColor(0xFFFFFFFF);
                btn5.setBackground(drawable);
                setData(5);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBtn();
                btn6.setTextColor(0xFFFFFFFF);
                btn6.setBackground(drawable);
                setData(6);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDefaultBtn();
                btn7.setTextColor(0xFFFFFFFF);
                btn7.setBackground(drawable);
                setData(7);
            }
        });
    }

    public void setData(int in){
        String out="";
        String[] daysOfWeek = getResources().getStringArray(R.array.weekDay);
        String[] months = getResources().getStringArray(R.array.months);
        out += daysOfWeek[btnDate.get(in).get(Calendar.DAY_OF_WEEK)-1] + ", " + btnDate.get(in).get(Calendar.DAY_OF_MONTH) + " "
                + months[btnDate.get(in).get(Calendar.MONTH)] + " " + btnDate.get(in).get(Calendar.YEAR);
        dateDay.setText(out);
    }

    public void initConnection(){
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        List<User> users = daoSession.getUserDao().loadAll();
        User lastUser = users.get(users.size()-1);
        Authentication authentication = new Authentication(lastUser.getLogin(), lastUser.getPassword(), getApplication());
        Log.d("Socket", "Start connection");
        socketThread = new SocketThread(handler, getApplication(), this);
        socketThread.setAuthentication(authentication);
        socketThread.setConnect(true);
        socketThread.execute();
    }
    
    public Calendar copy(Calendar calendar){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(calendar.getTime());
        return calendar1;
    }

    public void toSign(){
        Intent intent = new Intent(this, SignActivity.class);
        startActivity(intent);
    }

    public void configNowChart(){
        nowChart = findViewById(R.id.nowChart);
        nowChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // display msg when value selected

            }

            @Override
            public void onNothingSelected() {

            }
        });
        nowChart.setUsePercentValues(false);
        nowChart.setRotationEnabled(false);
        nowChart.setDrawHoleEnabled(false);
        nowChart.setDrawEntryLabels(true); //
        Legend legend = nowChart.getLegend();
        legend.setEnabled(false);

        Description description = new Description();
        description.setText("");
        nowChart.setDescription(description);

        /* TEST */
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        ArrayList<String> types = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        Refactor formatter;
        List<Value> allVal = daoSession.getValueDao().loadAll();

        types.add("null");
        values.add(1);
        setDataNowChart(types, values);

        final Runnable runnable = new Runnable() {

            @Override
            public void run() {
                updateChart();
            }
        };

        threadData = new Thread(new Runnable() {
            @Override
            public void run() {
                //runOnUiThread(runnable);
                while(true){
                    updateChart();
                    Log.d("Thread", "Go");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                /*try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        });

        threadData.start();
    }

    public void setDataNowChart(ArrayList<String> types, List<Integer> values){
        List<PieEntry> valueEnt = new ArrayList<>();
        Log.d("Size", "VAL SIZE: "+values.size()+" TYPES SIZE: "+types.size());
        int allVal = 0;
        for (int i = 0; i < values.size(); i++) {
            Log.d("Chart", "Types get: "+ types.get(i));
            Drawable tempAddIC;
            if(types.get(i).equals("null")){
                tempAddIC = bobrIC;
                valueEnt.add(new PieEntry(values.get(i), tempAddIC));
            } else if(types.get(i).equals("bulb")){
                tempAddIC = bulbIC;
                valueEnt.add(new PieEntry(values.get(i), tempAddIC));
            } else if(types.get(i).equals("phone")){
                tempAddIC = phoneIC;
                valueEnt.add(new PieEntry(values.get(i), tempAddIC));
            } else if(types.get(i).equals("laptop")){
                tempAddIC = laptopIC;
                valueEnt.add(new PieEntry(values.get(i), tempAddIC));
            } else if(types.get(i).equals("kettle")){
                tempAddIC = kettleIC;
                valueEnt.add(new PieEntry(values.get(i), tempAddIC));
            }
            allVal+=values.get(i);
        }
        vattOut = "";
        vattOut += allVal + " W";
        PieDataSet dataSet = new PieDataSet(valueEnt, "Types");
        dataSet.setSliceSpace(5);
        dataSet.setSelectionShift(8);
        dataSet.setDrawValues(false); //
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(0xFF1FA1D9);
        colors.add(0xFF00B7EA);
        colors.add(0xFF13CEDD);
        colors.add(0xFF5EE7E6);
        dataSet.setColors(colors);
        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        nowChart.setData(pieData);
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                vatt.setText(vattOut);
                nowChart.highlightValues(null);
                nowChart.invalidate();
            }
        });

    }

    public void updateChart(){
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        ArrayList<String> types;
        ArrayList<Integer> values;
        List<Value> allVal = daoSession.getValueDao().loadAll();
        Value currentVal;
        if(allVal.size() > 0){
            currentVal = allVal.get(allVal.size() - 1);
            Log.d("Socket", "Current val: "+currentVal.getTypes());
            Refactor formatter = new Refactor(currentVal.getTypes(), currentVal.getValues());
            types = formatter.typesToArr();
            values = formatter.valuesToArr();
            if((types.size() > 0) && (values.size() > 0)) {
                setDataNowChart(types, values);
            }
        }
    }

}
