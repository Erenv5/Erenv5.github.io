package com.hotel.hotel.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Clerk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    /**
     * 权限
     *
     * fontDesk 前台
     * manager  经理
     */
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
    public Clerk(String name, String username, String password, String permission, String remark) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.remark = remark;
    }

    protected Clerk(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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
