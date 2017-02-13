package edu.usf.it.PersonRegistry

import grails.transaction.Transactional

@Transactional
class SourceService {

  def importSource(data) {
    if(!!data) {
      if('sources' in data) {
        def summary = [
          existing: 0,
          added: 0,
          errored: 0
        ]
        System.out.println("Running Source Import")
        data.sources.each { s ->
          def sexists = Gender.findByCode(s.code)
          if(!!!sexists) {
            sexists = s as Source
            if(!sexists.save(failOnError:false, flush: true, insert: true, validate: true)) {
              log.warn "'${sexists.errors.fieldError.field}' value '${sexists.errors.fieldError.rejectedValue}' rejected" 
              System.out.println("'${sexists.errors.fieldError.field}' value '${sexists.errors.fieldError.rejectedValue}' rejected") 
              summary.errored++
              System.out.print("-")
            } else {
              summary.added++
              System.out.print("+")
            }  
          } else {
            summary.existing++
            System.out.print(".")
          }
        }
        System.out.println("\nSource Import Complete")
        return summary;
      } 
      return [ error: "Your data doesn't contain any entries"]
    }
    return [ error: "You must provide Source data for the import"]
  }
}
