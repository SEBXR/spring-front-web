package springapp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import springapp.dba.DBAConnection;

public class Produit {
    Integer id;
    String nom;
    String description;
    String image;
    Integer prix_min;
    String proprio;
    Integer statut;

    public Produit() {}

    public Produit(Integer id, String nom, String description, String image, Integer prix_min, String proprio , Integer statut) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;
        this.prix_min = prix_min;
        this.proprio = proprio;
        this.statut = statut;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Integer getPrix_min() {
        return prix_min;
    }

    public String getProprio() {
        return proprio;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrix_min(Integer prix_min) {
        this.prix_min = prix_min;
    }

    public void setProprio(String proprio) {
        this.proprio = proprio;
    }


    public Integer getStatut() {
        return this.statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", prix_min='" + getPrix_min() + "'" +
            ", proprio='" + getProprio() + "'" +
            ", statut='" + getStatut() + "'" +
            "}";
    }
    

    public static ArrayList<Produit> findAllProduitDispo()throws Exception{
        ArrayList<Produit> data = new ArrayList<>();
        Connection con = null;
        Statement state = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            state = con.createStatement();
            rs = state.executeQuery("select * from v_produits where statut=0");
            while(rs.next()){
                Produit p = new Produit(rs.getInt("id"), rs.getString("produit"), rs.getString("descriptions"), rs.getString("image"), rs.getInt("prixminimum"), rs.getString("propriétaire") , rs.getInt("statut"));
                data.add(p);
            }
        } catch (Exception e) {
            throw e;
        }finally{
            rs.close();
            state.close();
            con.close();
        }
        return data;
    }    
    public  Integer getOffreMax()throws Exception{
        Integer data = 0;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBAConnection.connect();
            ps = con.prepareStatement("select idproduit,max(offre) offremax from enchereproduit where idproduit=? group by idproduit");       
            ps.setInt(1,this.getId());
            rs= ps.executeQuery(); 
            while(rs.next()){
                data = rs.getInt("offremax");
            }
        } catch (Exception e) {
            throw e;
        }finally{
            rs.close();
            ps.close();
            con.close();
        }
        return data;        
    }

    public static ArrayList<Produit> rechercheTous(String nomProduit)throws Exception{
        ArrayList<Produit> data = new ArrayList<>();
        Connection con = null;
        Statement state = null;
        ResultSet rs = null;
        String sql = "";
        try{
            con = DBAConnection.connect();
            state = con.createStatement();
            sql="select * from v_produits where produit ilike '%"+nomProduit+"%' and statut = 0";
            rs = state.executeQuery(sql);
            while(rs.next()){
                Produit p = new Produit(rs.getInt("id"), rs.getString("produit"), rs.getString("descriptions"), rs.getString("image"), rs.getInt("prixminimum"), rs.getString("propriétaire") , rs.getInt("statut"));
                data.add(p);
            }
        }catch(Exception e){
            throw e;
        }finally{
            rs.close();
            state.close();
            con.close();
        }
        return data;
    }
}
