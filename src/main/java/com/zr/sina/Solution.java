package com.zr.sina;

import com.zr.sina.entity.ArticleModel;
import com.zr.sina.util.ElementParse;
import com.zr.sina.util.HtmlParse;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.zr.sina.util.ElementParse.writerErrorFileContent;

public class Solution {
    public static void main(String[] args) throws IOException {
        String type = "private";
        List<String> hrefList = new ArrayList<String>();
        hrefList.add("http://control.blog.sina.com.cn/blog_rebuild/blog/controllers/articlelist.php?uid=2238048762&p=1&status=5");
        hrefList.add("http://control.blog.sina.com.cn/blog_rebuild/blog/controllers/articlelist.php?uid=2238048762&p=2&status=5");
        hrefList.add("http://control.blog.sina.com.cn/blog_rebuild/blog/controllers/articlelist.php?uid=2238048762&p=3&status=5");
        hrefList.add("http://control.blog.sina.com.cn/blog_rebuild/blog/controllers/articlelist.php?uid=2238048762&p=4&status=5");
        getData(type, hrefList);
    }

    public static String getPrivateData(String href) throws IOException {

        //重新打开一个连接
        URL url = new URL(href);
        String cookieVal = "_ga=1GA1.2.1670021441.1589982461; __utma=226521935.1670021441.1589982461.1591964188.1591964188.1; Hm_lvt_d8d668bc92ee885787caab7ba4aa77ec=1594453341; __gads=ID=0fb7993aa9511184:T=1589982461:R:S=ALNI_MZ94Svf_WR9qIij7E8jR_Bte-GzPg; _gid=GA1.2.1458897310.1610108960; .Cnblogs.AspNetCore.Cookies=CfDJ8EklyHYHyB5Oj4onWtxTnxaE80iDKSyrjdLlXgYKmU5lISXHtuy_3ypgIYn5Hs3DCOehtehq8ojJx7J07lK8gkYg9-bY6FTXNkNYBklZgRt0sDZPeXzXjiThEEhqkvjUsW4bkxvhSiqJyrb7Yk00W2FntOci8ChJl4xXYISYPR2309rRqj6XiGy3vzaK0nnkhmk_SygSTp9Ey2VAtRfqhLSVqZTqLMCVm3d92stcJ3eIFt4VD4QU0R7aLMAn9nTriTZep9HTD_vAJUQPm08IvnvW__TSSR3nrpdkEhZIQZHNHVoZPNvALKsLKrC7zlYlJ74YTAhsMUUIe3sXebeRJ3AVN77hRqAS-mKkPuq3Iw3vU5AAAUTKxxPi7Z-LwUPunfOL_xefLhaQNKvHqOLmyuwRudKkvkwBALMUu1jZHkqLVz1rPTh1UO3_4BLYfBGaN9bHXxtZPUAkNTMG_GpWet8F609ULy-y8C2KcxEzhqQ2FFSkHUMnJ2vTSeyr7qNoR8xa1RWII8uiSCysiDghLUxucGaKhXQ2NFlnim4FvvvzJ2fne0v1ORFJVX43F4rFww; .CNBlogsCookie=301DF5DD705ECE1CBCE666DB04AB8B36B92B3B4D8ABECF4F0DDE05278A1BBBD585F8332C7723CA99F044E67E4BD178A3B99B1C5BE53F67D5DFBC50C7A99FE33291E9D4554F1A3FB56F0E57DCBD847F0FB00D7E0E3768E8E6CBB45B8FB5165DB1E6048D98; _gat=1";
        cookieVal = "SINAGLOBAL=114.245.47.172_1590209165.682195; UOR=,finance.sina.com.cn,; __gads=ID=7076a32e4162f59e:T=1591360114:S=ALNI_MbUprX44JRX7KPIYItRW6e3tvC5hA; _ga=GA1.3.834786986.1591360114; lxlrttp=1578733570; U_TRS1=0000001e.3462b8b.5ee5a7d3.f57bbc25; vjuids=1c4a5e545.174ec17a337.0.6447b001ef35a; vjlast=1601862745; U_TRS2=0000000e.39b93feb.5ff84865.5c5da728; Apache=124.202.173.14_1610106980.715175; ULV=1610106995023:6:2:2:124.202.173.14_1610106980.715175:1610106983365; SessionID=j0cfs4itmj259n7250d9775nl7; ULOGIN_IMG=tc-779127979b5bf7c1b3d2975aae87072e3c97; SUB=_2A25y_V8BDeRhGeRM6FoR9CbLzT6IHXVRizfJrDV_PUNbm9AKLVbkkW9NU_IQzWua_dGJeV9l9rOF8zg1oXC3t31f; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WhhvYLh0ELlP4osd1nEPlrO5JpX5KzhUgL.FozEe0n7ShnNSoz2dJLoI0qLxKqL1KqL1hMLxKqL1KML1h-LxK-L1KzLBKnLxKnLB-qLBKzLxKnL1h5L1h2LxKnLBo-LBo-t; ALF=1641702097; SINABLOGNUINFO=2238048762.8565e9fa.; rotatecount=3";
        HttpURLConnection resumeConnection = (HttpURLConnection) url
                .openConnection();
        if (cookieVal != null) {
            //发送cookie信息上去，以表明自己的身份，否则会被认为没有权限
            resumeConnection.setRequestProperty("Cookie", cookieVal);
        }
        resumeConnection.connect();
        InputStream urlStream = resumeConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlStream));
        String ss = null;
        String total = "";
        while ((ss = bufferedReader.readLine()) != null) {
            total += ss;
        }
        return total;
    }

    /**
     * 获取公开blog内容
     */
    public static void getData(String type, List<String> hrefList) {
        int page = 0;
        for (String href : hrefList) {
            String html = null;
            if ("private".equals(type)) {//私密文章标题url
                try {
                    html = getPrivateData(href);
                    ++page;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {//公开文章标题和url
                HtmlParse htmlParse = new HtmlParse(href);
                html = htmlParse.htmlParse();
                ++page;
            }
            if (html != null) {
                ElementParse.parseHrefAndTitle(Jsoup.parse(html), page);
                for (ArticleModel articleModel : ElementParse.list) {
                    String content = null;
                    if ("private".equals(type)) {//私密文章内容
                        try {
                            content = getPrivateData(articleModel.getUrl());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {//公开文章内容
                        HtmlParse contentHtml = new HtmlParse(articleModel.getUrl());
                        content = contentHtml.htmlParse();
                    }
                    ElementParse.parseContent(Jsoup.parse(content), articleModel);
                }
            }
        }
        //写入word
        writerErrorFileContent();
    }


}
