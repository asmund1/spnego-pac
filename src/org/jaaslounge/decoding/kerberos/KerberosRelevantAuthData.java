package org.jaaslounge.decoding.kerberos;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLSequence;
import org.bouncycastle.asn1.DLTaggedObject;
import org.jaaslounge.decoding.DecodingException;
import org.jaaslounge.decoding.DecodingUtil;

public class KerberosRelevantAuthData extends KerberosAuthData {

    private List<KerberosAuthData> authorizations;

    public KerberosRelevantAuthData(byte[] token, Key key) throws DecodingException {
        ASN1InputStream stream = new ASN1InputStream(new ByteArrayInputStream(token));
        DLSequence authSequence;
        try {
            authSequence = DecodingUtil.as(DLSequence.class, stream);
            stream.close();
        } catch(IOException e) {
            throw new DecodingException("kerberos.ticket.malformed", null, e);
        }

        authorizations = new ArrayList<KerberosAuthData>();
        Enumeration<?> authElements = authSequence.getObjects();
        while(authElements.hasMoreElements()) {
            DLSequence authElement = DecodingUtil.as(DLSequence.class, authElements);
            
            DERInteger authType = DecodingUtil.as(DERInteger.class, DecodingUtil.as(DERTaggedObject.class, authElement, 0));
            
            DEROctetString authData = DecodingUtil.as(DEROctetString.class, DecodingUtil.as(
                    DERTaggedObject.class, authElement, 1));

            authorizations.addAll(KerberosAuthData.parse(authType.getValue().intValue(), authData
                    .getOctets(), key));
        }
    }

    public List<KerberosAuthData> getAuthorizations() {
        return authorizations;
    }

}
