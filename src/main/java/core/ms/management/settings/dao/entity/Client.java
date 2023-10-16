package core.ms.management.settings.dao.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_id")
    private long id;
    @Column(name = "cli_ci")
    private String ci;
    @Column(name = "cli_names")
    private String names;
    @Column(name = "cli_last_name_paternal")
    private String lastNamesPaternal;
    @Column(name = "cli_last_name_maternal")
    private String lastNamesMaternal;
    @Column(name = "cli_address")
    private String address;
    @Column(name = "cli_cell_phone")
    private int cellPhone;
    @Column(name = "cli_phone")
    private int phone;
    @Column(name = "cli_email")
    private String email;
    @Column(name = "cli_date_birth")
    private Date dateBirth;

    @ManyToOne(targetEntity = City.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne(targetEntity = Sex.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sex_id")
    private Sex sexuality;

    @ManyToOne(targetEntity = Occupation.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "occ_id")
    private Occupation occupation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNamesPaternal() {
        return lastNamesPaternal;
    }

    public void setLastNamesPaternal(String lastNamesPaternal) {
        this.lastNamesPaternal = lastNamesPaternal;
    }

    public String getLastNamesMaternal() {
        return lastNamesMaternal;
    }

    public void setLastNamesMaternal(String lastNamesMaternal) {
        this.lastNamesMaternal = lastNamesMaternal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(int cellPhone) {
        this.cellPhone = cellPhone;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Sex getSexuality() {
        return sexuality;
    }

    public void setSexuality(Sex sexuality) {
        this.sexuality = sexuality;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}
