package com.hibernate.cache.model;

import javax.persistence.*;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * @author <a href="mailto:sunil.pulugula@wavemaker.com">Sunil Kumar</a>
 * @since 16/11/15
 */
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "user")
@Entity
@Table(name = "USER")
public class User implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "USERID", nullable = false)
    private Integer userid;

    @Column(name = "USERNAME", length = 20)
    private String username;

    @Column(name = "PASSWORD", length = 20)
    private String password;

    @Column(name = "ROLE", length = 20)
    private String role;

    @Column(name = "ACTIVE")
    private boolean active;

    public User() {
    }

    public User(Integer userid, String username, String password, String role, boolean active) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = active;
    }


    public Integer getUserid() {
        return this.userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }


    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if ((o == null))
            return false;
        if (!(o instanceof User))
            return false;

        User that = (User) o;

        return ((this.getUserid() == that.getUserid()) || (this.getUserid() != null && that.getUserid() != null && this.getUserid().equals(that.getUserid())));

    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getUserid() == null ? 0 : this.getUserid().hashCode());

        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                '}';
    }
}
