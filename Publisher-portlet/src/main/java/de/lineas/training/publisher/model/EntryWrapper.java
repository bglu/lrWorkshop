package de.lineas.training.publisher.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Entry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Entry
 * @generated
 */
public class EntryWrapper implements Entry, ModelWrapper<Entry> {
    private Entry _entry;

    public EntryWrapper(Entry entry) {
        _entry = entry;
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

    /**
    * Returns the primary key of this entry.
    *
    * @return the primary key of this entry
    */
    @Override
    public long getPrimaryKey() {
        return _entry.getPrimaryKey();
    }

    /**
    * Sets the primary key of this entry.
    *
    * @param primaryKey the primary key of this entry
    */
    @Override
    public void setPrimaryKey(long primaryKey) {
        _entry.setPrimaryKey(primaryKey);
    }

    /**
    * Returns the uuid of this entry.
    *
    * @return the uuid of this entry
    */
    @Override
    public java.lang.String getUuid() {
        return _entry.getUuid();
    }

    /**
    * Sets the uuid of this entry.
    *
    * @param uuid the uuid of this entry
    */
    @Override
    public void setUuid(java.lang.String uuid) {
        _entry.setUuid(uuid);
    }

    /**
    * Returns the entry ID of this entry.
    *
    * @return the entry ID of this entry
    */
    @Override
    public long getEntryId() {
        return _entry.getEntryId();
    }

    /**
    * Sets the entry ID of this entry.
    *
    * @param entryId the entry ID of this entry
    */
    @Override
    public void setEntryId(long entryId) {
        _entry.setEntryId(entryId);
    }

    /**
    * Returns the txt of this entry.
    *
    * @return the txt of this entry
    */
    @Override
    public java.lang.String getTxt() {
        return _entry.getTxt();
    }

    /**
    * Sets the txt of this entry.
    *
    * @param txt the txt of this entry
    */
    @Override
    public void setTxt(java.lang.String txt) {
        _entry.setTxt(txt);
    }

    /**
    * Returns the group ID of this entry.
    *
    * @return the group ID of this entry
    */
    @Override
    public long getGroupId() {
        return _entry.getGroupId();
    }

    /**
    * Sets the group ID of this entry.
    *
    * @param groupId the group ID of this entry
    */
    @Override
    public void setGroupId(long groupId) {
        _entry.setGroupId(groupId);
    }

    @Override
    public boolean isNew() {
        return _entry.isNew();
    }

    @Override
    public void setNew(boolean n) {
        _entry.setNew(n);
    }

    @Override
    public boolean isCachedModel() {
        return _entry.isCachedModel();
    }

    @Override
    public void setCachedModel(boolean cachedModel) {
        _entry.setCachedModel(cachedModel);
    }

    @Override
    public boolean isEscapedModel() {
        return _entry.isEscapedModel();
    }

    @Override
    public java.io.Serializable getPrimaryKeyObj() {
        return _entry.getPrimaryKeyObj();
    }

    @Override
    public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
        _entry.setPrimaryKeyObj(primaryKeyObj);
    }

    @Override
    public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
        return _entry.getExpandoBridge();
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portal.model.BaseModel<?> baseModel) {
        _entry.setExpandoBridgeAttributes(baseModel);
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
        _entry.setExpandoBridgeAttributes(expandoBridge);
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portal.service.ServiceContext serviceContext) {
        _entry.setExpandoBridgeAttributes(serviceContext);
    }

    @Override
    public java.lang.Object clone() {
        return new EntryWrapper((Entry) _entry.clone());
    }

    @Override
    public int compareTo(de.lineas.training.publisher.model.Entry entry) {
        return _entry.compareTo(entry);
    }

    @Override
    public int hashCode() {
        return _entry.hashCode();
    }

    @Override
    public com.liferay.portal.model.CacheModel<de.lineas.training.publisher.model.Entry> toCacheModel() {
        return _entry.toCacheModel();
    }

    @Override
    public de.lineas.training.publisher.model.Entry toEscapedModel() {
        return new EntryWrapper(_entry.toEscapedModel());
    }

    @Override
    public de.lineas.training.publisher.model.Entry toUnescapedModel() {
        return new EntryWrapper(_entry.toUnescapedModel());
    }

    @Override
    public java.lang.String toString() {
        return _entry.toString();
    }

    @Override
    public java.lang.String toXmlString() {
        return _entry.toXmlString();
    }

    @Override
    public void persist()
        throws com.liferay.portal.kernel.exception.SystemException {
        _entry.persist();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof EntryWrapper)) {
            return false;
        }

        EntryWrapper entryWrapper = (EntryWrapper) obj;

        if (Validator.equals(_entry, entryWrapper._entry)) {
            return true;
        }

        return false;
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
     */
    public Entry getWrappedEntry() {
        return _entry;
    }

    @Override
    public Entry getWrappedModel() {
        return _entry;
    }

    @Override
    public void resetOriginalValues() {
        _entry.resetOriginalValues();
    }
}
