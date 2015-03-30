package com.sega.testJsonParser.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.hl7.fhir.instance.model.Conformance;
import org.hl7.fhir.instance.model.Reference;
import org.hl7.fhir.instance.model.Conformance.ConformanceRestResourceComponent;
import org.hl7.fhir.instance.model.Conformance.ConformanceStatementStatus;
import org.hl7.fhir.instance.model.Conformance.RestfulConformanceMode;
import org.hl7.fhir.instance.model.Conformance.SearchParamType;
import org.hl7.fhir.instance.model.Conformance.TypeRestfulInteraction;
import org.hl7.fhir.instance.model.ContactPoint.ContactPointSystem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConferenceController {

	@RequestMapping("/conformance")
	@ResponseBody
	public Conformance getConformance() throws IOException{
		Conformance conformance = new Conformance();
		Properties pro = loadFile();
		initialConformance(conformance, pro);
		return conformance;
	}
	
	private void initialConformance(Conformance conformance, Properties pro){
		conformance.setId(pro.getProperty("id"));
		conformance.setVersion(pro.getProperty("version")).setName(pro.getProperty("name")).setPublisher(pro.getProperty("publisher"));
		conformance.setDescription(pro.getProperty("description"));
		String format = pro.getProperty("format");
		String[] f = format.split(";");
		for(String s : f){
			conformance.addFormat(s);
		}
		conformance.setDate(new Date()).setStatus(ConformanceStatementStatus.DRAFT);
		conformance.addTelecom().setSystem(ContactPointSystem.PHONE).setValue("1111111111111");
		String[] types = pro.getProperty("type").split(";");
		conformance.addRest().setMode(RestfulConformanceMode.SERVER).setDocumentation("fdafdsafdsafdsaf").addOperation().setName("getPatient").setDefinition(new Reference().setDisplay("localhost:8080/Patient/{id}"));
		for(String type:types){
			if(type.equals("Conformance"))
				setConformance(conformance);
			if(type.equals("Patient"))
				setPatient(conformance);
		}
	}
	
	private Properties loadFile() throws IOException{
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("description.properties");
		Properties pro = new Properties();
		pro.load(in);
		return pro;
	}
	
	private void setConformance(Conformance conformance){
		ConformanceRestResourceComponent crrc = conformance.getRest().get(0).addResource().setType("Conformance").setProfile(new Reference().setReference("dfadfsa"));
		crrc.addInteraction().setCode(TypeRestfulInteraction.READ).setCode(TypeRestfulInteraction.VREAD);
		crrc.addSearchParam().setName("format").setDefinition("http://hl7.org/fhir/SearchParameter/conformance-format").setType(SearchParamType.TOKEN).setDocumentation("formats supported (xml | json | mime type)");
		crrc.addSearchParam().setName("format").setDefinition("http://hl7.org/fhir/SearchParameter/conformance-date").setType(SearchParamType.DATE).setDocumentation("The conformance statement publication date");
	}
	
	private void setPatient(Conformance conformance){
		ConformanceRestResourceComponent crrc = conformance.getRest().get(0).addResource().setType("Patient").setProfile(new Reference().setReference("dfadfsa"));
		crrc.addInteraction().setCode(TypeRestfulInteraction.READ).setCode(TypeRestfulInteraction.CREATE);
		crrc.addSearchParam().setName("format").setDefinition("http://hl7.org/fhir/SearchParameter/conformance-format").setType(SearchParamType.TOKEN).setDocumentation("formats supported (xml | json | mime type)");
		crrc.addSearchParam().setName("format").setDefinition("http://hl7.org/fhir/SearchParameter/conformance-date").setType(SearchParamType.DATE).setDocumentation("The conformance statement publication date");
		
	}
}
