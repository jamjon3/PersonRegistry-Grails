package edu.usf.it.PersonRegistry

class Biodemographic {
    Date dateCreated
    Date lastUpdated
    Date birthDate
    Date deathDate
    static belongsTo = [
      person: Person,
      source: Source,
      gender: Gender
    ]
    static hasOne = [ 
      person: Person,
      source: Source,
      gender: Gender
    ]
    static mapping = {
      autoTimestamp true
    }
    static constraints = {
      person(unique: ['source'])
      birthDate(nullable:true)
      deathDate(nullable:true)
      gender(nullable:false,blank:false)        
    }
}
