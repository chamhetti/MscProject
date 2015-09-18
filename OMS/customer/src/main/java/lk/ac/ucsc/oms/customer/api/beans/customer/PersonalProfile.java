package lk.ac.ucsc.oms.customer.api.beans.customer;

import lk.ac.ucsc.oms.customer.api.beans.Gender;

public interface PersonalProfile {

    String getEmail();

    void setEmail(String email);

    String getMobile();

    void setMobile(String mobile);

    String getOfficeTele();

    void setOfficeTele(String officeTele);

    String getNationality();

    void setNationality(String nationality);

    Gender getGender();

    void setGender(Gender gender);

    String getFirstName();

    void setFirstName(String name);

    String getTitle();

    void setTitle(String title);

    long getCustomerId();

    void setCustomerId(long customerId);

    String getLastName();

    void setLastName(String lastName);
}
