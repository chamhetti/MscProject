package com.directfn.exchange_simulator.message_processor.impl.beans;


public class DataSeparator {
    private String tag;
    private String data;
    private String separator = ";";


    public DataSeparator(String separator) {
        this.separator = separator;
    }

    /**
     * @param sData is the message data
     */
    public void setData(String sData) {
        if (sData == null) {
            tag = null;
            data = null;
            return;
        }
        int iEqual = sData.indexOf(separator);

        if (iEqual > 0) {
            tag = sData.substring(0, iEqual);
            data = sData.substring(iEqual + 1).trim();
        } else {
            tag = null;
            data = null;
        }
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param separator is the data separator
     */
    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
