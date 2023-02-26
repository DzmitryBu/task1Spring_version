package by.it_academy.fitness.core.dto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

public class UserCreate {

    private UUID uuid;

    private long dt_create;

    private long dt_update;

    private String mail;

    private String fio;

    private UserRole role;

    private UserStatus status;
    private String password;

    public UserCreate() {
    }

    public UserCreate(UUID uuid, LocalDateTime dt_create, LocalDateTime dt_update, String mail,
                      String fio, UserRole role, UserStatus status, String password) {
        this.uuid = uuid;
        this.dt_create = dt_create.toEpochSecond(ZoneOffset.UTC);
        this.dt_update = dt_update.toEpochSecond(ZoneOffset.UTC);
        this.mail = mail;
        this.fio = fio;
        this.role = UserRole.USER;
        this.status = status;
        this.password = password;
    }

    public UUID getUuid() {
        return uuid;
    }

    public long getDt_create() {
        return dt_create;
    }

    public long getDt_update() {
        return dt_update;
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

    public String getPassword() {
        return password;
    }

    public void setDt_update(LocalDateTime dt_update) {
        this.dt_update = dt_update.toEpochSecond(ZoneOffset.UTC);
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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreate that = (UserCreate) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(dt_create, that.dt_create) && Objects.equals(dt_update, that.dt_update) && Objects.equals(mail, that.mail) && Objects.equals(fio, that.fio) && role == that.role && status == that.status && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dt_create, dt_update, mail, fio, role, status, password);
    }

    @Override
    public String toString() {
        return "UserCreate{" +
                "uuid=" + uuid +
                ", dt_create=" + dt_create +
                ", dt_update=" + dt_update +
                ", mail='" + mail + '\'' +
                ", fio='" + fio + '\'' +
                ", role=" + role +
                ", status=" + status + '\'' +
                '}';
    }
}
