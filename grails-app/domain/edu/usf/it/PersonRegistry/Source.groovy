package edu.usf.it.PersonRegistry

class Source {
    Date dateCreated
    Date lastUpdated 
    String code
    String description
    static hasMany = [
      names: Name,
      biodemographics: Biodemographic,
      identifierSources: IdentifierSource,
      identifierTypes: IdentifierType
    ]
    static mapping = {
      autoTimestamp true
    }
    static constraints = {
      code(   
        blank: false,
        nullable: false,
        size: 3..255,
        unique: true,
        //Custom constraint - only allow upper, lower, digits, dash and underscore
        validator: { val, obj -> val ==~ /[A-Za-z0-9_.-]+/ }
      )
      description(blank: true,nullable: true)
    }
    
}
