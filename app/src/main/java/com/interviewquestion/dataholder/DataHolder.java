package com.interviewquestion.dataholder;

public class DataHolder {
    private static DataHolder dataHolder;


    private DataHolder() {
        //singleton
    }

    public synchronized static DataHolder getInstance() {
        if (dataHolder == null) {
            dataHolder = new DataHolder();
        }
        return dataHolder;
    }

}
