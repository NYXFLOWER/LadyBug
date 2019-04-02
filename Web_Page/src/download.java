
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.tag.rt.core.OutTag;

import com.mysql.cj.jdbc.JdbcConnection;
import com.mysql.cj.jdbc.JdbcPreparedStatement;
import com.mysql.cj.jdbc.JdbcStatement;

/**
 * Servlet implementation class DownLoad
 */
@WebServlet("/download")
public class download extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Connection ct;
		JdbcPreparedStatement ps;
		// ResultSet rs;
		// 驱动程序名
		String driver = "com.mysql.cj.jdbc.Driver";
		// URL指向要访问的数据库名library
		String url = "jdbc:mysql://localhost:3306/db_csa";
		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "MangoEyes941027";
		
        // 登陆时的用户名
		HttpSession session = request.getSession(false);
		String txtUsername = (String)session.getAttribute("txtUsername") ;
        
        
        // 数据库中存储的用户名和链接
        String myuserword;
        String myaddress;
        
        Map params =  new HashMap();
        
        
        // 接收数据
        
        
		try {
			// 加载驱动程序
			/*
			 * Class.forName(driver); // getConnection()方法，连接MySQL数据库！！ ct =
			 * (Connection) DriverManager.getConnection(url, user, password); if
			 * (ct != null) { System.out.println("数据库连接成功!"); }
			 */

			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2. 获得数据库连接
			java.sql.Connection conn = DriverManager.getConnection(url, user, password);
			// 3.操作数据库，实现增删改查
			java.sql.Statement stmt = conn.createStatement();

			//ResultSet rs_count=stmt.executeQuery("select COUNT(`"+ txtUsername+"`) FROM `download` ;");

			
			//List<String> list = new   ArrayList<String>();
			ResultSet rs = stmt.executeQuery("select address from report_tab where username="+"'"+txtUsername+"'");
			// 如果有数据，rs.next()返回true
			myaddress="";
			while (rs.next()) {
				myaddress = rs.getString("address");
				System.out.println(myaddress);
			FileInputStream fis = new FileInputStream(myaddress);
			ServletOutputStream outputStream = response.getOutputStream();
			byte[] bs = new byte[500];
			int total = 0;
			while ((total = fis.read(bs)) != -1) {
				outputStream.write(bs, 0, total);
				outputStream.flush();
				}
			fis.close();
			outputStream.close();
			}

			conn.close();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
