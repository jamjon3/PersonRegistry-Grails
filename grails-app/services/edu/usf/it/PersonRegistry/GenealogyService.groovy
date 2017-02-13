package edu.usf.it.PersonRegistry

import grails.converters.JSON
import grails.transaction.Transactional

@Transactional
class GenealogyService {

  def importGenealogy(genealogy) {
    if(!!genealogy) {
      if('genealogy' in genealogy) {
        def summary = [
          existing: 0,
          added: 0,
          errored: 0
        ]
        System.out.println("Running Genealogy Import")
        genealogy.genealogy.each { g ->
          if(g.relationship in Genealogy.constrainedProperties ['relationship']['inList']) {
            def gexists = Genealogy.findByRelationshipAndNicknameAndRealname(g.relationship,g.nickname,g.realname)
            // def gexists = Genealogy.find(g as Genealogy)
            if(!!!gexists) {
              gexists = g as Genealogy
              if(!gexists.save(failOnError:false, flush: true, insert: true, validate: true)) {
                log.warn "'${gexists.errors.fieldError.field}' value '${gexists.errors.fieldError.rejectedValue}' rejected" 
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
          } else {
            summary.errored++
            System.out.print("-")
            log.warn "relationship type is invalid"
          }
          // Remove this soon
          // System.out.println(g as JSON)
        }
        System.out.println("\nGenealogy Import Complete")
        return summary;
      }
      return [ error: "Your data doesn't contain any entries"]
    }
    return [ error: "You must provide Geneological data for the import"]
  }
}
