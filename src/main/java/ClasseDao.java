import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClasseDao {
    static Connection con = DataConnection.getConnection();

    public int addClasse(Classe classe)throws SQLException {
        String query = "insert into classe(libelleClasse, periodeClasse) VALUES (?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, classe.getLibelleClasse());
        ps.setString(2, classe.getPeriodeClasse());
        int n = ps.executeUpdate();
        return n;
    }

    public void deleteClasse(int id) throws SQLException {
        String query = "delete classe where idClasse =?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Classe getClasse(int id) throws SQLException {

        String query = "select * from classe where idClasse= ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        Classe classe = new Classe();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            classe.setIdClasse(rs.getInt("idClasse"));
            classe.setLibelleClasse(rs.getString("libelleClasse"));
            classe.setPeriodeClasse(rs.getString("periodeClasse"));

        }

        if (check == true) {
            return classe;
        }
        else
            return null;
    }

    public List<Classe> getClasses()
            throws SQLException
    {
        String query = "select * from classe";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Classe> ls = new ArrayList();

        while (rs.next()) {
            Classe classe = new Classe();
            classe.setIdClasse(rs.getInt("idClasse"));
            classe.setLibelleClasse(rs.getString("libelleClasse"));
            classe.setPeriodeClasse(rs.getString("periodeClasse"));
            ls.add(classe);
        }
        return ls;
    }

    public void updateClasse(Classe classe) throws SQLException {

        String query = "update classe set libelleClasse=?,  periodeClasse= ? where idClasse = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, classe.getLibelleClasse());
        ps.setString(2, classe.getPeriodeClasse());
        ps.setInt(3, classe.getIdClasse());
        ps.executeUpdate();
    }
}
