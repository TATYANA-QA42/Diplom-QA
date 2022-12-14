package ru.netology.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {

    private static final String url = System.getProperty("dbUrl");
    private static final String user = System.getProperty("dbUser");
    private static final String password = System.getProperty("dbPass");

@SneakyThrows
    public static String getScalarFromTable(String column, String tableName) throws SQLException {
        QueryRunner runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)
        ) {
            val info = "SELECT " + column + " FROM " + tableName + " ORDER BY created DESC LIMIT 1;";
            val scalar = runner.query(conn, info, new ScalarHandler<String>());
            return scalar;
        }
    }

    public static String getStatusFromPaymentEntity() throws SQLException {
        return getScalarFromTable("status", "payment_entity");
    }

    public static String getTransactionIdFromPaymentEntity() throws SQLException {
        return getScalarFromTable("transaction_id", "payment_entity");
    }

    public static String getPaymentIdFromOrderEntity() throws SQLException {
        return getScalarFromTable("payment_id", "order_entity");
    }

    public static String getCreditIdFromOrderEntity() throws SQLException {
        return getScalarFromTable("credit_id", "order_entity");
    }

    public static String getStatusFromCreditRequestEntity() throws SQLException {
        return getScalarFromTable("status", "credit_request_entity");
    }

    public static String getBankIdFromCreditRequestEntity() throws SQLException {
        return getScalarFromTable("bank_id", "credit_request_entity");
    }
@SneakyThrows
    public static void clearDBTables() {
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)
        ) {
            runner.update(conn, "DELETE  FROM credit_request_entity;");
            runner.update(conn, "DELETE  FROM payment_entity;");
            runner.update(conn, "DELETE  FROM order_entity;");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}