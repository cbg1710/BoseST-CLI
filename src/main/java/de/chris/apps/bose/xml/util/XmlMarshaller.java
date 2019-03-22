package de.chris.apps.bose.xml.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlMarshaller {

	private final static Logger LOG = LoggerFactory.getLogger(XmlMarshaller.class);

	private static XmlMarshaller marshaller;

	public static XmlMarshaller getInstance() {
		if (marshaller == null) {
			marshaller = new XmlMarshaller();
		}
		return marshaller;
	}

	public <T> T unmarshall(Class<T> type, String xml) {
		try {
			JAXBContext context = JAXBContext.newInstance(type);
			return (T) context.createUnmarshaller().unmarshal(new StringReader(xml));
		}
		catch (Exception e) {
			LOG.warn("Error unmarshalling xml: " + e.getMessage());
			return null;
		}
	}

	public <T> String marshall(T t) {
		try {
			JAXBContext context = JAXBContext.newInstance(t.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			StringWriter writer = new StringWriter();
			marshaller.marshal(t, writer);
			return writer.toString();
		}
		catch (Exception e) {
			LOG.warn("Error marshalling xml: " + e.getMessage());
			return null;
		}
	}
}
