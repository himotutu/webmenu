package com.qhit.utils;

/**
 * Created by Administrator on 2018/7/25 0025.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 根据表名，自动生成javabean,dao,mapper,service,service实现类
 *
 * Version 2.0
 * 1 符合SpringBoot
 *
 */
public class GenerateCode {

    private static String tableName = "web_fun";
    private static String javaBeanName = BaseDao.dbTableToJavaBean(tableName);
    private static String javaPackageName = BaseDao.toLowerCaseFirstOne(javaBeanName);
    private static BaseDao dao = new BaseDao();

    public static void main(String[] args) throws Exception {
        //连接
        Connection conn = dao.getConn();
        String sql = "select * from "+tableName;
        sql=sql.toLowerCase();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        //元数据
        ResultSetMetaData metaData = rs.getMetaData();
        rs.close();
        //字段注释
        String[] comments = findComments(conn, metaData);
        //文件输出位置
        File dir = new File("d:\\codeout");
        if(!dir.exists()){
            dir.mkdirs();
        }
        //删除文件夹下的所有文件
        delAllFile(dir.getAbsolutePath());
        //创建 pojo dao service 文件夹
        makeSomeDirs(dir);
        //javabean
        String beanName = javaBeanName+".java";
        BufferedWriter beanWriter = new BufferedWriter(new FileWriter(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\pojo\\"+beanName));
        //dao
        String daoName = "I"+javaBeanName+"Dao.java";
        BufferedWriter daoWriter= new BufferedWriter(new FileWriter(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\dao\\"+daoName));
        //Mapper文件
        String mapperName = javaBeanName+"Mapper.xml";
        BufferedWriter mapperWriter= new BufferedWriter(new FileWriter(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\dao\\"+mapperName));
        //service
        String serviceName = "I"+javaBeanName+"Service.java";
        BufferedWriter serviceWriter= new BufferedWriter(new FileWriter(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\service\\"+serviceName));
        //service实现类
        String serviceImplName = javaBeanName+"ServiceImpl.java";
        BufferedWriter serviceImplWriter= new BufferedWriter(new FileWriter(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\service\\impl\\"+serviceImplName));

        //Controller
        String controllerName = javaBeanName+"Controller.java";
        BufferedWriter controllerWriter = new BufferedWriter(new FileWriter(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\controller\\"+controllerName));

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String now = format.format(date);

        // dao
        generateDao(metaData,javaBeanName, daoWriter, now);
        //mapper文件
        generateMapper(metaData,javaBeanName, mapperWriter, now);
        //javabean
        generateJavaBean(metaData, javaBeanName, beanWriter, now,comments);
        // service
        generateService(metaData,javaBeanName, serviceWriter, now);
        //service实现类
        generateServiceImpl(metaData,javaBeanName, serviceImplWriter, now);

        //controller
        generateController(metaData,javaBeanName, controllerWriter, now);

        System.out.println("执行成功");

    }

    private static void generateController(ResultSetMetaData metaData, String javaBeanName, BufferedWriter controllerWriter, String now) throws IOException, SQLException {
        controllerWriter.write("package com.qhit."+javaPackageName+".controller; \n\n");
        controllerWriter.write("import com.qhit."+javaPackageName+".pojo."+javaBeanName+"; \r\n");
        controllerWriter.write("import com.qhit."+javaPackageName+".service.I"+javaBeanName+"Service; \r\n");
        controllerWriter.write("import org.springframework.web.bind.annotation.RequestMapping; \r\n");
        controllerWriter.write("import javax.annotation.Resource; \r\n");
        controllerWriter.write("import java.util.List; \r\n");
        controllerWriter.write("import org.springframework.web.bind.annotation.RestController; \n\n");
        controllerWriter.write("/** \r\n");
        controllerWriter.write("* Created by GeneratorCode on "+now+"\r\n");
        controllerWriter.write("*/ \n\n");
        controllerWriter.write("@RestController \r\n");
        controllerWriter.write("@RequestMapping(\"/"+javaPackageName+"\") \r\n");
        controllerWriter.write("public class "+javaBeanName+"Controller { \n\n");
        controllerWriter.write("    @Resource \r\n");
        controllerWriter.write("    I"+javaBeanName+"Service "+javaPackageName+"Service; \n\n");
        controllerWriter.write("    @RequestMapping(\"/insert\") \r\n");
        controllerWriter.write("    public void insert("+javaBeanName+" "+javaPackageName+") { \r\n");
        controllerWriter.write("        "+javaPackageName+"Service.insert("+javaPackageName+"); \r\n");
        controllerWriter.write("    } \n\n");
        controllerWriter.write("    @RequestMapping(\"/delete\") \r\n");
        //主键
        String primarykey = metaData.getColumnName(1);
        String priName = BaseDao.dbColsToJavaCols(primarykey);
        controllerWriter.write("    public void delete(Integer "+priName+") { \r\n");
        controllerWriter.write("        "+javaPackageName+"Service.delete("+priName+"); \r\n");
        controllerWriter.write("    } \n\n");
        controllerWriter.write("    @RequestMapping(\"/update\") \r\n");
        controllerWriter.write("    public void update("+javaBeanName+" "+javaPackageName+") { \r\n");
        controllerWriter.write("        "+javaPackageName+"Service.update("+javaPackageName+"); \r\n");
        controllerWriter.write("    } \n\n");
        controllerWriter.write("    @RequestMapping(\"/updateSelective\") \r\n");
        controllerWriter.write("    public void updateSelective("+javaBeanName+" "+javaPackageName+") { \r\n");
        controllerWriter.write("        "+javaPackageName+"Service.updateSelective("+javaPackageName+"); \r\n");
        controllerWriter.write("    } \n\n");
        controllerWriter.write("    @RequestMapping(\"/load\") \r\n");
        controllerWriter.write("    public "+javaBeanName+" load(Integer "+priName+") { \r\n");
        controllerWriter.write("        "+javaBeanName+" "+javaPackageName+" = "+javaPackageName+"Service.findById("+priName+"); \r\n");
        controllerWriter.write("        return "+javaPackageName+"; \r\n");
        controllerWriter.write("    } \n\n");
        controllerWriter.write("    @RequestMapping(\"/list\") \r\n");
        controllerWriter.write("    public List<"+javaBeanName+"> list()  { \r\n");
        controllerWriter.write("        List<"+javaBeanName+"> list = "+javaPackageName+"Service.findAll(); \r\n");
        controllerWriter.write("        return list; \r\n");
        controllerWriter.write("    } \n\n");
        controllerWriter.write("    @RequestMapping(\"/search\") \r\n");
        controllerWriter.write("    public List<"+javaBeanName+"> search("+javaBeanName+" "+javaPackageName+") { \r\n");
        controllerWriter.write("        List<"+javaBeanName+"> list = "+javaPackageName+"Service.search("+javaPackageName+"); \r\n");
        controllerWriter.write("        return list; \r\n");
        controllerWriter.write("    } \n\n");
        controllerWriter.write("} \r\n");
        // 关流
        controllerWriter.close();
    }



    private static void generateJavaBean(ResultSetMetaData metaData, String javaBeanName, BufferedWriter beanWriter, String now,String[] comments) throws IOException, SQLException {
        //javabean写头部
        beanWriter.write("package com.qhit."+javaPackageName+".pojo;\n\n\n");
        beanWriter.write("/** \n");
        beanWriter.write("* Created by GeneratorCode on "+now +"\n");
        beanWriter.write("*/ \n\n");
        beanWriter.write("public class "+javaBeanName+" { \n");
        //存储get,set方法
        StringBuffer buffer = new StringBuffer("\n");
        int count = metaData.getColumnCount();
        for(int i=1;i<=count;i++){
            //列名 小写
            String columnName = metaData.getColumnName(i);
            String javaCol = BaseDao.dbColsToJavaCols(columnName);
            String upperCaseColumnName = dao.toUpperCaseFirstOne(javaCol);
            //列类型 大写
            String type = metaData.getColumnTypeName(i);
            if(type.equals("VARCHAR")||type.equals("VARCHAR2")||type.equals("TIME")||type.equals("DATE")||type.equals("DATETIME")||type.equals("TIMESTAMP")){
                beanWriter.write("    private String "+javaCol+";    //"+comments[i-1] +" \n");
                buffer.append("    public String get"+upperCaseColumnName+"() { \n");
                buffer.append("        return "+javaCol+";\n");
                buffer.append("    }\n\n");
                buffer.append("    public void set"+upperCaseColumnName+"(String "+javaCol+") { \n");
                buffer.append("        this."+javaCol+" = "+javaCol+";\n");
                buffer.append("    }\n");
            }else if(type.equals("INT")){
                beanWriter.write("    private Integer "+javaCol+";    //"+comments[i-1]+" \n");
                buffer.append("    public Integer get"+upperCaseColumnName+"() { \n");
                buffer.append("        return "+javaCol+";\n");
                buffer.append("    }\n\n");
                buffer.append("    public void set"+upperCaseColumnName+"(Integer "+javaCol+") { \n");
                buffer.append("        this."+javaCol+" = "+javaCol+";\n");
                buffer.append("    } \n\n");
            }else if(type.equals("FLOAT")){
                beanWriter.write("    private Float "+javaCol+";    //"+comments[i-1]+" \n");
                buffer.append("    public Float get"+upperCaseColumnName+"() { \n");
                buffer.append("        return "+javaCol+";\n");
                buffer.append("    }\n\n");
                buffer.append("    public void set"+upperCaseColumnName+"(Float "+javaCol+") { \n");
                buffer.append("        this."+javaCol+" = "+javaCol+";\n");
                buffer.append("    }\n\n");
            }else if(type.equals("DOUBLE")){
                beanWriter.write("    private Double "+javaCol+";    //"+comments[i-1]+" \n");
                buffer.append("    public Double get"+upperCaseColumnName+"() { \n");
                buffer.append("        return "+javaCol+";\n");
                buffer.append("    }\n\n");
                buffer.append("    public void set"+upperCaseColumnName+"(Double "+javaCol+") { \n");
                buffer.append("        this."+javaCol+" = "+javaCol+";\n");
                buffer.append("    } \n\n");
            }
        }
        beanWriter.write(buffer.toString()+"\n }");
        //关流
        beanWriter.close();
    }

    private static void generateMapper(ResultSetMetaData metaData,String javaBeanName, BufferedWriter mapperWriter, String now) throws IOException, SQLException {
        mapperWriter.write("<?xml version=\"1.0\" encoding=\"utf-8\"?> \n");
        mapperWriter.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org/DTD Mapper 3.0\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\"> \n");
        mapperWriter.write("<mapper namespace=\"com.qhit."+javaPackageName+".dao.I"+javaBeanName+"Dao\"> \n");
        mapperWriter.write("\t<!-- 自定义结果集 --> \n");
        mapperWriter.write("\t<resultMap id=\"BaseResultMap\" type=\"com.qhit."+javaPackageName+".pojo."+javaBeanName+"\"> \n");
        int count = metaData.getColumnCount();
        //主键
        String primarykey = metaData.getColumnName(1);
        String priName = BaseDao.dbColsToJavaCols(primarykey);
        mapperWriter.write("\t\t<id property=\""+priName+"\" column=\""+primarykey+"\" javaType=\"java.lang.Integer\"></id> \n");
        for(int i=2;i<=count;i++) {
            //列名 小写
            String columnName = metaData.getColumnName(i);
            String javaCol = BaseDao.dbColsToJavaCols(columnName);
            String upperCaseColumnName = dao.toUpperCaseFirstOne(javaCol);
            //列类型 大写
            String type = metaData.getColumnTypeName(i);
            if(type.equals("VARCHAR")||type.equals("VARCHAR2")||type.equals("TIME")||type.equals("DATE")||type.equals("DATETIME")||type.equals("TIMESTAMP")){
                mapperWriter.write("\t\t<result property=\""+javaCol+"\" column=\""+columnName+"\" javaType=\"java.lang.String\"></result> \n");
            }else if(type.equals("INT")){
                mapperWriter.write("\t\t<result property=\""+javaCol+"\" column=\""+columnName+"\" javaType=\"java.lang.Integer\"></result> \n");
            }else if(type.equals("FLOAT")){
                mapperWriter.write("\t\t<result property=\""+javaCol+"\" column=\""+columnName+"\" javaType=\"java.lang.Float\"></result> \n");
            }else if(type.equals("DOUBLE")){
                mapperWriter.write("\t\t<result property=\""+javaCol+"\" column=\""+columnName+"\" javaType=\"java.lang.Double\"></result> \n");
            }
        }
        mapperWriter.write("\t</resultMap> \n\n");
        mapperWriter.write("\t<!-- 在各种标签中，id属性必须与接口中的方法名一样，id的值必须是唯一的，parameterType指查询时使用的参数类型， \n");
        mapperWriter.write("\tresultType属性指明查询返回的结果集类型    --> \n");
        mapperWriter.write(" \n");
        mapperWriter.write("\t<sql id=\"Base_Column_List\" > \n");
        StringBuffer baseColumnBuffer = new StringBuffer();
        for (int i = 1; i <=count ; i++) {
            String columnName = metaData.getColumnName(i);
            baseColumnBuffer.append(columnName+",");
        }
        String baseColumn = baseColumnBuffer.substring(0,baseColumnBuffer.length()-1);
        mapperWriter.write("\t\t"+baseColumn+" \n");
        mapperWriter.write("\t</sql> \n");
        mapperWriter.write("\t<select id=\"findById\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Integer\" > \n");
        mapperWriter.write("\t\tselect \n");
        mapperWriter.write("\t\t<include refid=\"Base_Column_List\" /> \n");
        mapperWriter.write("\t\tfrom "+tableName+" \n");
        mapperWriter.write("\t\twhere "+primarykey+" = #{"+priName+"} \n");
        mapperWriter.write("\t</select> \n");
        mapperWriter.write("\t<delete id=\"delete\" parameterType=\"java.lang.Integer\" > \n");
        mapperWriter.write("\t\tdelete from "+tableName+" \n");
        mapperWriter.write("\t\twhere "+primarykey+" = #{"+priName+"} \n");
        mapperWriter.write("\t</delete> \n");
        mapperWriter.write("\t<insert id=\"insert\" parameterType=\"com.qhit."+javaPackageName+".pojo."+javaBeanName+"\" > \n");
        StringBuffer insertIntoBuffer = new StringBuffer("insert into "+tableName+"(");
        StringBuffer insertValueBuffer = new StringBuffer("values (");
        for(int i=2;i<=count;i++) {
            //列名 小写
            String columnName = metaData.getColumnName(i);
            String javaCol = BaseDao.dbColsToJavaCols(columnName);
            insertIntoBuffer.append(columnName+",");
            insertValueBuffer.append("#{"+javaCol+"},");
        }
        String insertInto = insertIntoBuffer.substring(0,insertIntoBuffer.length()-1);
        insertInto += ")";
        String insertValue = insertValueBuffer.substring(0,insertValueBuffer.length()-1);
        insertValue += ")";
        mapperWriter.write("\t\t"+insertInto+" \n");
        mapperWriter.write("\t\t"+insertValue+" \n");
        mapperWriter.write("\t</insert> \n");
        mapperWriter.write("\t<update id=\"updateSelective\" parameterType=\"com.qhit."+javaPackageName+".pojo."+javaBeanName+"\" > \n");
        mapperWriter.write("\t\tupdate "+tableName+" \n");
        mapperWriter.write("\t\t<set > \n");
        for(int i=2;i<=count;i++) {
            //列名 小写
            String columnName = metaData.getColumnName(i);
            String javaCol = BaseDao.dbColsToJavaCols(columnName);
            mapperWriter.write("\t\t\t<if test=\""+javaCol+" != null and "+javaCol+"!='' \" > \n");
            mapperWriter.write("\t\t\t\t"+columnName+" = #{"+javaCol+"}, \n");
            mapperWriter.write("\t\t\t</if> \n");
        }
        mapperWriter.write("\t\t</set> \n");
        mapperWriter.write("\t\twhere "+primarykey+" = #{"+priName+"} \n");
        mapperWriter.write("\t</update> \n");
        mapperWriter.write("\t<update id=\"update\" parameterType=\"com.qhit."+javaPackageName+".pojo."+javaBeanName+"\" > \n");
        mapperWriter.write("\t\tupdate "+tableName+" \n");
        StringBuffer updateBuffer = new StringBuffer("set ");
        for(int i=2;i<=count;i++) {
            //列名 小写
            String columnName = metaData.getColumnName(i);
            String javaCol = BaseDao.dbColsToJavaCols(columnName);
            updateBuffer.append(" "+columnName+" = #{"+javaCol+"},");
        }
        String updateStr = updateBuffer.substring(0,updateBuffer.length()-1);
        mapperWriter.write("\t\t"+updateStr+" \r\n");
        mapperWriter.write("\t\twhere "+primarykey+" = #{"+priName+"} \r\n");
        mapperWriter.write("\t</update> \r\n");

        mapperWriter.write("\t<select id=\"findAll\" resultMap=\"BaseResultMap\" > \r\n");
        mapperWriter.write("\t\tselect \r\n");
        mapperWriter.write("\t\t<include refid=\"Base_Column_List\" /> \r\n");
        mapperWriter.write("\t\tfrom "+tableName+" \r\n");
        mapperWriter.write("\t</select> \r\n");

        for(int i=2;i<=count;i++) {
            //列名 小写
            String columnName = metaData.getColumnName(i);
            String javaCol = BaseDao.dbColsToJavaCols(columnName);
            String upperCaseColumnName = dao.toUpperCaseFirstOne(javaCol);
            //列类型 大写
            String type = metaData.getColumnTypeName(i);
            if(type.equals("VARCHAR")||type.equals("VARCHAR2")||type.equals("TIME")||type.equals("DATE")||type.equals("DATETIME")||type.equals("TIMESTAMP")){
                mapperWriter.write("\t<select id=\"findBy"+upperCaseColumnName+"\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.String\" > \r\n");
            }else if(type.equals("INT")){
                mapperWriter.write("\t<select id=\"findBy"+upperCaseColumnName+"\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Integer\" > \r\n");
            }else if(type.equals("FLOAT")){
                mapperWriter.write("\t<select id=\"findBy"+upperCaseColumnName+"\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Float\" > \r\n");
            }else if(type.equals("DOUBLE")){
                mapperWriter.write("\t<select id=\"findBy"+upperCaseColumnName+"\" resultMap=\"BaseResultMap\" parameterType=\"java.lang.Double\" > \r\n");
            }
            mapperWriter.write("\t\tselect \r\n");
            mapperWriter.write("\t\t<include refid=\"Base_Column_List\" /> \r\n");
            mapperWriter.write("\t\tfrom "+tableName+" \r\n");
            mapperWriter.write("\t\twhere "+columnName+" = #{"+javaCol+"} \r\n");
            mapperWriter.write("\t</select> \r\n");
        }
//        批量删除
        mapperWriter.write("\t<delete id=\"deleteBatch\" parameterType=\"java.util.Arrays\"> \r\n");
        mapperWriter.write("\t\tdelete from "+tableName+" where "+primarykey+" in \r\n");
        mapperWriter.write("\t\t<foreach collection=\"array\" item=\"id\" open=\"(\" close=\")\" separator=\",\"> \r\n");
        mapperWriter.write("\t\t\t#{id} \r\n");
        mapperWriter.write("\t\t</foreach> \r\n");
        mapperWriter.write("\t</delete> \r\n");
//      查询
        mapperWriter.write("\t<select id=\"search\" parameterType=\"com.qhit."+javaPackageName+".pojo."+javaBeanName+"\" resultMap=\"BaseResultMap\"> \r\n");
        mapperWriter.write("\t\tselect * from  "+tableName+" \r\n");
        mapperWriter.write("\t\t<where > \r\n");
        for(int i=2;i<=count;i++) {
            //列名 小写
            String columnName = metaData.getColumnName(i);
            String javaCol = BaseDao.dbColsToJavaCols(columnName);
            mapperWriter.write("\t\t\t<if test=\""+javaCol+" != null and "+javaCol+"!='' \" > \r\n");
            mapperWriter.write("\t\t\t\t and "+columnName+" = #{"+javaCol+"} \r\n");
            mapperWriter.write("\t\t\t</if> \r\n");
        }
        mapperWriter.write("\t\t</where> \r\n");
        mapperWriter.write("\t</select> \r\n");

        mapperWriter.write("</mapper> \r\n");
        mapperWriter.close();
    }

    /**
     * Dao模板
     * @param javaBeanName
     * @param daoWriter
     * @param now
     * @throws IOException
     */
    private static void generateDao(ResultSetMetaData metaData,String javaBeanName, BufferedWriter daoWriter, String now) throws IOException, SQLException {
        daoWriter.write("package com.qhit."+javaPackageName+".dao;\n\n");
        daoWriter.write("import org.apache.ibatis.annotations.Mapper;\n");
        daoWriter.write("import com.qhit."+javaPackageName+".pojo."+javaBeanName+";\n");
        daoWriter.write("import java.util.List;\n\n");
        daoWriter.write("/** \n");
        daoWriter.write("* Created by GeneratorCode on "+now+"\n");
        daoWriter.write("*/\n\n");
        daoWriter.write("@Mapper  \n");
        daoWriter.write("public interface I"+javaBeanName+"Dao {\n\n");
        daoWriter.write("    boolean insert(Object object);\n\n");
        daoWriter.write("    boolean  update(Object object);\n\n");
        daoWriter.write("    boolean  updateSelective(Object object);\n\n");
        daoWriter.write("    boolean delete(Object object);\n\n");
        daoWriter.write("    List freeFind(String sql);\n\n");
        daoWriter.write("    List findAll();\n\n");
        daoWriter.write("    List findById(Object id);\n\n");
        daoWriter.write("    boolean freeUpdate(String sql);\n\n");
        daoWriter.write("    List<"+javaBeanName+"> search("+javaBeanName+" "+javaPackageName+");\n\n");
        int count = metaData.getColumnCount();
        for(int i=2;i<=count;i++) {
            //列名 小写
            String columnName = metaData.getColumnName(i);
            String javaCol = BaseDao.dbColsToJavaCols(columnName);
            String upperCaseColumnName = dao.toUpperCaseFirstOne(javaCol);
            daoWriter.write("    List findBy"+upperCaseColumnName+"(Object "+javaCol+");\n\n");
        }
        daoWriter.write("}");
        daoWriter.close();
    }

    private static void generateServiceImpl(ResultSetMetaData metaData,String javaBeanName, BufferedWriter serviceImplWriter, String now) throws IOException, SQLException {
        serviceImplWriter.write("package com.qhit."+javaPackageName+".service.impl;\n\n");
        serviceImplWriter.write("import com.qhit."+javaPackageName+".service.I"+javaBeanName+"Service;\n");
        serviceImplWriter.write("import java.util.List;\n");
        serviceImplWriter.write("import com.qhit."+javaPackageName+".dao.I"+javaBeanName+"Dao;\n");
        serviceImplWriter.write("import com.qhit."+javaPackageName+".pojo."+javaBeanName+";\r\n");
        serviceImplWriter.write("import org.springframework.stereotype.Service;\r\n");
        serviceImplWriter.write("import javax.annotation.Resource; \n\n");
        serviceImplWriter.write("/**\n");
        serviceImplWriter.write("* Created by GeneratorCode on "+now+"\n");
        serviceImplWriter.write("*/\n\n");
        serviceImplWriter.write("@Service \r\n");
        serviceImplWriter.write("public class "+javaBeanName+"ServiceImpl  implements I"+javaBeanName+"Service {\n\n");
        serviceImplWriter.write("    @Resource \r\n");
        serviceImplWriter.write("    I"+javaBeanName+"Dao dao;\n\n");
        serviceImplWriter.write("    @Override \r\n");
        serviceImplWriter.write("    public boolean insert(Object object) { \r\n");
        serviceImplWriter.write("        return dao.insert(object); \r\n");
        serviceImplWriter.write("    } \n\n");
        serviceImplWriter.write("    @Override \r\n");
        serviceImplWriter.write("    public boolean update(Object object) { \r\n");
        serviceImplWriter.write("        return dao.update(object); \r\n");
        serviceImplWriter.write("    } \n\n");
        serviceImplWriter.write("    @Override \r\n");
        serviceImplWriter.write("    public boolean updateSelective(Object object) { \r\n");
        serviceImplWriter.write("        return dao.updateSelective(object); \r\n");
        serviceImplWriter.write("    } \n\n");
        serviceImplWriter.write("    @Override \r\n");
        serviceImplWriter.write("    public boolean delete(Object id) { \r\n");
        serviceImplWriter.write("        "+javaBeanName+" "+javaPackageName+" = findById(id); \r\n");
        serviceImplWriter.write("        return dao.delete("+javaPackageName+"); \r\n");
        serviceImplWriter.write("    } \n\n");
        serviceImplWriter.write("    @Override \r\n");
        serviceImplWriter.write("    public List findAll() { \r\n");
        serviceImplWriter.write("        return dao.findAll(); \r\n");
        serviceImplWriter.write("    } \n\n");
        serviceImplWriter.write("    @Override \r\n");
        serviceImplWriter.write("    public "+javaBeanName+" findById(Object id) { \r\n");
        serviceImplWriter.write("        List<"+javaBeanName+"> list = dao.findById(id); \r\n");
        serviceImplWriter.write("        return  list.get(0); \r\n");
        serviceImplWriter.write("    } \n\n");
        serviceImplWriter.write("    @Override \r\n");
        serviceImplWriter.write("    public List<"+javaBeanName+"> search("+javaBeanName+" "+javaPackageName+") { \r\n");
        serviceImplWriter.write("        return dao.search("+javaPackageName+"); \r\n");
        serviceImplWriter.write("    } \n\n");
        serviceImplWriter.write("}");
        serviceImplWriter.close();
    }

    /**
     * Service模板
     * @param javaBeanName
     * @param serviceWriter
     * @param now
     * @throws IOException
     */
    private static void generateService(ResultSetMetaData metaData,String javaBeanName, BufferedWriter serviceWriter, String now) throws IOException, SQLException {
        serviceWriter.write("package com.qhit."+javaPackageName+".service;\n\n");
        serviceWriter.write("import java.util.List;\n");
        serviceWriter.write("import com.qhit."+javaPackageName+".pojo."+javaBeanName+";\r\n");
        serviceWriter.write("/**\n");
        serviceWriter.write("* Created by GeneratorCode on "+now+"\n");
        serviceWriter.write("*/\n");
        serviceWriter.write("public interface I"+javaBeanName+"Service {\n\n");
        serviceWriter.write("    boolean insert(Object object);\n\n");
        serviceWriter.write("    boolean  update(Object object);\n\n");
        serviceWriter.write("    boolean  updateSelective(Object object);\n\n");
        serviceWriter.write("    boolean delete(Object id);\n\n");
        serviceWriter.write("    List findAll();\n\n");
        serviceWriter.write("    "+javaBeanName+" findById(Object id);\n\n");
        serviceWriter.write("    List<"+javaBeanName+"> search("+javaBeanName+" "+javaPackageName+");\n\n");
        serviceWriter.write("}");
        serviceWriter.close();
    }


    //删除文件夹
    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除指定文件夹下所有文件
//param path 文件夹完整绝对路径
    public static boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 创建文件夹
     */
    private static void makeSomeDirs(File dir) {
        File file1 = new File(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\pojo");
        File file2 = new File(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\dao");
        File file3 = new File(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\service\\impl");
        File file4 = new File(dir.getAbsolutePath()+"\\com\\qhit\\"+javaPackageName+"\\controller");
        file1.mkdirs();
        file2.mkdirs();
        file3.mkdirs();
        file4.mkdirs();
    }

    /**
     * 查询字段的注释
     * @param conn
     * @param metaData
     * @return
     * @throws SQLException
     */
    private static String[] findComments(Connection conn,  ResultSetMetaData metaData) throws SQLException {
        //字段注释
        String[] comments = new String[metaData.getColumnCount()];
        String commentSql = "show full columns from "+tableName;
        PreparedStatement ps2 = conn.prepareStatement(commentSql);
        ResultSet set = ps2.executeQuery();
        int j = 0;
        while (set.next()){
            comments[j] = set.getString("Comment").trim();
            j++;
        }
        return comments;
    }

}
