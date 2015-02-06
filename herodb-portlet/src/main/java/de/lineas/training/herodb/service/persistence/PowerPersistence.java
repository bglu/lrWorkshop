package de.lineas.training.herodb.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import de.lineas.training.herodb.model.Power;

/**
 * The persistence interface for the power service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PowerPersistenceImpl
 * @see PowerUtil
 * @generated
 */
public interface PowerPersistence extends BasePersistence<Power> {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this interface directly. Always use {@link PowerUtil} to access the power persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
     */

    /**
    * Caches the power in the entity cache if it is enabled.
    *
    * @param power the power
    */
    public void cacheResult(de.lineas.training.herodb.model.Power power);

    /**
    * Caches the powers in the entity cache if it is enabled.
    *
    * @param powers the powers
    */
    public void cacheResult(
        java.util.List<de.lineas.training.herodb.model.Power> powers);

    /**
    * Creates a new power with the primary key. Does not add the power to the database.
    *
    * @param PowerId the primary key for the new power
    * @return the new power
    */
    public de.lineas.training.herodb.model.Power create(long PowerId);

    /**
    * Removes the power with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param PowerId the primary key of the power
    * @return the power that was removed
    * @throws de.lineas.training.herodb.NoSuchPowerException if a power with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public de.lineas.training.herodb.model.Power remove(long PowerId)
        throws com.liferay.portal.kernel.exception.SystemException,
            de.lineas.training.herodb.NoSuchPowerException;

    public de.lineas.training.herodb.model.Power updateImpl(
        de.lineas.training.herodb.model.Power power)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the power with the primary key or throws a {@link de.lineas.training.herodb.NoSuchPowerException} if it could not be found.
    *
    * @param PowerId the primary key of the power
    * @return the power
    * @throws de.lineas.training.herodb.NoSuchPowerException if a power with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public de.lineas.training.herodb.model.Power findByPrimaryKey(long PowerId)
        throws com.liferay.portal.kernel.exception.SystemException,
            de.lineas.training.herodb.NoSuchPowerException;

    /**
    * Returns the power with the primary key or returns <code>null</code> if it could not be found.
    *
    * @param PowerId the primary key of the power
    * @return the power, or <code>null</code> if a power with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public de.lineas.training.herodb.model.Power fetchByPrimaryKey(long PowerId)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns all the powers.
    *
    * @return the powers
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<de.lineas.training.herodb.model.Power> findAll()
        throws com.liferay.portal.kernel.exception.SystemException;

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
    public java.util.List<de.lineas.training.herodb.model.Power> findAll(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException;

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
    public java.util.List<de.lineas.training.herodb.model.Power> findAll(
        int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Removes all the powers from the database.
    *
    * @throws SystemException if a system exception occurred
    */
    public void removeAll()
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the number of powers.
    *
    * @return the number of powers
    * @throws SystemException if a system exception occurred
    */
    public int countAll()
        throws com.liferay.portal.kernel.exception.SystemException;
}
