package edu.usf.it.PersonRegistry

class Person {
    Date dateCreated
    Date lastUpdated 
    UUID id
    Source primaryNameSource
    Source primaryBiodemographicSource
    Source primarySource
    boolean reserved = false
    
    static hasMany = [
      identifiers:Identifier,
      names:Name,
      biodemographics:Biodemographic
    ]
    static mapping = {
      id generator: 'uuid2', type: 'uuid-char', length: 36
      autoTimestamp true
    }
    static constraints = {
      primaryNameSource(nullable: true)
      primarySource(nullable: true)
      primaryBiodemographicSource(nullable: true)
      reserved(nullable: false)
    }
}
