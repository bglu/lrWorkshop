package de.lineas.training.taglib;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

public class UserTableTag extends SimpleTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {
		Template t = initVelocityTemplate();
	    VelocityContext ctx = new VelocityContext();

	    List<User> userList = getUserList();
	    ctx.put("userList", userList);

	    StringWriter writer = new StringWriter();
	    t.merge(ctx, writer);

	    JspWriter out = getJspContext().getOut();
	    out.write(writer.toString());
	}
	
	
	private List<User> getUserList() {
	    try {
	        return UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	    } catch (SystemException e) {
	        e.printStackTrace();
	        return Collections.emptyList();
	    }
	}
	
	private Template initVelocityTemplate() {
	    VelocityEngine ve = new VelocityEngine();
	    ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
	    ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
	    ve.init();
	    Template t = ve.getTemplate("templates/userTable.vm");
	    return t;
	}
}
