/*
 * Created on Apr 23, 2008
 *
 */
package se.swami.saml.metadata.test;

import java.io.InputStream;

import org.oasis.saml.metadata.EntitiesDescriptorDocument;
import org.oasis.saml.metadata.EntityDescriptorType;
import org.oasis.saml.metadata.SPSSODescriptorType;

import se.swami.saml.metadata.utils.EntityUtils;
import se.swami.saml.metadata.utils.X509CertificateUtils;

import junit.framework.TestCase;

public class TestCertificate extends TestCase {

	private EntitiesDescriptorDocument mdDoc;
	
	public void setUp() {
		try {
			InputStream mdXml = Thread.currentThread().getContextClassLoader().getResourceAsStream("urn-mace-swami.se-swamid-test-1.0-metadata-signed.xml");
			mdDoc = EntitiesDescriptorDocument.Factory.parse(mdXml);
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
	public void testFindEntity() {
		EntityDescriptorType entity = EntityUtils.findEntity(mdDoc.getEntitiesDescriptor(),"https://sp1.lab.it.su.se");
		assertNotNull(entity);
	}
	
	public void testRemoveCertificate() {
		try {
			EntityDescriptorType entity = EntityUtils.findEntity(mdDoc.getEntitiesDescriptor(),"https://sp1.lab.it.su.se");
			for (SPSSODescriptorType sp : entity.getSPSSODescriptorArray()) {
				X509CertificateUtils.removeExpiredCertificates(sp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
}
