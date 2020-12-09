package nhung.nguyen.infoage.Adapter;

public class ModelUser {
    public String name, email, phone, image, uid;

    public ModelUser() {
    }

    public ModelUser(String email, String image, String name, String phone,String uid) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
