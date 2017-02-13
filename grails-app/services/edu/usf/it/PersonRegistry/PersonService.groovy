package edu.usf.it.PersonRegistry

import grails.converters.JSON
import grails.transaction.Transactional
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Statement
import org.hibernate.jdbc.Work
import edu.usf.it.PersonRegistry.work.PersonWithUUIDWork

@Transactional
class PersonService {
  def importPerson(data) {
    if(!!data) {
      if('persons' in data) {
        def summary = [
          existing: 0,
          added: 0,
          errored: 0
        ]
        System.out.println("Running Person Import")
        Person.withNewSession { session ->
          session.doWork(new PersonWithUUIDWork(data))
        }
        System.out.println("\nPerson Import Complete\n")
      } else {
        System.out.println("Your data doesn't contain any entries")
      }      
    } else {
      System.out.println("You must provide Person data for the import")
    }
  }
}
