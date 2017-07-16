package org.sjtu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by ace on 6/14/17.
 */
@Entity
@Table(name = "address")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "addr_id")
    private Integer addrId;

    @Column(name = "country", length = 32)
    private String addrCountry;

    @Column(name = "city", length = 32)
    private String addrCity;

    @Column(name = "addr", length = 512)
    private String addr;

    @Column(name = "postalcode", length = 8)
    private String postalCode;

    @Column(name = "phone", length = 20)
    private String addrPhone;

    @Column(name = "recv_name", length = 128)
    private String addrRecvName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "addrUser")
    private UserEntity addrUser;

    public AddressEntity() {}

    public Integer getAddrId() {
        return addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }

    public String getAddrCountry() {
        return addrCountry;
    }

    public void setAddrCountry(String addrCountry) {
        this.addrCountry = addrCountry;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddrPhone() {
        return addrPhone;
    }

    public void setAddrPhone(String addrPhone) {
        this.addrPhone = addrPhone;
    }

    public String getAddrRecvName() {
        return addrRecvName;
    }

    public void setAddrRecvName(String addrRecvName) {
        this.addrRecvName = addrRecvName;
    }

    public UserEntity getAddrUser() {
        return addrUser;
    }

    public void setAddrUser(UserEntity addrUser) {
        this.addrUser = addrUser;
    }

    @Override
    public String toString() {
        return "AddressEntity{" +
                "addrId=" + addrId +
                ", addrCountry='" + addrCountry + '\'' +
                ", addrCity='" + addrCity + '\'' +
                ", addr='" + addr + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", addrPhone='" + addrPhone + '\'' +
                ", addrRecvName='" + addrRecvName + '\'' +
                ", addrUser=" + addrUser +
                '}';
    }
}
