package mapmarker.com.mapwithmarker.model;

public class MLocation {
    private int id;
    private String Address;
    private Double lat;
    private Double lng ;

    public MLocation(int id, String address, Double lat, Double lng) {
        this.id = id;
        Address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }


}
