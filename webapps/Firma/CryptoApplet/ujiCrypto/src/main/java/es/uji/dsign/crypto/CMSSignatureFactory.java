package es.uji.dsign.crypto;

import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert. CertStore;
import java.security.cert.Certificate;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedGenerator;

import es.uji.dsign.cms.MyCMSSignedDataGenerator;
import es.uji.dsign.util.Base64;
import es.uji.dsign.util.ConfigHandler;
import es.uji.dsign.util.i18n.LabelManager;


public class CMSSignatureFactory extends AbstractSignatureFactory implements ISignFormatProvider
{
    private Logger log = Logger.getLogger(CMSSignatureFactory.class);
    private String _strerr= "";
    
	public byte[] formatSignature(SignatureOptions sigOpt) throws KeyStoreException, Exception
	{
		byte[] content= sigOpt.getToSignByteArray();
		X509Certificate sCer= sigOpt.getCertificate();
		PrivateKey pk= sigOpt.getPrivateKey();
		Provider pv= sigOpt.getProvider();
		
		// Init the provider registry
		super.initProviderList();
		
		MyCMSSignedDataGenerator gen = new MyCMSSignedDataGenerator();
		
		if ( sCer == null ){
			_strerr= LabelManager.get("ERROR_CMS_NOCERT");
			return null;
		}
				
		if ( pk == null ){
			_strerr= LabelManager.get("ERROR_CMS_NOKEY");
			return null;
		}
		
		gen.addSigner(pk, (X509Certificate) sCer, CMSSignedGenerator.DIGEST_SHA1);
		CMSProcessableByteArray cba = new CMSProcessableByteArray(content);

		List<Certificate> certList = new ArrayList<Certificate>();
		
		//TODO:  Add the intermediate CAs if we have them
		certList.add(sCer);
		
		CertStore certst = CertStore.getInstance("Collection", new CollectionCertStoreParameters(certList), "BC");
	
		gen.addCertificatesAndCRLs(certst);
			
		CMSSignedData data = gen.generate(cba, pv.getName());
		
		if (data != null)
		{
			
			// Now we must check if a timestamp must be calculated 
			// reading the configuration file.
			
			Properties prop= ConfigHandler.getProperties();
			if ( prop == null ){
				_strerr= LabelManager.get("ERROR_CMS_CONFIG_NOT_FOUND");
				return null;
			}
			
			String doTs= prop.getProperty("CMS_TIMESTAMPING");
			String tsaUrl= prop.getProperty("CMS_TSA_URL");
			
			if (doTs != null && doTs.toLowerCase().equals("true")){

				MessageDigest dig= MessageDigest.getInstance("SHA1");
				dig.update(data.getEncoded());
				byte[] tsHash= dig.digest();

				log.info("The calculated hash for timestamp inclussion is " + new String(Base64.encode(tsHash)));

				if( tsaUrl==null ){
					_strerr= LabelManager.get("ERROR_CMS_CONFIG_TSAURL_NOT_FOUND");
					log.info("Bad configuration file: cannot get TSA URL");
					return null;
				}

				byte[] ts= TimeStampFactory.getTimeStamp(tsaUrl, tsHash);
				if (ts== null){
					_strerr= LabelManager.get("ERROR_CMS_CALCULATING_TS");
					log.info("Cannot calculate timestamp from: " + tsaUrl);
					return null;
				}
				return ("<data>\r\n  <cms_signature>\r\n" 
				         + new String(Base64.encode(data.getEncoded(),true)) +
				       "\r\n  </cms_signature>\r\n  <cms_timestamp>\r\n" 
				         + new String(Base64.encode(ts,true)) + "\r\n  </cms_timestamp>\r\n</data>\r\n").getBytes();  
				
			}
			else{
				return Base64.encode(data.getEncoded(),true);  
			}
		}
		else
		{
			_strerr= LabelManager.get("ERROR_CMS_SIGNATURE");
			System.out.println("Fuera por 4");
			return null;
		}
	}
	
	public String getError(){
		return _strerr;
	}
}
