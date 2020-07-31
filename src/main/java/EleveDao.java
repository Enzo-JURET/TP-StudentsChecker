import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EleveDao {
    static Connection con = DataConnection.getConnection();

    public int addEleve(Eleve eleve)throws SQLException{
        String query = "insert into eleve(nomEleve, prenomEleve, dateNaissance, idClasse, mail, mdp) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, eleve.getNomEleve());
        ps.setString(2, eleve.getPrenomEleve());
        ps.setString(3, eleve.getDateNaissance());
        ps.setInt(4, eleve.getIdClasse());
        ps.setString(5, eleve.getMail());
        ps.setString(6, eleve.getMdp());
        int n = ps.executeUpdate();
        return n;
    }

    public void deleteEleve(int id) throws SQLException {
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
            eleve.setMail(rs.getString("mail"));
            eleve.setMdp(rs.getString("mdp"));
            ls.add(eleve);
        }
        return ls;
    }

    public List<Eleve> getElevesByClasse(Integer idClase)
            throws SQLException
    {
        String query = "select * from eleve where idClasse='"+idClase+"'";
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
            eleve.setMail(rs.getString("mail"));
            eleve.setMdp(rs.getString("mdp"));
            ls.add(eleve);
        }
        return ls;
    }

    public void updateEleve(Eleve eleve) throws SQLException {

        String query = "update eleve set nomEleve=?,  prenomEleve= ?, dateNaissance=?, idClasse=?, mail=?, mdp=? where idEleve = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, eleve.getNomEleve());
        ps.setString(2, eleve.getPrenomEleve());
        ps.setString(3, eleve.getDateNaissance());
        ps.setInt(4, eleve.getIdClasse());
        ps.setInt(7, eleve.getIdEleve());
        ps.setString(5, eleve.getMail());
        ps.setString(6, eleve.getMdp());
        ps.executeUpdate();
    }

    public Eleve getEleve(String mail) throws SQLException {

        String query = "select * from eleve where mail= ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, mail);
        Eleve eleve = new Eleve();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            eleve.setIdEleve(rs.getInt("idEleve"));
            eleve.setNomEleve(rs.getString("nomEleve"));
            eleve.setPrenomEleve(rs.getString("prenomEleve"));
            eleve.setDateNaissance(rs.getString("dateNaissance"));
            eleve.setMail(rs.getString("mail"));
            eleve.setMdp(rs.getString("mail"));
            eleve.setIdClasse(rs.getInt("idClasse"));
        }

        if (check == true) {
            return eleve;
        }
        else
            return null;
    }

    public Eleve getEleve(String mail, String mdp) throws SQLException {

        String query = "select * from eleve where mail=? and mdp=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, mail);
        ps.setString(2, mdp);
        Eleve eleve = new Eleve();
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            eleve.setIdEleve(rs.getInt("idEleve"));
            eleve.setNomEleve(rs.getString("nomEleve"));
            eleve.setPrenomEleve(rs.getString("prenomEleve"));
            eleve.setDateNaissance(rs.getString("dateNaissance"));
            eleve.setMail(rs.getString("mail"));
            eleve.setMdp(rs.getString("mdp"));
            eleve.setIdClasse(rs.getInt("idClasse"));
        }

        if (check == true) {
            return eleve;
        }
        else
            return null;
    }
}
