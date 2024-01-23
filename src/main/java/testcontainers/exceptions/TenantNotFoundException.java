package testcontainers.exceptions;

public class TenantNotFoundException extends RuntimeException {

  public TenantNotFoundException(String message) {
    super(message);
  }
}
