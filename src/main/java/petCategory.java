import jdk.jfr.Category;

import javax.swing.text.html.HTML;
import java.util.ArrayList;

public class petCategory {
    public long id;
    public Category category;
    public String name;
    public ArrayList<String> photoUrls;
    public ArrayList<HTML.Tag> tags;
    public String status;

    public petCategory(long id, Category category, String name, ArrayList<String> photoUrls, ArrayList<HTML.Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<HTML.Tag> getTags() {
        return tags;
    }

    public ArrayList<String> getPhotoUrls() {
        return photoUrls;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }
}
