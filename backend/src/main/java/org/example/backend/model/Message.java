package org.example.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Message {
  @Id
  private String id;
  private String kontaktname;
  private String alter;
  private String kontaktnachricht;

  public String getKontaktname() {
    return kontaktname;
  }

  public void setKontaktname(String kontaktname) {
    this.kontaktname = kontaktname;
  }

  public String getAlter() {
    return alter;
  }

  public void setAlter(String alter) {
    this.alter = alter;
  }

  public String getKontaktnachricht() {
    return kontaktnachricht;
  }

  public void setKontaktnachricht(String kontaktnachricht) {
    this.kontaktnachricht = kontaktnachricht;
  }
}