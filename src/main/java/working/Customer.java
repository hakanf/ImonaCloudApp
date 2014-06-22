package working;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    public Set<Channel> getChannels() {

        return this.channels;
    }

    public void setChannels(Set<Channel> channels) {
        this.channels = channels;
    }

}
