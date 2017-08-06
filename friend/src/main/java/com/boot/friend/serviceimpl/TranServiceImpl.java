package com.boot.friend.serviceimpl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.boot.friend.domain.enums.errorcode.CommonErrorCode;
import com.boot.friend.domain.enums.errorcode.TranMappPageEnmu;
import com.boot.friend.service.TranService;
import com.boot.friend.untils.StringUtil;

/**
 * 
 * @description <p>
 *              外部访问入口
 *              </p>
 * @author 213li
 * @date 2017年8月6日 下午5:27:51
 * @update
 */
@Service
public class TranServiceImpl implements TranService {

	private static final Logger log = LoggerFactory.getLogger(TranServiceImpl.class);
	/**
	 * Error Attributes in the Application
	 */
	@Autowired
	private ErrorAttributes errorAttributes;

	@Override
	public ModelAndView tranService(HttpServletRequest request) {
		log.info("<----------开始-----接收外部访问---------->");
		String trancode = request.getParameter("trancode");// 根据不同交易码跳转不同页面

		if (StringUtil.isEmpty(trancode)) {
			log.info(CommonErrorCode.E000000001.errormsg);
			log.info("<----------结束-----接收外部访问---------->");
			return new ModelAndView("greeting", getErrorAttributes(request, CommonErrorCode.E000000001.errorcode,
					CommonErrorCode.E000000001.errormsg));
		}
		String trandata = request.getParameter("trandata");
		if (StringUtil.isEmpty(trandata)) {
			log.info(CommonErrorCode.E000000002.errormsg);
			log.info("<----------结束-----接收外部访问---------->");
			return new ModelAndView("greeting", getErrorAttributes(request, CommonErrorCode.E000000002.errorcode,
					CommonErrorCode.E000000002.errormsg));
		}
		// 根据不同交易码调到不同页面 
		String viewName = null;
		try {
			viewName = TranMappPageEnmu.getData().get(trancode).pageurl;
		} catch (Exception e) {
			log.info(CommonErrorCode.E000000004.errormsg);
			log.info("<----------结束-----接收外部访问---------->");
			return new ModelAndView("greeting", getErrorAttributes(request, CommonErrorCode.E000000004.errorcode,
					CommonErrorCode.E000000004.errormsg));
		}

		ModelAndView modelAndView = new ModelAndView(viewName);
		modelAndView.addObject("trandata", trandata);
		log.info("<----------结束-----接收外部访问---------->");
		return null;
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, String error, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("message", message);
		map.put("error", error);
		map.put("status", 200);
		map.put("timestamp", System.currentTimeMillis());
		String URL = request.getRequestURL().toString();
		map.put("URL", URL);
		log.debug(map.toString());
		return map;
	}

}
