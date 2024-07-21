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
    @Column(name = "days_passed")
    public Integer daysPassed;
    @Column(name="interest ")
    public Double interest;
    @Column(name = "foreign_currency_interest")
    public Double foreignCurrencyInterest;
    @Column(name = "local_currency_interest")
    public Double localCurrencyInterest;
    @Column(name="foreign_currency_debt_custody_expenses")
    public Double foreignCurrencyDebtCustodyExpenses;
    @Column(name="local_currency_debt_custody_expenses")
    public Double localCurrencyDebtCustodyExpenses;
    @Column(name = "foreign_currency_capital_amortization")
    public Double foreignCurrencyCapitalAmortization;
    @Column(name = "local_currency_capital_amortization")
    public Double localCurrencyCapitalAmortization;
    @Column(name = "foreign_currency_previous_balance")
    public Double foreignCurrencyPreviousBalance;
    @Column(name = "local_currency_previous_balance")
    public Double localCurrencyPreviousBalance;
    @Column(name = "foreign_currency_new_capital_balance")
    public Double foreignCurrencyNewCapitalBalance;
    @Column(name = "local_currency_new_capital_balance")
    public Double localCurrencyNewCapitalBalance;
    @Column(name = "foreign_currency_expiration_service_cost")
    public Double foreignCurrencyExpirationServiceCost;
    @Column(name = "local_currency_expiration_service_cost")
    public Double localCurrencyExpirationServiceCost;
    @Column(name="next_expiration_date")
    public Date nextExpirationDate;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "user_create")
    public int userCreate;
    @Column(name = "user_update")
    public int userUpdate;
    @ManyToOne(targetEntity = Contract.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    public Contract contract;
    @ManyToOne(targetEntity = TypeOperation.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "type_operation_id")
    public TypeOperation typeOperation;

}
