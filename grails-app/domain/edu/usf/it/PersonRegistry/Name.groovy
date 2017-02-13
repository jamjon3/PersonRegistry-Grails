package edu.usf.it.PersonRegistry

class Name {
    Date dateCreated
    Date lastUpdated
    String prefix = ""
    String first = ""
    String middle = ""
    String last = ""
    String suffix = ""
        
    static belongsTo = [
      person: Person,
      source: Source
    ]
    static hasOne = [ 
      person: Person,
      source: Source
    ]
    static hasMany = [
      nicknames:NickName,
    ]
    static mapping = {
      autoTimestamp true
    }
    static constraints = {
      person(unique: ['source'])
      prefix(nullable:false,blank:true)
      first(nullable:false,blank:true)
      middle(nullable:false,blank:true)
      last(nullable:false,blank:true)
      suffix(nullable:false,blank:true)        
    }
    def beforeInsert() {
      if(!!!prefix) prefix = ""
      if(!!!first) first = ""
      if(!!!middle) middle = ""
      if(!!!last) last = ""
      if(!!!suffix) suffix = ""
    }
    def beforeUpdate() {
      if(!!!prefix) prefix = ""
      if(!!!first) first = ""
      if(!!!middle) middle = ""
      if(!!!last) last = ""
      if(!!!suffix) suffix = ""
    }    
}
