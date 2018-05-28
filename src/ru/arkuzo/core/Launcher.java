package ru.arkuzo.core;

import ru.arkuzo.DatabaseHandlers.EventWriter;
import ru.arkuzo.Factories.BoardIntefaceFactory;
import ru.arkuzo.Factories.SensorFactory;
import ru.arkuzo.Factories.TransportFactory;
import ru.arkuzo.Servers.CommandLineListener;
import ru.arkuzo.Servers.TransportServer;

import java.io.IOException;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.*;

public class Launcher implements Serializable { 
    private static final String DB_PROPERTIES_FILE="/etc/homeserver/db.properties";
    private static final String SERVER_PROPERTIES_FILE="/etc/homeserver/server.properties";
    private static final String DRIVER_NAME = "org.postgresql.Driver";
    private static int transportPort;
    private static int applicationPort;
    private static String dbAddress;
    private static String dbUser;
    private static String dbPassword;
    private static boolean propertiesWriteFlag = false;
    private static ExecutorService exService = Executors.newCachedThreadPool();
    private static TransportServer tServer;
    private static Connection conn;
    private static CommandLineListener keyboardInput;
    
    /**
     * @param args
     * @args --transportPort        configuring the transport port for server to listen
     * @args --applicationPort      configuring the application port for server to listen
     * @args --dbAddress            configuring database address
     * @args --dbUser               configuring database user
     * @args --dbPassword           configuring database password
     * @args --remember             write configuration to file
     */
    public static void main  (String[] args) throws Exception {
        initializeProperties(args);
        startDbConnection();
        initFactories();
        keyboardInput=CommandLineListener.getListener();
        tServer=TransportServer.getServer();
        exService.submit(tServer);
        exService.submit(keyboardInput);
        
    }
    
    private static void initializeProperties(String[] args) throws IOException {
        try {
            getDbProperty();
        } catch (IOException ex) {
            System.err.println("Database properties reading error.\r\n"
                    + "Check file \"db.properties\"");
            throw ex;
        }
        try {
            getPortProperty();
        } catch (IOException ex) {
            System.err.println("Server properties reading error.\r\n"
                    + "Check file \"server.properties\"");
            ex.printStackTrace();
            return;
        }
        parseArgs(args);
        if(propertiesWriteFlag){
            try {
                setDbProperty();
            } catch (IOException ex) {
                System.err.println("Database properties writing error.\r\n"
                    + "Check file \"db.properties\"");
                throw ex;
            }
            try{
                setPortProperty();
            } catch (IOException ex) {
                System.err.println("Database properties writing error.\r\n"
                    + "Check file \"db.properties\"");
                throw ex;
            }
        }    
    }
    
     private static void getDbProperty() throws FileNotFoundException, IOException{
        Properties prop = new Properties();
        try(FileInputStream fileInputStream = new FileInputStream(DB_PROPERTIES_FILE)){
            prop.load(fileInputStream);
            dbAddress=prop.getProperty("dbAddressString");
            dbUser=prop.getProperty("dbUser");
            dbPassword=prop.getProperty("dbPassword");
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }
    }
     
    private static void getPortProperty() throws FileNotFoundException, IOException{
        Properties prop = new Properties();
        transportPort=-1;
        try(FileInputStream fileInputStream = new FileInputStream(SERVER_PROPERTIES_FILE)){
            prop.load(fileInputStream);
            transportPort=Integer.valueOf(prop.getProperty("transportPort","5433"));
            applicationPort=Integer.valueOf(prop.getProperty("applicationPort","5434"));
        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        }
    }
    
    private static void parseArgs(String[] args) {
        for(int i=0;i<args.length;i++){
            switch(args[i]){
                case "--transportPort":
                    transportPort=Integer.valueOf(args[i+1]); i++; break;
                case "--applicationPort":
                    applicationPort=Integer.valueOf(args[i+1]); i++; break;
                case "--dbAddress":
                    dbAddress=args[i+1]; i++; break;
                case "--dbUser":
                    dbUser=args[i+1]; i++; break;
                case "--dbPassword":
                    dbPassword=args[i+1]; i++; break;
                case "--remember":
                    propertiesWriteFlag=true; break;
                default:
                    throw new IllegalArgumentException("Unknown command line argument: "+args[i]);
            }
        }
    }
    
    private static void setDbProperty() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("dbUser", dbUser);
        prop.setProperty("dbPassword", dbPassword);
        prop.setProperty("dbAddress", dbAddress);
        FileOutputStream fos = new FileOutputStream(DB_PROPERTIES_FILE);
        prop.store(fos, "Database properites");
    }

    private static void setPortProperty() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        prop.setProperty("applicationPort", String.valueOf(applicationPort));
        prop.setProperty("transportPort", String.valueOf(transportPort));
        FileOutputStream fos = new FileOutputStream(SERVER_PROPERTIES_FILE);
        prop.store(fos, "Server properites");
    }

    /**
     * @return the transportPort
     */
    public static int getTransportPort() {
        return transportPort;
    }

    /**
     * @return the applicationPort
     */
    public static int getApplicationPort() {
        return applicationPort;
    }

    private static void startDbConnection() throws Exception {
        Class.forName(DRIVER_NAME);
        conn = DriverManager.getConnection(dbAddress, dbUser, dbPassword);
        System.out.println("Database initialized "+conn.getCatalog());
        EventWriter.write("Connection with db established");
    }

    public static Connection getDbConnection() {
        return conn;
    }

    private static void initFactories() throws SQLException, IOException {
        TransportFactory.init();
        BoardIntefaceFactory.init();
        SensorFactory.init();
    }

    public static void stop() {
        EventWriter.write("Server shutdown initialised");
        exService.shutdown();
        while(!exService.isTerminated());
        EventWriter.write("Server terminated");
    }
    
}