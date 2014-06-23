package CustomerApp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "channel", catalog = "customer")
public class Channel implements java.io.Serializable {

    private Integer channelId;
    private Customer customer;
    private String channelName;



    public Channel() {
    }

    public Channel(Customer customer, String channelName) {
        this.customer = customer;
        this.channelName = channelName;
    }



    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CHANNEL_ID", unique = true, nullable = false)
    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Column(name = "CHANNEL_NAME", precision = 6)
    public String getChannelName() {
        return this.channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }



}
