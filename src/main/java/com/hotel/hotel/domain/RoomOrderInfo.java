package com.hotel.hotel.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RoomOrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false)
    private String status;

    @Column
    private Date orderTime;

    @Column
    private String remark;

    /**
     * 用于用户自行预定房间
     * @param name
     * @param tel
     * @param roomId
     * @param status
     * @param orderTime
     * @param remark
     */
    public RoomOrderInfo(String name, String tel, String roomId, String status, Date orderTime, String remark) {
        this.name = name;
        this.tel = tel;
        this.roomId = roomId;
        this.status = status;
        this.orderTime = orderTime;
        this.remark = remark;
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
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
