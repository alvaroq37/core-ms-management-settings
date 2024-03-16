package core.ms.management.settings.dao.entity;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "t_contract")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;
    @Column(name = "value")
    public float value;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_expiration")
    public Date dateExpiration;
    @Column(name = "status")
    public boolean status;
    @Column(name = "rate_interest")
    public float rateInterest;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "user_create")
    public int userCreate;
    @Column(name = "user_update")
    public int userUpdate;
    @ManyToOne(targetEntity = BusinessDiscounts.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "business_discount_id")
    public BusinessDiscounts businessDiscounts;
    @ManyToOne(targetEntity = Client.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    public Client client;
    @ManyToOne(targetEntity = Jewel.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "jewel_id")
    public Jewel jewel;
    @ManyToOne(targetEntity = Agency.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="agency_id")
    public Agency agency;
    @ManyToOne(targetEntity = Currency.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="currency_id")
    public Currency currency;
}
