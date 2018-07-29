package com.hotel.hotel.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class RoomLiveInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String tel;

    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Date liveTime;

    @Column(nullable = false)
    private String deposit;

    @Column(nullable = false)
    private String operator;

    @Column(nullable = false)
    private String price;

    @Column
    private String remark;

    /**
     * 用于用户自行预定房间
     * @param name
     * @param tel
     * @param roomId
     * @param status
     * @param liveTime
     * @param deposit
     * @param operator
     * @param price
     * @param remark
     */
    public RoomLiveInfo(String name, String tel, String roomId, String status, Date liveTime,String deposit,String operator, String price, String remark) {
        this.name = name;
        this.tel = tel;
        this.roomId = roomId;
        this.status = status;
        this.liveTime = liveTime;
        this.deposit = deposit;
        this.operator = operator;
        this.price = price;
        this.remark = remark;
    }

    protected RoomLiveInfo(){}

    public Long getInfoId() {
        return infoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getOrderTime() {
        return liveTime;
    }

    public void setOrderTime(Date orderTime) {
        this.liveTime = orderTime;
    }

    public Date getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(Date liveTime) {
        this.liveTime = liveTime;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
