//没用了

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.jdbc.JdbcPreparedStatement;

public class LoginServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        //接收参数
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");

        //判断登陆是否成功 连接数据库
        JdbcConnection ct;
        JdbcPreparedStatement ps;
        ResultSet rs;
        // 驱动程序名
        String driver = "com.mysql.cj.jdbc.Driver";
        // URL指向要访问的数据库名library
        String url = "jdbc:mysql://localhost:3306/db_csa";
        // MySQL配置时的用户名
        String user = "root";
        // MySQL配置时的密码
        String password = "MangoEyes941027";
        
        // 输入的用户名和密码
        String txtUsername;
        String txtPassword;
        // 数据库中存储的用户名和密码
        String myuserword;
        String mypwd;

        // 接收数据
        txtUsername = request.getParameter("txtUsername");
        txtPassword = request.getParameter("txtPassword");
        
        try {
            // 加载驱动程序
            Class.forName(driver);
            // getConnection()方法，连接MySQL数据库！！
            ct = (JdbcConnection) DriverManager.getConnection(url, user,
                    password);
            if (ct != null) {
                System.out.println("数据库连接成功!");
            }

            ps = (JdbcPreparedStatement) ct
                    .prepareStatement("select * from users where username=? and pwd=? ");
            ps.setString(1, txtUsername); // 将第一个？设置为参数txtUsername
            ps.setString(2, txtPassword); // 将第二个?设置为参数txtPassword
            // rs是一个ResultSet结果集,可以把rs理解成返回一张表行的结果集
            rs = ps.executeQuery(); // 执行查找看是否存在，有的话rs.next()返回true,反之为
                                    // false
            // 如果存在此用户，则跳转到主界面
            if (rs.next()) {
                myuserword = rs.getString(1);// 获得表格的第一列，此处为用户名
                mypwd = rs.getString(2); // 获得表格的第二列，此处为密码
                System.out.println("成功从login数据库的users表中获取到用户名和密码：");
                System.out.println(myuserword + "\t" + mypwd + "\t");// “\t”为“转义字符”,代表的是一个tab，也就是8个空格。
//                response.sendRedirect("personalpage.jsp?username="+URLEncoder.encode(txtUsername));
                
                //创建session对象
                HttpSession session = request.getSession();
                //把用户数据保存在session域对象中
                session.setAttribute("txtUsername", txtUsername);
                //跳转到用户主页
//                response.sendRedirect(request.getContextPath()+"/IndexServlet");   
                response.sendRedirect("personalpage.jsp?username="+URLEncoder.encode(txtUsername));

                
            } else {
                System.out.println("没有该用户，请重新输入");
                response.sendRedirect("login.jsp");
            }
            ct.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}