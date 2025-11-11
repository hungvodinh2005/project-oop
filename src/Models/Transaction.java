
package Models;
import java.util.Date;
public class Transaction {
    private String id;
    private int userId;
    private String categoryId;
    private double money;
    private Date date;
    private String description;

    public Transaction() {}

    public Transaction(String id, int userId, String categoryId, double money, Date date, String description) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.money = money;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double amount) {
        this.money = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
