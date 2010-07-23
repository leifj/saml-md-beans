/*
 * Created on Apr 23, 2008
 *
 */
package se.swami.saml.metadata.utils;

import java.io.ByteArrayInputStream;
import java.security.cert.CertSelector;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.oasis.saml.metadata.EntityDescriptorType;
import org.oasis.saml.metadata.KeyDescriptorType;
import org.oasis.saml.metadata.RoleDescriptorType;
import org.w3c.xmldsig.KeyInfoType;
import org.w3c.xmldsig.X509DataType;

public class X509CertificateUtils {
	
	// A mini-version of su-commons:ArrayUtils
	private interface Predicate {
		public boolean evaluate(Object o);
	}
	
	private static Object find(Object[] a, Predicate p)
    {
            for (int i = 0; i < a.length; i++)
            {
                    if (p.evaluate(a[i]))
                            return a[i];
            }
            
            return null;
    }
	
	public static KeyDescriptorType getKeyDescriptor(RoleDescriptorType role, final Enum<?> use)
	{
		if (use == null) {
			KeyDescriptorType keyDescriptor[] = role.getKeyDescriptorArray();
			if (keyDescriptor == null) {
				return role.addNewKeyDescriptor();
			} else {
				return keyDescriptor[0];
			}
		} else {
			final KeyDescriptorType keyDescriptor[] = role.getKeyDescriptorArray();
			return (KeyDescriptorType)find(keyDescriptor,new Predicate() {
				public boolean evaluate(Object object) {
					return ((KeyDescriptorType)object).getUse().equals(use);
				}
			});
		}
	}
	
	public static KeyInfoType getKeyInfo(KeyDescriptorType keyDescriptor)
	{
		return keyDescriptor.getKeyInfo() == null ? keyDescriptor.addNewKeyInfo() : keyDescriptor.getKeyInfo();
	}

	public static void addCertificate(RoleDescriptorType role, X509Certificate cert) 
		throws CertificateEncodingException 
	{
		KeyDescriptorType keyDescriptor = getKeyDescriptor(role, null);
		KeyInfoType keyInfo = getKeyInfo(keyDescriptor);
		X509DataType x509DataType = keyInfo.addNewX509Data();
		x509DataType.addX509Certificate(cert.getEncoded());
	}
	
	public static void removeCertificates(KeyDescriptorType keyDescriptor, CertSelector certSelector) throws CertificateException
	{
		removeCertificates(keyDescriptor.getKeyInfo(),certSelector);
	}
	
	public static void removeCertificates(KeyInfoType keyInfo,CertSelector certSelector) throws CertificateException {
		for (X509DataType x509DataType : keyInfo.getX509DataArray()) {
			removeCertificates(x509DataType,certSelector);
		}
	}

	public static void removeCertificates(X509DataType dataType, CertSelector certSelector) throws CertificateException 
	{
		byte[][] certs = dataType.getX509CertificateArray();
		CertificateFactory cf = CertificateFactory.getInstance("X509");
		for (int i = 0; i < certs.length; i++) {
			byte[] certData = certs[i];
			X509Certificate certificate = (X509Certificate)cf.generateCertificate(new ByteArrayInputStream(certData));
			if (certSelector.match(certificate)) {
				dataType.removeX509Certificate(i);
			}
		}
	}

	public static void removeCertificates(RoleDescriptorType role, CertSelector certSelector) throws CertificateException
	{
		KeyDescriptorType keyDescriptor[] = role.getKeyDescriptorArray();
		for (int i = 0; i < keyDescriptor.length; i++) {
			removeCertificates(keyDescriptor[i],certSelector);
		}
	}
	
	public static void removeExpiredCertificates(RoleDescriptorType role) throws CertificateException
	{
		removeExpiredCertificates(role,new Date());
	}
	
	public static void removeExpiredCertificates(RoleDescriptorType role, Date date) throws CertificateException
	{
		removeCertificates(role,new CertificateInvalidSelector(date));
	}
	
	public static void removeExpiredCertificates(EntityDescriptorType entity, Date date) throws CertificateException
	{
		for (RoleDescriptorType role : entity.getRoleDescriptorArray()) {
			removeExpiredCertificates(role,date);
		}
	}
	
	public static void removeExpiredCertificates(EntityDescriptorType entity) throws CertificateException
	{
		removeExpiredCertificates(entity, new Date());
	}
}
