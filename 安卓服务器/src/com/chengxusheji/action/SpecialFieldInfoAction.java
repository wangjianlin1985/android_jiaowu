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
import com.chengxusheji.dao.SpecialFieldInfoDAO;
import com.chengxusheji.domain.SpecialFieldInfo;
import com.chengxusheji.dao.CollegeInfoDAO;
import com.chengxusheji.domain.CollegeInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SpecialFieldInfoAction extends BaseAction {

    /*界面层需要查询的属性: 专业编号*/
    private String specialFieldNumber;
    public void setSpecialFieldNumber(String specialFieldNumber) {
        this.specialFieldNumber = specialFieldNumber;
    }
    public String getSpecialFieldNumber() {
        return this.specialFieldNumber;
    }

    /*界面层需要查询的属性: 专业名称*/
    private String specialFieldName;
    public void setSpecialFieldName(String specialFieldName) {
        this.specialFieldName = specialFieldName;
    }
    public String getSpecialFieldName() {
        return this.specialFieldName;
    }

    /*界面层需要查询的属性: 所在学院*/
    private CollegeInfo specialCollegeNumber;
    public void setSpecialCollegeNumber(CollegeInfo specialCollegeNumber) {
        this.specialCollegeNumber = specialCollegeNumber;
    }
    public CollegeInfo getSpecialCollegeNumber() {
        return this.specialCollegeNumber;
    }

    /*界面层需要查询的属性: 成立日期*/
    private String specialBirthDate;
    public void setSpecialBirthDate(String specialBirthDate) {
        this.specialBirthDate = specialBirthDate;
    }
    public String getSpecialBirthDate() {
        return this.specialBirthDate;
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
    @Resource CollegeInfoDAO collegeInfoDAO;
    @Resource SpecialFieldInfoDAO specialFieldInfoDAO;

    /*待操作的SpecialFieldInfo对象*/
    private SpecialFieldInfo specialFieldInfo;
    public void setSpecialFieldInfo(SpecialFieldInfo specialFieldInfo) {
        this.specialFieldInfo = specialFieldInfo;
    }
    public SpecialFieldInfo getSpecialFieldInfo() {
        return this.specialFieldInfo;
    }

    /*跳转到添加SpecialFieldInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的CollegeInfo信息*/
        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        return "add_view";
    }

    /*添加SpecialFieldInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddSpecialFieldInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证专业编号是否已经存在*/
        String specialFieldNumber = specialFieldInfo.getSpecialFieldNumber();
        SpecialFieldInfo db_specialFieldInfo = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(specialFieldNumber);
        if(null != db_specialFieldInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该专业编号已经存在!"));
            return "error";
        }
        try {
            CollegeInfo specialCollegeNumber = collegeInfoDAO.GetCollegeInfoByCollegeNumber(specialFieldInfo.getSpecialCollegeNumber().getCollegeNumber());
            specialFieldInfo.setSpecialCollegeNumber(specialCollegeNumber);
            specialFieldInfoDAO.AddSpecialFieldInfo(specialFieldInfo);
            ctx.put("message",  java.net.URLEncoder.encode("SpecialFieldInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SpecialFieldInfo添加失败!"));
            return "error";
        }
    }

    /*查询SpecialFieldInfo信息*/
    public String QuerySpecialFieldInfo() {
        if(currentPage == 0) currentPage = 1;
        if(specialFieldNumber == null) specialFieldNumber = "";
        if(specialFieldName == null) specialFieldName = "";
        if(specialBirthDate == null) specialBirthDate = "";
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QuerySpecialFieldInfoInfo(specialFieldNumber, specialFieldName, specialCollegeNumber, specialBirthDate, currentPage);
        /*计算总的页数和总的记录数*/
        specialFieldInfoDAO.CalculateTotalPageAndRecordNumber(specialFieldNumber, specialFieldName, specialCollegeNumber, specialBirthDate);
        /*获取到总的页码数目*/
        totalPage = specialFieldInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = specialFieldInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("specialFieldInfoList",  specialFieldInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("specialFieldNumber", specialFieldNumber);
        ctx.put("specialFieldName", specialFieldName);
        ctx.put("specialCollegeNumber", specialCollegeNumber);
        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        ctx.put("specialBirthDate", specialBirthDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySpecialFieldInfoOutputToExcel() { 
        if(specialFieldNumber == null) specialFieldNumber = "";
        if(specialFieldName == null) specialFieldName = "";
        if(specialBirthDate == null) specialBirthDate = "";
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QuerySpecialFieldInfoInfo(specialFieldNumber,specialFieldName,specialCollegeNumber,specialBirthDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SpecialFieldInfo信息记录"; 
        String[] headers = { "专业编号","专业名称","所在学院","成立日期","联系人","联系电话"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<specialFieldInfoList.size();i++) {
        	SpecialFieldInfo specialFieldInfo = specialFieldInfoList.get(i); 
        	dataset.add(new String[]{specialFieldInfo.getSpecialFieldNumber(),specialFieldInfo.getSpecialFieldName(),specialFieldInfo.getSpecialCollegeNumber().getCollegeName(),
new SimpleDateFormat("yyyy-MM-dd").format(specialFieldInfo.getSpecialBirthDate()),specialFieldInfo.getSpecialMan(),specialFieldInfo.getSpecialTelephone()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SpecialFieldInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SpecialFieldInfo信息*/
    public String FrontQuerySpecialFieldInfo() {
        if(currentPage == 0) currentPage = 1;
        if(specialFieldNumber == null) specialFieldNumber = "";
        if(specialFieldName == null) specialFieldName = "";
        if(specialBirthDate == null) specialBirthDate = "";
        List<SpecialFieldInfo> specialFieldInfoList = specialFieldInfoDAO.QuerySpecialFieldInfoInfo(specialFieldNumber, specialFieldName, specialCollegeNumber, specialBirthDate, currentPage);
        /*计算总的页数和总的记录数*/
        specialFieldInfoDAO.CalculateTotalPageAndRecordNumber(specialFieldNumber, specialFieldName, specialCollegeNumber, specialBirthDate);
        /*获取到总的页码数目*/
        totalPage = specialFieldInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = specialFieldInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("specialFieldInfoList",  specialFieldInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("specialFieldNumber", specialFieldNumber);
        ctx.put("specialFieldName", specialFieldName);
        ctx.put("specialCollegeNumber", specialCollegeNumber);
        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        ctx.put("specialBirthDate", specialBirthDate);
        return "front_query_view";
    }

    /*查询要修改的SpecialFieldInfo信息*/
    public String ModifySpecialFieldInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键specialFieldNumber获取SpecialFieldInfo对象*/
        SpecialFieldInfo specialFieldInfo = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(specialFieldNumber);

        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        ctx.put("specialFieldInfo",  specialFieldInfo);
        return "modify_view";
    }

    /*查询要修改的SpecialFieldInfo信息*/
    public String FrontShowSpecialFieldInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键specialFieldNumber获取SpecialFieldInfo对象*/
        SpecialFieldInfo specialFieldInfo = specialFieldInfoDAO.GetSpecialFieldInfoBySpecialFieldNumber(specialFieldNumber);

        List<CollegeInfo> collegeInfoList = collegeInfoDAO.QueryAllCollegeInfoInfo();
        ctx.put("collegeInfoList", collegeInfoList);
        ctx.put("specialFieldInfo",  specialFieldInfo);
        return "front_show_view";
    }

    /*更新修改SpecialFieldInfo信息*/
    public String ModifySpecialFieldInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            CollegeInfo specialCollegeNumber = collegeInfoDAO.GetCollegeInfoByCollegeNumber(specialFieldInfo.getSpecialCollegeNumber().getCollegeNumber());
            specialFieldInfo.setSpecialCollegeNumber(specialCollegeNumber);
            specialFieldInfoDAO.UpdateSpecialFieldInfo(specialFieldInfo);
            ctx.put("message",  java.net.URLEncoder.encode("SpecialFieldInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SpecialFieldInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除SpecialFieldInfo信息*/
    public String DeleteSpecialFieldInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            specialFieldInfoDAO.DeleteSpecialFieldInfo(specialFieldNumber);
            ctx.put("message",  java.net.URLEncoder.encode("SpecialFieldInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SpecialFieldInfo删除失败!"));
            return "error";
        }
    }

}
