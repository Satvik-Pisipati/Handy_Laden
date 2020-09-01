package ch.bzz.phoneshop.service;

import ch.bzz.phoneshop.data.DataHandler;
import ch.bzz.phoneshop.model.Manufacturer;

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
 * @since 23.04.20
 */
@Path("manufacturer")
public class ManufacturerService {

    /**
     * List manufacturer response.
     *
     * @param userRole the user role
     * @return the response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listManufacturer(
            @CookieParam("userRole") String userRole
    ) {
        Map<String, Manufacturer> manufacturerMap = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            httpStatus = 200;
            manufacturerMap = DataHandler.getManufacturerMap();
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
                .entity(manufacturerMap)
                .cookie(cookie)
                .build();
        return response;

    }

    /**
     * Read manufacturer response.
     *
     * @param manufacturerUUID the manufacturer uuid
     * @param userRole         the user role
     * @return the response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readManufacturer(
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
            @QueryParam("uuid") String manufacturerUUID,
            @CookieParam("userRole") String userRole)
    {
        Manufacturer manufacturer = null;
        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else {
            manufacturer = DataHandler.getManufacturerMap().get(manufacturerUUID);

            if (manufacturer != null) {
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
                .entity(manufacturer)
                .cookie(cookie)
                .build();
        return response;

    }

    /**
     * Create manufacturer response.
     *
     * @param manufacturer the manufacturer
     * @param userRole     the user role
     * @return the response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createManufacturer(
            @Valid @BeanParam Manufacturer manufacturer,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            httpStatus = 200;

            manufacturer.setManufacturerUUID(UUID.randomUUID().toString());

            Map<String, Manufacturer> manufacturerMap = DataHandler.getManufacturerMap();

            manufacturerMap.put(manufacturer.getManufacturerUUID(), manufacturer);
            DataHandler.writeManufacturer(manufacturerMap);

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
     * Update manufacturer response.
     *
     * @param manufacturer the manufacturer
     * @param userRole     the user role
     * @return the response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateManufacturer(
            @Valid @BeanParam Manufacturer manufacturer,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;
        if (userRole != null && userRole.equals("admin")) {

            Map<String, Manufacturer> manufacturerMap = DataHandler.getManufacturerMap();
            if (manufacturerMap.containsKey(manufacturer.getManufacturerUUID())) {
                manufacturerMap.put(manufacturer.getManufacturerUUID(), manufacturer);
                DataHandler.writeManufacturer(manufacturerMap);
                httpStatus = 200;
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
