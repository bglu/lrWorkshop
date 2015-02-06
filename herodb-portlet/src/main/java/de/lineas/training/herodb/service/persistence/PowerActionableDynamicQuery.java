package de.lineas.training.herodb.service.persistence;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import de.lineas.training.herodb.model.Power;
import de.lineas.training.herodb.service.PowerLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public abstract class PowerActionableDynamicQuery
    extends BaseActionableDynamicQuery {
    public PowerActionableDynamicQuery() throws SystemException {
        setBaseLocalService(PowerLocalServiceUtil.getService());
        setClass(Power.class);

        setClassLoader(de.lineas.training.herodb.service.ClpSerializer.class.getClassLoader());

        setPrimaryKeyPropertyName("PowerId");
    }
}
