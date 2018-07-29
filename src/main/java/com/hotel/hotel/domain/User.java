package com.hotel.hotel.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * User 实体
 * 包括所有种类用户
 * 会员、前台、经理
 */

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "姓名不能为空")
    @Size(min = 3,max = 50)
    @Column(nullable = false)
    private String username;

    @NotBlank(message = "密码不能为空")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "联系方式不能为空")
    @Column(nullable = false, unique = true)
    private String telephone;

    @NotBlank(message = "姓名不能为空")
    @Size(min = 2,max = 20)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "性别不能为空")
    @Column(nullable = false)
    private String sex;

    @Column
    private String address;

    @Column
    private String email;

    @NotNull(message = "积分不能为空")
    @Column(nullable = false)
    private Long intergal;

    /**
     * 会员等级：
     * norm 普通会员
     * vip  VIP会员
     */
    @NotBlank(message = "等级不能为空")
    @Column(nullable = false)
    private String level;

    @Column
    private Date last_housing_time;

    @Column
    private Date last_order_time;

    @Column
    private String remark;

//    public void setAuthorities(List<Authority> authorities) {
//        this.authorities = authorities;
//    }
//
//    public void setEncodePassword(String password) {
//        PasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodePasswd = encoder.encode(password);
//        this.password = encodePasswd;
//    }

//    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
//    @JoinTable(name="user_member",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
//        inverseJoinColumns = @JoinColumn(name = "member_id", referencedColumnName = "id"))
//    private List<Authority> authorities;

    /**
     * 此构造用于前台注册
     * @param name
     * @param sex
     * @param password
     * @param telephone
     * @param address
     * @param email
     * @param intergal
     * @param level
     * @param last_housing_time
     * @param last_order_time
     * @param remark
     */
    public User(String name, String sex, String username, String password, String telephone, String address, String email, Long intergal, String level, Date last_housing_time, Date last_order_time, String remark) {
        this.name = name;
        this.sex = sex;
        this.username = username;
        this.password = password;
        this.telephone = telephone;
        this.address = address;
        this.email = email;
        this.intergal = intergal;
        this.level = level;
        this.last_housing_time = last_housing_time;
        this.last_order_time = last_order_time;
        this.remark = remark;
    }



    /**
     * 此构造用于会员自行注册
     * @Param username
     * @param name
     * @param sex
     * @param password
     * @param telephone
     * @param address
     * @param email
     */
    public User(String username,String name, String sex, String password, String telephone, String address, String email, String remark) {
        this.username = username;
        this.name = name;
        this.sex = sex;
        this.password = password;
        this.telephone = telephone;
        this.address = address;
        this.email = email;
        this.remark = remark;
        this.level = "norm";
        this.intergal = 0L;
        this.last_housing_time=null;
        this.last_order_time=null;
    }

    protected User(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 将 List<Authority> 转化为 List<SimpleGrantedAuthority> ，否则前端拿不到角色列表名称
     * 前端只能展示字符串
     * @return
     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
//        for(GrantedAuthority authority : this.getAuthorities()){
//            simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
//        }
//        return simpleGrantedAuthorities;
//    }

    public String getPassword() {
        return password;
    }

//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIntergal() {
        return intergal;
    }

    public void setIntergal(Long intergal) {
        this.intergal = intergal;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getLast_housing_time() {
        return last_housing_time;
    }

    public void setLast_housing_time(Date last_housing_time) {
        this.last_housing_time = last_housing_time;
    }

    public Date getLast_order_time() {
        return last_order_time;
    }

    public void setLast_order_time(Date last_order_time) {
        this.last_order_time = last_order_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
