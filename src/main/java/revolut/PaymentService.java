package revolut;

public class PaymentService {
    private String serviceName;
    private String serviceStatus;

    public PaymentService(String name){
        this.serviceName = name;
        this.serviceStatus = "Pass";
    }

    public String getType() {
        return serviceName;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
