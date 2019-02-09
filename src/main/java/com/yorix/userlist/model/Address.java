package com.yorix.userlist.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Address {
    @Id
    private int id;
    @Column(name = "country_id")
    private int countryId;
    @Column(name = "city_id")
    private int cityId;
    @Column(name = "street_id")
    private int streetId;
}
