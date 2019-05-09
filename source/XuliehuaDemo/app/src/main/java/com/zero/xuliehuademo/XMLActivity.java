package com.zero.xuliehuademo;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.zero.xuliehuademo.xml.Course;
import com.zero.xuliehuademo.xml.Student;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 性能测试
 */
public class XMLActivity extends AppCompatActivity {
    private static final String TAG = "Zero";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_dom)
    public void onBtnDomClicked() {
        domTest(XMLActivity.this);
    }

    @OnClick(R.id.btn_sax)
    public void onBtnSaxClicked() {
        try {
            saxTest(XMLActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_pull)
    public void onBtnPullClicked() {
        try {
            pullTest(XMLActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void domTest(Context context) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(context.getResources().openRawResource(R.raw.students));
            //通过Document对象的getElementsByTagName()返根节点的一个list集合
            NodeList studentList = document.getElementsByTagName("student");
            for (int i = 0; i < studentList.getLength(); i++) {
                Student student = new Student();
                //循环遍历获取每一个student
                Node studentNode = studentList.item(i);
                if (((Element) studentNode).hasAttribute("id")) {
                    student.setId(Integer.parseInt(((Element) studentNode).getAttribute("id")));
                }
                //解析student节点的子节点
                NodeList childList = studentNode.getChildNodes();
                for (int t = 0; t < childList.getLength(); t++) {
                    //区分出text类型的node以及element类型的node
                    if (childList.item(t).getNodeType() == Node.ELEMENT_NODE) {
                        if (childList.item(t).getNodeName().equalsIgnoreCase("Courses")) {
                            NodeList courses = childList.item(t).getChildNodes();
                            for (int j = 0; j < courses.getLength(); j++) {
                                Node courseNode = courses.item(j);
                                if (courseNode.getNodeType() != Node.ELEMENT_NODE) {
                                    continue;
                                }
                                NamedNodeMap namedNodeMap = courseNode.getAttributes();
                                Course course = new Course();
                                student.addCourse(course);
                                for (int k = 0; k < namedNodeMap.getLength(); k++) {
                                    Node courseAttr = namedNodeMap.item(k);
                                    if (courseAttr.getNodeName().equals("name")) {
                                        course.setName(courseAttr.getNodeValue());
                                    } else if (courseAttr.getNodeName().equals("score")) {
                                        course.setScore(Float.parseFloat(courseAttr.getNodeValue()));
                                    }
                                }
                            }
                        } else {
                            Node child = childList.item(t);
                            if (child.getNodeName().equals("name")) {
                                student.setName(child.getTextContent());
                            } else if (child.getNodeName().equals("age")) {
                                student.setAge(Integer.parseInt(child.getTextContent()));
                            } else if (child.getNodeName().equals("sax")) {
                                student.setSax(child.getTextContent());
                            }
                        }
                    }
                }
                Log.i("Zero", "解析完毕: " + student);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Log.e("Zero", e.getMessage());
        }
    }

    public void saxTest(Context context) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        sp.parse(context.getResources().openRawResource(R.raw.students), new DefaultHandler() {

            String currentTag = null;
            Student student = null;

            /**
             * 文档解析开始时被调用
             * @throws SAXException
             */
            @Override
            public void startDocument() throws SAXException {
                super.startDocument();
            }

            /**
             * 文档解析结束时被调用
             * @throws SAXException
             */
            @Override
            public void endDocument() throws SAXException {
                super.endDocument();
            }

            /**
             *
             * @param uri 命名空间
             * @param localName 不带命名空间前缀的标签名
             * @param qName 带命名空间的标签名
             * @param attributes 标签的属性集合 <student id="0"></student>
             * @throws SAXException
             */
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                super.startElement(uri, localName, qName, attributes);
                currentTag = localName;
                if ("student".equals(currentTag)) {
                    student = new Student();
                    student.setId(Integer.parseInt(attributes.getValue("id")));
                }
                if ("course".equals(currentTag)) {
                    if (student != null) {
                        Course course = new Course();
                        course.setName(attributes.getValue("name"));
                        course.setScore(Float.parseFloat(attributes.getValue("score")));
                        student.addCourse(course);
                    }
                }

            }

            /**
             *  解析到结束标签时被调用 '/>'
             * @param uri
             * @param localName
             * @param qName
             * @throws SAXException
             */
            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                super.endElement(uri, localName, qName);
                if ("student".equals(localName)) {
                    Log.i(TAG, "endElement: student: " + student);
                }
            }

            /**
             *
             * @param ch 内容
             * @param start 起始位置
             * @param length 长度
             * @throws SAXException
             */
            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                super.characters(ch, start, length);
                String str = new String(ch, start, length).trim();
                if (TextUtils.isEmpty(str))
                    return;
                if ("name".equals(currentTag) && student != null) {
                    student.setName(str);
                }
                if ("age".equals(currentTag) && student != null) {
                    student.setAge(Integer.parseInt(str));
                }
                if ("sax".equals(currentTag) && student != null) {
                    student.setSax(str);
                }

            }
        });
    }

    public void pullTest(Context context) throws Exception {
        XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
        parser.setInput(context.getResources().openRawResource(R.raw.students), "utf-8");//设置数据源编码
        int eventCode = parser.getEventType();//获取事件类型
        Student student = null;
        while (eventCode != XmlPullParser.END_DOCUMENT) {
            switch (eventCode) {
                case XmlPullParser.START_DOCUMENT://开始读取XML文档
                    break;
                case XmlPullParser.START_TAG://开始读取标签
                    String name = parser.getName();
                    if ("student".equals(name)) {
                        student = new Student();
                        student.setId(Integer.parseInt(parser.getAttributeValue(null, "id")));
                    }
                    if ("name".equals(name) && student != null) {
                        student.setName(parser.nextText());
                    }
                    if ("age".equals(name) && student != null) {
                        student.setAge(Integer.parseInt(parser.nextText().trim()));
                    }
                    if ("sax".equals(name) && student != null) {
                        student.setSax(parser.nextText());
                    }
                    if ("course".equals(name) && student != null) {
                        Course course = new Course();
                        course.setName(parser.getAttributeValue(null, "name"));
                        course.setScore(Float.parseFloat(parser.getAttributeValue(null, "score")));
                        student.addCourse(course);
                    }
                    break;
                case XmlPullParser.END_TAG://结束原始事件
                    if ("student".equals(parser.getName())) {
                        Log.i(TAG, "pullTest: student: " + student);
                    }
                    break;

            }
            eventCode = parser.next();
        }

    }
}
