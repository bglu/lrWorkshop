package de.lineas.training.publisher.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;

import de.lineas.training.publisher.model.Entry;
import de.lineas.training.publisher.service.EntryLocalService;
import de.lineas.training.publisher.service.EntryLocalServiceUtil;
import de.lineas.training.publisher.util.FacesUtil;
import de.lineas.training.publisher.util.FacesUtilImpl;

import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class EntryBean {
	
	@ManagedProperty(value = "#{facesUtil}")
	private FacesUtil facesUtil;
	
	private EntryLocalService entryService;

	
	private String test = "Hello";
	private List<Entry> entries = null;
	private Entry entry = null;
	
	private boolean editMode = false;
	
	public EntryBean() {
		initDi();
		loadEntries();
	}
	
	public EntryBean(FacesUtil facesUtil, EntryLocalService entryService) {
		setFacesUtil(facesUtil);
		setEntryService(entryService);
		loadEntries();
	}

	
	//-- Action Listeners
	
	public String handleEdit() {
		toggleEditMode();
		return "";
	}
	
	
	public String handleSave() {
		try {
			entryService.updateEntry(entry);
			
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		toggleEditMode();
		loadEntries();
		
		return "";
	}
	public String handleCancel() {
		toggleEditMode();
		return "";
	}

	
	
	//-- Helpers
	private void initDi() {
		if (facesUtil == null) {
			facesUtil = new FacesUtilImpl();
		}
		if (entryService == null) {
			entryService = EntryLocalServiceUtil.getService();
		}
	}
	
	private void loadEntries(){
		Group group = facesUtil.getGroup();
		try {
			entries = entryService.findByGroupId(group.getGroupId());
			if (entries.isEmpty()) {
				Entry dummyEntry = entryService.addEntry(group.getGroupId(), "");
				entries = new ArrayList<Entry>();
				entries.add(dummyEntry);
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		entry = entries.get(0);
		
	}


	
	
	//-- Getters and setters

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public List<Entry> getEntries() {
		return entries;
	}


	public FacesUtil getFacesUtil() {
		return facesUtil;
	}


	public void setFacesUtil(FacesUtil facesUtil) {
		if (facesUtil != null) {
			this.facesUtil = facesUtil;
		} 
	}

	public EntryLocalService getEntryService() {
		return entryService;
	}

	public void setEntryService(EntryLocalService entryService) {
		this.entryService = entryService;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
	
	public void toggleEditMode(){
		setEditMode(! isEditMode());
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}
	

}
