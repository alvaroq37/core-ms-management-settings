package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "t_jewel")
public class Jewel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public long id;
    @Column(name= "jewel")
    public String jewel;
    @Column(name = "description")
    public String description;
    @Column(name="number_parts")
    public long numberParts;
    @Column(name="jewel_type")
    public long jewelType;
    @Column(name="gross_weight")
    public float grossWeight;
    @Column(name="net_weight")
    public float netWeight;
    @Column(name="net_weight_loan")
    public float netWeightLoan;
    @ManyToOne(targetEntity = Material.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "material_id")
    public Material material;
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "user_create")
    public int user_create;
    @Column(name = "user_update")
    public int userUpdate;
    @ManyToOne(targetEntity = Contract.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    public Contract contract;
}
