package com.hotel.hotel.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Clerk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "密码不能为空")
    @Column(nullable = false)
    private String password;

    /**
     * 权限
     *
     * fontDesk 前台
     * manager  经理
     */
    @NotEmpty(message = "权限不能为空")
    @Column(nullable = false)
    private String permission;

    @Column
    private String remark;

    /**
     * 此构造用于经理添加员工
     * @param password
     * @param permission
     * @param remark
     */
    public Clerk(String password, String permission, String remark) {
        this.password = password;
        this.permission = permission;
        this.remark = remark;
    }

    protected Clerk(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
