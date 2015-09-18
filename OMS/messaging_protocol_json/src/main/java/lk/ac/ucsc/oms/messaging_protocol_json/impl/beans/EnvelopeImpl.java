package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import com.google.gson.annotations.SerializedName;

/**
 * @author hetti
 */
public class EnvelopeImpl implements Envelope {

    @SerializedName("HED")
    private HeaderImpl header;
    @SerializedName("DAT")
    private Object body;

    public EnvelopeImpl() {
    }

    public EnvelopeImpl(HeaderImpl header, Object body) {
        this.header = header;
        this.body = body;
    }

    @Override
    public HeaderImpl getHeader() {
        return header;
    }

    @Override
    public void setHeader(HeaderImpl header) {
        this.header = header;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }

}
