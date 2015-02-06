package de.lineas.training.herodb.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PowerSoap implements Serializable {
    private long _PowerId;
    private String _PowerName;
    private String _PowerDescription;

    public PowerSoap() {
    }

    public static PowerSoap toSoapModel(Power model) {
        PowerSoap soapModel = new PowerSoap();

        soapModel.setPowerId(model.getPowerId());
        soapModel.setPowerName(model.getPowerName());
        soapModel.setPowerDescription(model.getPowerDescription());

        return soapModel;
    }

    public static PowerSoap[] toSoapModels(Power[] models) {
        PowerSoap[] soapModels = new PowerSoap[models.length];

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModel(models[i]);
        }

        return soapModels;
    }

    public static PowerSoap[][] toSoapModels(Power[][] models) {
        PowerSoap[][] soapModels = null;

        if (models.length > 0) {
            soapModels = new PowerSoap[models.length][models[0].length];
        } else {
            soapModels = new PowerSoap[0][0];
        }

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModels(models[i]);
        }

        return soapModels;
    }

    public static PowerSoap[] toSoapModels(List<Power> models) {
        List<PowerSoap> soapModels = new ArrayList<PowerSoap>(models.size());

        for (Power model : models) {
            soapModels.add(toSoapModel(model));
        }

        return soapModels.toArray(new PowerSoap[soapModels.size()]);
    }

    public long getPrimaryKey() {
        return _PowerId;
    }

    public void setPrimaryKey(long pk) {
        setPowerId(pk);
    }

    public long getPowerId() {
        return _PowerId;
    }

    public void setPowerId(long PowerId) {
        _PowerId = PowerId;
    }

    public String getPowerName() {
        return _PowerName;
    }

    public void setPowerName(String PowerName) {
        _PowerName = PowerName;
    }

    public String getPowerDescription() {
        return _PowerDescription;
    }

    public void setPowerDescription(String PowerDescription) {
        _PowerDescription = PowerDescription;
    }
}
