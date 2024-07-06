package com.localWeb.localWeb.models.dto.request;

import com.localWeb.localWeb.models.dto.common.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteOAuthRequest {
    private String name;
    private String surname;
    private PhoneRequest phone;
    // Address fields
    private AddressDTO primaryAddress;
    private AddressDTO secondaryAddress; // Nullable for the second address
}