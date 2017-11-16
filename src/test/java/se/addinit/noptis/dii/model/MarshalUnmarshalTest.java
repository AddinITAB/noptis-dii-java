package se.addinit.noptis.dii.model;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class MarshalUnmarshalTest {

    private static JAXBContext jaxbContext;
    private static ObjectFactory factory = new ObjectFactory();

    @BeforeClass
    public static void initContext() throws JAXBException {
        jaxbContext = JAXBContext.newInstance("se.addinit.noptis.dii.model");
    }

    @Test
    public void marshalUnmarshalNetwork() throws JAXBException, DatatypeConfigurationException {
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        CurrentNetworkVersion currentNetworkVersion = factory.createCurrentNetworkVersion();
        currentNetworkVersion.setModificationType(ModificationType.DEFINE);
        currentNetworkVersion.setDocumentLayoutVersion("3.0.14");

        Document.Source documentSource = factory.createDocumentSource();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        XMLGregorianCalendar createdDateTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(format.format(new Date()));
        documentSource.setCreatedDateTime(createdDateTime);
        currentNetworkVersion.setSource(documentSource);

        JAXBElement<CurrentNetworkVersion> currentNetworkVersionElement = factory.createNetworkVersion(currentNetworkVersion);

        marshaller.marshal(currentNetworkVersionElement, byteArrayOutputStream);

        String xml = byteArrayOutputStream.toString();
        System.out.println(xml);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        @SuppressWarnings("unchecked")
        JAXBElement<CurrentNetworkVersion> jaxbElement = (JAXBElement<CurrentNetworkVersion>) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes()));
        CurrentNetworkVersion actual = jaxbElement.getValue();

        assertThat(actual.getModificationType()).isNotNull().isEqualTo(ModificationType.DEFINE);
        assertThat(actual.getDocumentLayoutVersion()).isNotNull().isEqualTo("3.0.14");
    }

}
