package de.lineas.training.herodb.service.persistence;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import de.lineas.training.herodb.NoSuchPowerException;
import de.lineas.training.herodb.model.Power;
import de.lineas.training.herodb.model.impl.PowerImpl;
import de.lineas.training.herodb.model.impl.PowerModelImpl;
import de.lineas.training.herodb.service.persistence.PowerPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the power service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PowerPersistence
 * @see PowerUtil
 * @generated
 */
public class PowerPersistenceImpl extends BasePersistenceImpl<Power>
    implements PowerPersistence {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. Always use {@link PowerUtil} to access the power persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
     */
    public static final String FINDER_CLASS_NAME_ENTITY = PowerImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
        ".List1";
    public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
        ".List2";
    public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PowerModelImpl.ENTITY_CACHE_ENABLED,
            PowerModelImpl.FINDER_CACHE_ENABLED, PowerImpl.class,
            FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PowerModelImpl.ENTITY_CACHE_ENABLED,
            PowerModelImpl.FINDER_CACHE_ENABLED, PowerImpl.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PowerModelImpl.ENTITY_CACHE_ENABLED,
            PowerModelImpl.FINDER_CACHE_ENABLED, Long.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
    private static final String _SQL_SELECT_POWER = "SELECT power FROM Power power";
    private static final String _SQL_COUNT_POWER = "SELECT COUNT(power) FROM Power power";
    private static final String _ORDER_BY_ENTITY_ALIAS = "power.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Power exists with the primary key ";
    private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
                PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
    private static Log _log = LogFactoryUtil.getLog(PowerPersistenceImpl.class);
    private static Power _nullPower = new PowerImpl() {
            @Override
            public Object clone() {
                return this;
            }

            @Override
            public CacheModel<Power> toCacheModel() {
                return _nullPowerCacheModel;
            }
        };

    private static CacheModel<Power> _nullPowerCacheModel = new CacheModel<Power>() {
            @Override
            public Power toEntityModel() {
                return _nullPower;
            }
        };

    public PowerPersistenceImpl() {
        setModelClass(Power.class);
    }

    /**
     * Caches the power in the entity cache if it is enabled.
     *
     * @param power the power
     */
    @Override
    public void cacheResult(Power power) {
        EntityCacheUtil.putResult(PowerModelImpl.ENTITY_CACHE_ENABLED,
            PowerImpl.class, power.getPrimaryKey(), power);

        power.resetOriginalValues();
    }

    /**
     * Caches the powers in the entity cache if it is enabled.
     *
     * @param powers the powers
     */
    @Override
    public void cacheResult(List<Power> powers) {
        for (Power power : powers) {
            if (EntityCacheUtil.getResult(PowerModelImpl.ENTITY_CACHE_ENABLED,
                        PowerImpl.class, power.getPrimaryKey()) == null) {
                cacheResult(power);
            } else {
                power.resetOriginalValues();
            }
        }
    }

    /**
     * Clears the cache for all powers.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache() {
        if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
            CacheRegistryUtil.clear(PowerImpl.class.getName());
        }

        EntityCacheUtil.clearCache(PowerImpl.class.getName());

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }

    /**
     * Clears the cache for the power.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache(Power power) {
        EntityCacheUtil.removeResult(PowerModelImpl.ENTITY_CACHE_ENABLED,
            PowerImpl.class, power.getPrimaryKey());

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }

    @Override
    public void clearCache(List<Power> powers) {
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

        for (Power power : powers) {
            EntityCacheUtil.removeResult(PowerModelImpl.ENTITY_CACHE_ENABLED,
                PowerImpl.class, power.getPrimaryKey());
        }
    }

    /**
     * Creates a new power with the primary key. Does not add the power to the database.
     *
     * @param PowerId the primary key for the new power
     * @return the new power
     */
    @Override
    public Power create(long PowerId) {
        Power power = new PowerImpl();

        power.setNew(true);
        power.setPrimaryKey(PowerId);

        return power;
    }

    /**
     * Removes the power with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param PowerId the primary key of the power
     * @return the power that was removed
     * @throws de.lineas.training.herodb.NoSuchPowerException if a power with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Power remove(long PowerId)
        throws NoSuchPowerException, SystemException {
        return remove((Serializable) PowerId);
    }

    /**
     * Removes the power with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the power
     * @return the power that was removed
     * @throws de.lineas.training.herodb.NoSuchPowerException if a power with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Power remove(Serializable primaryKey)
        throws NoSuchPowerException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Power power = (Power) session.get(PowerImpl.class, primaryKey);

            if (power == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
                }

                throw new NoSuchPowerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    primaryKey);
            }

            return remove(power);
        } catch (NoSuchPowerException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    @Override
    protected Power removeImpl(Power power) throws SystemException {
        power = toUnwrappedModel(power);

        Session session = null;

        try {
            session = openSession();

            if (!session.contains(power)) {
                power = (Power) session.get(PowerImpl.class,
                        power.getPrimaryKeyObj());
            }

            if (power != null) {
                session.delete(power);
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        if (power != null) {
            clearCache(power);
        }

        return power;
    }

    @Override
    public Power updateImpl(de.lineas.training.herodb.model.Power power)
        throws SystemException {
        power = toUnwrappedModel(power);

        boolean isNew = power.isNew();

        Session session = null;

        try {
            session = openSession();

            if (power.isNew()) {
                session.save(power);

                power.setNew(false);
            } else {
                session.merge(power);
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

        if (isNew) {
            FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
        }

        EntityCacheUtil.putResult(PowerModelImpl.ENTITY_CACHE_ENABLED,
            PowerImpl.class, power.getPrimaryKey(), power);

        return power;
    }

    protected Power toUnwrappedModel(Power power) {
        if (power instanceof PowerImpl) {
            return power;
        }

        PowerImpl powerImpl = new PowerImpl();

        powerImpl.setNew(power.isNew());
        powerImpl.setPrimaryKey(power.getPrimaryKey());

        powerImpl.setPowerId(power.getPowerId());
        powerImpl.setPowerName(power.getPowerName());
        powerImpl.setPowerDescription(power.getPowerDescription());

        return powerImpl;
    }

    /**
     * Returns the power with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the power
     * @return the power
     * @throws de.lineas.training.herodb.NoSuchPowerException if a power with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Power findByPrimaryKey(Serializable primaryKey)
        throws NoSuchPowerException, SystemException {
        Power power = fetchByPrimaryKey(primaryKey);

        if (power == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
            }

            throw new NoSuchPowerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                primaryKey);
        }

        return power;
    }

    /**
     * Returns the power with the primary key or throws a {@link de.lineas.training.herodb.NoSuchPowerException} if it could not be found.
     *
     * @param PowerId the primary key of the power
     * @return the power
     * @throws de.lineas.training.herodb.NoSuchPowerException if a power with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Power findByPrimaryKey(long PowerId)
        throws NoSuchPowerException, SystemException {
        return findByPrimaryKey((Serializable) PowerId);
    }

    /**
     * Returns the power with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the power
     * @return the power, or <code>null</code> if a power with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Power fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        Power power = (Power) EntityCacheUtil.getResult(PowerModelImpl.ENTITY_CACHE_ENABLED,
                PowerImpl.class, primaryKey);

        if (power == _nullPower) {
            return null;
        }

        if (power == null) {
            Session session = null;

            try {
                session = openSession();

                power = (Power) session.get(PowerImpl.class, primaryKey);

                if (power != null) {
                    cacheResult(power);
                } else {
                    EntityCacheUtil.putResult(PowerModelImpl.ENTITY_CACHE_ENABLED,
                        PowerImpl.class, primaryKey, _nullPower);
                }
            } catch (Exception e) {
                EntityCacheUtil.removeResult(PowerModelImpl.ENTITY_CACHE_ENABLED,
                    PowerImpl.class, primaryKey);

                throw processException(e);
            } finally {
                closeSession(session);
            }
        }

        return power;
    }

    /**
     * Returns the power with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param PowerId the primary key of the power
     * @return the power, or <code>null</code> if a power with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public Power fetchByPrimaryKey(long PowerId) throws SystemException {
        return fetchByPrimaryKey((Serializable) PowerId);
    }

    /**
     * Returns all the powers.
     *
     * @return the powers
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<Power> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
    @Override
    public List<Power> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
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
    @Override
    public List<Power> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        boolean pagination = true;
        FinderPath finderPath = null;
        Object[] finderArgs = null;

        if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
                (orderByComparator == null)) {
            pagination = false;
            finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
            finderArgs = FINDER_ARGS_EMPTY;
        } else {
            finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
            finderArgs = new Object[] { start, end, orderByComparator };
        }

        List<Power> list = (List<Power>) FinderCacheUtil.getResult(finderPath,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_POWER);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_POWER;

                if (pagination) {
                    sql = sql.concat(PowerModelImpl.ORDER_BY_JPQL);
                }
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (!pagination) {
                    list = (List<Power>) QueryUtil.list(q, getDialect(), start,
                            end, false);

                    Collections.sort(list);

                    list = new UnmodifiableList<Power>(list);
                } else {
                    list = (List<Power>) QueryUtil.list(q, getDialect(), start,
                            end);
                }

                cacheResult(list);

                FinderCacheUtil.putResult(finderPath, finderArgs, list);
            } catch (Exception e) {
                FinderCacheUtil.removeResult(finderPath, finderArgs);

                throw processException(e);
            } finally {
                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Removes all the powers from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void removeAll() throws SystemException {
        for (Power power : findAll()) {
            remove(power);
        }
    }

    /**
     * Returns the number of powers.
     *
     * @return the number of powers
     * @throws SystemException if a system exception occurred
     */
    @Override
    public int countAll() throws SystemException {
        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
                FINDER_ARGS_EMPTY, this);

        if (count == null) {
            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(_SQL_COUNT_POWER);

                count = (Long) q.uniqueResult();

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
                    FINDER_ARGS_EMPTY, count);
            } catch (Exception e) {
                FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
                    FINDER_ARGS_EMPTY);

                throw processException(e);
            } finally {
                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Initializes the power persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.de.lineas.training.herodb.model.Power")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Power>> listenersList = new ArrayList<ModelListener<Power>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Power>) InstanceFactory.newInstance(
                            getClassLoader(), listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(PowerImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }
}
