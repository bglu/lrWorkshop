package de.lineas.training.publisher.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import de.lineas.training.publisher.service.ClpSerializer;
import de.lineas.training.publisher.service.EntryLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;


public class EntryClp extends BaseModelImpl<Entry> implements Entry {
    private String _uuid;
    private long _entryId;
    private String _txt;
    private long _groupId;
    private BaseModel<?> _entryRemoteModel;

    public EntryClp() {
    }

    @Override
    public Class<?> getModelClass() {
        return Entry.class;
    }

    @Override
    public String getModelClassName() {
        return Entry.class.getName();
    }

    @Override
    public long getPrimaryKey() {
        return _entryId;
    }

    @Override
    public void setPrimaryKey(long primaryKey) {
        setEntryId(primaryKey);
    }

    @Override
    public Serializable getPrimaryKeyObj() {
        return _entryId;
    }

    @Override
    public void setPrimaryKeyObj(Serializable primaryKeyObj) {
        setPrimaryKey(((Long) primaryKeyObj).longValue());
    }

    @Override
    public Map<String, Object> getModelAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("uuid", getUuid());
        attributes.put("entryId", getEntryId());
        attributes.put("txt", getTxt());
        attributes.put("groupId", getGroupId());

        return attributes;
    }

    @Override
    public void setModelAttributes(Map<String, Object> attributes) {
        String uuid = (String) attributes.get("uuid");

        if (uuid != null) {
            setUuid(uuid);
        }

        Long entryId = (Long) attributes.get("entryId");

        if (entryId != null) {
            setEntryId(entryId);
        }

        String txt = (String) attributes.get("txt");

        if (txt != null) {
            setTxt(txt);
        }

        Long groupId = (Long) attributes.get("groupId");

        if (groupId != null) {
            setGroupId(groupId);
        }
    }

    @Override
    public String getUuid() {
        return _uuid;
    }

    @Override
    public void setUuid(String uuid) {
        _uuid = uuid;

        if (_entryRemoteModel != null) {
            try {
                Class<?> clazz = _entryRemoteModel.getClass();

                Method method = clazz.getMethod("setUuid", String.class);

                method.invoke(_entryRemoteModel, uuid);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public long getEntryId() {
        return _entryId;
    }

    @Override
    public void setEntryId(long entryId) {
        _entryId = entryId;

        if (_entryRemoteModel != null) {
            try {
                Class<?> clazz = _entryRemoteModel.getClass();

                Method method = clazz.getMethod("setEntryId", long.class);

                method.invoke(_entryRemoteModel, entryId);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public String getTxt() {
        return _txt;
    }

    @Override
    public void setTxt(String txt) {
        _txt = txt;

        if (_entryRemoteModel != null) {
            try {
                Class<?> clazz = _entryRemoteModel.getClass();

                Method method = clazz.getMethod("setTxt", String.class);

                method.invoke(_entryRemoteModel, txt);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public long getGroupId() {
        return _groupId;
    }

    @Override
    public void setGroupId(long groupId) {
        _groupId = groupId;

        if (_entryRemoteModel != null) {
            try {
                Class<?> clazz = _entryRemoteModel.getClass();

                Method method = clazz.getMethod("setGroupId", long.class);

                method.invoke(_entryRemoteModel, groupId);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public BaseModel<?> getEntryRemoteModel() {
        return _entryRemoteModel;
    }

    public void setEntryRemoteModel(BaseModel<?> entryRemoteModel) {
        _entryRemoteModel = entryRemoteModel;
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

        Class<?> remoteModelClass = _entryRemoteModel.getClass();

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

        Object returnValue = method.invoke(_entryRemoteModel,
                remoteParameterValues);

        if (returnValue != null) {
            returnValue = ClpSerializer.translateOutput(returnValue);
        }

        return returnValue;
    }

    @Override
    public void persist() throws SystemException {
        if (this.isNew()) {
            EntryLocalServiceUtil.addEntry(this);
        } else {
            EntryLocalServiceUtil.updateEntry(this);
        }
    }

    @Override
    public Entry toEscapedModel() {
        return (Entry) ProxyUtil.newProxyInstance(Entry.class.getClassLoader(),
            new Class[] { Entry.class }, new AutoEscapeBeanHandler(this));
    }

    @Override
    public Object clone() {
        EntryClp clone = new EntryClp();

        clone.setUuid(getUuid());
        clone.setEntryId(getEntryId());
        clone.setTxt(getTxt());
        clone.setGroupId(getGroupId());

        return clone;
    }

    @Override
    public int compareTo(Entry entry) {
        long primaryKey = entry.getPrimaryKey();

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

        if (!(obj instanceof EntryClp)) {
            return false;
        }

        EntryClp entry = (EntryClp) obj;

        long primaryKey = entry.getPrimaryKey();

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
        StringBundler sb = new StringBundler(9);

        sb.append("{uuid=");
        sb.append(getUuid());
        sb.append(", entryId=");
        sb.append(getEntryId());
        sb.append(", txt=");
        sb.append(getTxt());
        sb.append(", groupId=");
        sb.append(getGroupId());
        sb.append("}");

        return sb.toString();
    }

    @Override
    public String toXmlString() {
        StringBundler sb = new StringBundler(16);

        sb.append("<model><model-name>");
        sb.append("de.lineas.training.publisher.model.Entry");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>uuid</column-name><column-value><![CDATA[");
        sb.append(getUuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>entryId</column-name><column-value><![CDATA[");
        sb.append(getEntryId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>txt</column-name><column-value><![CDATA[");
        sb.append(getTxt());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>groupId</column-name><column-value><![CDATA[");
        sb.append(getGroupId());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}
