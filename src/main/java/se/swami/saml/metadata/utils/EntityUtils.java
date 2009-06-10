/*
 * Created on Apr 23, 2008
 *
 */
package se.swami.saml.metadata.utils;

import java.net.URI;

import org.oasis.saml.metadata.EntitiesDescriptorType;
import org.oasis.saml.metadata.EntityDescriptorType;

public class EntityUtils {

	public static EntityDescriptorType findEntity(EntitiesDescriptorType entities, String entityID) {
		for (EntityDescriptorType entity : entities.getEntityDescriptorArray()) {
			if (entity.getEntityID().equals(entityID))
				return entity;
		}
		return null;
	}
	
	public static String entityFileName(String entityID) {
		URI uri = URI.create(entityID);
		if (uri == null || uri.getHost() == null)
			throw new IllegalArgumentException("Malformed entityID: "+entityID);
		return uri.getHost()+".xml";
	}
	
	public static String entityFileName(EntityDescriptorType entity) {
		return entityFileName(entity.getEntityID());
	}
}
