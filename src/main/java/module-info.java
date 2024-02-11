module com.servertcp.clientservertcp_withfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.servertcp.clientservertcp_withfx to javafx.fxml;
    exports com.servertcp.clientservertcp_withfx;
}