package de.lineas.training.herodb.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import de.lineas.training.herodb.model.Power;

import java.util.List;

/**
 * The persistence utility for the power service. This utility wraps {@link PowerPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PowerPersistence
 * @see PowerPersistenceImpl
 * @generated
 */
public class PowerUtil {
    private static PowerPersistence _persistence;

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
     */

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
     */
    public static void clearCache() {
        getPersistence().clearCache();
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
     */
    public static void clearCache(Power power) {
        getPersistence().clearCache(power);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
     */
    public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
        throws SystemException {
        return getPersistence().countWithDynamicQuery(dynamicQuery);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
     */
    public static List<Power> findWithDynamicQuery(DynamicQuery dynamicQuery)
        throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
     */
    public static List<Power> findWithDynamicQuery(DynamicQuery dynamicQuery,
        int start, int end) throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
     */
    public static List<Power> findWithDynamicQuery(DynamicQuery dynamicQuery,
        int start, int end, OrderByComparator orderByComparator)
        throws SystemException {
        return getPersistence()
                   .findWithDynamicQuery(dynamicQuery, start, end,
            orderByComparator);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
     */
    public static Power update(Power power) throws SystemException {
        return getPersistence().update(power);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
     */
    public static Power update(Power power, ServiceContext serviceContext)
        throws SystemException {
        return getPersistence().update(power, serviceContext);
    }

    /**
    * Caches the power in the entity cache if it is enabled.
    *
    * @param power the power
    */
    public static void cacheResult(de.lineas.training.herodb.model.Power power) {
        getPersistence().cacheResult(power);
    }

    /**
    * Caches the powers in the entity cache if it is enabled.
    *
    * @param powers the powers
    */
    public static void cacheResult(
        java.util.List<de.lineas.training.herodb.model.Power> powers) {
        getPersistence().cacheResult(powers);
    }

    /**
    * Creates a new power with the primary key. Does not add the power to the database.
    *
    * @param PowerId the primary key for the new power
    * @return the new power
    */
    public static de.lineas.training.herodb.model.Power create(long PowerId) {
        return getPersistence().create(PowerId);
    }

    /**
    * Removes the power with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param PowerId the primary key of the power
    * @return the power that was removed
    * @throws de.lineas.training.herodb.NoSuchPowerException if a power with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static de.lineas.training.herodb.model.Power remove(long PowerId)
        throws com.liferay.portal.kernel.exception.SystemException,
            de.lineas.training.herodb.NoSuchPowerException {
        return getPersistence().remove(PowerId);
    }

    public static de.lineas.training.herodb.model.Power updateImpl(
        de.lineas.training.herodb.model.Power power)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().updateImpl(power);
    }

    /**
    * Returns the power with the primary key or throws a {@link de.lineas.training.herodb.NoSuchPowerException} if it could not be found.
    *
    * @param PowerId the primary key of the power
    * @return the power
    * @throws de.lineas.training.herodb.NoSuchPowerException if a power with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static de.lineas.training.herodb.model.Power findByPrimaryKey(
        long PowerId)
        throws com.liferay.portal.kernel.exception.SystemException,
            de.lineas.training.herodb.NoSuchPowerException {
        return getPersistence().findByPrimaryKey(PowerId);
    }

    /**
    * Returns the power with the primary key or returns <code>null</code> if it could not be found.
    *
    * @param PowerId the primary key of the power
    * @return the power, or <code>null</code> if a power with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static de.lineas.training.herodb.model.Power fetchByPrimaryKey(
        long PowerId)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().fetchByPrimaryKey(PowerId);
    }

    /**
    * Returns all the powers.
    *
    * @return the powers
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<de.lineas.training.herodb.model.Power> findAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll();
    }

    /**
    * Returns a range of all the powers.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.lineas.training.herodb.model.impl.PowerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param start the lower bound of the range of powers
    * @param end the upper bound of the range of powers (not inclusive)
    * @return the range of powers
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<de.lineas.training.herodb.model.Power> findAll(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll(start, end);
    }

    /**
    * Returns an ordered range of all the powers.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.lineas.training.herodb.model.impl.PowerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param start the lower bound of the range of powers
    * @param end the upper bound of the range of powers (not inclusive)
    * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
    * @return the ordered range of powers
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<de.lineas.training.herodb.model.Power> findAll(
        int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll(start, end, orderByComparator);
    }

    /**
    * Removes all the powers from the database.
    *
    * @throws SystemException if a system exception occurred
    */
    public static void removeAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        getPersistence().removeAll();
    }

    /**
    * Returns the number of powers.
    *
    * @return the number of powers
    * @throws SystemException if a system exception occurred
    */
    public static int countAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().countAll();
    }

    public static PowerPersistence getPersistence() {
        if (_persistence == null) {
            _persistence = (PowerPersistence) PortletBeanLocatorUtil.locate(de.lineas.training.herodb.service.ClpSerializer.getServletContextName(),
                    PowerPersistence.class.getName());

            ReferenceRegistry.registerReference(PowerUtil.class, "_persistence");
        }

        return _persistence;
    }

    /**
     * @deprecated As of 6.2.0
     */
    public void setPersistence(PowerPersistence persistence) {
    }
}
