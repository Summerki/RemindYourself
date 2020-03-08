package com.suki.remindyourself.po;

import com.suki.remindyourself.vo.UserTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * po对象，对应数据库的user表
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User implements RowMapper<User> {
    private Long id;
    private String username;
    private String password;
    private String email;

    @Autowired
    UserTable userTable;

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong(userTable.idColumn));
        user.setUsername(resultSet.getString(userTable.usernameColumn));
        user.setPassword(resultSet.getString(userTable.passwordColumn));
        user.setEmail(resultSet.getString(userTable.emailColumn));
        return user;
    }
}
