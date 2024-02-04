package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long id;
    @Column(name = "ci")
    public String ci;
    @Column(name = "names")
    public String names;
    @Column(name = "last_name_paternal")
    public String lastNamesPaternal;
    @Column(name = "last_name_maternal")
    public String lastNamesMaternal;
    @Column(name = "address")
    public String address;
    @Column(name = "cell_phone")
    public int cellPhone;
    @Column(name = "phone")
    public int phone;
    @Column(name = "email")
    public String email;
    @Column(name = "date_birth")
    public Date dateBirth;

    @OneToOne(targetEntity = City.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    public City city;

    @OneToOne(targetEntity = Sex.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sex_id")
    public Sex sex;

    @OneToOne(targetEntity = Occupation.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "occ_id")
    public Occupation occupation;

    @OneToOne(targetEntity = ClientCategory.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "cc_id")
    public ClientCategory clientCategory;
}