package de.lineas.training.publisher.staging;

import java.util.List;

import javax.portlet.PortletPreferences;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;

import de.lineas.training.publisher.model.Entry;
import de.lineas.training.publisher.model.EntryClp;
import de.lineas.training.publisher.service.EntryLocalServiceUtil;



public class EntryDataHandler extends BasePortletDataHandler {
	
	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {
		
		long groupId = portletDataContext.getScopeGroupId();
		System.out.println("Exporting from group with id: " + groupId);
		List<Entry> entries = EntryLocalServiceUtil.findByGroupId(groupId);
		JSONArray jsonEntires = JSONFactoryUtil.createJSONArray();
		
		for (Entry e : entries) {
			JSONObject jsonEntry = JSONFactoryUtil.createJSONObject();
			jsonEntry.put("uuid", e.getUuid());
			jsonEntry.put("txt", e.getTxt());
			
			jsonEntires.put(jsonEntry);
		}
		

		return jsonEntires.toString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {
		
		
		long groupId = portletDataContext.getScopeGroupId();
		deleteOldEntries(groupId);
		
		System.out.println("Importing into group with id: " + groupId);
		
		JSONArray jsonEntires = JSONFactoryUtil.createJSONArray(data);
		
		for (int i=0; i < jsonEntires.length(); i++) {
			JSONObject jsonEntry = jsonEntires.getJSONObject(i);
			
			String uuid =jsonEntry.getString("uuid");
			String txt = jsonEntry.getString("txt");
			
			//see if entry already exists
			Entry previous = EntryLocalServiceUtil.fetchEntryByUuidAndGroupId(uuid, groupId);
			if (previous != null) {
				System.out.println("Updating entry with uuid: " + uuid);
				previous.setTxt(txt);
				EntryLocalServiceUtil.updateEntry(previous);
			} else {
				System.out.println("Creating new entry with uuid: " + uuid);
				EntryLocalServiceUtil.addEntry(uuid, groupId, txt);
			}
			
		}

		return portletPreferences;
	}
	
	private void deleteOldEntries(long groupId) {
		System.out.println("Deleting all entries from group: " + groupId);
		try {
			List<Entry> entries = EntryLocalServiceUtil.findByGroupId(groupId);
			for (Entry e : entries) {
				e = EntryLocalServiceUtil.deleteEntry(e);
				System.out.println("Entry deleted: " + e.getUuid());
			}
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
