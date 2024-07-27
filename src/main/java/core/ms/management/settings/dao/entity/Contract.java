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
    @Column(name = "maximum_range")
    public Double maximumRange;
    @Column(name="capital_balance")
    public Double capitalBalance;
    @Column(name = "available_capital")
    public Double availableCapital;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_expiration")
    public Date dateExpiration;
    @Column(name = "status")
    public boolean status;
    @Column(name = "rate_interest")
    public Double rateInterest;
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
    @ManyToOne(targetEntity = Agency.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="agency_id")
    public Agency agency;
    @ManyToOne(targetEntity = Currency.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="currency_id")
    public Currency currency;
    @ManyToOne(targetEntity = LoanType.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_type_id")
    public LoanType loanType;
}
