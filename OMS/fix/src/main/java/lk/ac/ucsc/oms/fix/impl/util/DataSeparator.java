package lk.ac.ucsc.oms.fix.impl.util;


public class DataSeparator {
    private String tag;
    private String data;
    private String separator = ";";

    public DataSeparator(String separator) {
        this.separator = separator;
    }

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

    public String getTag() {
        return tag;
    }

    public String getData() {
        return data;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
