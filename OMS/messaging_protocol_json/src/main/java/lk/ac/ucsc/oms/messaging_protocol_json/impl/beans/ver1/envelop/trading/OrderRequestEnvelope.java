package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.envelop.trading;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.trading.OrderRequestBean;
import com.google.gson.annotations.SerializedName;


public class OrderRequestEnvelope implements Envelope {
    @SerializedName("HED")
    private HeaderImpl header;
    @SerializedName("DAT")
    private OrderRequestBean body;

    @Override
    public HeaderImpl getHeader() {
        return header;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = (OrderRequestBean) body;
    }

    @Override
    public void setHeader(HeaderImpl header) {
        this.header = header;
    }
}
