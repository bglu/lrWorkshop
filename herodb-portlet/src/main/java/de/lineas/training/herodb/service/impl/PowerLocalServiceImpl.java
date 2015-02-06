package de.lineas.training.herodb.service.impl;

import com.google.common.base.Strings;
import com.liferay.portal.kernel.exception.SystemException;

import de.lineas.training.herodb.model.Power;
import de.lineas.training.herodb.model.PowerClp;
import de.lineas.training.herodb.service.base.PowerLocalServiceBaseImpl;

/**
 * The implementation of the power local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.lineas.training.herodb.service.PowerLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see de.lineas.training.herodb.service.base.PowerLocalServiceBaseImpl
 * @see de.lineas.training.herodb.service.PowerLocalServiceUtil
 */
public class PowerLocalServiceImpl extends PowerLocalServiceBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this interface directly. Always use {@link de.lineas.training.herodb.service.PowerLocalServiceUtil} to access the power local service.
     */
	
	
	/**
	 * Adds new Power to the database.
	 * @param powerName - name of the power
	 * @param powerDescription - description of the power
	 * @return power that was added
	 */
	public Power addPower(String powerName, String powerDescription) {
		Power p = new PowerClp();
		
		if ( ! Strings.isNullOrEmpty(powerName) && ! Strings.isNullOrEmpty(powerDescription)) {
			try {
				long powerId = counterLocalService.increment();
				p = powerPersistence.create(powerId);
				
				if (p != null) {
					p.setPowerName(powerName);
					p.setPowerDescription(powerDescription);
					
					p = powerPersistence.update(p);
				}
				
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}
		
		return p;
	}
	
	/**
	 * Updates power object identified by given id.
	 * 
	 * @param powerId - Id of power object to be updated
	 * @param powerName - new name to be set
	 * @param powerDescription - new description to set
	 * @return updated power object
	 */
	public Power updatePower(long powerId, String powerName, String powerDescription){
		Power p = new PowerClp();
		
		try {
			p = powerPersistence.fetchByPrimaryKey(powerId);
			
			if ( p!= null &&
				 ! Strings.isNullOrEmpty(powerName) &&
				 ! Strings.isNullOrEmpty(powerDescription)
				) {
				
				p.setPowerName(powerName);
				p.setPowerDescription(powerDescription);
				
				p = powerPersistence.update(p);
			}
			
		} catch (SystemException e) {
			e.printStackTrace();
		}
		
		
		return p;
	}
}
