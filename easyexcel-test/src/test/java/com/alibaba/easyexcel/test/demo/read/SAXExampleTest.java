package com.alibaba.easyexcel.test.demo.read;

import com.alibaba.easyexcel.test.util.TestFileUtil;
import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

/**
 * @Description: SAXExample （则提供了方法来解析 XML 文档。
 * 解析过程中，SAXParser 会调用一个实现了 org.xml.sax.ContentHandler 接口的对象的方法，
 * 以处理文档中的各种事件（例如开始文档、结束文档、开始元素、结束元素等）。）
 * @Author: yichengdong
 * @CreateDate: 2024/11/17 9:20
 * @Copyright : 豆浆油条个人非正式工作室
 */
public class SAXExampleTest {

    @Test
    public void test() {
        try {
            // 创建一个 SAX 解析器工厂实例
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // 配置解析器工厂（可选）
            factory.setNamespaceAware(true); // 设置命名空间感知

            // 使用工厂创建一个 SAX 解析器
            SAXParser saxParser = factory.newSAXParser();

            // 创建一个默认的处理器，并重写需要处理的方法
            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void startDocument() throws SAXException {
                    System.out.println("开始解析文档...");
                }

                @Override
                public void endDocument() throws SAXException {
                    System.out.println("结束解析文档...");
                }

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    System.out.println("开始元素: " + qName);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    System.out.println("结束元素: " + qName);
                }

                @Override
                public void characters(char ch[], int start, int length) throws SAXException {
                    System.out.println("字符数据: " + new String(ch, start, length));
                }
            };

            // 指定要解析的 XML 文件路径
            String filePath = TestFileUtil.getPath() + "demo" + File.separator + "example.xml";

            // 使用解析器解析 XML 文件
            saxParser.parse(filePath, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
