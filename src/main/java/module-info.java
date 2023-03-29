module it.unipi.mainproject.sushideliveryservice {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires com.google.gson;
    requires spring.security.crypto;
    requires xstream;
    
    opens it.unipi.mainproject.sushideliveryservice.beans to com.google.gson, javafx.base;
    opens it.unipi.mainproject.sushideliveryservice to javafx.fxml;
    exports it.unipi.mainproject.sushideliveryservice;
    exports it.unipi.mainproject.sushideliveryservice.languages to xstream;
}
