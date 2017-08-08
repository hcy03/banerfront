package com.cmbc.gateway.init;

//import com.msds.baseUtil.baseconfig.BaseConfig;
import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.core.config.Configurator;

@Slf4j
public class SystemInitListener implements ServletContextListener {

	//	String confFile = "baseConf.properties";
	String contextpath = "contextpath";
	String homeDir = "gwsr.dir";
	String dubbo = "dubbo.properties.file";

	//			gwsr.dir [/app/gateway]
	//			sys_config_path [config]
	//			configPath [/app/gateway/config]
	//			logConf [/app/gateway/config/log4j2.xml]
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		homeDir = System.getProperty(homeDir);
		log.info("gwsr.dir [" + homeDir + "]");
		String sys_config_path = sce.getServletContext().getInitParameter("sys_config_path");
		log.info("sys_config_path [" + sys_config_path + "]");
		//初始化配置
		String configPath = homeDir + File.separator + sys_config_path;
		log.info("configPath [" + configPath + "]");
		//		String config = configPath + File.separator + confFile;
		//		System.out.println("baseConfi file [" + config + "]");
		//		BaseConfig.getInstence(config);

		//初始化log4j2.xml配置文件路径
		String logConf = configPath + File.separator + "log4j2.xml";
		log.info("logConf [" + logConf + "]");
		Configurator.initialize("Log4j2", logConf);

		//初始化spring配置文件路径
		System.setProperty(contextpath, configPath);
		//初始化dubbo配置文件路径
		//		String bubboConf = configPath + File.separator + "dubbo.properties";
		//		log.info("bubboConf [" + bubboConf + "]");
		//		System.setProperty(dubbo, bubboConf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
