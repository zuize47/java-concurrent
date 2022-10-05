package hoangnd.learn.java;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
 
class User {
    String userId;
 
    public User(String userId) {
        this.userId = userId;
    }
}
 
class UserService {
    public static User getUserDetails(String userId) {
        return new User(userId);
    }
}
 
class CreditRatingService {
    public static Double getCreditRating(User user) {
        return Double.parseDouble(user.userId);
    }
}
 
class ApiUtil {
    public static CompletableFuture<User> getUsersDetail(String userId) {
        return CompletableFuture.supplyAsync(() -> {
            for (var i = 1; i < 100; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("getUsersDetail:" + i);
            }
            return UserService.getUserDetails(userId);
        });
    }
 
    public static CompletableFuture<Double> getCreditRating(User user) {
        return CompletableFuture.supplyAsync(() -> {
            for (var i = 1; i < 5; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("getCreditRating:" + i);
            }
            return CreditRatingService.getCreditRating(user);
        });
    }
}
 
public class CompletableFuture7 {
 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // Using thenCompose()
        CompletableFuture<Double> flattened = ApiUtil.getUsersDetail("1")
                .thenCompose(user -> ApiUtil.getCreditRating(user));
        System.out.println(flattened.get()); // 1.0
    }
}