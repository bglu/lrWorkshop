package de.lineas.training.publisher.service.persistence;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import de.lineas.training.publisher.model.Entry;
import de.lineas.training.publisher.service.EntryLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class EntryActionableDynamicQuery
    extends BaseActionableDynamicQuery {
    public EntryActionableDynamicQuery() throws SystemException {
        setBaseLocalService(EntryLocalServiceUtil.getService());
        setClass(Entry.class);

        setClassLoader(de.lineas.training.publisher.service.ClpSerializer.class.getClassLoader());

        setPrimaryKeyPropertyName("entryId");
    }
}
