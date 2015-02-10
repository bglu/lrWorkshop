package de.lineas.training.publisher.service.impl;

import java.util.List;

import com.liferay.portal.kernel.exception.SystemException;

import de.lineas.training.publisher.model.Entry;
import de.lineas.training.publisher.service.base.EntryLocalServiceBaseImpl;

/**
 * The implementation of the entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.lineas.training.publisher.service.EntryLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see de.lineas.training.publisher.service.base.EntryLocalServiceBaseImpl
 * @see de.lineas.training.publisher.service.EntryLocalServiceUtil
 */
public class EntryLocalServiceImpl extends EntryLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link de.lineas.training.publisher.service.EntryLocalServiceUtil} to access the entry local service.
     */
	
	public List<Entry> findByGroupId(long groupId) throws SystemException {
		return entryPersistence.findByGroupId(groupId);
	}
	
	public Entry addEntry(long groupId, String txt) throws SystemException {
		Entry e = null;
		
		long entryId = counterLocalService.increment();
		
		e = entryPersistence.create(entryId);
		e.setGroupId(groupId);
		e.setTxt(txt);
		
		e = entryPersistence.update(e);
		
		return e;
	}
	
	public Entry addEntry(String uuid, long groupId, String txt) throws SystemException {
		Entry e = null;
		
		long entryId = counterLocalService.increment();
		
		e = entryPersistence.create(entryId);
		e.setUuid(uuid);
		e.setGroupId(groupId);
		e.setTxt(txt);
		
		e = entryPersistence.update(e);
		
		return e;
	}
}
