package ch.bzz.phoneshop.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;


/**
 * short description
 * <p>
 * Handy_Laden
 *
 * @author Satvik Pisipati
 * @version 1.0
 * @since 12.03.20
 */
public class Phones {

    @FormParam("phoneUUID")
    private String phoneUUID;

    @FormParam("name")
    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    private Manufacturer manufacturer;

    @FormParam("ram")
    @NotEmpty
    @Size(min = 2, max = 50)
    private String ram;

    @FormParam("rom")
    @NotEmpty
    @Size(min = 2, max = 50)
    private String rom;

    @FormParam("price")
    @DecimalMax(value = "9999.95")
    @DecimalMin(value = "100.00")
    private BigDecimal price;

    /**
     * Gets phone uuid.
     *
     * @return the phone uuid
     */
    public String getPhoneUUID() {
        return phoneUUID;
    }

    /**
     * Sets phone uuid.
     *
     * @param phoneUUID the phone uuid
     */
    public void setPhoneUUID(String phoneUUID) {
        this.phoneUUID = phoneUUID;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets manufacturer.
     *
     * @return the manufacturer
     */
    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets manufacturer.
     *
     * @param manufacturer the manufacturer
     */
    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Gets ram.
     *
     * @return the ram
     */
    public String getRam() {
        return ram;
    }

    /**
     * Sets ram.
     *
     * @param ram the ram
     */
    public void setRam(String ram) {
        this.ram = ram;
    }

    /**
     * Gets rom.
     *
     * @return the rom
     */
    public String getRom() {
        return rom;
    }

    /**
     * Sets rom.
     *
     * @param rom the rom
     */
    public void setRom(String rom) {
        this.rom = rom;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}


