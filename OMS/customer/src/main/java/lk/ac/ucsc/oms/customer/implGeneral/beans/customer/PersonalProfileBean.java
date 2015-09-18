package lk.ac.ucsc.oms.customer.implGeneral.beans.customer;

import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.customer.api.beans.Gender;
import lk.ac.ucsc.oms.customer.api.beans.customer.PersonalProfile;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Id;


@Indexed
public class PersonalProfileBean extends CacheObject implements PersonalProfile {
    @Id
    @Field
    private long customerId;
    @Field
    private String title;
    @Field
    private String firstName;
    @Field
    private Gender gender;
    @Field
    private String nationality;
    @Field
    private String officeTele;
    @Field
    private String mobile;
    @Field
    private String email;
    @Field
    private String lastName;


    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    @Override
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String getOfficeTele() {
        return officeTele;
    }

    @Override
    public void setOfficeTele(String officeTele) {
        this.officeTele = officeTele;
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String name) {
        this.firstName = name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public long getCustomerId() {
        return customerId;
    }

    @Override
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }


}
