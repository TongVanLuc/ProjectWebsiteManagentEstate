package com.javaweb.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "rentarea")
public class RentAreaEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private Long value;

    @ManyToOne
    @JoinColumn(name = "buildingid")
    private BuildingEntity buildingid;

    public BuildingEntity getBuildingid() {
        return buildingid;
    }

    public void setBuildingid(BuildingEntity buildingid) {
        this.buildingid = buildingid;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

}
