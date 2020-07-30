import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Maintest {

    public static void main(String[] args)throws SQLException{

        Eleves eleves = new Eleves();
        eleves.setPrenomEleve("ayman");
        eleves.setNomEleve("agharbi");
        eleves.setDateNaissance("1998-05-14");
        eleves.setIdClasse(1);
        ElevesDao alevesDao = new ElevesDao();

        // add
        alevesDao.add(eleves);

        // read
        Eleves ele = alevesDao.getEleve(1);
        System.out.println(ele.toString());

        // read All
        List<Eleves> ls = alevesDao.getEleves();
        for (Eleves allele : ls) {
            System.out.println(allele);
        }

        // update
        Eleves tempEleve = alevesDao.getEleve(1);
        tempEleve.setNomEleve("Asgard");
        alevesDao.update(tempEleve);

        // delete
        alevesDao.delete(1);
    }
}
