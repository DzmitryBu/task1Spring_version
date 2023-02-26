package by.it_academy.fitness.core.dto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

public class User {

    private UUID uuid;

    private long dtCreate;

    private long dtUpdate;

    private String mail;

    private String fio;

    private UserRole role;

    private UserStatus status;

    public User(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String mail,
                String fio, UserRole role, UserStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate.toEpochSecond(ZoneOffset.UTC);
        this.dtUpdate = dtUpdate.toEpochSecond(ZoneOffset.UTC);
        this.mail = mail;
        this.fio = fio;
        this.role = UserRole.USER;
        this.status = status;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getDt_create() {
        return dtCreate;
    }

    public long getDt_update() {
        return dtUpdate;
    }

    public String getMail() {
        return mail;
    }

    public String getFio() {
        return fio;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setDt_update(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate.toEpochSecond(ZoneOffset.UTC);
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid) && Objects.equals(dtCreate, user.dtCreate) && Objects.equals(dtUpdate, user.dtUpdate) && Objects.equals(mail, user.mail) && Objects.equals(fio, user.fio) && role == user.role && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, mail, fio, role, status);
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", dt_create=" + dtCreate +
                ", dt_update=" + dtUpdate +
                ", mail='" + mail + '\'' +
                ", fio='" + fio + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
