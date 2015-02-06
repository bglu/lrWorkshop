package de.lineas.training.herodb.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import de.lineas.training.herodb.service.ClpSerializer;
import de.lineas.training.herodb.service.PowerLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;


public class PowerClp extends BaseModelImpl<Power> implements Power {
    private long _PowerId;
    private String _PowerName;
    private String _PowerDescription;
    private BaseModel<?> _powerRemoteModel;

    public PowerClp() {
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
    public long getPrimaryKey() {
        return _PowerId;
    }

    @Override
    public void setPrimaryKey(long primaryKey) {
        setPowerId(primaryKey);
    }

    @Override
    public Serializable getPrimaryKeyObj() {
        return _PowerId;
    }

    @Override
    public void setPrimaryKeyObj(Serializable primaryKeyObj) {
        setPrimaryKey(((Long) primaryKeyObj).longValue());
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

    @Override
    public long getPowerId() {
        return _PowerId;
    }

    @Override
    public void setPowerId(long PowerId) {
        _PowerId = PowerId;

        if (_powerRemoteModel != null) {
            try {
                Class<?> clazz = _powerRemoteModel.getClass();

                Method method = clazz.getMethod("setPowerId", long.class);

                method.invoke(_powerRemoteModel, PowerId);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public String getPowerName() {
        return _PowerName;
    }

    @Override
    public void setPowerName(String PowerName) {
        _PowerName = PowerName;

        if (_powerRemoteModel != null) {
            try {
                Class<?> clazz = _powerRemoteModel.getClass();

                Method method = clazz.getMethod("setPowerName", String.class);

                method.invoke(_powerRemoteModel, PowerName);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public String getPowerDescription() {
        return _PowerDescription;
    }

    @Override
    public void setPowerDescription(String PowerDescription) {
        _PowerDescription = PowerDescription;

        if (_powerRemoteModel != null) {
            try {
                Class<?> clazz = _powerRemoteModel.getClass();

                Method method = clazz.getMethod("setPowerDescription",
                        String.class);

                method.invoke(_powerRemoteModel, PowerDescription);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public BaseModel<?> getPowerRemoteModel() {
        return _powerRemoteModel;
    }

    public void setPowerRemoteModel(BaseModel<?> powerRemoteModel) {
        _powerRemoteModel = powerRemoteModel;
    }

    public Object invokeOnRemoteModel(String methodName,
        Class<?>[] parameterTypes, Object[] parameterValues)
        throws Exception {
        Object[] remoteParameterValues = new Object[parameterValues.length];

        for (int i = 0; i < parameterValues.length; i++) {
            if (parameterValues[i] != null) {
                remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
            }
        }

        Class<?> remoteModelClass = _powerRemoteModel.getClass();

        ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

        Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i].isPrimitive()) {
                remoteParameterTypes[i] = parameterTypes[i];
            } else {
                String parameterTypeName = parameterTypes[i].getName();

                remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
            }
        }

        Method method = remoteModelClass.getMethod(methodName,
                remoteParameterTypes);

        Object returnValue = method.invoke(_powerRemoteModel,
                remoteParameterValues);

        if (returnValue != null) {
            returnValue = ClpSerializer.translateOutput(returnValue);
        }

        return returnValue;
    }

    @Override
    public void persist() throws SystemException {
        if (this.isNew()) {
            PowerLocalServiceUtil.addPower(this);
        } else {
            PowerLocalServiceUtil.updatePower(this);
        }
    }

    @Override
    public Power toEscapedModel() {
        return (Power) ProxyUtil.newProxyInstance(Power.class.getClassLoader(),
            new Class[] { Power.class }, new AutoEscapeBeanHandler(this));
    }

    @Override
    public Object clone() {
        PowerClp clone = new PowerClp();

        clone.setPowerId(getPowerId());
        clone.setPowerName(getPowerName());
        clone.setPowerDescription(getPowerDescription());

        return clone;
    }

    @Override
    public int compareTo(Power power) {
        long primaryKey = power.getPrimaryKey();

        if (getPrimaryKey() < primaryKey) {
            return -1;
        } else if (getPrimaryKey() > primaryKey) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PowerClp)) {
            return false;
        }

        PowerClp power = (PowerClp) obj;

        long primaryKey = power.getPrimaryKey();

        if (getPrimaryKey() == primaryKey) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return (int) getPrimaryKey();
    }

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(7);

        sb.append("{PowerId=");
        sb.append(getPowerId());
        sb.append(", PowerName=");
        sb.append(getPowerName());
        sb.append(", PowerDescription=");
        sb.append(getPowerDescription());
        sb.append("}");

        return sb.toString();
    }

    @Override
    public String toXmlString() {
        StringBundler sb = new StringBundler(13);

        sb.append("<model><model-name>");
        sb.append("de.lineas.training.herodb.model.Power");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>PowerId</column-name><column-value><![CDATA[");
        sb.append(getPowerId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>PowerName</column-name><column-value><![CDATA[");
        sb.append(getPowerName());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>PowerDescription</column-name><column-value><![CDATA[");
        sb.append(getPowerDescription());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}
