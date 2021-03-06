package lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.envelop.authentication;

import lk.ac.ucsc.oms.messaging_protocol_json.api.beans.Envelope;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;
import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.ver1.authentication.AuthenticationNormalRequestBean;
import com.google.gson.annotations.SerializedName;

/**
 * @author hetti
 */
public class AuthenticationNormalRequestEnvelope implements Envelope {
    @SerializedName("HED")
    private HeaderImpl header;
    @SerializedName("DAT")
    private AuthenticationNormalRequestBean body;

    public HeaderImpl getHeader() {
        return header;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = (AuthenticationNormalRequestBean) body;
    }

    public void setHeader(HeaderImpl header) {
        this.header = header;
    }

}
