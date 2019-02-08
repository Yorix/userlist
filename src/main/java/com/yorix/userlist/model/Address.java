package com.yorix.userlist.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@Data
public class Address {
    @Column(name = "country_id")
    private int countryId;
    @Column(name = "city_id")
    private int cityId;
    @Column(name = "street_id")
    private int streetId;
}
