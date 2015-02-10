package de.lineas.training.publisher.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import de.lineas.training.publisher.model.Entry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Entry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Entry
 * @generated
 */
public class EntryCacheModel implements CacheModel<Entry>, Externalizable {
    public String uuid;
    public long entryId;
    public String txt;
    public long groupId;

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(9);

        sb.append("{uuid=");
        sb.append(uuid);
        sb.append(", entryId=");
        sb.append(entryId);
        sb.append(", txt=");
        sb.append(txt);
        sb.append(", groupId=");
        sb.append(groupId);
        sb.append("}");

        return sb.toString();
    }

    @Override
    public Entry toEntityModel() {
        EntryImpl entryImpl = new EntryImpl();

        if (uuid == null) {
            entryImpl.setUuid(StringPool.BLANK);
        } else {
            entryImpl.setUuid(uuid);
        }

        entryImpl.setEntryId(entryId);

        if (txt == null) {
            entryImpl.setTxt(StringPool.BLANK);
        } else {
            entryImpl.setTxt(txt);
        }

        entryImpl.setGroupId(groupId);

        entryImpl.resetOriginalValues();

        return entryImpl;
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        uuid = objectInput.readUTF();
        entryId = objectInput.readLong();
        txt = objectInput.readUTF();
        groupId = objectInput.readLong();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput)
        throws IOException {
        if (uuid == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(uuid);
        }

        objectOutput.writeLong(entryId);

        if (txt == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(txt);
        }

        objectOutput.writeLong(groupId);
    }
}
