package lk.ac.ucsc.oms.messaging_protocol_json.api.beans;

import lk.ac.ucsc.oms.messaging_protocol_json.impl.beans.HeaderImpl;


public interface Envelope {

    void setHeader(HeaderImpl header);

    HeaderImpl getHeader();

    Object getBody();

    void setBody(Object body);

}
