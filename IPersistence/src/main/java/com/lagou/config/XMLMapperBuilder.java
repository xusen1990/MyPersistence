package com.lagou.config;

import com.lagou.pojo.Configuration;
import com.lagou.pojo.MappedStatement;
import com.lagou.pojo.SqlCommandType;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");


        //遍历select, insert, update, delete 四种标签
        for(SqlCommandType sct : SqlCommandType.values()){
            String type = sct.name().toLowerCase();
            List<Element> list = rootElement.selectNodes("//" + type);
            if(list != null && list.size() > 0){
                for (Element element : list) {
                    String id = element.attributeValue("id");
                    String resultType = element.attributeValue("resultType");
                    String parameterType = element.attributeValue("parameterType");
                    String sqlText = element.getTextTrim();
                    MappedStatement mappedStatement = new MappedStatement();
                    mappedStatement.setId(id);
                    mappedStatement.setResultType(resultType);
                    mappedStatement.setParameterType(parameterType);
                    mappedStatement.setSql(sqlText);
                    //设置标签type
                    mappedStatement.setSqlCommandType(sct);

                    String statementId = namespace + "." + id;
                    //将mappedStatement封装到configuration中
                    configuration.getMapStatementMap().put(statementId, mappedStatement);
                }
            }
        }
    }
}
