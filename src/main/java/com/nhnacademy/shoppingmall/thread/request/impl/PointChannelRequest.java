package com.nhnacademy.shoppingmall.thread.request.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PointChannelRequest extends ChannelRequest {



    @Override
    public void execute() {
        DbConnectionThreadLocal.initialize();
        //todo#14-5 포인트 적립구현, connection은 point적립이 완료되면 반납합니다.

        Connection connection = DbConnectionThreadLocal.getConnection();


        try {

            String sql = "UPDATE users SET points = points + ? WHERE user_id = ?";


            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, 100);
            pstmt.setString(2, "1");


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("포인트 적립에 실패했습니다.");
            }
        } catch (SQLException e) {
            log.error("SQL 에러가 발생했습니다.", e);
            DbConnectionThreadLocal.setSqlError(true);
        } finally {
            DbConnectionThreadLocal.reset();
        }

        log.debug("pointChannel execute");
        DbConnectionThreadLocal.reset();
    }
}
