package com.hotel.hotel.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String ORDERED = new String("ordered");

    //手动赋值主键生成策略
    @Id
    @GeneratedValue(generator = "roomId")
    @GenericGenerator(name = "roomId", strategy = "assigned")
    @Column(length = 8)
    private String roomId;

    /**
     * single :单人房
     * double :双人房
     * quad ：四人房
     * hourly :钟点房
     * pres :总统套房
     */
    @Column(nullable = false)
    private String type;

    /**
     * empty :空房
     * ordered :已预订
     * lived :已入住
     *
     */
    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String floor;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String normMemberPrice;

    @Column(nullable = false)
    private String vipMemberPrice;

    @Column
    private String remark;


    /**
     * 此构造用于扩建新房间
     * @param roomId
     * @param type
     * @param status
     * @param floor
     * @param price
     * @param normMemberPrice
     * @param vipMemberPrice
     * @param remark
     */
    public Room(String roomId, String type, String status, String floor, String price, String normMemberPrice, String vipMemberPrice, String remark) {
        this.roomId = roomId;
        this.type = type;
        this.status = status;
        this.floor = floor;
        this.price = price;
        this.normMemberPrice = normMemberPrice;
        this.vipMemberPrice = vipMemberPrice;
        this.remark = remark;
    }

    /**
     * 此构造用于修改房间信息
     * @param roomId
     * @param price
     * @param normMemberPrice
     * @param vipMemberPrice
     * @param remark
     */
    public Room(String roomId, String price, String normMemberPrice, String vipMemberPrice, String remark) {
        this.roomId = roomId;
        this.price = price;
        this.normMemberPrice = normMemberPrice;
        this.vipMemberPrice = vipMemberPrice;
        this.remark = remark;
    }

    protected Room(){}

    public String getRoomId() {
        return roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNormMemberPrice() {
        return normMemberPrice;
    }

    public void setNormMemberPrice(String normMemberPrice) {
        this.normMemberPrice = normMemberPrice;
    }

    public String getVipMemberPrice() {
        return vipMemberPrice;
    }

    public void setVipMemberPrice(String vipMemberPrice) {
        this.vipMemberPrice = vipMemberPrice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
