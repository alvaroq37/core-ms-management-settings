package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
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
    @Column(name = "date_create")
    public Date dateCreate;
    @Column(name = "date_update")
    public Date dateUpdate;
    @Column(name = "user_create")
    public int userCreate;
    @Column(name = "user_update")
    public int userUpdate;
    @ManyToOne(targetEntity = Occupation.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "occ_id")
    public Occupation occupation;
    @ManyToOne(targetEntity = Gender.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "gender_id")
    public Gender gender;
    @ManyToOne(targetEntity = City.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    public City city;
    @ManyToOne(targetEntity = Agency.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "agency_id")
    public Agency agency;
}
