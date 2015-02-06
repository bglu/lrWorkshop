package de.lineas.training.herodb.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.icefaces.ace.component.selectmenu.ISelectMenu;
import org.icefaces.ace.event.SelectEvent;
import org.icefaces.ace.event.UnselectEvent;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import de.lineas.training.herodb.model.Power;
import de.lineas.training.herodb.service.PowerLocalServiceUtil;

import java.util.List;
import java.util.ArrayList;

@ManagedBean
@SessionScoped
public class PowerBean {
	
	//properties
	
	
	private List<Power> powers = new ArrayList<Power>();
	
	private String powerId;
	private String powerName;
	private String powerDescription;
	
	private Power selectedPower;
	
	
	//------  Constructor
	public PowerBean () {
		updatePowers();
	}
	
	//---- Action Listener
	public String addPower(){
		if (! powerName.isEmpty() && ! powerDescription.isEmpty()){
			Power p = PowerLocalServiceUtil.addPower(powerName, powerDescription);
			if (p != null) {
				System.out.println(String.format("Power added: %s", p.getPowerName()));
			} else {
				System.err.println(String.format("Power was NOT added: %s", powerName));
			}
		}
		
		updatePowers();
		
		//stay on this view
		return "";
	}
	
	public String editPower() {
		if (	! powerId.isEmpty() &&
				! powerName.isEmpty() &&
				! powerDescription.isEmpty()){
			
			Power p = PowerLocalServiceUtil.updatePower(Long.parseLong(powerId), powerName, powerDescription);
			
			if (p != null) {
				System.out.println(String.format("Power edited: %s", p.getPowerName()));
			} else {
				System.err.println(String.format("Power was NOT edited: %s", powerName));
			}
		}
		
		updatePowers();
		
		//stay on this view
		return "";
	}
	
	public String deletePower() {
		Power p = null;
		
		if (! powerId.isEmpty()) {
			try {
				p = PowerLocalServiceUtil.deletePower(Long.parseLong(powerId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		if (p!= null) {
			System.out.println(String.format("Power deleted: %s", p.getPowerName()));
		} else {
			System.err.println(String.format("Power was NOT deleted: %s", powerName));
		}
		
		updatePowers();
		
		//stay on this view
		return "";
	}
	
	
	public void selectListener(SelectEvent e) {
		selectedPower = (Power) e.getObject();
		
		if (selectedPower != null) {
			setPowerName(selectedPower.getPowerName());
			setPowerDescription(selectedPower.getPowerDescription());
			setPowerId(String.valueOf(selectedPower.getPowerId()));
		}
		
	}
	
	public void unselectListener(UnselectEvent e) {
		selectedPower = null;
		setPowerName("");
		setPowerDescription("");
		setPowerId("");
	}
	
	//---- Helpers
	private void updatePowers () {
		try {
			powers = PowerLocalServiceUtil.getPowers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}
	
	//--------- Getters and Setters

	public List<Power> getPowers() {
		return powers;
	}

	public String getPowerId() {
		return powerId;
	}

	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}

	public String getPowerName() {
		return powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}

	public String getPowerDescription() {
		return powerDescription;
	}

	public void setPowerDescription(String powerDescription) {
		this.powerDescription = powerDescription;
	}

	public Power getSelectedPower() {
		return selectedPower;
	}

}
