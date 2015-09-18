package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.envelop.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry.OrderDetailsRequestBean;
import com.google.gson.annotations.SerializedName;


public class OrderDetailsRequestEnvelope implements Envelope {

    @SerializedName("HED")
    private HeaderImpl header;
    @SerializedName("DAT")
    private OrderDetailsRequestBean body;

    public HeaderImpl getHeader() {
        return header;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = (OrderDetailsRequestBean) body;
    }

    public void setHeader(HeaderImpl header) {
        this.header = header;
    }
}
