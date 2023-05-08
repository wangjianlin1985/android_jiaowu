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
import com.chengxusheji.dao.StudentSelectCourseInfoDAO;
import com.chengxusheji.domain.StudentSelectCourseInfo;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.dao.CourseInfoDAO;
import com.chengxusheji.domain.CourseInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class StudentSelectCourseInfoAction extends BaseAction {

    /*界面层需要查询的属性: 学生对象*/
    private Student studentNumber;
    public void setStudentNumber(Student studentNumber) {
        this.studentNumber = studentNumber;
    }
    public Student getStudentNumber() {
        return this.studentNumber;
    }

    /*界面层需要查询的属性: 课程对象*/
    private CourseInfo courseNumber;
    public void setCourseNumber(CourseInfo courseNumber) {
        this.courseNumber = courseNumber;
    }
    public CourseInfo getCourseNumber() {
        return this.courseNumber;
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

    private int selectId;
    public void setSelectId(int selectId) {
        this.selectId = selectId;
    }
    public int getSelectId() {
        return selectId;
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
    @Resource StudentDAO studentDAO;
    @Resource CourseInfoDAO courseInfoDAO;
    @Resource StudentSelectCourseInfoDAO studentSelectCourseInfoDAO;

    /*待操作的StudentSelectCourseInfo对象*/
    private StudentSelectCourseInfo studentSelectCourseInfo;
    public void setStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
        this.studentSelectCourseInfo = studentSelectCourseInfo;
    }
    public StudentSelectCourseInfo getStudentSelectCourseInfo() {
        return this.studentSelectCourseInfo;
    }

    /*跳转到添加StudentSelectCourseInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*查询所有的CourseInfo信息*/
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "add_view";
    }

    /*添加StudentSelectCourseInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentNumber = studentDAO.GetStudentByStudentNumber(studentSelectCourseInfo.getStudentNumber().getStudentNumber());
            studentSelectCourseInfo.setStudentNumber(studentNumber);
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            studentSelectCourseInfo.setCourseNumber(courseNumber);
            studentSelectCourseInfoDAO.AddStudentSelectCourseInfo(studentSelectCourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo添加失败!"));
            return "error";
        }
    }

    /*查询StudentSelectCourseInfo信息*/
    public String QueryStudentSelectCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber, courseNumber, currentPage);
        /*计算总的页数和总的记录数*/
        studentSelectCourseInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*获取到总的页码数目*/
        totalPage = studentSelectCourseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = studentSelectCourseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentSelectCourseInfoList",  studentSelectCourseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("courseNumber", courseNumber);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryStudentSelectCourseInfoOutputToExcel() { 
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber,courseNumber);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "StudentSelectCourseInfo信息记录"; 
        String[] headers = { "记录编号","学生对象","课程对象"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<studentSelectCourseInfoList.size();i++) {
        	StudentSelectCourseInfo studentSelectCourseInfo = studentSelectCourseInfoList.get(i); 
        	dataset.add(new String[]{studentSelectCourseInfo.getSelectId() + "",studentSelectCourseInfo.getStudentNumber().getStudentName(),
studentSelectCourseInfo.getCourseNumber().getCourseName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"StudentSelectCourseInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询StudentSelectCourseInfo信息*/
    public String FrontQueryStudentSelectCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber, courseNumber, currentPage);
        /*计算总的页数和总的记录数*/
        studentSelectCourseInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*获取到总的页码数目*/
        totalPage = studentSelectCourseInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = studentSelectCourseInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentSelectCourseInfoList",  studentSelectCourseInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("courseNumber", courseNumber);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "front_query_view";
    }

    /*查询要修改的StudentSelectCourseInfo信息*/
    public String ModifyStudentSelectCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键selectId获取StudentSelectCourseInfo对象*/
        StudentSelectCourseInfo studentSelectCourseInfo = studentSelectCourseInfoDAO.GetStudentSelectCourseInfoBySelectId(selectId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("studentSelectCourseInfo",  studentSelectCourseInfo);
        return "modify_view";
    }

    /*查询要修改的StudentSelectCourseInfo信息*/
    public String FrontShowStudentSelectCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键selectId获取StudentSelectCourseInfo对象*/
        StudentSelectCourseInfo studentSelectCourseInfo = studentSelectCourseInfoDAO.GetStudentSelectCourseInfoBySelectId(selectId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("studentSelectCourseInfo",  studentSelectCourseInfo);
        return "front_show_view";
    }

    /*更新修改StudentSelectCourseInfo信息*/
    public String ModifyStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentNumber = studentDAO.GetStudentByStudentNumber(studentSelectCourseInfo.getStudentNumber().getStudentNumber());
            studentSelectCourseInfo.setStudentNumber(studentNumber);
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            studentSelectCourseInfo.setCourseNumber(courseNumber);
            studentSelectCourseInfoDAO.UpdateStudentSelectCourseInfo(studentSelectCourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除StudentSelectCourseInfo信息*/
    public String DeleteStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            studentSelectCourseInfoDAO.DeleteStudentSelectCourseInfo(selectId);
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo删除失败!"));
            return "error";
        }
    }

}
