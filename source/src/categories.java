package source.src;



public class categories {
    private int categories_id;
    private String categories_name;
    private String categories_description;
    public int getCategories_id() {
        return categories_id;
    }
    public void setCategories_id(int categories_id) {
        this.categories_id = categories_id;
    }
    public String getCategories_name() {
        return categories_name;
    }
    public void setCategories_name(String categories_name) {
        this.categories_name = categories_name;
    }
    public String getCategories_description() {
        return categories_description;
    }
    public void setCategories_description(String categories_description) {
        this.categories_description = categories_description;
    }
    @Override
    public String toString() {
        return "categories [categories_id=" + categories_id + ", categories_name=" + categories_name
                + ", categories_description=" + categories_description + "]";
    }
    
}
