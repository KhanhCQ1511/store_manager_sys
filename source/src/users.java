package source.src;

public class users {
    private int users_id;
    private String users_username;
    private String users_password;
    private String users_fullname;
    private String users_phone;
    private String users_address;
    private String users_description;

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getUsers_username() {
        return users_username;
    }

    public void setUsers_username(String users_username) {
        this.users_username = users_username;
    }

    public String getUsers_password() {
        return users_password;
    }

    public void setUsers_password(String users_password) {
        this.users_password = users_password;
    }

    public String getUsers_fullname() {
        return users_fullname;
    }

    public void setUsers_fullname(String users_fullname) {
        this.users_fullname = users_fullname;
    }

    public String getUsers_phone() {
        return users_phone;
    }

    public void setUsers_phone(String users_phone) {
        this.users_phone = users_phone;
    }

    public String getUsers_address() {
        return users_address;
    }

    public void setUsers_address(String users_address) {
        this.users_address = users_address;
    }

    public String getUsers_description() {
        return users_description;
    }

    public void setUsers_description(String users_description) {
        this.users_description = users_description;
    }

    private int administrators;

    public int getAdministrators() {
        return administrators;
    }

    public void setAdministrators(int administrators) {
        this.administrators = administrators;
    }

    @Override
    public String toString() {
        return "users [users_id=" + users_id + ", users_username=" + users_username + ", users_password="
                + users_password + ", users_fullname=" + users_fullname + ", users_phone=" + users_phone
                + ", users_address=" + users_address + ", users_description=" + users_description + "]";
    }

}