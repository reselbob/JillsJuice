package jillsjuice.model;

import java.util.UUID;
import org.json.JSONObject;

public class Customer {
  private final UUID id;
  private final String firstName;
  private final String lastName;
  private final String email;
  private final String phone;
  private final Address address;

  public Customer(
      UUID id, String firstName, String lastName, String email, String phone, Address address) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
    this.address = address;
  }

  public UUID getId() {
    return this.id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public Address getAddress() {
    return address;
  }

  @Override
  public String toString() {
    return new JSONObject(this).toString();
  }
}
