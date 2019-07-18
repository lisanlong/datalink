package com.ucar.datalink.biz.meta;


import com.ucar.datalink.biz.utils.ddl.TableType;
import org.apache.ddlutils.platform.DatabaseMetaDataWrapper;
import org.apache.ddlutils.platform.MetaDataColumnDescriptor;
import org.springframework.jdbc.support.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OracleCon {
    private String url="jdbc:oracle:thin:@localhost:1521:ORCL";
    private String username="root";
    private String pw="123456";
    private Connection conn=null;
    Connection con ;
    public Connection OpenConn(){

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            try {
                conn= DriverManager.getConnection(url,username,pw);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public ResultSet executeQuery(String sql){
        OracleCon db = new OracleCon();
        ResultSet  rs = null;
        Connection con =db.OpenConn();
        try {
            Statement sm = con.createStatement();
            rs = sm.executeQuery(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }




    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private static Map<String, Object> readColumns(ResultSet resultSet, List<MetaDataColumnDescriptor> columnDescriptors)
            throws SQLException {
        //printMetaData(resultSet);

        Map<String, Object> values = new HashMap<>();
        for (MetaDataColumnDescriptor descriptor : columnDescriptors) {
            values.put(descriptor.getName(), descriptor.readColumn(resultSet));
        }
        return values;
    }

// 获取数据库中所有表的表名，并添加到列表结构中。

    public List getTableNameList(Connection conn) throws SQLException {
        DatabaseMetaData dbmd = conn.getMetaData();
       //访问当前用户ANDATABASE下的所有表
       ResultSet rs = dbmd.getTables("null", "ROOT", "TARGET", new String[] { "TABLE" });
        //System.out.println("kkkkkk"+dbmd.getTables("null", "%", "%", new String[] { "TABLE" }));
        List tableNameList = new ArrayList();
        while (rs.next()) {
            tableNameList.add(rs.getString("TABLE_NAME"));
        }
        return tableNameList;
    }
    private static List<MetaDataColumnDescriptor> getDescriptorsForTable() {
        List<MetaDataColumnDescriptor> result = new ArrayList<>();

        result.add(new MetaDataColumnDescriptor("TABLE_NAME", Types.VARCHAR));
        result.add(new MetaDataColumnDescriptor("TABLE_TYPE", Types.VARCHAR, "UNKNOWN"));
        result.add(new MetaDataColumnDescriptor("TABLE_CAT", Types.VARCHAR));
        result.add(new MetaDataColumnDescriptor("TABLE_SCHEM", Types.VARCHAR));
        result.add(new MetaDataColumnDescriptor("REMARKS", Types.VARCHAR));

        return result;
    }
    public static void main(String s[]) throws SQLException
    {
        String aaa="";
       String bb= (String)aaa;


        OracleCon dbConn = new OracleCon();

        DatabaseMetaDataWrapper metaDataWrapper = new DatabaseMetaDataWrapper();
        Connection con=dbConn.OpenConn();

        DatabaseMetaData databaseMetaData = con.getMetaData();


       /* ResultSet rs = databaseMetaData.getTables("null", "ROOT", "TARGET", new String[] { "TABLE" });
        List tableNameList = new ArrayList();
        while (rs.next()) {
            tableNameList.add(rs.getString("TABLE_NAME"));
        }
*/


        metaDataWrapper.setMetaData(databaseMetaData);
        metaDataWrapper.setTableTypes(new String[] {"TABLE","VIEW" });
        String convertTableName = "TARGET";
        metaDataWrapper.setCatalog("ROOT");
        metaDataWrapper.setSchemaPattern("ROOT");
        ResultSet tableResultSet = metaDataWrapper.getTables(convertTableName);
        List tableNameList = new ArrayList();
        while ((tableResultSet != null) && tableResultSet.next()) {
            tableNameList.add(tableResultSet.getString("TABLE_NAME"));
            Map<String, Object> values = readColumns(tableResultSet, getDescriptorsForTable());
        }

        JdbcUtils.closeResultSet(tableResultSet);


       /* OracleCon dbConn = new OracleCon();
        Connection conn = dbConn.OpenConn();
        if(conn==null)
            System.out.println("连接失败");
        else
            System.out.println("连接成功");
        try {
            List tableList = dbConn.getTableNameList(conn);//取出当前用户的所有表
//List tableList = dbConn.getColumnNameList(conn, "LOGIN");//表名称必须是大写的，取出当前表的所有列
            System.out.println(tableList.size());
            for (Object object : tableList) {

                String ss=(String)object;

                System.out.println(ss);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }



}
