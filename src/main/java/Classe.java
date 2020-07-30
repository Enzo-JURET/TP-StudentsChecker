import javax.swing.plaf.nimbus.State;
import java.sql.*;

public class Classe {

    // Non obligatoire
    private Integer idClasse;
    private String libelleClasse;
    private String periodeClasse;

    public Integer getIdClasse() {
        return idClasse;
    }

    public String getLibelleClasse() {
        return libelleClasse;
    }

    public String getPeriodeClasse() {
        return periodeClasse;
    }

    public void setIdClasse(Integer idClasse) {
        this.idClasse = idClasse;
    }

    public void setLibelleClasse(String libelleClasse) {
        this.libelleClasse = libelleClasse;
    }

    public void setPeriodeClasse(String periodeClasse) {
        this.periodeClasse = periodeClasse;
    }

    // Constructeur de la Class Classe
    public Classe() {}

    public Connection ConnexionBDD(String url, String user, String password) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    // -------------------- CRUD de la table Classe --------------------
    // Read du CRUD
    public void Select(Connection connection) throws SQLException {
        String query = "select * from tpstudentscheckerdb.classe";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
            System.out.println("Libelle : "+resultSet.getString("libelleClasse")+" | période : "+resultSet.getString("periodeClasse"));
        }
    }

    // Create du CRUD
    public void Create(Connection connection, String libelleClasse, String periodeClasse) throws SQLException {
        String query = "insert into tpstudentscheckerdb.classe (libelleClasse, periodeClasse) values (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, libelleClasse);
        statement.setString(2, periodeClasse);
        statement.executeQuery();
    }

    // Update du CRUD
    public void Update(Connection connection, Integer idClasse, String nomColonne, String valeur) throws SQLException {
        String query = "update tpstudentscheckerdb.classe set "+nomColonne+"='"+valeur+"' where idClasse="+idClasse;
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeQuery();
    }

    // Delete du CRUD
    public void Delete(Connection connection, Integer idClasse) throws SQLException {
        String query = "delete from tpstudentscheckerdb.classe where idClasse='"+idClasse+"'";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.executeQuery();
    }

    public static void main(String[] args) throws SQLException {
        Classe classe = new Classe();
        String url = "jdbc:mariadb://localhost:3306";
        String user = "root";
        String password = "";
        classe.Select(classe.ConnexionBDD(url, user, password));
        System.out.print("\n");
        classe.Create(classe.ConnexionBDD(url, user, password), "RISR2019", "2019-2021");
        classe.Select(classe.ConnexionBDD(url, user, password));
        System.out.print("\n");
        classe.Update(classe.ConnexionBDD(url, user, password), 12, "libelleClasse", "RIE2019");
        classe.Select(classe.ConnexionBDD(url, user, password));
        System.out.print("\n");
        // Je fais le delete avec le libelleClasse pour le moment car c'est chiant de chercher l'id auto-increment
        classe.Delete(classe.ConnexionBDD(url, user, password), 12);
        classe.Select(classe.ConnexionBDD(url, user, password));
    }
}
