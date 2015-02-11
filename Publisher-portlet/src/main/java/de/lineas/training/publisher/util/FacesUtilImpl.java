package de.lineas.training.publisher.util;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.liferay.faces.util.portal.WebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;

@ManagedBean(name="facesUtil", eager=true)
@ApplicationScoped
public class FacesUtilImpl implements FacesUtil, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ThemeDisplay getThemeDisplay() {
		FacesContext 		currentInstance = FacesContext.getCurrentInstance();
		ExternalContext 	externalContext = currentInstance.getExternalContext();
		Map<String, Object> requestMap 		= externalContext.getRequestMap();
		ThemeDisplay 		td 				= (ThemeDisplay) requestMap.get(WebKeys.THEME_DISPLAY);
		
		return td;
	}
	
	public Group getGroup() {
		ThemeDisplay 	td 		= getThemeDisplay();
		Layout 			layout 	= td.getLayout();
		Group 			group 	= null;
		
		try {
			group = layout.getGroup();
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		return group;
	}

	@Override
	public boolean isStagingGroup() {
		return getGroup().isStagingGroup();
	}

}
