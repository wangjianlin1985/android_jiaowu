package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.CourseInfoDAO;
import com.chengxusheji.domain.CourseInfo;
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CourseInfoAction extends BaseAction {

    /*界面层需要查询的属性: 课程编号*/
    private String courseNumber;
    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public String getCourseNumber() {
        return this.courseNumber;
    }

    /*界面层需要查询的属性: 课程名称*/
    private String courseName;
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getCourseName() {
        return this.courseName;
    }

    /*界面层需要查询的属性: 上课老师*/
    private Teacher courseTeacher;
    public void setCourseTeacher(Teacher courseTeacher) {
        this.courseTeacher = courseTeacher;
    }
    public Teacher getCourseTeacher() {
        return this.courseTeacher;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource TeacherDAO teacherDAO;
    @Resource CourseInfoDAO courseInfoDAO;

    /*待操作的CourseInfo对象*/
    private CourseInfo courseInfo;
    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }
    public CourseInfo getCourseInfo() {
        return this.courseInfo;
    }

    /*跳转到添加CourseInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Teacher信息*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*添加CourseInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证课程编号是否已经存在*/
        String courseNumber = courseInfo.getCourseNumber();
        CourseInfo db_courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);
        if(null != db_courseInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该课程编号已经存在!"));
            return "error";
        }
        try {
            Teacher courseTeacher = teacherDAO.GetTeacherByTeacherNumber(courseInfo.getCourseTeacher().getTeacherNumber());
            courseInfo.setCourseTeacher(courseTeacher);
            courseInfoDAO.AddCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo添加失败!"));
            return "error";
        }
    }

    /*查询CourseInfo信息*/
    public String QueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, courseTeacher, currentPage);
        /*计算总的页数和总的记录数*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName, courseTeacher);
        /*获取到总的页码数目*/
        totalPage = courseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = courseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseInfoList",  courseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseNumber", courseNumber);
        ctx.put("courseName", courseName);
        ctx.put("courseTeacher", courseTeacher);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryCourseInfoOutputToExcel() { 
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber,courseName,courseTeacher);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "CourseInfo信息记录"; 
        String[] headers = { "课程编号","课程名称","上课老师","上课时间","上课地点","课程学分"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<courseInfoList.size();i++) {
        	CourseInfo courseInfo = courseInfoList.get(i); 
        	dataset.add(new String[]{courseInfo.getCourseNumber(),courseInfo.getCourseName(),courseInfo.getCourseTeacher().getTeacherName(),
courseInfo.getCourseTime(),courseInfo.getCoursePlace(),courseInfo.getCourseScore() + ""});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"CourseInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询CourseInfo信息*/
    public String FrontQueryCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        if(courseNumber == null) courseNumber = "";
        if(courseName == null) courseName = "";
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryCourseInfoInfo(courseNumber, courseName, courseTeacher, currentPage);
        /*计算总的页数和总的记录数*/
        courseInfoDAO.CalculateTotalPageAndRecordNumber(courseNumber, courseName, courseTeacher);
        /*获取到总的页码数目*/
        totalPage = courseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = courseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("courseInfoList",  courseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseNumber", courseNumber);
        ctx.put("courseName", courseName);
        ctx.put("courseTeacher", courseTeacher);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "front_query_view";
    }

    /*查询要修改的CourseInfo信息*/
    public String ModifyCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键courseNumber获取CourseInfo对象*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("courseInfo",  courseInfo);
        return "modify_view";
    }

    /*查询要修改的CourseInfo信息*/
    public String FrontShowCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键courseNumber获取CourseInfo对象*/
        CourseInfo courseInfo = courseInfoDAO.GetCourseInfoByCourseNumber(courseNumber);

        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("courseInfo",  courseInfo);
        return "front_show_view";
    }

    /*更新修改CourseInfo信息*/
    public String ModifyCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Teacher courseTeacher = teacherDAO.GetTeacherByTeacherNumber(courseInfo.getCourseTeacher().getTeacherNumber());
            courseInfo.setCourseTeacher(courseTeacher);
            courseInfoDAO.UpdateCourseInfo(courseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除CourseInfo信息*/
    public String DeleteCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            courseInfoDAO.DeleteCourseInfo(courseNumber);
            ctx.put("message",  java.net.URLEncoder.encode("CourseInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CourseInfo删除失败!"));
            return "error";
        }
    }

}
