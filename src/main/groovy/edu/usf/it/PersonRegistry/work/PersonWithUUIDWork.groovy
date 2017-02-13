package edu.usf.it.PersonRegistry.work

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import org.hibernate.jdbc.Work

class PersonWithUUIDWork implements Work {
  def data
  
  PersonWithUUIDWork(data) {
    this.data = data
  }
  public void execute(Connection connection) throws SQLException {
    PreparedStatement checkExists = connection.prepareStatement("select count(*) as `exists` from person where id=?")
    PreparedStatement addPerson = connection.prepareStatement("insert into person (id,reserved,version,date_created,last_updated) values (?,?,?,?,?)")
    def summary = [
      existing: 0,
      added: 0,
      errored: 0
    ]
    data.persons.each { p ->
      checkExists.setString(1, p.id)
      ResultSet r = checkExists.executeQuery()
      r.next()
      int count = r.getInt("exists")
      r.close()
      
      if(count < 1) {
        System.out.print("+")
        addPerson.setString(1, p.id)
        addPerson.setBoolean(2, p.reserved)
        addPerson.setInt(3,1)
        addPerson.setDate(4, new java.sql.Date(new java.util.Date().getTime()))
        addPerson.setDate(5, new java.sql.Date(new java.util.Date().getTime()))
        addPerson.executeUpdate()
        summary.added++
      } else {
        summary.existing++
        System.out.print(".")
      }
    }
    System.out.println("\nExisting Person Existing Entries: " + summary.existing)   
    System.out.println("Existing Person Added Entries: " + summary.added)   
    System.out.println("Existing Person Errored Entries: " + summary.errored)   

    releaseQuietly(checkExists)
    releaseQuietly(addPerson)
  }
  
  private void releaseQuietly(PreparedStatement statement) {
    if ( statement == null ) {
      return;
    }
    try {
      statement.close();
    }
    catch ( SQLException e ) {
      // ignore
    }
  }
  
  
}
