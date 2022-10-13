package service.impl;

import entity.Clan;
import service.ClanService;
import utils.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClanServiceImpl implements ClanService {

    @Override
    public Clan get(long clanId) {
        Statement statement = DBConnection.getConnection();
        String url = "SELECT * FROM clan " +
                "WHERE id = '" + clanId + "'";

        Clan clan = new Clan();

        try {
            ResultSet resultSet = statement.executeQuery(url);

            if (resultSet.next()) {
                clan.setId(resultSet.getInt("id"));
                clan.setName(resultSet.getString("name"));
                clan.setGold(resultSet.getInt("gold"));
            }

            return clan;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long add(Clan clan) {
        Statement statement = DBConnection.getConnection();
        String url = "INSERT INTO clan (name, gold) " +
                "VALUES ('" + clan.getName() + "', '0')";

        try {
            return statement.executeUpdate(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long update(Clan clan) {
        Statement statement = DBConnection.getConnection();
        String url = "UPDATE clan " +
                "SET gold = '" + clan.getGold() + "'" +
                "WHERE id = '" + clan.getId() + "'";

        try {
            return statement.executeUpdate(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
