package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "t_contract_operation")
public class ContractOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;
    @Column(name = "type_operation")
    public Long typeOperation;
    @Column(name = "capital_balance")
    public Double capitalBalance;
    @Column(name = "capital_available")
    public Double capitalAvailable;
    @Column(name = "date_payment")
    public Date datePayment;
    @Column(name = "date_next_payment")
    public Date date_next_payment;
    @ManyToOne(targetEntity = Contract.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    public Contract contract;

}
