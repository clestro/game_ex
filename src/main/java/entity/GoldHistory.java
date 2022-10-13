package entity;

import enumaration.GoldAddType;

import java.time.LocalDateTime;

public class GoldHistory {

    long id;
    Clan clan;
    Task task;
    User user;
    GoldAddType goldAddType;
    LocalDateTime createdTime;
    int gold;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Clan getClan() {
        return clan;
    }

    public void setClan(Clan clan) {
        this.clan = clan;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GoldAddType getGoldAddType() {
        return goldAddType;
    }

    public void setGoldAddType(GoldAddType goldAddType) {
        this.goldAddType = goldAddType;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "GoldHistory{" +
                "clan=" + clan.toString() +
                ", task=" + task.toString() +
                ", user=" + user.toString() +
                ", goldAddType=" + goldAddType +
                ", createdTime=" + createdTime +
                ", gold=" + gold +
                '}';
    }
}
