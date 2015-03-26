package com.sega.testJsonParser.configuration;

import java.io.IOException;
import java.nio.charset.Charset;

import org.hl7.fhir.instance.formats.IParser;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.Resource;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class FhirHttpMessageConverter extends AbstractHttpMessageConverter<Resource>{
	
	private IParser parser = new JsonParser();

	public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	
	public FhirHttpMessageConverter() {
		super(new MediaType("application", "json", DEFAULT_CHARSET));
	}
	@Override
	protected Resource readInternal(Class<? extends Resource> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		
		try {
			return parser.parse(inputMessage.getBody());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new HttpMessageNotReadableException(e.getMessage());
		}
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void writeInternal(Resource resource, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		try {
			String json = parser.composeString(resource);
			outputMessage.getBody().write(json.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
