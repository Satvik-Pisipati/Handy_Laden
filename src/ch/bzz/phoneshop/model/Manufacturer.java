package ch.bzz.phoneshop.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;

/**
 * short description
 * <p>
 * Handy_Laden
 *
 * @author Satvik Pisipati
 * @version 1.0
 * @since 12.03.20
 */
public class Manufacturer {

    @FormParam("manufacturerUUID")
    private String manufacturerUUID;

    @FormParam("name")
    @NotEmpty
    @Size(min = 2, max = 100)
    private String manufacturer;


    /**
     * Gets manufacturer uuid.
     *
     * @return the manufacturer uuid
     */
    public String getManufacturerUUID() {
        return manufacturerUUID;
    }

    /**
     * Sets manufacturer uuid.
     *
     * @param _manufacturerUUID the manufacturer uuid
     */
    public void setManufacturerUUID(String _manufacturerUUID) {
        this.manufacturerUUID = _manufacturerUUID;
    }

    /**
     * Gets manufacturer.
     *
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets manufacturer.
     *
     * @param _manufacturer the manufacturer
     */
    public void setManufacturer(String _manufacturer) {
        this.manufacturer = _manufacturer;
    }

}
