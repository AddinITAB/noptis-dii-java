package se.addinit.noptis.dii.validation;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class NoptisValidatorTest {

    private NoptisValidator noptisValidator = new NoptisValidator();

    public NoptisValidatorTest() throws IOException, SAXException {
    }

    private static final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<PublicationDelivery xmlns=\"http://www.pubtrans.com/DII/3.0\" xmlns:ns2=\"http://www.opengis.net/gml/3.2\" xmlns:ns3=\"http://www.siri.org.uk/siri\" version=\"any\">\n" +
            "    <PublicationTimestamp>2016-11-29T13:32:06.869+01:00</PublicationTimestamp>\n" +
            "    <ParticipantRef>NSR</ParticipantRef>\n" +
            "</PublicationDelivery>";

    private static final String networkVersionXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<NetworkVersion xmlns=\"http://www.pubtrans.org/DII/3.0\">\n" +
            "    <Source CreatedDateTime=\"2017-11-16T09:30:47\"/>\n" +
            "</NetworkVersion>";

    @Test
    public void validationFailsForInvalidXml() throws SAXException, IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<PublicationDelivery xmlns=\"http://www.pubtrans.com/DII/3.0\" xmlns:ns2=\"http://www.opengis.net/gml/3.2\" xmlns:ns3=\"http://www.siri.org.uk/siri\"></PublicationDelivery>";

        assertThatThrownBy(() -> noptisValidator.validate(new StreamSource(new StringReader(xml)))).isInstanceOf(SAXParseException.class);
    }

    @Test
    @Ignore
    public void validateNetworkXml() throws IOException, SAXException {
        noptisValidator.validate(new StreamSource(new StringReader(networkVersionXml)));
    }

}