package Models;
public class Budget {
    private String id;
    private int userId;
    private String categoryId;
    private String month; 
    private double limitAmount;

    public Budget() {}

    public Budget(String id, int userId, String categoryId, String month, double limitAmount) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.month = month;
        this.limitAmount = limitAmount;
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
