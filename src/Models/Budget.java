package Models;
public class Budget {
    private int id;
    private int userId;
    private int categoryId;
    private String month; 
    private double limitAmount;

    public Budget() {}

    public Budget(int id, int userId, int categoryId, String month, double limitAmount) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.month = month;
        this.limitAmount = limitAmount;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(double limitAmount) {
        this.limitAmount = limitAmount;
    }
}
