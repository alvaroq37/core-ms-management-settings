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
    @ManyToOne(targetEntity = TypeOperation.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_operation_id")
    public TypeOperation typeOperation;
    @JoinColumn(name = "amount")
    public Double amount;
    @Column(name = "capital_balance")
    public Double capitalBalance;
    @Column(name = "capital_available")
    public Double capitalAvailable;
    @Column(name="maximum_range")
    public Double maximumRange;
    @Column(name = "date_start")
    public Date dateStart;
    @Column(name = "date_payment")
    public Date datePayment;
    @Column(name = "date_next_payment")
    public Date dateNextPayment;
    @Column(name = "days_passed")
    public Integer daysPassed;
    @Column(name="interest ")
    public Double interest;
    @ManyToOne(targetEntity = Contract.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    public Contract contract;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "user_create")
    public int userCreate;
    @Column(name = "user_update")
    public int userUpdate;

}
