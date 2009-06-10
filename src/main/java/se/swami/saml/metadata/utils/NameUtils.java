/*
 * Created on Apr 28, 2008
 *
 */
package se.swami.saml.metadata.utils;

import java.net.URL;
import java.util.Locale;

import org.oasis.saml.metadata.LocalizedNameType;
import org.oasis.saml.metadata.LocalizedURIType;

public class NameUtils {

	public static LocalizedNameType stringToName(String str, String lang)
	{
		LocalizedNameType lit = LocalizedNameType.Factory.newInstance();
		lit.setLang(lang);
		lit.setStringValue(str);
		return lit;
	}
	
	public static LocalizedNameType stringToName(String str)
	{
		return stringToName(str, Locale.getDefault().getLanguage());
	}
	
	public static LocalizedURIType uriToUri(URL uri)
	{
		return uriToUri(uri,Locale.getDefault().getLanguage());
	}
	
	public static LocalizedURIType uriToUri(URL uri, String lang)
	{
		LocalizedURIType lit = LocalizedURIType.Factory.newInstance();
		lit.setLang(lang);
		lit.setStringValue(uri.toString());
		
		return lit;
	}
}
