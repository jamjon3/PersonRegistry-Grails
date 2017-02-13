/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.usf.it.PersonRegistry

/**
 *
 * @author james
 */
enum StringMatchEvaluationEnum {
    EXACT,STARTSWITH,ENDSWITH,CONTAINS;
    public static StringMatchEvaluationEnum byName(String str) {
        for (stringMatchEvaluationEnum in StringMatchEvaluationEnum.values()) {
            if(str.trim().toUpperCase().equalsIgnoreCase(stringMatchEvaluationEnum.name())) {
                return stringMatchEvaluationEnum;
            }
        }
        return StringMatchEvaluationEnum.EXACT;            
    }   		
}

