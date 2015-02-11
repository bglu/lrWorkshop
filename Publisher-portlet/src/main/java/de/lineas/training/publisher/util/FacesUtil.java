package de.lineas.training.publisher.util;

import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;

public interface FacesUtil {

	public ThemeDisplay getThemeDisplay();
	public Group getGroup();
	
	public boolean isStagingGroup();
}
