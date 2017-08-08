package com.cmbc.gateway.core.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.TokenBuffer;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import com.cmbc.gateway.constants.GWExceptionConstants;
import com.cmbc.gateway.exception.GateWayException;

@Slf4j
@Component
public class MessageChangeUtil {

	private static ObjectMapper mapper = new ObjectMapper();

	/**
	 * Json字符串转Java对象, 多层次带转义符的方式
	 * 
	 * @param json
	 *            Json字符串
	 * @param clazz
	 *            Java对象类型
	 * @return
	 * @throws GateWayException
	 */
	@SuppressWarnings("unchecked")
	public <T> T json2ObjectBase(String json, Class<T> clazz) throws GateWayException {
		Object object = new Object();
		try {
			JSONObject jsonObj = JSONObject.fromObject(json);
			object = JSONObject.toBean(jsonObj, clazz);
		} catch (Exception e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
			throw new GateWayException(GWExceptionConstants.REQ0001_CODE, GWExceptionConstants.REQ0001_DESC + e.getMessage());
		}
		return (T) object;
	}

	/**
	 * Json字符串转Java对象, 多层次不带转义符的方式
	 * 
	 * @param jsonData
	 *            Json字符串
	 * @param clazz
	 *            对象类型
	 * @return
	 * @throws GateWayException
	 */
	public <T> T json2Object(String jsonData, Class<T> clazz) throws GateWayException {
		try {
			return mapper.readValue(jsonData, clazz);
		} catch (Exception e) {
			throw new GateWayException(GWExceptionConstants.REQ0001_CODE, GWExceptionConstants.REQ0001_DESC + e.getMessage());
		}
	}

	/**
	 * Java对象转Json字符串, 多层次带转义符
	 * 
	 * @param obj
	 * @param classz
	 * @return
	 * @throws GateWayException
	 */
	public String object2JsonBase(Object obj, Class<?> classz) throws GateWayException {
		String jsonString = "";
		try {
			JSONObject json = JSONObject.fromObject(obj);
			jsonString = json.toString();
		} catch (Exception e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
			throw new GateWayException(GWExceptionConstants.REQ0001_CODE, GWExceptionConstants.REQ0001_DESC + e.getMessage());
		}
		return jsonString;
	}

	/**
	 * Java对象转Json字符串, 多层次不带转义符
	 * 
	 * @param data
	 * @return
	 * @throws GateWayException
	 */
	public String object2Json(Object data) throws GateWayException {
		try {
			TokenBuffer buffer = new TokenBuffer(mapper);
			mapper.writeValue(buffer, data);
			return mapper.readTree(buffer.asParser()).toString();
		} catch (Exception e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
			throw new GateWayException(GWExceptionConstants.REQ0001_CODE, GWExceptionConstants.REQ0001_DESC + e.getMessage());
		}
	}

	public Object xmlToJaveObject(String message, Class<?> classz) throws GateWayException {
		InputStream stream = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
			dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			dbf.setExpandEntityReferences(false);
			DocumentBuilder db = dbf.newDocumentBuilder();
			stream = new ByteArrayInputStream(message.getBytes("UTF-8"));
			Document document = db.parse(stream);
			JAXBContext context = JAXBContext.newInstance(classz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object obj = (Object) unmarshaller.unmarshal(document);
			return obj;
		} catch (Exception e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
			throw new GateWayException(GWExceptionConstants.REQ0001_CODE, GWExceptionConstants.REQ0001_DESC + e.getMessage());
		} finally {
			if (null != stream) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public String javaObjectToXml(Object obj, Class<?> classz) throws GateWayException {
		StringWriter sw = null;
		try {
			JAXBContext context = JAXBContext.newInstance(classz);
			Marshaller marshaller = context.createMarshaller();
			sw = new StringWriter();
			marshaller.marshal(obj, sw);
			String str = sw.toString();
			return str.replaceAll(" standalone=\"yes\"", "");
		} catch (Exception e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
			throw new GateWayException(GWExceptionConstants.REQ0001_CODE, GWExceptionConstants.REQ0001_DESC + e.getMessage());
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
