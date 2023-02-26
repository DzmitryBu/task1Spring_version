package by.it_academy.fitness.entity;

import by.it_academy.fitness.core.dto.UserRole;
import by.it_academy.fitness.core.dto.UserStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users",
        uniqueConstraints =
                {
                        @UniqueConstraint(columnNames = "uuid"),
                        @UniqueConstraint(columnNames = "mail")
                })
public class UserCreateEntity {

    @Id
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "fio")
    private String fio;

    @Column(name = "dt_create")
    private LocalDateTime dt_create;

    @Column(name = "dt_update")
    @Version
    private LocalDateTime dt_update;

    @Column(name = "mail")
    private String mail;

//    @ManyToOne
//    @JoinTable(
//            name="fitness.user_role",
//            joinColumns=
//            @JoinColumn(name="user_uuid", referencedColumnName="ID"),
//            inverseJoinColumns=
//            @JoinColumn(name="role_ID", referencedColumnName="ID")
//    )
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;

//    @ManyToOne
//    @JoinTable(
//            name="fitness.user_status",
//            joinColumns=
//            @JoinColumn(name="user_uuid", referencedColumnName="ID"),
//            inverseJoinColumns=
//            @JoinColumn(name="status_ID", referencedColumnName="ID")
//    )
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;

    @Column(name = "password")
    private String password;

    public UserCreateEntity() {
    }

    public UserCreateEntity(UUID uuid,
                            String fio,
                            LocalDateTime dt_create,
                            LocalDateTime dt_update,
                            String mail,
                            UserRole role,
                            UserStatus status,
                            String password) {
        this.uuid = uuid;
        this.fio = fio;
        this.dt_create = dt_create;
        this.dt_update = dt_update;
        this.mail = mail;
        this.role = role;
        this.status = status;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getFio() {
        return fio;
    }

    public LocalDateTime getDtCreate() {
        return dt_create;
    }

    public LocalDateTime getDtUpdate() {
        return dt_update;
    }

    public String getMail() {
        return mail;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setDt_update(LocalDateTime dt_update) {
        this.dt_update = dt_update;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreateEntity that = (UserCreateEntity) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(fio, that.fio)
                && Objects.equals(dt_create, that.dt_create) && Objects.equals(dt_update, that.dt_update)
                && Objects.equals(mail, that.mail) && role == that.role
                && status == that.status && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fio, dt_create, dt_update, mail, role, status, password);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "uuid=" + uuid +
                ", fio='" + fio + '\'' +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
                ", mail='" + mail + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
