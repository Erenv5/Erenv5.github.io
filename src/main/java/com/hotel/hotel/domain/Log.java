package com.hotel.hotel.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String roomId;

    @Column(nullable = false)
    private String remark;

    /**
     * 用于记录每次操作详情
     * @param userId
     * @param roomId
     * @param remark
     */
    public Log(Long userId, String roomId, String remark) {
        this.userId = userId;
        this.roomId = roomId;
        this.remark = remark;
    }

    protected Log(){}

    public Long getLogId() {
        return logId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        roomId = roomId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
