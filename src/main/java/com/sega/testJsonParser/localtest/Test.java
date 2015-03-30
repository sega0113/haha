package com.sega.testJsonParser.localtest;

import java.util.Date;

import org.hl7.fhir.instance.formats.IParser;
import org.hl7.fhir.instance.formats.JsonParser;
import org.hl7.fhir.instance.model.CodeableConcept;
import org.hl7.fhir.instance.model.ContactPoint;
import org.hl7.fhir.instance.model.HumanName;
import org.hl7.fhir.instance.model.Narrative;
import org.hl7.fhir.instance.model.Patient;
import org.hl7.fhir.instance.model.Period;
import org.hl7.fhir.instance.model.Reference;
import org.hl7.fhir.instance.utils.NarrativeGenerator;
import org.hl7.fhir.utilities.xhtml.XhtmlNode;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Test {

	public static void main(String[] args) throws Exception {
		Patient p = new Patient();
		p.addIdentifier().setLabel("Dog Tag").setSystem("http://www.maroondah.vic.gov.au/AnimalRegFees.aspx").setValue("1234123").setPeriod(new Period().setStart(new Date(1010, 05, 31))).setAssigner(new Reference().setDisplay("Maroondah City Council"));
		p.addName().setUse(new HumanName.NameUseEnumFactory().fromCode("usual")).addGiven("Kenzi");
		p.setGender(new Patient.AdministrativeGenderEnumFactory().fromCode("female"));
		p.setBirthDate(new Date(2010, 03, 23));
		p.addContact().addRelationship().addCoding().setSystem("http://hl7.org/fhir/patient-contact-relationship").setCode("owner");
		p.getContact().get(0).setName(new HumanName().addFamily("Chalmers").addGiven("Peter").addGiven("James"));
		p.getContact().get(0).addTelecom().setSystem(new ContactPoint.ContactPointSystemEnumFactory().fromCode("phone")).setValue("(03) 5555 6473").setUse(new ContactPoint.ContactPointUseEnumFactory().fromCode("work"));
		CodeableConcept c1 = new CodeableConcept();
		c1.addCoding().setSystem("http://hl7.org/fhir/animal-species").setCode("canislf").setDisplay("Dog");
		CodeableConcept c2 = new CodeableConcept();
		c2.addCoding().setSystem("http://snomed.info/sct").setCode("58108001").setDisplay("Golden retriever");
		c2.addCoding().setSystem("http://hl7.org/fhir/animal-breed").setCode("gret").setDisplay("Golden retriever");
		CodeableConcept c3 = new CodeableConcept();
		c3.addCoding().setSystem("http://hl7.org/fhir/animal-genderstatus").setCode("neutered");
		p.setAnimal(new Patient.AnimalComponent().setSpecies(c1).setBreed(c2).setGenderStatus(c3)).setManagingOrganization(new Reference().setDisplay("Pete's Vetinary Services")).setActive(true);
		IParser parser = new JsonParser();
		System.out.println(parser.composeString(p));
		System.out.println(new Date());

	}

}
