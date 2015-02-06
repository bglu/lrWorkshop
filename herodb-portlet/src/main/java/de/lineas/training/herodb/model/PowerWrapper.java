package de.lineas.training.herodb.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Power}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Power
 * @generated
 */
public class PowerWrapper implements Power, ModelWrapper<Power> {
    private Power _power;

    public PowerWrapper(Power power) {
        _power = power;
    }

    @Override
    public Class<?> getModelClass() {
        return Power.class;
    }

    @Override
    public String getModelClassName() {
        return Power.class.getName();
    }

    @Override
    public Map<String, Object> getModelAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("PowerId", getPowerId());
        attributes.put("PowerName", getPowerName());
        attributes.put("PowerDescription", getPowerDescription());

        return attributes;
    }

    @Override
    public void setModelAttributes(Map<String, Object> attributes) {
        Long PowerId = (Long) attributes.get("PowerId");

        if (PowerId != null) {
            setPowerId(PowerId);
        }

        String PowerName = (String) attributes.get("PowerName");

        if (PowerName != null) {
            setPowerName(PowerName);
        }

        String PowerDescription = (String) attributes.get("PowerDescription");

        if (PowerDescription != null) {
            setPowerDescription(PowerDescription);
        }
    }

    /**
    * Returns the primary key of this power.
    *
    * @return the primary key of this power
    */
    @Override
    public long getPrimaryKey() {
        return _power.getPrimaryKey();
    }

    /**
    * Sets the primary key of this power.
    *
    * @param primaryKey the primary key of this power
    */
    @Override
    public void setPrimaryKey(long primaryKey) {
        _power.setPrimaryKey(primaryKey);
    }

    /**
    * Returns the power ID of this power.
    *
    * @return the power ID of this power
    */
    @Override
    public long getPowerId() {
        return _power.getPowerId();
    }

    /**
    * Sets the power ID of this power.
    *
    * @param PowerId the power ID of this power
    */
    @Override
    public void setPowerId(long PowerId) {
        _power.setPowerId(PowerId);
    }

    /**
    * Returns the power name of this power.
    *
    * @return the power name of this power
    */
    @Override
    public java.lang.String getPowerName() {
        return _power.getPowerName();
    }

    /**
    * Sets the power name of this power.
    *
    * @param PowerName the power name of this power
    */
    @Override
    public void setPowerName(java.lang.String PowerName) {
        _power.setPowerName(PowerName);
    }

    /**
    * Returns the power description of this power.
    *
    * @return the power description of this power
    */
    @Override
    public java.lang.String getPowerDescription() {
        return _power.getPowerDescription();
    }

    /**
    * Sets the power description of this power.
    *
    * @param PowerDescription the power description of this power
    */
    @Override
    public void setPowerDescription(java.lang.String PowerDescription) {
        _power.setPowerDescription(PowerDescription);
    }

    @Override
    public boolean isNew() {
        return _power.isNew();
    }

    @Override
    public void setNew(boolean n) {
        _power.setNew(n);
    }

    @Override
    public boolean isCachedModel() {
        return _power.isCachedModel();
    }

    @Override
    public void setCachedModel(boolean cachedModel) {
        _power.setCachedModel(cachedModel);
    }

    @Override
    public boolean isEscapedModel() {
        return _power.isEscapedModel();
    }

    @Override
    public java.io.Serializable getPrimaryKeyObj() {
        return _power.getPrimaryKeyObj();
    }

    @Override
    public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
        _power.setPrimaryKeyObj(primaryKeyObj);
    }

    @Override
    public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
        return _power.getExpandoBridge();
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portal.model.BaseModel<?> baseModel) {
        _power.setExpandoBridgeAttributes(baseModel);
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
        _power.setExpandoBridgeAttributes(expandoBridge);
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portal.service.ServiceContext serviceContext) {
        _power.setExpandoBridgeAttributes(serviceContext);
    }

    @Override
    public java.lang.Object clone() {
        return new PowerWrapper((Power) _power.clone());
    }

    @Override
    public int compareTo(de.lineas.training.herodb.model.Power power) {
        return _power.compareTo(power);
    }

    @Override
    public int hashCode() {
        return _power.hashCode();
    }

    @Override
    public com.liferay.portal.model.CacheModel<de.lineas.training.herodb.model.Power> toCacheModel() {
        return _power.toCacheModel();
    }

    @Override
    public de.lineas.training.herodb.model.Power toEscapedModel() {
        return new PowerWrapper(_power.toEscapedModel());
    }

    @Override
    public de.lineas.training.herodb.model.Power toUnescapedModel() {
        return new PowerWrapper(_power.toUnescapedModel());
    }

    @Override
    public java.lang.String toString() {
        return _power.toString();
    }

    @Override
    public java.lang.String toXmlString() {
        return _power.toXmlString();
    }

    @Override
    public void persist()
        throws com.liferay.portal.kernel.exception.SystemException {
        _power.persist();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PowerWrapper)) {
            return false;
        }

        PowerWrapper powerWrapper = (PowerWrapper) obj;

        if (Validator.equals(_power, powerWrapper._power)) {
            return true;
        }

        return false;
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
     */
    public Power getWrappedPower() {
        return _power;
    }

    @Override
    public Power getWrappedModel() {
        return _power;
    }

    @Override
    public void resetOriginalValues() {
        _power.resetOriginalValues();
    }
}
