package edu.usf.it.PersonRegistry

import grails.util.GrailsNameUtils
import grails.util.GrailsUtil
import grails.converters.*
import edu.usf.it.PersonRegistry.Identifier
import edu.usf.it.PersonRegistry.IdentifierType
import edu.usf.it.PersonRegistry.Person
import edu.usf.it.PersonRegistry.Source
import edu.usf.it.PersonRegistry.Genealogy
import javax.crypto.Cipher
import java.security.NoSuchAlgorithmException
import org.hibernate.ScrollMode
import org.jasypt.util.text.StrongTextEncryptor
import org.hibernate.ScrollableResults

class BootStrap {
  def genealogyService
  def personService
  def genderService
  def sourceService
  def sessionFactory
  def init = { servletContext ->
    // Testing Java for AES Strength (must be "unlimited" and > 128)
    int allowedKeyLength = 0;
    try {
      allowedKeyLength = Cipher.getMaxAllowedKeyLength("AES");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    System.out.println("The allowed key length for AES is: " + allowedKeyLength);
    // Testing if the Genealogy table needs initializing with default data genealogy
//    if(!Genealogy.count()) {
//      def genealogyResult = genealogyService.importGenealogy(JSON.parse(new File('src/main/groovy/edu/usf/it/PersonRegistry/Default', 'GenealogyData.json').text))
//      System.out.println("Existing Genealogic Existing Entries: " + genealogyResult.existing)   
//      System.out.println("Existing Genealogic Added Entries: " + genealogyResult.added)   
//      System.out.println("Existing Genealogic Errored Entries: " + genealogyResult.errored)   
//    }
    if(!Person.count()) {
      personService.importPerson(JSON.parse(new File('src/main/groovy/edu/usf/it/PersonRegistry/Default', 'PersonData.json').text))
    }
    def genderResult = genderService.importGender(JSON.parse(new File('src/main/groovy/edu/usf/it/PersonRegistry/Default', 'GenderData.json').text))
    System.out.println("Existing Gender Existing Entries: " + genderResult.existing)   
    System.out.println("Existing Gender Added Entries: " + genderResult.added)   
    System.out.println("Existing Gender Errored Entries: " + genderResult.errored)   
    def sourceResult = sourceService.importSource(JSON.parse(new File('src/main/groovy/edu/usf/it/PersonRegistry/Default', 'SourceData.json').text))
    System.out.println("Existing Source Existing Entries: " + sourceResult.existing)   
    System.out.println("Existing Source Added Entries: " + sourceResult.added)   
    System.out.println("Existing Source Errored Entries: " + sourceResult.errored)   
  }
  def destroy = {
  }
}
