
package Models;
import java.util.Date;
public class Transaction {
    private int id;
    private int userId;
    private int categoryId;
    private double money;
    private Date date;
    private String description;

    public Transaction() {}

    public Transaction(int id, int userId, int categoryId, double money, Date date, String description) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.money = money;
        this.date = date;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public double getAmount() {
        return money;
    }

    public void setAmount(double amount) {
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
