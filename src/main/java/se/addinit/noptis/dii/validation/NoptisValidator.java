package se.addinit.noptis.dii.validation;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;

public class NoptisValidator {

    private final Schema noptisSchema;

    public NoptisValidator() throws IOException, SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setResourceResolver(new SchemaResourceResolver("/noptis_schema_list_3_0.txt"));
        Source source = new StreamSource(getClass().getResourceAsStream("/NOPTIS-DII-3.0/schema/xsd/DII.xsd"));
        this.noptisSchema = factory.newSchema(source);
    }

    public Schema getSchema() throws SAXException, IOException {
        return this.noptisSchema;
    }

    public void validate(Source source) throws IOException, SAXException {
        Validator validator = this.noptisSchema.newValidator();
        validator.validate(source);
    }

}
