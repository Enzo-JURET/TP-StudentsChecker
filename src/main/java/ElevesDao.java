import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ElevesDao {
    static Connection con = DataConnection.getConnection();

    public int add(Eleves eleves)throws SQLException{
        String query = "insert into eleve(nomEleve, prenomEleve, dateNaissance, idClasse) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, eleves.getNomEleve());
        ps.setString(2, eleves.getPrenomEleve());
        ps.setString(3, eleves.getDateNaissance());
        ps.setInt(4, eleves.getIdClasse());
        int n = ps.executeUpdate();
        return n;
    }

    public void delete(int id) throws SQLException {
        String query = "delete from eleve where idEleve =?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Eleves getEleve(int id) throws SQLException {

        String query = "select * from eleve where idEleve= ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        Eleves eleves = new Eleves();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            eleves.setIdEleve(rs.getInt("idEleve"));
            eleves.setNomEleve(rs.getString("nomEleve"));
            eleves.setPrenomEleve(rs.getString("prenomEleve"));
            eleves.setDateNaissance(rs.getString("dateNaissance"));
            eleves.setIdClasse(rs.getInt("idClasse"));
        }

        if (check == true) {
            return eleves;
        }
        else
            return null;
    }

    public List<Eleves> getEleves()
            throws SQLException
    {
        String query = "select * from eleve";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Eleves> ls = new ArrayList();

        while (rs.next()) {
            Eleves eleves = new Eleves();
            eleves.setIdEleve(rs.getInt("idEleve"));
            eleves.setNomEleve(rs.getString("nomEleve"));
            eleves.setPrenomEleve(rs.getString("prenomEleve"));
            eleves.setDateNaissance(rs.getString("dateNaissance"));
            eleves.setIdClasse(rs.getInt("idClasse"));
            ls.add(eleves);
        }
        return ls;
    }

    public void update(Eleves eleves) throws SQLException {

        String query = "update eleve set nomEleve=?,  prenomEleve= ?, dateNaissance=?, idClasse=? where idEleve = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, eleves.getNomEleve());
        ps.setString(2, eleves.getPrenomEleve());
        ps.setString(3, eleves.getDateNaissance());
        ps.setInt(4, eleves.getIdClasse());
        ps.setInt(5, eleves.getIdEleve());
        ps.executeUpdate();
    }
}
