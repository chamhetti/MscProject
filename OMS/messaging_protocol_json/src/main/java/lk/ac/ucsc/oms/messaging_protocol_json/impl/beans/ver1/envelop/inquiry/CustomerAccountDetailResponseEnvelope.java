package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.envelop.inquiry;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.inquiry.CustomerAccountDetailResponseBean;
import com.google.gson.annotations.SerializedName;


public class CustomerAccountDetailResponseEnvelope implements Envelope {

    @SerializedName("HED")
    private HeaderImpl header;
    @SerializedName("DAT")
    private CustomerAccountDetailResponseBean body;

    public HeaderImpl getHeader() {
        return header;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = (CustomerAccountDetailResponseBean) body;
    }

    public void setHeader(HeaderImpl header) {
        this.header = header;
    }
}
