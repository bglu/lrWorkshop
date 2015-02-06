package de.lineas.training.herodb.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import de.lineas.training.herodb.model.Power;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Power in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Power
 * @generated
 */
public class PowerCacheModel implements CacheModel<Power>, Externalizable {
    public long PowerId;
    public String PowerName;
    public String PowerDescription;

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(7);

        sb.append("{PowerId=");
        sb.append(PowerId);
        sb.append(", PowerName=");
        sb.append(PowerName);
        sb.append(", PowerDescription=");
        sb.append(PowerDescription);
        sb.append("}");

        return sb.toString();
    }

    @Override
    public Power toEntityModel() {
        PowerImpl powerImpl = new PowerImpl();

        powerImpl.setPowerId(PowerId);

        if (PowerName == null) {
            powerImpl.setPowerName(StringPool.BLANK);
        } else {
            powerImpl.setPowerName(PowerName);
        }

        if (PowerDescription == null) {
            powerImpl.setPowerDescription(StringPool.BLANK);
        } else {
            powerImpl.setPowerDescription(PowerDescription);
        }

        powerImpl.resetOriginalValues();

        return powerImpl;
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        PowerId = objectInput.readLong();
        PowerName = objectInput.readUTF();
        PowerDescription = objectInput.readUTF();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput)
        throws IOException {
        objectOutput.writeLong(PowerId);

        if (PowerName == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(PowerName);
        }

        if (PowerDescription == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(PowerDescription);
        }
    }
}
