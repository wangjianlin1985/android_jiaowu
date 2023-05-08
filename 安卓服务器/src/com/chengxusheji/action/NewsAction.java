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
import com.chengxusheji.dao.NewsDAO;
import com.chengxusheji.domain.News;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class NewsAction extends BaseAction {

	/*图片或文件字段newsPhoto参数接收*/
	private File newsPhotoFile;
	private String newsPhotoFileFileName;
	private String newsPhotoFileContentType;
	public File getNewsPhotoFile() {
		return newsPhotoFile;
	}
	public void setNewsPhotoFile(File newsPhotoFile) {
		this.newsPhotoFile = newsPhotoFile;
	}
	public String getNewsPhotoFileFileName() {
		return newsPhotoFileFileName;
	}
	public void setNewsPhotoFileFileName(String newsPhotoFileFileName) {
		this.newsPhotoFileFileName = newsPhotoFileFileName;
	}
	public String getNewsPhotoFileContentType() {
		return newsPhotoFileContentType;
	}
	public void setNewsPhotoFileContentType(String newsPhotoFileContentType) {
		this.newsPhotoFileContentType = newsPhotoFileContentType;
	}
    /*界面层需要查询的属性: 新闻标题*/
    private String newsTitle;
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }
    public String getNewsTitle() {
        return this.newsTitle;
    }

    /*界面层需要查询的属性: 发布日期*/
    private String newsDate;
    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
    public String getNewsDate() {
        return this.newsDate;
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

    private int newsId;
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }
    public int getNewsId() {
        return newsId;
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
    @Resource NewsDAO newsDAO;

    /*待操作的News对象*/
    private News news;
    public void setNews(News news) {
        this.news = news;
    }
    public News getNews() {
        return this.news;
    }

    /*跳转到添加News视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加News信息*/
    @SuppressWarnings("deprecation")
    public String AddNews() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*处理新闻图片上传*/
            String newsPhotoPath = "upload/noimage.jpg"; 
       	 	if(newsPhotoFile != null)
       	 		newsPhotoPath = photoUpload(newsPhotoFile,newsPhotoFileContentType);
       	 	news.setNewsPhoto(newsPhotoPath);
            newsDAO.AddNews(news);
            ctx.put("message",  java.net.URLEncoder.encode("News添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("News添加失败!"));
            return "error";
        }
    }

    /*查询News信息*/
    public String QueryNews() {
        if(currentPage == 0) currentPage = 1;
        if(newsTitle == null) newsTitle = "";
        if(newsDate == null) newsDate = "";
        List<News> newsList = newsDAO.QueryNewsInfo(newsTitle, newsDate, currentPage);
        /*计算总的页数和总的记录数*/
        newsDAO.CalculateTotalPageAndRecordNumber(newsTitle, newsDate);
        /*获取到总的页码数目*/
        totalPage = newsDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = newsDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsList",  newsList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("newsTitle", newsTitle);
        ctx.put("newsDate", newsDate);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryNewsOutputToExcel() { 
        if(newsTitle == null) newsTitle = "";
        if(newsDate == null) newsDate = "";
        List<News> newsList = newsDAO.QueryNewsInfo(newsTitle,newsDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "News信息记录"; 
        String[] headers = { "记录编号","新闻标题","新闻内容","发布日期","新闻图片"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<newsList.size();i++) {
        	News news = newsList.get(i); 
        	dataset.add(new String[]{news.getNewsId() + "",news.getNewsTitle(),news.getNewsContent(),new SimpleDateFormat("yyyy-MM-dd").format(news.getNewsDate()),news.getNewsPhoto()});
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
			response.setHeader("Content-disposition","attachment; filename="+"News.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询News信息*/
    public String FrontQueryNews() {
        if(currentPage == 0) currentPage = 1;
        if(newsTitle == null) newsTitle = "";
        if(newsDate == null) newsDate = "";
        List<News> newsList = newsDAO.QueryNewsInfo(newsTitle, newsDate, currentPage);
        /*计算总的页数和总的记录数*/
        newsDAO.CalculateTotalPageAndRecordNumber(newsTitle, newsDate);
        /*获取到总的页码数目*/
        totalPage = newsDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = newsDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("newsList",  newsList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("newsTitle", newsTitle);
        ctx.put("newsDate", newsDate);
        return "front_query_view";
    }

    /*查询要修改的News信息*/
    public String ModifyNewsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键newsId获取News对象*/
        News news = newsDAO.GetNewsByNewsId(newsId);

        ctx.put("news",  news);
        return "modify_view";
    }

    /*查询要修改的News信息*/
    public String FrontShowNewsQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键newsId获取News对象*/
        News news = newsDAO.GetNewsByNewsId(newsId);

        ctx.put("news",  news);
        return "front_show_view";
    }

    /*更新修改News信息*/
    public String ModifyNews() {
        ActionContext ctx = ActionContext.getContext();
        try {
            /*处理新闻图片上传*/
            if(newsPhotoFile != null) {
            	String newsPhotoPath = photoUpload(newsPhotoFile,newsPhotoFileContentType);
            	news.setNewsPhoto(newsPhotoPath);
            }
            newsDAO.UpdateNews(news);
            ctx.put("message",  java.net.URLEncoder.encode("News信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("News信息更新失败!"));
            return "error";
       }
   }

    /*删除News信息*/
    public String DeleteNews() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            newsDAO.DeleteNews(newsId);
            ctx.put("message",  java.net.URLEncoder.encode("News删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("News删除失败!"));
            return "error";
        }
    }

}
