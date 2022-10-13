package service;

import entity.Clan;
import entity.GoldHistory;
import entity.Task;
import entity.User;
import enumaration.GoldAddType;
import service.impl.ClanServiceImpl;
import utils.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GoldHistoryService {

    public int byTask(Clan clan, Task task, int gold){
        Statement statement = DBConnection.getConnection();
        String url = "INSERT INTO gold_history (clan_id, task_id, gold_add_type, created_time, gold) " +
                "VALUES ('" + clan.getId() + "', '" + task.getId() + "', '" + GoldAddType.TASK + "', '" + LocalDateTime.now() + "', '" + gold + "')";

        try {
            return statement.executeUpdate(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int byUser(Clan clan, User user, int gold){
        Statement statement = DBConnection.getConnection();
        String url = "INSERT INTO gold_history (clan_id, user_id, gold_add_type, created_time, gold) " +
                "VALUES ('" + clan.getId() + "', '" + user.getId() + "', '" + GoldAddType.USER + "', '" + LocalDateTime.now() + "', '" + gold + "')";

        try {
            return statement.executeUpdate(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<GoldHistory> getAllByClan(long clanId) {
        Statement statement = DBConnection.getConnection();
        String url = "SELECT * FROM gold_history WHERE clan_id = '" + clanId + "'";
        List<GoldHistory> goldHistoryList = new ArrayList<>();


        try {
            ResultSet resultSet = statement.executeQuery(url);
            ClanService clanService = new ClanServiceImpl();
            TaskService taskService = new TaskService(clanService);
            UserService userService = new UserService(clanService);

            while (resultSet.next()) {
                GoldHistory goldHistory = new GoldHistory();

                goldHistory.setId(resultSet.getInt("id"));
                goldHistory.setClan(clanService.get(resultSet.getInt("clan_id")));
                goldHistory.setTask(taskService.get(resultSet.getInt("task_id")));
                goldHistory.setUser(userService.get(resultSet.getInt("user_id")));
                goldHistory.setGoldAddType(GoldAddType.valueOf(resultSet.getString("gold_add_type")));
                goldHistory.setCreatedTime(resultSet.getTimestamp("created_time").toLocalDateTime());
                goldHistory.setGold(resultSet.getInt("gold"));

                goldHistoryList.add(goldHistory);
            }

            return goldHistoryList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
