package edu.usf.it.PersonRegistry

class IdentifierSource {
    Date dateCreated
    Date lastUpdated 
    static belongsTo = [
      identifier: Identifier,
      source: Source
    ]
    static mapping = {
      autoTimestamp true
    }
    static hasOne = [ 
      identifier: Identifier,
      source: Source
    ]
    static constraints = {
      identifier(unique: ['source'])
    }
}
