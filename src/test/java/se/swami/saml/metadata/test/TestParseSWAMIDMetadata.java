/*
 * Created on Mar 11, 2008
 *
 */
package se.swami.saml.metadata.test;

import java.io.InputStream;

import org.oasis.saml.metadata.EntitiesDescriptorDocument;

import junit.framework.TestCase;

public class TestParseSWAMIDMetadata extends TestCase {

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
	
	public void testParseMD() {}
	
	public void testCheckName() {
		try {			
			assertEquals("urn:mace:swami.se:swamid:test-1.0",mdDoc.getEntitiesDescriptor().getName());
		} catch (Exception ex) {
			ex.printStackTrace();
			fail();
		}
	}
}
