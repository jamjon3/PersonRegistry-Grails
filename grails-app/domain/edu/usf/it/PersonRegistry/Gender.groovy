package edu.usf.it.PersonRegistry

class Gender {
  Date dateCreated
  Date lastUpdated
  String code
  String description
  static hasMany = [ 
    biodemographics: Biodemographic
  ]
  static mapping = {
    autoTimestamp true
  }
  static constraints = {
    code(nullable:false,blank:false,size: 1..32,unique: true)
  }
}
