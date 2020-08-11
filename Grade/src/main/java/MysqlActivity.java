import java.sql.*;
import java.util.List;
import java.util.Map;

public class MysqlActivity {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL =
            "jdbc:mysql://localhost:3306/Test?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static final String USER = "root";
    static final String PASS = "Aaa987654321`";

    Students class1 = new Students("/Users/alexahu/Documents/class1.xlsx", "Sheet1");
    List<Map<String, Object>> studentsInfotoEnter = class1.getStudentsInfo();



    public static void toPrint(List<Map<String, Object>> s) {
        for (int i = 0; i < s.size(); i++) {
            for (Map.Entry<String, Object> entry : s.get(i).entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public static void assignTitleValue(List<Map<String, Object>> s) {
        for (int i = 0; i < s.size(); i++) {

        }
    }

    public static void main(String[] args) {
        List<Map<String, Object>> infos = new MysqlActivity().studentsInfotoEnter;
        toPrint(infos);
        Connection connection = null;
        Statement stmt = null;
        try {
            //注册JDBC驱动
            Class.forName(JDBC_DRIVER);
            //打开链接
            System.out.println("连接数据库...");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            //执行查询
            System.out.println("实例化statement对象...");
            stmt = connection.createStatement();
            /**
            String sql;
            for (int i = 0; i < infos.size(); i++) {
                StringBuffer stringBuffer = new StringBuffer();
                for (Map.Entry<String, Object> entry : infos.get(i).entrySet()) {
                    stringBuffer = stringBuffer.append(entry.getValue()).append("','").insert(0, "'");
                }
                String substring = stringBuffer.substring(7, stringBuffer.length() - 2);
                sql = "INSERT INTO `StudentScore` VALUES (" + substring + ")";
                System.out.println(sql);
                int i1 = stmt.executeUpdate(sql);
                System.err.println(i1);
            }
             */
            String sql1 = "UPDATE `StudentScore` SET gpa = ROUND(literature + math + statistics + computerScience) / 4";
            stmt.executeUpdate(sql1);
            /*
            ResultSet rs = stmt.executeQuery(sql1);
            while(rs.next()) {
            //通过字段检索
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            double literature = rs.getDouble("literature");
            double math = rs.getDouble("math");
            double stats = rs.getDouble("stats");
            double cs = rs.getDouble("computerScience");
            double gpa = rs.getDouble("GPA");

            //输出数据
            System.out.println("ID: " + id);
            System.out.println("name: " + name);
            System.out.println("email: " + email);
            System.out.println("literature: " + literature);
            System.out.println("math: " + math);
            System.out.println("statistics: " + stats);
            System.out.println("computer science: " + cs);
            System.out.println("overall gpa: " + gpa);
            }
            //完成后关闭
            rs.close();
            */
            stmt.close();
            connection.close();
        } catch (SQLException se) {
            //处理JDBC错误
            se.printStackTrace();
        } catch (Exception e) {
            //处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            //关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                //do nothing
            } try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("end");
    }
}

