module org.example.testsitesel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires static lombok;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires dev.failsafe.core;
    requires org.slf4j;
    requires io.github.bonigarcia.webdrivermanager;

    opens org.example.testsitesel to javafx.fxml;
    exports org.example.testsitesel;
    exports org.example.testsitesel.domain;
}