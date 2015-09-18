package lk.ac.ucsc.oms.trs_connector.implGeneral.beans.inquiry.common;


public class DataFld {

    private String fieldType = null;
    private String valuesSet = null;
    private  static final int HASH_CONST = 31;

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getValuesSet() {
        return valuesSet;
    }

    public void setValuesSet(String valuesSet) {
        this.valuesSet = valuesSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DataFld dataFld = (DataFld) o;

        if (fieldType != null) {
            if (!fieldType.equals(dataFld.fieldType)) {
                return false;
            }
        } else {
            if (dataFld.fieldType != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = fieldType != null ? fieldType.hashCode() : 0;
        result = HASH_CONST * result + (valuesSet != null ? valuesSet.hashCode() : 0);
        return result;
    }
}
