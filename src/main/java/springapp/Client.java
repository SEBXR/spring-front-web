package springapp;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import springapp.dba.DBAConnection;

public class Client {
    Integer idClient;
    String nom;
    String prenom;
    String mdp;
    String email;
    float budget;
    float offre;
    int demande;

    public Client() {
    }

    public Client(Integer idClient, String nom, String prenom, String mdp, String email, float budget, float offre,
            int demande) {
        this.idClient = idClient;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
        this.email = email;
        this.budget = budget;
        this.offre = offre;
        this.demande = demande;
    }

    public Integer getIdClient() {
        return this.idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMdp() {
        return this.mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getBudget() {
        return this.budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public float getOffre() {
        return this.offre;
    }

    public void setOffre(float offre) {
        this.offre = offre;
    }

    public int getDemande() {
        return this.demande;
    }

    public void setDemande(int demande) {
        this.demande = demande;
    }

    @Override
    public String toString() {
        return "{" +
                " idClient='" + getIdClient() + "'" +
                ", nom='" + getNom() + "'" +
                ", prenom='" + getPrenom() + "'" +
                ", mdp='" + getMdp() + "'" +
                ", email='" + getEmail() + "'" +
                ", budget='" + getBudget() + "'" +
                ", offre='" + getOffre() + "'" +
                ", demande='" + getDemande() + "'" +
                "}";
    }

    public int Login() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idClient = 0;
        try {
            con = DBAConnection.connect();
            ps = con.prepareStatement("select id from Clients where emailclients = ? and mdpclients = ?");

            ps.setString(1, this.getEmail());
            ps.setString(2, this.getMdp());
            rs = ps.executeQuery();

            while (rs.next()) {
                idClient = rs.getInt("id");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            ps.close();
            con.close();
        }
        return idClient;

    }

    public int RechargerCompte() throws Exception {
        Connection con = null;
        PreparedStatement ps = null;

        int manova = 0;

        try {
            con = DBAConnection.connect();
            ps = con.prepareStatement("update clients set demande = ? where id = ? ");
            ps.setFloat(1, this.getDemande());
            ps.setInt(2, this.getIdClient());

            manova = ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
            con.close();
        }
        return manova;
    }

    public int Inscrisption() throws Exception {
        Connection con = null;
        int mapiditra = 0;
        PreparedStatement ps = null;
        try {
            con = DBAConnection.connect();
            ps = con.prepareStatement(
                    "insert into Clients(nomclients,prenomclients,mdpclients,emailclients, budget) values(?,?,?,?,?)");
            ps.setString(1, this.getNom());
            ps.setString(2, this.getPrenom());
            ps.setString(3, this.getMdp());
            ps.setString(4, this.getEmail());
            ps.setFloat(5, this.getBudget());

            mapiditra = ps.executeUpdate();
        } catch (Exception ex) {
            throw ex;
        } finally {
            ps.close();
            con.close();
        }
        return mapiditra;
    }

    public String findNames() throws Exception {
        String names = null;
        Connection connect = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            connect = DBAConnection.connect();
            state = connect.createStatement();
            rs = state.executeQuery("select nomclients , prenomclients from clients where id=" + this.idClient);
            while (rs.next())
                names = rs.getString("nomclients") + " " + rs.getString("prenomclients");
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            state.close();
            connect.close();
        }
        return names;
    }

    public ArrayList<Produit> findAllProduitDispo() throws Exception {
        ArrayList<Produit> data = new ArrayList<>();
        String sql = "select * from v_produits where statut = 0 and idproprietaire != " + this.idClient;
        Connection con = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            state = con.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Produit p = new Produit(rs.getInt("id"), rs.getString("produit"), rs.getString("descriptions"),
                        rs.getString("image"), rs.getInt("prixminimum"), rs.getString("propriétaire"),
                        rs.getInt("statut"));
                data.add(p);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            state.close();
            con.close();
        }
        return data;
    }

    public ArrayList<Produit> findHistory() throws Exception {
        ArrayList<Produit> data = new ArrayList<>();
        String sql = "select  id , produit , descriptions , image , ep.offre as offre , propriétaire , ep.statut  from v_produits vp  join enchereproduit ep on ep.idproduit = vp.id  where ep.idclient = "
                + this.idClient;
        Connection con = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            state = con.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Produit p = new Produit(rs.getInt("id"), rs.getString("produit"), rs.getString("descriptions"),
                        rs.getString("image"), rs.getInt("offre"), rs.getString("propriétaire"),
                        rs.getInt("statut"));
                data.add(p);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            state.close();
            con.close();
        }
        return data;
    }

    public int DeposerOffre(Produit p, float offre) throws Exception {
        Connection con = null;
        PreparedStatement ps = null;
        int mapiditra = 0;
        if (p.getOffreMax() >= offre)
            throw new Exception("Offre trop basse");
        if (this.findBudget() < offre)
            throw new Exception("Vous n'avez pas assez d'argent");
        try {
            con = DBAConnection.connect();
            ps = con.prepareStatement(
                    "insert into EnchereProduit(idClient,idProduit,offre) values(?,?,?)");
            ps.setInt(1, this.getIdClient());
            ps.setInt(2, p.getId());
            ps.setFloat(3, offre);
            mapiditra = ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
            con.close();
        }
        return mapiditra;
    }

    public float findBudget() throws Exception {
        float budget = 0;
        String sql = "select budget from clients where id=" + this.idClient;
        Connection con = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            state = con.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                budget = rs.getFloat("budget");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            state.close();
            con.close();
        }
        return budget;
    }

    public ArrayList<Produit> recherche(String nom_produit) throws Exception {
        ArrayList<Produit> data = new ArrayList<>();
        String sql = "select * from v_produits where statut = 0 and produit ilike '%" + nom_produit
                + "%' and idproprietaire != " + this.idClient;
        Connection con = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            state = con.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                Produit p = new Produit(rs.getInt("id"), rs.getString("produit"), rs.getString("descriptions"),
                        rs.getString("image"), rs.getInt("prixminimum"), rs.getString("propriétaire"),
                        rs.getInt("statut"));
                data.add(p);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            state.close();
            con.close();
        }
        return data;
    }

    public Integer isAlreadyBid(Produit p) throws Exception {
        Integer last_offer = 0;
        String sql = "select max(offre) as offre_max from v_produits vp join enchereproduit ep on ep.idproduit = vp.id  where ep.idclient = "
                + this.idClient
                + " and vp.id = " + p.getId() + " and vp.statut = 0";
        Connection con = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            state = con.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                last_offer = rs.getInt("offre_max");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            state.close();
            con.close();
        }
        return last_offer;
    }
}
