package com.rd.monitoring.commands;

import info.magnolia.cms.beans.config.ServerConfiguration;
import info.magnolia.commands.impl.BaseRepositoryCommand;
import info.magnolia.context.Context;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.objectfactory.Components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rd.monitoring.utils.MonitoringUtils;

public class RetrieveDataFromServerCmd extends BaseRepositoryCommand {

	private static final Logger log = LoggerFactory.getLogger(RetrieveDataFromServerCmd.class);

	private static MonitoringUtils mUtils = new MonitoringUtils();

	private static boolean isRunning;

	public RetrieveDataFromServerCmd() {
		super();
		isRunning = false;
	}

	@Override
	public boolean execute(Context arg0) throws Exception {

		if (!isRunning) {
			isRunning = true;
			boolean isAdmin = Components.getComponent(ServerConfiguration.class).isAdmin();
			try {

				if (isAdmin) {
					mUtils.printServerData();
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			isRunning = false;
		}
		return true;
	}

}
