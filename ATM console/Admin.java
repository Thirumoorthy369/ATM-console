import java.util.ArrayList;
import java.util.List;

public class Admin extends Account{
    private  double atmBalance; //admin deposit amt to update atmbal

    public Admin(String username, int password) {
        super(username,password);
    }
}
