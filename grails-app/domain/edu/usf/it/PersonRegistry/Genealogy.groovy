package edu.usf.it.PersonRegistry

class Genealogy {
    Date dateCreated
    Date lastUpdated
    String nickname
    String realname
    String relationship
    static mapping = {
        autoTimestamp true
    }
    static constraints = {
      relationship(inList:['root','multi','match','akin'],blank:false,nullable:false,unique: ['realname', 'nickname'])
      nickname(blank:false,nullable:false,unique: ['realname', 'relationship'])
      realname(blank:false,nullable:false,unique: ['nickname', 'relationship'])        
    }
}
