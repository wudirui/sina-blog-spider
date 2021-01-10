package com.zr.sina.util;

import com.zr.sina.entity.ArticleModel;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ElementParse {

    public static List<ArticleModel> list = new ArrayList<ArticleModel>();

    private static String fileDir = "F:\\demo2\\";

    public ElementParse() {
    }

    /**
     * 解析出标题和对应的文章链接
     *
     * @param document
     */
    public static void parseHrefAndTitle(Document document, int page) {
        //根据网页标签解析源码
        Elements elements = document.select(".articleCell");
        int size = elements.size();
        int index = 0;
        for (Element element : elements) {
            index++;
            Elements span1 = element.select(".atc_title a");
            Elements span2 = element.select(".atc_tm");
            String title = span1.text();
            Pattern pattern = Pattern.compile("[0-9]{4}[0-9]{1,2}[0-9]{1,2}");
            Matcher matcher = pattern.matcher(title);

            if ((title == null || "".equals(title))) {
                System.out.println("==========================》当前页码为：" + page + ",第" + index + "条文章没有");
            }
            String href = span1.attr("href");
            String time = span2.text().split(" ")[0].replace("-", "");
            if (!matcher.find() && !"".equals(time)) {
                title = time + title;
            }
            ArticleModel articleModel = new ArticleModel();
            articleModel.setTitle(title);
            articleModel.setUrl(href);
            list.add(articleModel);
            System.out.println(index + "." + articleModel.getTitle());
        }
    }

    /**
     * 解析文章内容
     *
     * @param document
     * @param articleModel
     */
    public static void parseContent(Document document, ArticleModel articleModel) {
        //根据网页标签解析源码
        Elements elements = document.select("#sina_keyword_ad_area2 p");
        List<String> list = new ArrayList<String>();

        for (Element element : elements) {
            list.add(element.text());
        }
        articleModel.setContents(list);
    }

    /**
     * 写导入题库错误文件类容
     *
     * @author niugang
     */
    public static void writerErrorFileContent() {
        for (ArticleModel articleModel : list) {
            String fileName = articleModel.getTitle();
            XWPFDocument document = new XWPFDocument();
            OutputStream stream = null;
            BufferedOutputStream bufferStream = null;
            try {
                File dir = new File(fileDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                stream = new FileOutputStream(new File(fileDir + fileName + ".doc"));
                bufferStream = new BufferedOutputStream(stream, 1024);
                //创建一个段落
                XWPFParagraph p1 = document.createParagraph();
                // 设置居中
                p1.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun r1 = p1.createRun();
                // 是否加粗
                r1.setBold(true);
                // 与下一行的距离
                r1.setTextPosition(30);
                // 段落名称
                r1.setText(articleModel.getTitle());
                r1.setFontFamily("宋体");
                // 字体大小
                r1.setFontSize(16);// 字体大小
                // 增加换行
                r1.addCarriageReturn();
                List<String> contents = articleModel.getContents();
                for (int i = 0; i < contents.size(); i++) {
                    String paragraph = contents.get(i);
                    // 创建第二个段落
                    XWPFParagraph p2 = document.createParagraph();
                    XWPFRun r2 = p2.createRun();
                    System.out.println("  " + paragraph);
                    r2.setText("    " + paragraph);
                    //r2.addCarriageReturn();
                    // 设置字体
                    r2.setFontFamily("宋体");
                    r2.setFontSize(12);// 字体大小
                }

                document.write(stream);
                stream.close();
                bufferStream.close();
            } catch (Exception ex) {
                System.out.println("写word或Excel错误文件失败：" + ex.getMessage());
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("写word或Excel错误文件失败：" + e.getMessage());
                    }
                }
                if (bufferStream != null) {
                    try {
                        bufferStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("写word或Excel错误文件失败：" + e.getMessage());
                    }
                }
            }
        }

    }


    private static void writeToWord(List<String> list, ArticleModel articleModel) {

        OutputStream ostream = null;
        try {
            ostream = new FileOutputStream("F:\\demo.docx");
            String title = "----标题：" + articleModel.getTitle();
            ostream.write(title.getBytes());
            for (String data : list) {
                System.out.println(data);
                ostream.write(data.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ostream != null) {
                try {
                    ostream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void readFromWord(List<String> list, ArticleModel articleModel) throws FileNotFoundException {

        File file = new File("F:\\demo.docx");
        if (file.exists()) {
            System.out.println("此文件存在");
        } else {
            System.out.println("此文件不存在");
        }

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String s;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
            System.out.println("文件大小为（MB）：" + new FileInputStream(file).available() / 1024 / 1024 + "M");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
