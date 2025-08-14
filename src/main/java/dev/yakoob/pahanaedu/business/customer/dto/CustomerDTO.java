package dev.yakoob.pahanaedu.business.customer.dto;

import java.time.LocalDate;

public class CustomerDTO {
    private Integer customerId;
    private String name;
    private String address;
    private String mobileNumber;
    private int unitsConsumed;
    private LocalDate registrationDate;
    private String email;

    private CustomerDTO(Builder builder) {
        this.customerId = builder.customerId;
        this.name = builder.name;
        this.address = builder.address;
        this.mobileNumber = builder.mobileNumber;
        this.unitsConsumed = builder.unitsConsumed;
        this.registrationDate = builder.registrationDate;
        this.email = builder.email;
    }

    public CustomerDTO(Integer customerId, String name, String address, String mobileNumber, int unitsConsumed, LocalDate registrationDate, String email) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.unitsConsumed = unitsConsumed;
        this.registrationDate = registrationDate;
        this.email = email;
    }

    // Getters
    public Integer getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getMobileNumber() { return mobileNumber; }
    public int getUnitsConsumed() { return unitsConsumed; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public String getEmail() { return email; }

    //builder class
    public static class Builder {
        private Integer customerId;
        private String name;
        private String address;
        private String mobileNumber;
        private int unitsConsumed;
        private LocalDate registrationDate;
        private String email;

        public Builder customerId(Integer customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public Builder unitsConsumed(int unitsConsumed) {
            this.unitsConsumed = unitsConsumed;
            return this;
        }

        public Builder registrationDate(LocalDate registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public CustomerDTO build() {
            return new CustomerDTO(this);
        }
    }
}
