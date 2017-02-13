package edu.usf.it.PersonRegistry

import edu.usf.it.PersonRegistry.StringMatchEvaluationEnum
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.hibernate.FlushMode
// import com.bloomhealthco.jasypt.GormEncryptedStringType
import org.hibernate.ScrollableResults
import org.hibernate.ScrollMode
import grails.converters.JSON

class Identifier {
    Date dateCreated
    Date lastUpdated    
    String value
    static belongsTo = [ 
        identifierType: IdentifierType, 
        person: Person
    ]
    static hasOne = [ 
        person: Person
    ]
    static hasMany = [
      identifierSources: IdentifierSource
    ]
    static constraints = {
      value(   
        blank: false,
        nullable: false,
        unique: ['identifierType'],
        validator: {val, obj -> 
          return ({
            if(!!obj.identifierType.validator.trim()) {
              return Pattern.compile(obj.identifierType.validator.trim()).matcher(val).matches();
            } else {
              return true
            }                    
          }.call() && {  
            boolean valid = true;
            Identifier.withNewSession { session ->
              session.flushMode = FlushMode.MANUAL
              try {
                // println Identifier.findMatchingIdentifiersWithType(val,obj.identifierType) as JSON
                valid = !!!Identifier.findMatchingIdentifiersWithType(val,obj.identifierType)
              } finally {
                session.setFlushMode(FlushMode.AUTO)
              }
            }
            return valid
          }.call())
        }
      )        
    }
    static mapping = {
        autoTimestamp true
    	  // value type: GormEncryptedStringType
    }
    static List findMatchingIdentifiersWithType(String val, IdentifierType identifierType,String match = "EXACT") {
        StringMatchEvaluationEnum stringMatchEvaluationEnum = StringMatchEvaluationEnum.byName(match)
        List result = []
        Identifier.withNewSession { session ->
            session.flushMode = FlushMode.MANUAL
            try {
                ScrollableResults rs =  Identifier.createCriteria().scroll {
                    eq('identifierType',identifierType)
                }
                while (rs.next()) {
                    def identifier = rs.get()
                    switch(stringMatchEvaluationEnum) {
                        case StringMatchEvaluationEnum.EXACT: 
                            if(val.equalsIgnoreCase(identifier.value.get(0))) result << identifier
                            break
                        case StringMatchEvaluationEnum.STARTSWITH:    
                            if(identifier.value.get(0).toLowerCase().startsWith(val.toLowerCase())) result << identifier
                            break
                        case StringMatchEvaluationEnum.ENDSWITH:                             
                            if(identifier.value.get(0).toLowerCase().endsWith(val.toLowerCase())) result << identifier
                            break
                        case StringMatchEvaluationEnum.CONTAINS:                             
                            if(identifier.value.get(0).toLowerCase().contains(val.toLowerCase())) result << identifier
                            break
                    }
                }
                rs.close()
            } finally {
                session.setFlushMode(FlushMode.AUTO)                
            }
        }
        return result
    }
}
