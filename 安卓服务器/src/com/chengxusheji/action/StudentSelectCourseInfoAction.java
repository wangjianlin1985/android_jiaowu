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

    /*�������Ҫ��ѯ������: ѧ������*/
    private Student studentNumber;
    public void setStudentNumber(Student studentNumber) {
        this.studentNumber = studentNumber;
    }
    public Student getStudentNumber() {
        return this.studentNumber;
    }

    /*�������Ҫ��ѯ������: �γ̶���*/
    private CourseInfo courseNumber;
    public void setCourseNumber(CourseInfo courseNumber) {
        this.courseNumber = courseNumber;
    }
    public CourseInfo getCourseNumber() {
        return this.courseNumber;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource StudentDAO studentDAO;
    @Resource CourseInfoDAO courseInfoDAO;
    @Resource StudentSelectCourseInfoDAO studentSelectCourseInfoDAO;

    /*��������StudentSelectCourseInfo����*/
    private StudentSelectCourseInfo studentSelectCourseInfo;
    public void setStudentSelectCourseInfo(StudentSelectCourseInfo studentSelectCourseInfo) {
        this.studentSelectCourseInfo = studentSelectCourseInfo;
    }
    public StudentSelectCourseInfo getStudentSelectCourseInfo() {
        return this.studentSelectCourseInfo;
    }

    /*��ת�����StudentSelectCourseInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*��ѯ���е�CourseInfo��Ϣ*/
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        return "add_view";
    }

    /*���StudentSelectCourseInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentNumber = studentDAO.GetStudentByStudentNumber(studentSelectCourseInfo.getStudentNumber().getStudentNumber());
            studentSelectCourseInfo.setStudentNumber(studentNumber);
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            studentSelectCourseInfo.setCourseNumber(courseNumber);
            studentSelectCourseInfoDAO.AddStudentSelectCourseInfo(studentSelectCourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯStudentSelectCourseInfo��Ϣ*/
    public String QueryStudentSelectCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber, courseNumber, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        studentSelectCourseInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = studentSelectCourseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryStudentSelectCourseInfoOutputToExcel() { 
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber,courseNumber);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "StudentSelectCourseInfo��Ϣ��¼"; 
        String[] headers = { "��¼���","ѧ������","�γ̶���"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"StudentSelectCourseInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯStudentSelectCourseInfo��Ϣ*/
    public String FrontQueryStudentSelectCourseInfo() {
        if(currentPage == 0) currentPage = 1;
        List<StudentSelectCourseInfo> studentSelectCourseInfoList = studentSelectCourseInfoDAO.QueryStudentSelectCourseInfoInfo(studentNumber, courseNumber, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        studentSelectCourseInfoDAO.CalculateTotalPageAndRecordNumber(studentNumber, courseNumber);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = studentSelectCourseInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�StudentSelectCourseInfo��Ϣ*/
    public String ModifyStudentSelectCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������selectId��ȡStudentSelectCourseInfo����*/
        StudentSelectCourseInfo studentSelectCourseInfo = studentSelectCourseInfoDAO.GetStudentSelectCourseInfoBySelectId(selectId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("studentSelectCourseInfo",  studentSelectCourseInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�StudentSelectCourseInfo��Ϣ*/
    public String FrontShowStudentSelectCourseInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������selectId��ȡStudentSelectCourseInfo����*/
        StudentSelectCourseInfo studentSelectCourseInfo = studentSelectCourseInfoDAO.GetStudentSelectCourseInfoBySelectId(selectId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<CourseInfo> courseInfoList = courseInfoDAO.QueryAllCourseInfoInfo();
        ctx.put("courseInfoList", courseInfoList);
        ctx.put("studentSelectCourseInfo",  studentSelectCourseInfo);
        return "front_show_view";
    }

    /*�����޸�StudentSelectCourseInfo��Ϣ*/
    public String ModifyStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentNumber = studentDAO.GetStudentByStudentNumber(studentSelectCourseInfo.getStudentNumber().getStudentNumber());
            studentSelectCourseInfo.setStudentNumber(studentNumber);
            CourseInfo courseNumber = courseInfoDAO.GetCourseInfoByCourseNumber(studentSelectCourseInfo.getCourseNumber().getCourseNumber());
            studentSelectCourseInfo.setCourseNumber(courseNumber);
            studentSelectCourseInfoDAO.UpdateStudentSelectCourseInfo(studentSelectCourseInfo);
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��StudentSelectCourseInfo��Ϣ*/
    public String DeleteStudentSelectCourseInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            studentSelectCourseInfoDAO.DeleteStudentSelectCourseInfo(selectId);
            ctx.put("message",  java.net.URLEncoder.encode("StudentSelectCourseInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("StudentSelectCourseInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
