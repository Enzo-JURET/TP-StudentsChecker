import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    static Connection con = DataConnection.getConnection();

    public boolean connection(String id, String mdp) throws SQLException {

        String query = "select * from admin where mailAdmin= ? and mdpAdmin=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, mdp);
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
        }

        if(check)

        return check;
    }

}
