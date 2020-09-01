package ch.bzz.phoneshop.service;

import ch.bzz.phoneshop.data.DataHandler;
import ch.bzz.phoneshop.model.Manufacturer;
import ch.bzz.phoneshop.model.Phones;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * short description
 * <p>
 * Handy_Laden
 *
 * @author Satvik Pisipati
 * @version 1.0
 * @since 14.03.20
 */


@Path("phones")
public class PhonesService{

    /**
     * List phones response.
     *
     * @param userRole the user role
     * @return the response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPhones(
            @CookieParam("userRole") String userRole
    ) {
        Map<String, Phones> phonesMap = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            phonesMap = DataHandler.getPhonesMap();
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity(phonesMap)
                .cookie(cookie)
                .build();
        return response;

    }

    /**
     * Read phone response.
     *
     * @param phoneUUID the phone uuid
     * @param userRole  the user role
     * @return the response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPhone(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String phoneUUID,
            @CookieParam("userRole") String userRole
    ) {
        Phones phone = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            phone = DataHandler.getPhonesMap().get(phoneUUID);

            if (phone != null) {
                httpStatus = 200;
            } else {
                httpStatus = 404;
            }
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity(phone)
                .cookie(cookie)
                .build();
        return response;
    }


    /**
     * Create phone response.
     *
     * @param phones           the phones
     * @param manufacturerUUID the manufacturer uuid
     * @param userRole         the user role
     * @return the response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPhone(
            @Valid @BeanParam Phones phones,
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @FormParam("manufacturerUUID") String manufacturerUUID,

            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            httpStatus = 200;

            phones.setPhoneUUID(UUID.randomUUID().toString());
            if (DataHandler.getManufacturerMap().containsKey(manufacturerUUID)) {
                Manufacturer manufacturer = DataHandler.getManufacturerMap().get(manufacturerUUID);
                phones.setManufacturer(manufacturer);

                Map<String, Phones> phonesMap = DataHandler.getPhonesMap();

                phonesMap.put(phones.getPhoneUUID(), phones);
                DataHandler.writePhones(phonesMap);
            } else {
                httpStatus = 400;
            }
        } else {
            httpStatus = 403;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }


    /**
     * Update phone response.
     *
     * @param phone            the phone
     * @param manufacturerUUID the manufacturer uuid
     * @param userRole         the user role
     * @return the response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePhone(
            @Valid @BeanParam Phones phone,
            @FormParam("manufacturerUUID") String manufacturerUUID,

            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            Map<String, Phones> phonesMap = DataHandler.getPhonesMap();
            if (phonesMap.containsKey(phone.getPhoneUUID())) {
                if (DataHandler.getManufacturerMap().containsKey(manufacturerUUID)) {
                    Manufacturer manufacturer = DataHandler.getManufacturerMap().get(manufacturerUUID);

                    phone.setManufacturer(manufacturer);
                    phonesMap.put(phone.getPhoneUUID(), phone);
                    DataHandler.writePhones(phonesMap);
                    httpStatus = 200;
                } else {
                    httpStatus = 400;
                }
            } else {
                httpStatus = 404;
            }
        } else {
            httpStatus = 403;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }

    /**
     * Delete phone response.
     *
     * @param phoneUUID the phone uuid
     * @param userRole  the user role
     * @return the response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePhone(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("phoneUUID") String phoneUUID,

            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            Map<String, Phones> phonesMap = DataHandler.getPhonesMap();
            Phones phone = phonesMap.get(phoneUUID);
            if (phone != null) {
                httpStatus = 200;
                phonesMap.remove(phoneUUID);
                DataHandler.writePhones(phonesMap);
            } else {
                httpStatus = 404;
            }
        } else {
            httpStatus = 403;
        }
        NewCookie cookie = new NewCookie(
                "userRole",
                userRole,
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();
        return response;
    }
}
