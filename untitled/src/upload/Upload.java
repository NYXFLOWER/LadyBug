package upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.mysql.cj.jdbc.JdbcPreparedStatement;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/upload")
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.setCharacterEncoding("GBK");
		/*String name=request.getParameter("username");
		String file=request.getParameter("fileup");
		System.out.println(name);
		System.out.println(file);*/
		
		// Connection ct;
		JdbcPreparedStatement ps;
		// ResultSet rs;
		// 驱动程序名
		String driver = "com.mysql.cj.jdbc.Driver";
		// URL指向要访问的数据库名library
		String url = "jdbc:mysql://localhost:3306/db_csa";		// MySQL配置时的用户名
		String user = "root";
		// MySQL配置时的密码
		String password = "mtmmd011210ok";
		String username="Genius";

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
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if(isMultipart){
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = this.getServletConfig().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			//String username=request.getParameter("keywords");
			String path =new String("D:\\zuoye");
			String address="";
			


			// Parse the request
			 try {
		            //可以上传多个文件
		            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
		            for(FileItem item : list){
		                //获取表单的属性名字
		                String name = item.getFieldName();
		                address=path+name;
		                //如果获取的 表单信息是普通的 文本 信息
		                if(item.isFormField()){
		                    //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的
		                    String value = username+"_"+item.getString() ;
		                    request.setAttribute(name, value);
		                }else{//对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些
		                    /**
		                     * 以下三步，主要获取 上传文件的名字
		                     */
		                    //获取路径名
		                    String value = item.getName() ;
		                    //索引到最后一个反斜杠
		                    int start = value.lastIndexOf("\\");
		                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠，
		                    String filename = value.substring(start+1);
		                    request.setAttribute(name, filename);
		                    //真正写到磁盘上
		                    //它抛出的异常 用exception 捕捉
		                    //item.write( new File(path,filename) );//第三方提供的
		                    //手动写的
		                    OutputStream out = new FileOutputStream(new File(path,filename));
		                    InputStream in = item.getInputStream() ;
		                    int length = 0 ;
		                    byte [] buf = new byte[1024] ;
		                    System.out.println("获取上传文件的总共的容量："+item.getSize());
		                    // in.read(buf) 每次读到的数据存放在   buf 数组中
		                    while( (length = in.read(buf) ) != -1){
		                        //在   buf 数组中 取出数据 写到 （输出流）磁盘上
		                        out.write(buf, 0, length);
		                    }
		                    in.close();
		                    out.close();
				    }
				}
			} catch (FileUploadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
				ResultSet rs = stmt.executeQuery("select address from user_upload where username='"+username+"'");
				if(rs.next()==true){
					stmt.execute("SET SQL_SAFE_UPDATES = 0;");
					stmt.execute("DELETE FROM user_upload WHERE username='"+username+"';");
				}
				stmt.execute("INSERT INTO `user_upload`.`user_upload` ( `username`, `address`) VALUES ( '"+username+"', '"+address+"');");
				SampleInput i =new SampleInput(address);
			 
			 
			 conn.close();}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
