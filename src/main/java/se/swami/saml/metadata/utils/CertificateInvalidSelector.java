/*
 * Created on Apr 23, 2008
 *
 */
package se.swami.saml.metadata.utils;

import java.security.cert.CertSelector;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;

public class CertificateInvalidSelector implements CertSelector {

	private Date date;
	
	public CertificateInvalidSelector(Date date) {
		this.date = date;
	}
	
	public Object clone() {
		return new CertificateInvalidSelector(date);
	}
	public boolean match(Certificate cert) {
		if (cert instanceof X509Certificate) {
			X509Certificate certificate = (X509Certificate) cert;
			try {
				certificate.checkValidity(date);
				return true;
			} catch (CertificateExpiredException ex) {
				return false;
			} catch (CertificateNotYetValidException ex) {
				return false;
			}
		} else {
			return false;
		}
	}
}