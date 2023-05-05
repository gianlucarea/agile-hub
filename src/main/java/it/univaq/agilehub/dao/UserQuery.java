package it.univaq.agilehub.dao;

import it.univaq.agilehub.model.User;

public class UserQuery implements UserDao{

    private static final String insertUser ="insert into agilehub.user value (?)";

    @Override
    public boolean insertUser(User user) {
        return false;
    }

}
