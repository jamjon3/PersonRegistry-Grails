package edu.usf.it.PersonRegistry

import grails.transaction.Transactional

@Transactional
class GenderService {

  def importGender(data) {
    if(!!data) {
      if('genders' in data) {
        def summary = [
          existing: 0,
          added: 0,
          errored: 0
        ]
        System.out.println("Running Gender Import")
        data.genders.each { g ->
          def gexists = Gender.findByCode(g.code)
          if(!!!gexists) {
            gexists = g as Gender
            if(!gexists.save(failOnError:false, flush: true, insert: true, validate: true)) {
              log.warn "'${gexists.errors.fieldError.field}' value '${gexists.errors.fieldError.rejectedValue}' rejected" 
              System.out.println("'${gexists.errors.fieldError.field}' value '${gexists.errors.fieldError.rejectedValue}' rejected") 
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
        System.out.println("\nGender Import Complete")
        return summary;
      } 
      return [ error: "Your data doesn't contain any entries"]
    }
    return [ error: "You must provide Gender data for the import"]
  }
}
