package service;

import entity.Clan;
import entity.User;
import utils.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final ClanService clans;

    public UserService(ClanService clans) {
        this.clans = clans;
    }

    public void addGoldToClan(long userId, long clanId, int gold) {
        Clan clan = clans.get(clanId);
        User user = get(userId);
        if (clan.getId() != 0 && user.getId() != 0) {
            clan.setGold(clan.getGold() + gold);
            GoldHistoryService goldHistoryService = new GoldHistoryService();

            goldHistoryService.byUser(clan, user, gold);
            clans.update(clan);
        }
    }

    public long add(User user) {
        Statement statement = DBConnection.getConnection();
        String url = "INSERT INTO users (username) " +
                "VALUES ('" + user.getUsername() + "')";

        try {
            return statement.executeUpdate(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User get(long userId) {
        Statement statement = DBConnection.getConnection();
        String url = "SELECT * FROM users " +
                "WHERE id = '" + userId + "'";

        User user = new User();

        try {
            ResultSet resultSet = statement.executeQuery(url);

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAll() {
        Statement statement = DBConnection.getConnection();
        String url = "SELECT * FROM users";
        List<User> users = new ArrayList<>();


        try {
            ResultSet resultSet = statement.executeQuery(url);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}