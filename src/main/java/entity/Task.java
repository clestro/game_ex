package entity;

import enumaration.TaskEnum;

public class Task {
    private long id;

    private TaskEnum task;

    private int gold;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TaskEnum getTask() {
        return task;
    }

    public void setTask(TaskEnum task) {
        this.task = task;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", task=" + task +
                ", gold=" + gold +
                '}';
    }
}
