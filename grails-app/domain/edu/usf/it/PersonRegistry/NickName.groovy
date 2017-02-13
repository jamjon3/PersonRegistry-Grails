package edu.usf.it.PersonRegistry

class NickName {
    Date dateCreated
    Date lastUpdated
    String nickname
    static belongsTo = [
      name: Name
    ]
    static hasOne = [ 
      name: Name
    ]
    static mapping = {
      autoTimestamp true
    }
    static constraints = {
      name(unique: ['nickname'])
      nickname(blank: false,nullable: false,size: 1..255)
    }
}
