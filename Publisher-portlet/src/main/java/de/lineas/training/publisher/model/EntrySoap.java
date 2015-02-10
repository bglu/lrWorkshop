package de.lineas.training.publisher.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class EntrySoap implements Serializable {
    private String _uuid;
    private long _entryId;
    private String _txt;
    private long _groupId;

    public EntrySoap() {
    }

    public static EntrySoap toSoapModel(Entry model) {
        EntrySoap soapModel = new EntrySoap();

        soapModel.setUuid(model.getUuid());
        soapModel.setEntryId(model.getEntryId());
        soapModel.setTxt(model.getTxt());
        soapModel.setGroupId(model.getGroupId());

        return soapModel;
    }

    public static EntrySoap[] toSoapModels(Entry[] models) {
        EntrySoap[] soapModels = new EntrySoap[models.length];

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModel(models[i]);
        }

        return soapModels;
    }

    public static EntrySoap[][] toSoapModels(Entry[][] models) {
        EntrySoap[][] soapModels = null;

        if (models.length > 0) {
            soapModels = new EntrySoap[models.length][models[0].length];
        } else {
            soapModels = new EntrySoap[0][0];
        }

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModels(models[i]);
        }

        return soapModels;
    }

    public static EntrySoap[] toSoapModels(List<Entry> models) {
        List<EntrySoap> soapModels = new ArrayList<EntrySoap>(models.size());

        for (Entry model : models) {
            soapModels.add(toSoapModel(model));
        }

        return soapModels.toArray(new EntrySoap[soapModels.size()]);
    }

    public long getPrimaryKey() {
        return _entryId;
    }

    public void setPrimaryKey(long pk) {
        setEntryId(pk);
    }

    public String getUuid() {
        return _uuid;
    }

    public void setUuid(String uuid) {
        _uuid = uuid;
    }

    public long getEntryId() {
        return _entryId;
    }

    public void setEntryId(long entryId) {
        _entryId = entryId;
    }

    public String getTxt() {
        return _txt;
    }

    public void setTxt(String txt) {
        _txt = txt;
    }

    public long getGroupId() {
        return _groupId;
    }

    public void setGroupId(long groupId) {
        _groupId = groupId;
    }
}
