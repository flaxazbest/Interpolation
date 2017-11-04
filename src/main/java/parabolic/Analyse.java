package parabolic;

import javafx.beans.property.SimpleStringProperty;

public class Analyse {

    private String x;
    private String f;
    private String phi;
    private String diff;
    private String error;

    public Analyse(String x, String f, String phi, String diff, String error) {
        this.x = x;
        this.f = f;
        this.phi = phi;
        this.diff = diff;
        this.error = error;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getPhi() {
        return phi;
    }

    public void setPhi(String phi) {
        this.phi = phi;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
