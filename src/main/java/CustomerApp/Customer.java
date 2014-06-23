package CustomerApp;

import java.util.*;
import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "customer", catalog = "customer", uniqueConstraints = {
        @UniqueConstraint(columnNames = "NAME"),
        @UniqueConstraint(columnNames = "SURNAME") })
public class Customer implements java.io.Serializable {

    private Integer customerId;
    private String name;
    private String surname;
    private String gender;
    private String birthDate;
    private String birthCity;
    private String activation;
    private Set<Channel> channels = new HashSet<Channel>(
            0);

    public Customer() {
    }

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Customer(String name, String surname,
                    Set<Channel> channels) {
        this.name = name;
        this.surname = surname;
        this.channels = channels;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CUSTOMER_ID", unique = true, nullable = false)
    public Integer getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Column(name = "NAME",  nullable = false, length = 10)
    public String getname() {
        return this.name;
    }

    public void setname(String name) {
        this.name = name;
    }

    @Column(name = "SURNAME",  nullable = false, length = 20)
    public String getsurname() {
        return this.surname;
    }

    public void setsurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "GENDER",  nullable = false, length = 10)

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Column(name = "BIRTH_DATE",  nullable = false, length = 10)

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Column(name = "BIRTH_CITY",  nullable = false, length = 20)

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    @Column(name = "ACTIVATION",  nullable = false, length = 10)

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    public Set<Channel> getChannels() {

        return this.channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

}
