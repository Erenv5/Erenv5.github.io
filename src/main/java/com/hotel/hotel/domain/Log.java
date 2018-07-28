package com.hotel.hotel.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String logId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String RoomId;

    @Column(nullable = false)
    private String remark;

    /**
     * 用于记录每次操作详情
     * @param userId
     * @param roomId
     * @param remark
     */
    public Log(String userId, String roomId, String remark) {
        this.userId = userId;
        RoomId = roomId;
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return RoomId;
    }

    public void setRoomId(String roomId) {
        RoomId = roomId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
