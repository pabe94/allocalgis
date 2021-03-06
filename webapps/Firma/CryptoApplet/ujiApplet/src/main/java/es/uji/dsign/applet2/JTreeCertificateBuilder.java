package es.uji.dsign.applet2;

import javax.swing.tree.DefaultMutableTreeNode;

import java.security.cert.X509Certificate;
import java.security.cert.Certificate;

import java.util.Hashtable;
import java.util.Vector;

import es.uji.dsign.crypto.keystore.IKeyStoreHelper;
import es.uji.dsign.crypto.X509CertificateHandler;
import es.uji.dsign.util.i18n.LabelManager;

public class JTreeCertificateBuilder
{
	public JTreeCertificateBuilder()
	{
	}

	public DefaultMutableTreeNode build(Hashtable<String,IKeyStoreHelper> ksh) throws Exception
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(LabelManager.get("LABEL_TREE_ROOT"));

		if (ksh == null)
		{
			throw new IllegalArgumentException("Keystore hastable can't be null");
		}

		X509Certificate xcert;
		X509CertificateHandler certHandle;
		boolean found = false;

		Vector<String> caStrs = new Vector<String>();
		Vector<DefaultMutableTreeNode> caNodes = new Vector<DefaultMutableTreeNode>();

		for (IKeyStoreHelper keystore : ksh.values())
		{
			try{
				Certificate[] certs= keystore.getUserCertificates();
				if ( certs!= null ){

					for (Certificate cer: certs)
					{
						found = false;
						xcert = (X509Certificate) cer;

						if (xcert != null)
						{
							certHandle = new X509CertificateHandler(xcert, "none", keystore);

							for (int j = 0; j < caStrs.size(); j++)
							{
								if (((String) caStrs.get(j)).equals(certHandle.getIssuerOrganization()))
								{
									((DefaultMutableTreeNode) caNodes.get(j)).add(new DefaultMutableTreeNode(certHandle));
									found = true;
								}
							}

							if (!found)
							{
								String issuerOrg = certHandle.getIssuerOrganization();
								DefaultMutableTreeNode nodeAux = new DefaultMutableTreeNode(issuerOrg);
								nodeAux.add(new DefaultMutableTreeNode(certHandle));
								caStrs.add(issuerOrg);
								root.add(nodeAux);
								caNodes.add(nodeAux);
							}
						}
					}
				}
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
		return root;
	}
}
