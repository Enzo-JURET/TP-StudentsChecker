import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EleveDao {
    static Connection con = DataConnection.getConnection();

    public int add(Eleve eleve)throws SQLException{
        String query = "insert into eleve(nomEleve, prenomEleve, dateNaissance, idClasse) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, eleve.getNomEleve());
        ps.setString(2, eleve.getPrenomEleve());
        ps.setString(3, eleve.getDateNaissance());
        ps.setInt(4, eleve.getIdClasse());
        int n = ps.executeUpdate();
        return n;
    }

    public void delete(int id) throws SQLException {
        String query = "delete from eleve where idEleve =?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Eleve getEleve(int id) throws SQLException {

        String query = "select * from eleve where idEleve= ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, id);
        Eleve eleve = new Eleve();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            eleve.setIdEleve(rs.getInt("idEleve"));
            eleve.setNomEleve(rs.getString("nomEleve"));
            eleve.setPrenomEleve(rs.getString("prenomEleve"));
            eleve.setDateNaissance(rs.getString("dateNaissance"));
            eleve.setIdClasse(rs.getInt("idClasse"));
        }

        if (check == true) {
            return eleve;
        }
        else
            return null;
    }

    public List<Eleve> getEleves()
            throws SQLException
    {
        String query = "select * from eleve";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        List<Eleve> ls = new ArrayList();

        while (rs.next()) {
            Eleve eleve = new Eleve();
            eleve.setIdEleve(rs.getInt("idEleve"));
            eleve.setNomEleve(rs.getString("nomEleve"));
            eleve.setPrenomEleve(rs.getString("prenomEleve"));
            eleve.setDateNaissance(rs.getString("dateNaissance"));
            eleve.setIdClasse(rs.getInt("idClasse"));
            ls.add(eleve);
        }
        return ls;
    }

    public void update(Eleve eleve) throws SQLException {

        String query = "update eleve set nomEleve=?,  prenomEleve= ?, dateNaissance=?, idClasse=? where idEleve = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, eleve.getNomEleve());
        ps.setString(2, eleve.getPrenomEleve());
        ps.setString(3, eleve.getDateNaissance());
        ps.setInt(4, eleve.getIdClasse());
        ps.setInt(5, eleve.getIdEleve());
        ps.executeUpdate();
    }
}