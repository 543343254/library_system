package com.wwh.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * User entity.
 *
 * @author Evan
 * @date 2019/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
@ToString
/*因为是做前后端分离，而前后端数据交互用的是 json 格式。
 那么 User 对象就会被转换为 json 数据。 而本项目使用 jpa 来做实体类的持久化，
 jpa 默认会使用 hibernate, 在 jpa 工作过程中，就会创造代理类来继承 User ，
 并添加 handler 和 hibernateLazyInitializer 这两个无须 json 化的属性，
 所以这里需要用 JsonIgnoreProperties 把这两个属性忽略掉。
 */
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Username.
     */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /**
     * Password.
     */
    private String password;

    /**
     * Salt for encoding.
     */
    private String salt;

    /**
     * Real name.
     */
    private String name;

    /**
     * Phone number.
     */
    private String phone;

    /**
     * Email address.
     * <p>
     * A Email address can be null,but should be correct if exists.
     */
    @Email(message = "请输入正确的邮箱")
    private String email;

    /**
     * User status.
     */
    private boolean enabled;

    /**
     * Transient property for storing role owned by current user.
     */
    @Transient
    private List<AdminRole> roles;

}

