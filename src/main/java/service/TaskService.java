package service;

import entity.Clan;
import entity.Task;
import enumaration.TaskEnum;
import utils.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TaskService { // какой-то сервис с заданиями

    private final ClanService clans;

    public TaskService(ClanService clans) {
        this.clans = clans;
    }

    public void completeTask(long clanId, long taskId) {

            Clan clan = clans.get(clanId);
            Task task = get(taskId);
            if (task.getId() != 0 && clan.getId() != 0) {
                clan.setGold(clan.getGold() + task.getGold());
                System.out.println(clan.getGold());
                GoldHistoryService goldHistoryService = new GoldHistoryService();

                goldHistoryService.byTask(clan, task, task.getGold());
                clans.update(clan);
            }
    }

    public Task get(long taskId) {
        Statement statement = DBConnection.getConnection();
        String url = "SELECT * FROM task " +
                "WHERE id = '" + taskId + "'";

        Task task = new Task();

        try {
            ResultSet resultSet = statement.executeQuery(url);

            if (resultSet.next()) {
                task.setId(resultSet.getInt("id"));
                task.setTask(TaskEnum.valueOf(resultSet.getString("task")));
                task.setGold(resultSet.getInt("gold"));
            }

            return task;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long add(Task task) {
        Statement statement = DBConnection.getConnection();
        String url = "INSERT INTO task (task) " +
                "VALUES ('" + task.getTask() + "')";

        try {
            return statement.executeUpdate(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Task> getAll() {
        Statement statement = DBConnection.getConnection();
        String url = "SELECT * FROM task";
        List<Task> tasks = new ArrayList<>();


        try {
            ResultSet resultSet = statement.executeQuery(url);

            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setTask(TaskEnum.valueOf(resultSet.getString("task")));
                task.setGold(resultSet.getInt("gold"));
                tasks.add(task);
            }

            return tasks;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}