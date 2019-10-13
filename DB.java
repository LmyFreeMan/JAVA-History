package com.limingyu;
import java.sql.*;

public class DB {
	/*设置标志位,判断MySQL数据库是否连上*/
	int flag=0;
	Connection conn=null;
	private PreparedStatement preparedStatement;
	/*连接数据库*/
       public te(String url,String username,String password) {
    	  try {
				Class.forName("com.mysql.jdbc.Driver");
				 conn = (Connection) DriverManager.getConnection(url, username, password);
				 this.flag=1;
			} catch(Exception e) {
				e.printStackTrace();
			}	
       }
      
      /*执行原生操作,非查询(successful)*/
       String execut(String sql) throws SQLException
      {
   		int rs = 0;
    	if(this.flag==1)
    	{
    		System.out.println(conn);
    		Statement stmt = conn.createStatement();
    		 rs = stmt.executeUpdate(sql);  
        }
		return "受影响的行数为"+rs; 
	 }
       /*执行原生查询操作(successful)*/
       ResultSet query(String sql) throws SQLException
       {
    		ResultSet rs = null;
     	if(this.flag==1)
     	{
     		Statement stmt = conn.createStatement();
     		 rs =stmt.executeQuery(sql);  
         }
 		return rs; 
 	   }
       /*利用主键进行查询*/
       ResultSet idquery(String tablename,int id) throws SQLException
       {
    		ResultSet rs = null;
     	if(this.flag==1)
     	{
     		 preparedStatement = null;
	       	 String sql="select * from "+tablename+" where id=?";
	       	 preparedStatement = conn.prepareStatement(sql);
	       	 preparedStatement.setInt(1,id);
	       	 System.out.println(tablename);
	       	 System.out.println(preparedStatement);
	   	     rs=preparedStatement.executeQuery();
         }
 		  return rs; 
 	   }
       /*利用主键进行进行删除(successful)*/
       boolean iddel(String tablename,int id) throws SQLException
       {
    	   preparedStatement = null;
    	   String sql="delete FROM "+tablename+" where id=?";
    	   preparedStatement = conn.prepareStatement(sql);
    	   preparedStatement.setInt(1,id);
    	   preparedStatement.executeUpdate();
		return true;  
       }
       /*利用主键进行进行更新(successful)*/
       boolean idupdate(String tablename,int id,String filed,String content) throws SQLException
       {
     	   preparedStatement = null;
    	   String sql="update "+tablename+" set "+filed+"=? where id=?";
    	   preparedStatement = conn.prepareStatement(sql);
    	   preparedStatement.setInt(2,id);
    	   preparedStatement.setString(1,filed);
    	   System.out.println(preparedStatement);
    	   preparedStatement.executeUpdate();
		return true;  
       }
       /*利用主键进行进行插入*/
       boolean insert(String[] args,String tablename,String...filed) throws SQLException
        {
    	   String f,v;
    	   f=v="";
    	   if(args.length==filed.length)
    	   {
    		   for(int i=0;i<args.length;i++)
    		   {
    			   if(i<args.length-1)
    			   {
    				   f=filed[i]+","+f;
    				   v="'"+args[i]+"'"+","+v;
    				   System.out.println("begin");
    				   System.out.println(f);
    			   }
    			   else
    			   {
    				   f=f+filed[i];
    				   v=v+"'"+args[i]+"'";
    			   }
    		   }
    		   System.out.println("dsadsadsa"+f);
			   System.out.println(v);
    		      int rs = 0;
    				   String sql="insert into "+tablename+" ("+f+")"+" values"+" ("+v+")";  
    				   System.out.println(sql);
    		    	   Statement stmt = conn.createStatement();
    		  		 rs = stmt.executeUpdate(sql);
    		  		 return true;
    	   }
    	 return false;
}
}
