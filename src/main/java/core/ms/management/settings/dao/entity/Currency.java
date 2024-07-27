package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "t_currency")
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;
    @Column(name = "desription")
    public String description;
    @Column(name = "abbreviation")
    public String abbreviation;
    @Column(name = "purchase_exchange_rate")
    public Double purchaseExchangeRate;
    @Column(name = "exchange_rate_sale")
    public Double exchangeRateSale;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "userCreate")
    public int userCreate;
    @Column(name = "user_update")
    public int userUpdate;
}
