package dev.orlyata.pinebobr;

import android.app.Application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DatasetCreater {
    private Application application;

    /**
     * Date type:
     * 1) now
     * 2) day
     * 3) week
     */
    private String dateType;

    /**
     * Type of diagram
     * date now always with devices = true
     */
    private boolean devices;

    /**
     * Dataset with types:
     * Integer key - hour/day/month
     * String key - type
     * Integer value - value
     */
    private Map<String, Integer> datasetNow;
    private Map<String, Integer> datasetDayWithDevices;
    private Map<Integer, Integer> datasetDayWithoutDevices;
    private Map<String, Integer> datasetWeekWithDevices;
    private Map<Integer, Integer> datasetWeekWithoutDevices;
    private Map<String, Integer> datasetMonthWithDevices;
    private Map<Integer, Integer> datasetMonthWithoutDevices;
    private Map<String, Integer> datasetYearWithDevices;
    private Map<Integer, Integer> datasetYearWithoutDevices;

    /**
     * Timestamp of value in last update database
     */
    private String lastTimestamp;

    /**
     * IF THIS VARIABLE EQUALS FALSE GET FUNCTION RETURN NULL
     * CHECK THIS VARIABLE BEFORE CALL GET FUNCTIONS
     */
    private boolean haveData;

    /**
     * Delay in sending data in milliseconds
     */
    private final int delay = 1000;

    private int voltage = 220;

    public DatasetCreater(String dateType, boolean devices, Application application){
        this.dateType = dateType;
        if(dateType.equals("now")){
            devices = true;
        }
        this.devices = devices;
        this.application = application;
    }

    public void createDataset(){
        /* Init database */
        DaoSession daoSession = ((App) application).getDaoSession();
        if(dateType.equals("now")){
            List<Value> valueList = daoSession.getValueDao().loadAll();
            if(valueList.size() == 0){
                haveData = false;
            } else {
                Value lastValue = valueList.get(valueList.size()-1);
                lastTimestamp = lastValue.getTimestamp();
                Refactor refactor = new Refactor(lastValue.getTypes(), lastValue.getValues());
                ArrayList<String> types = refactor.typesToArr();
                ArrayList<Integer> values = refactor.valuesToArr();
                for (int i = 0; i < types.size(); i++) {
                    datasetNow.put(types.get(i), values.get(i));
                }
                haveData = true;
            }
        } else if(dateType.equals("day")){
            if(devices){
                List<Value> valueList = daoSession.getValueDao().loadAll();
                if(valueList.size() == 0) {
                    haveData = false;
                } else {
                    List<Value> valueDayList = new ArrayList<>();
                    SimpleDateFormat format = new SimpleDateFormat("dd:mm:yyyy HH:mm:ss");
                    Date date;
                    Date currentTime = Calendar.getInstance().getTime();
                    for(Value value:valueList){
                        try{
                            date = format.parse(value.getTimestamp());
                            Calendar calendar = Calendar.getInstance();
                            Calendar currentCalendar = Calendar.getInstance();
                            calendar.setTime(date);
                            currentCalendar.setTime(currentTime);
                            if((calendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR))){
                                if(calendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR)){
                                    valueDayList.add(value);
                                } else if(calendar.get(Calendar.DAY_OF_YEAR) < currentCalendar.get(Calendar.DAY_OF_YEAR)){
                                    break;
                                }
                            }
                        } catch (ParseException e){
                            e.printStackTrace();
                        }
                    }
                    for(Value value:valueDayList){
                        List<String> tempTypes;
                        List<Integer> tempVal;
                        List<Integer> tempEnergy = new ArrayList<>();
                        Refactor refactor = new Refactor(value.getTypes(), value.getValues());
                        tempTypes = refactor.typesToArr();
                        tempVal = refactor.valuesToArr();
                        for(Integer i:tempVal){
                            tempEnergy.add(i*voltage*delay/1000);
                        }
                        for (int i = 0; i < tempTypes.size(); i++) {
                            if(datasetDayWithDevices.containsKey(tempTypes.get(i))){
                                int temp = datasetDayWithDevices.get(tempTypes.get(i)) + tempEnergy.get(i);
                                datasetDayWithDevices.remove(tempTypes.get(i));
                                datasetDayWithDevices.put(tempTypes.get(i), temp);
                            } else {
                                datasetDayWithDevices.put(tempTypes.get(i), tempEnergy.get(i));
                            }
                        }

                    }
                }
            } else {
                List<Value> valueList = daoSession.getValueDao().loadAll();
                if(valueList.size() == 0) {
                    haveData = false;
                } else {
                    List<Value> valueDayList = new ArrayList<>();
                    SimpleDateFormat format = new SimpleDateFormat("dd:mm:yyyy HH:mm:ss");
                    Date date;
                    Date currentTime = Calendar.getInstance().getTime();
                    for(Value value:valueList){
                        try{
                            date = format.parse(value.getTimestamp());
                            Calendar calendar = Calendar.getInstance();
                            Calendar currentCalendar = Calendar.getInstance();
                            calendar.setTime(date);
                            currentCalendar.setTime(currentTime);
                            if((calendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR))){
                                if(calendar.get(Calendar.DAY_OF_YEAR) == currentCalendar.get(Calendar.DAY_OF_YEAR)){
                                    valueDayList.add(value);
                                } else if(calendar.get(Calendar.DAY_OF_YEAR) < currentCalendar.get(Calendar.DAY_OF_YEAR)){
                                    break;
                                }
                            }
                        } catch (ParseException e){
                            e.printStackTrace();
                        }
                    }
                    for(Value value:valueDayList){
                        List<String> tempTypes;
                        List<Integer> tempVal;
                        Date dateTemp;
                        Calendar calendar = Calendar.getInstance();
                        List<Integer> tempEnergy = new ArrayList<>();
                        Refactor refactor = new Refactor(value.getTypes(), value.getValues());
                        tempTypes = refactor.typesToArr();
                        tempVal = refactor.valuesToArr();
                        try{
                            dateTemp = format.parse(value.getTimestamp());
                            calendar.setTime(dateTemp);
                        } catch (ParseException e){
                            e.printStackTrace();
                        }
                        for(Integer i:tempVal){
                            tempEnergy.add(i*voltage*delay/1000);
                        }
                        for (int i = 0; i < tempTypes.size(); i++) {
                            if(datasetDayWithoutDevices.containsKey(calendar.get(Calendar.HOUR_OF_DAY))){
                                int temp = datasetDayWithoutDevices.get(calendar.get(Calendar.HOUR_OF_DAY)) + tempEnergy.get(i);
                                datasetDayWithoutDevices.remove(calendar.get(Calendar.HOUR_OF_DAY));
                                datasetDayWithoutDevices.put(calendar.get(Calendar.HOUR_OF_DAY), temp);
                            } else {
                                datasetDayWithoutDevices.put(calendar.get(Calendar.HOUR_OF_DAY), tempEnergy.get(i));
                            }
                        }

                    }
                }
            }
        }
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public boolean isDevices() {
        return devices;
    }

    public void setDevices(boolean devices) {
        this.devices = devices;
    }

    public Map<String, Integer> getDatasetNow() {
        return datasetNow;
    }

    public void setDatasetNow(Map<String, Integer> datasetNow) {
        this.datasetNow = datasetNow;
    }

    public Map<String, Integer> getDatasetDayWithDevices() {
        return datasetDayWithDevices;
    }

    public void setDatasetDayWithDevices(Map<String, Integer> datasetDayWithDevices) {
        this.datasetDayWithDevices = datasetDayWithDevices;
    }

    public Map<Integer, Integer> getDatasetDayWithoutDevices() {
        return datasetDayWithoutDevices;
    }

    public void setDatasetDayWithoutDevices(Map<Integer, Integer> datasetDayWithoutDevices) {
        this.datasetDayWithoutDevices = datasetDayWithoutDevices;
    }

    public Map<String, Integer> getDatasetWeekWithDevices() {
        return datasetWeekWithDevices;
    }

    public void setDatasetWeekWithDevices(Map<String, Integer> datasetWeekWithDevices) {
        this.datasetWeekWithDevices = datasetWeekWithDevices;
    }

    public Map<Integer, Integer> getDatasetWeekWithoutDevices() {
        return datasetWeekWithoutDevices;
    }

    public void setDatasetWeekWithoutDevices(Map<Integer, Integer> datasetWeekWithoutDevices) {
        this.datasetWeekWithoutDevices = datasetWeekWithoutDevices;
    }

    public Map<String, Integer> getDatasetMonthWithDevices() {
        return datasetMonthWithDevices;
    }

    public void setDatasetMonthWithDevices(Map<String, Integer> datasetMonthWithDevices) {
        this.datasetMonthWithDevices = datasetMonthWithDevices;
    }

    public Map<Integer, Integer> getDatasetMonthWithoutDevices() {
        return datasetMonthWithoutDevices;
    }

    public void setDatasetMonthWithoutDevices(Map<Integer, Integer> datasetMonthWithoutDevices) {
        this.datasetMonthWithoutDevices = datasetMonthWithoutDevices;
    }

    public Map<String, Integer> getDatasetYearWithDevices() {
        return datasetYearWithDevices;
    }

    public void setDatasetYearWithDevices(Map<String, Integer> datasetYearWithDevices) {
        this.datasetYearWithDevices = datasetYearWithDevices;
    }

    public Map<Integer, Integer> getDatasetYearWithoutDevices() {
        return datasetYearWithoutDevices;
    }

    public void setDatasetYearWithoutDevices(Map<Integer, Integer> datasetYearWithoutDevices) {
        this.datasetYearWithoutDevices = datasetYearWithoutDevices;
    }

    public String getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(String lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public boolean isHaveData() {
        return haveData;
    }

    public void setHaveData(boolean haveData) {
        this.haveData = haveData;
    }

    public int getDelay() {
        return delay;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }
}
