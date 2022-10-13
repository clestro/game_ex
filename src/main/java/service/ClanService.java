package service;

import entity.Clan;

public interface ClanService {
    Clan get(long clanId);
    long add(Clan clan);

    long update(Clan clan);
}