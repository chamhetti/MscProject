package lk.ac.ucsc.oms.trs_connector.api;



import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.reply.AuthResponseNormal;
import lk.ac.ucsc.oms.trs_connector.api.beans.authentication.request.AuthRequestNormal;



public interface LoginControllerInterface {

    AuthResponseNormal doLogin(AuthRequestNormal authRequestNormal);


    /**
     * Method used to log out a customer
     *
     * @param userName
     * @return
     */
    boolean logOut(String userName);



}
