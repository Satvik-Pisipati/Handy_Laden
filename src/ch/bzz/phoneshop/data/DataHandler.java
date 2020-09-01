package ch.bzz.phoneshop.data;

import ch.bzz.phoneshop.model.Phones;
import ch.bzz.phoneshop.model.Manufacturer;
import ch.bzz.phoneshop.model.User;
import ch.bzz.phoneshop.service.Config;

import java.io.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Data handler.
 *
 * @author Satvik Pisipati
 */
public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Phones> phonesMap = new HashMap<>();
    private static Map<String, Manufacturer> manufacturerMap = new HashMap<>();

    private DataHandler() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DataHandler getInstance() {
        return DataHandler.instance;
    }

    /**
     * Read phones.
     */
    public static void readPhones() {

        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            String phonesPath = Config.getProperty("phonesFile");
            fileReader = new FileReader(phonesPath);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }
        try {
            String line;
            Phones phone = null;
            while ((line = bufferedReader.readLine()) != null) {
                phone = new Phones();
                String[] values = line.split(";");
                phone.setPhoneUUID(values[0]);
                phone.setName(values[1]);
                phone.setRam(values[2]);
                phone.setRom(values[3]);
                phone.setPrice(new BigDecimal(values[4]));

                Manufacturer manufacturer = getManufacturerMap().get(values[5]);
                phone.setManufacturer(manufacturer);

                phonesMap.put(values[0], phone);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    /**
     * Read manufacturer.
     */
    public static void readManufacturer() {
        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            String manufacturerPath = Config.getProperty("manufacturerFile");
            fileReader = new FileReader(manufacturerPath);
            bufferedReader = new BufferedReader(fileReader);

        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }
        try {
            String line;
            Manufacturer manufacturer = null;
            while ((line = bufferedReader.readLine()) != null) {
                manufacturer = new Manufacturer();
                String[] values = line.split(";");
                manufacturer.setManufacturerUUID(values[0]);
                manufacturer.setManufacturer(values[1]);

                manufacturerMap.put(values[0], manufacturer);
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                throw new RuntimeException();
            }
        }
    }

    /**
     * Write phones.
     *
     * @param phonesMap the phones map
     */
    public static void writePhones(Map<String, Phones> phonesMap) {
        Writer writer = null;
        FileOutputStream fileOutputStream = null;
        try {
            String path = Config.getProperty("phonesFile");
            fileOutputStream = new FileOutputStream(path);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "utf-8"));
            for (Map.Entry<String, Phones> phonesEntry : phonesMap.entrySet()) {
                Phones phones = phonesEntry.getValue();
                String contents = String.join(";",
                        phones.getPhoneUUID(),
                        phones.getName(),
                        phones.getRam(),
                        phones.getRom(),
                        phones.getPrice().toString(),
                        phones.getManufacturer().getManufacturerUUID()
                );
                writer.write(contents + '\n');
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Write manufacturer.
     *
     * @param manufacturerMap the manufacturer map
     */
    public static void writeManufacturer(Map<String, Manufacturer> manufacturerMap) {
        Writer writer = null;
        FileOutputStream fileOutputStream = null;
        try {
            String path = Config.getProperty("manufacturerFile");
            fileOutputStream = new FileOutputStream(path);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "utf-8"));
            for (Map.Entry<String, Manufacturer> manufacturerEntry : manufacturerMap.entrySet()) {
                Manufacturer manufacturer = manufacturerEntry.getValue();
                String contents = String.join(";",
                        manufacturer.getManufacturerUUID(),
                        manufacturer.getManufacturer()
                );
                writer.write(contents + '\n');
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Gets phones map.
     *
     * @return the phones map
     */
    public static Map<String, Phones> getPhonesMap() {
        if (phonesMap.isEmpty()) {
            readPhones();
        }
        return phonesMap;
    }

    /**
     * Sets phones map.
     *
     * @param phonesMap the phones map
     */
    public static void setPhonesMap(Map<String, Phones> phonesMap) {
        DataHandler.phonesMap = phonesMap;
    }

    /**
     * Gets manufacturer map.
     *
     * @return the manufacturer map
     */
    public static Map<String, Manufacturer> getManufacturerMap() {
        if (manufacturerMap.isEmpty()) {
            readManufacturer();
        }
        return manufacturerMap;
    }

    /**
     * Sets manufacturer map.
     *
     * @param manufacturerMap the manufacturer map
     */
    public static void setManufacturerMap(Map<String, Manufacturer> manufacturerMap) {
        DataHandler.manufacturerMap = manufacturerMap;
    }

    /**
     * Read user user.
     *
     * @param username the username
     * @param password the password
     * @return the user
     */
    public static User readUser(String username, String password) {
        BufferedReader bufferedReader;
        FileReader fileReader;
        try {
            String path = Config.getProperty("userFile");
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
        } catch (FileNotFoundException fileEx) {
            fileEx.printStackTrace();
            throw new RuntimeException();
        }
        User user = new User();
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null &&
                    user.getUserRole().equals("guest")) {
                String[] values = line.split(";");
                if (username.equals(values[0]) &&
                        password.equals(values[3])) {
                    user.setUsername(values[0]);
                    user.setUserRole(values[2]);
                }
            }
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            throw new RuntimeException();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException ioEx) {
                ioEx.printStackTrace();
                throw new RuntimeException();
            }
        }
        return user;

    }
}
