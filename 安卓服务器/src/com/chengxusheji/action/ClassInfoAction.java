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
import com.chengxusheji.dao.ClassInfoDAO;
import com.chengxusheji.domain.ClassInfo;
import com.chengxusheji.dao.SpecialFieldInfoDAO;
import com.chengxusheji.domain.SpecialFieldInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ClassInfoAction extends BaseAction {

    /*界面层需要查询的属性: 班级编号*/
    private String classNumber;
    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }
    public String getClassNumber() {
        return this.classNumber;
    }

    /*界面层需要查询的属性: 班级名称*/
    private String className;
    public void setClassName(String className) {
        this.className = className;
    }
    public String getClassName() {
        return this.className;
    }

    /*界面层需要查询的属性: 所属专业*/
    private SpecialFieldInfo classSpecialFieldNumber;
    public void setClassSpecialFieldNumber(SpecialFieldInfo classSpecialFieldNumber) {
        this.classSpecialFieldNumber = classSpecialFieldNumber;
    }
    public SpecialFieldInfo getClassSpecialFieldNumber() {
        return this.classSpecialFieldNumber;
    }

    /*界面层需要查询的属性: 成立日期*/
    private String classBirthDate;
    public void setClassBirthDate(String classBirthDate) {
        this.classBirthDate = classBirthDate;
    }
    public String getClassBirthDate() {
        return this.classBirthDate;
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
    @Resource SpecialFieldInfoDAO specialFieldInfoDAO;
    @Resource ClassInfoDAO classInfoDAO;

    /*待操作的ClassInfo对象*/
    private ClassInfo classInfo;
    public void setClassInfo(ClassInfo classInfo) {
        this.classInfo = classInfo;
    }
    public ClassInfo getClassInfo() {
        return this.classInfo;
    }

    /*跳转到添加ClassInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的SpecialFieldInfo信息*/
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        return "add_view";
    }

    /*添加ClassInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证班级编号是否已经存在*/
        String classNumber = classInfo.getClassNumber();
        ClassInfo db_classInfo = classInfoDAO.GetClassInfoByClassNumber(classNumber);
        if(null != db_classInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该班级编号已经存在!"));
            return "error";
        }
        try {
            SpecialFieldInfo classSpecialFieldNumber = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(classInfo.getClassSpecialFieldNumber().getSpecialFieldNumber());
            classInfo.setClassSpecialFieldNumber(classSpecialFieldNumber);
            classInfoDAO.AddClassInfo(classInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ClassInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ClassInfo添加失败!"));
            return "error";
        }
    }

    /*查询ClassInfo信息*/
    public String QueryClassInfo() {
        if(currentPage == 0) currentPage = 1;
        if(classNumber == null) classNumber = "";
        if(className == null) className = "";
        if(classBirthDate == null) classBirthDate = "";
        List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfoInfo(classNumber, className, classSpecialFieldNumber, classBirthDate, currentPage);
        /*计算总的页数和总的记录数*/
        classInfoDAO.CalculateTotalPageAndRecordNumber(classNumber, className, classSpecialFieldNumber, classBirthDate);
        /*获取到总的页码数目*/
        totalPage = classInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = classInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("classNumber", classNumber);
        ctx.put("className", className);
        ctx.put("classSpecialFieldNumber", classSpecialFieldNumber);
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        ctx.put("classBirthDate", classBirthDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryClassInfoOutputToExcel() { 
        if(classNumber == null) classNumber = "";
        if(className == null) className = "";
        if(classBirthDate == null) classBirthDate = "";
        List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfoInfo(classNumber,className,classSpecialFieldNumber,classBirthDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ClassInfo信息记录"; 
        String[] headers = { "班级编号","班级名称","所属专业","成立日期","班主任","联系电话"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<classInfoList.size();i++) {
        	ClassInfo classInfo = classInfoList.get(i); 
        	dataset.add(new String[]{classInfo.getClassNumber(),classInfo.getClassName(),classInfo.getClassSpecialFieldNumber().getSpecialFieldName(),
new SimpleDateFormat("yyyy-MM-dd").format(classInfo.getClassBirthDate()),classInfo.getClassTeacherCharge(),classInfo.getClassTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"ClassInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询ClassInfo信息*/
    public String FrontQueryClassInfo() {
        if(currentPage == 0) currentPage = 1;
        if(classNumber == null) classNumber = "";
        if(className == null) className = "";
        if(classBirthDate == null) classBirthDate = "";
        List<ClassInfo> classInfoList = classInfoDAO.QueryClassInfoInfo(classNumber, className, classSpecialFieldNumber, classBirthDate, currentPage);
        /*计算总的页数和总的记录数*/
        classInfoDAO.CalculateTotalPageAndRecordNumber(classNumber, className, classSpecialFieldNumber, classBirthDate);
        /*获取到总的页码数目*/
        totalPage = classInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = classInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("classInfoList",  classInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("classNumber", classNumber);
        ctx.put("className", className);
        ctx.put("classSpecialFieldNumber", classSpecialFieldNumber);
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        ctx.put("classBirthDate", classBirthDate);
        return "front_query_view";
    }

    /*查询要修改的ClassInfo信息*/
    public String ModifyClassInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键classNumber获取ClassInfo对象*/
        ClassInfo classInfo = classInfoDAO.GetClassInfoByClassNumber(classNumber);

        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        ctx.put("classInfo",  classInfo);
        return "modify_view";
    }

    /*查询要修改的ClassInfo信息*/
    public String FrontShowClassInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键classNumber获取ClassInfo对象*/
        ClassInfo classInfo = classInfoDAO.GetClassInfoByClassNumber(classNumber);

        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QueryAllSpecialFieldInfoInfo();
        ctx.put("specialFieldInfoList", specialFieldInfoList);
        ctx.put("classInfo",  classInfo);
        return "front_show_view";
    }

    /*更新修改ClassInfo信息*/
    public String ModifyClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            SpecialFieldInfo classSpecialFieldNumber = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(classInfo.getClassSpecialFieldNumber().getSpecialFieldNumber());
            classInfo.setClassSpecialFieldNumber(classSpecialFieldNumber);
            classInfoDAO.UpdateClassInfo(classInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ClassInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ClassInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除ClassInfo信息*/
    public String DeleteClassInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            classInfoDAO.DeleteClassInfo(classNumber);
            ctx.put("message",  java.net.URLEncoder.encode("ClassInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ClassInfo删除失败!"));
            return "error";
        }
    }

}
