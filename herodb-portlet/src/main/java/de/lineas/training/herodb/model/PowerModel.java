package de.lineas.training.herodb.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the Power service. Represents a row in the &quot;hero_Power&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link de.lineas.training.herodb.model.impl.PowerModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link de.lineas.training.herodb.model.impl.PowerImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Power
 * @see de.lineas.training.herodb.model.impl.PowerImpl
 * @see de.lineas.training.herodb.model.impl.PowerModelImpl
 * @generated
 */
public interface PowerModel extends BaseModel<Power> {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this interface directly. All methods that expect a power model instance should use the {@link Power} interface instead.
     */

    /**
     * Returns the primary key of this power.
     *
     * @return the primary key of this power
     */
    public long getPrimaryKey();

    /**
     * Sets the primary key of this power.
     *
     * @param primaryKey the primary key of this power
     */
    public void setPrimaryKey(long primaryKey);

    /**
     * Returns the power ID of this power.
     *
     * @return the power ID of this power
     */
    public long getPowerId();

    /**
     * Sets the power ID of this power.
     *
     * @param PowerId the power ID of this power
     */
    public void setPowerId(long PowerId);

    /**
     * Returns the power name of this power.
     *
     * @return the power name of this power
     */
    @AutoEscape
    public String getPowerName();

    /**
     * Sets the power name of this power.
     *
     * @param PowerName the power name of this power
     */
    public void setPowerName(String PowerName);

    /**
     * Returns the power description of this power.
     *
     * @return the power description of this power
     */
    @AutoEscape
    public String getPowerDescription();

    /**
     * Sets the power description of this power.
     *
     * @param PowerDescription the power description of this power
     */
    public void setPowerDescription(String PowerDescription);

    @Override
    public boolean isNew();

    @Override
    public void setNew(boolean n);

    @Override
    public boolean isCachedModel();

    @Override
    public void setCachedModel(boolean cachedModel);

    @Override
    public boolean isEscapedModel();

    @Override
    public Serializable getPrimaryKeyObj();

    @Override
    public void setPrimaryKeyObj(Serializable primaryKeyObj);

    @Override
    public ExpandoBridge getExpandoBridge();

    @Override
    public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

    @Override
    public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

    @Override
    public void setExpandoBridgeAttributes(ServiceContext serviceContext);

    @Override
    public Object clone();

    @Override
    public int compareTo(Power power);

    @Override
    public int hashCode();

    @Override
    public CacheModel<Power> toCacheModel();

    @Override
    public Power toEscapedModel();

    @Override
    public Power toUnescapedModel();

    @Override
    public String toString();

    @Override
    public String toXmlString();
}
